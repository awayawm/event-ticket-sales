<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>event-ticket-sales</title>
    </head>
    <body>

    <div class="container-fluid" style="max-width:960px">
        <div class="py-5 text-center">
        <h2>Get your tickets for ${session.event_name}</h2>
        </div>

        <div class="row">
        <div id="shake-form" class="col-md-8 order-md-1">

            <div id="alert_box"></div>

        <form action="/purchase/processPayment" id="purchase-confirmation-form" method="post">

        <div class="row">
        <div class="col-md-6 mb-3">
            <label for="first_name"><h6>First Name</h6></label>
            <input class="form-control" type="text" name="first_name" id="first_name" placeholder="First Name"/>
        </div>

        <div class="col-md-6 mb-3">
            <label for="last_name"><h6>Last name</h6></label>
            <input class="form-control" type="text" name="last_name" id="last_name" placeholder="Last Name"/>
        </div>
        </div>

        <div class="py-2">
        <label for="email_address"><h6>Email Address</h6></label>
        <input class="form-control" type="text" name="email_address" id="email_address" placeholder="Your Email Address"/>
        </div>

        <div class="py-2">
            <label for="phone_number"><h6>Phone Number</h6></label>
            <input class="form-control" type="text" name="phone_number" id="phone_number" placeholder="###-###-#####"/>
        </div>

        <div class="py-2">
          <label for="card-number"><h6>Card Number</h6></label>
          <div class="form-control" style="height: 40px" id="card-number"></div>
        </div>

        <div class="row">
        <div class="col-md-6 mb-3">
            <div class="py-2">
                <label for="cvv"><h6>CVV</h6></label>
                <div class="form-control" style="height: 40px" id="cvv"></div>
            </div>
        </div>
        <div class="col-md-6 mb-3">
            <div class="py-2">
              <label for="expiration-date"><h6>Expiration Date</h6></label>
              <div class="form-control" style="height: 40px" id="expiration-date"></div>
            </div>
        </div>
        </div>

        </form>
        </div>

        <div class="col-md-4 order-md-2 mb-4">

        <div class="card">
            <div class="card-header">
                <h4 class="text-muted">Your Cart</h4>
            </div>

        <ul class="list-group list-group-flush">

        <g:each in="${itemMapList}" var="item">
            <g:if test="${item.quantity != '0'}">
              <li class="list-group-item d-flex justify-content-between 1h-condensed">
                <span><h6>${item.ticketObject.name}</h6></span>
                ${item.quantity} @ <g:formatNumber number="${item.ticketObject.price}" type="currency" currencyCode="USD" />
              </li>
            </g:if>
        </g:each>

        <li class="list-group-item d-flex justify-content-between 1h-condensed">
         <span><h6>Taxes and Fees</h6></span>
         <g:formatNumber number="${session.totalSurcharge + session.taxes}" type="currency" currencyCode="USD" />
        </li>

        <li class="list-group-item d-flex justify-content-between 1h-condensed">
         <div>
          <span><h6>Total</h6></span>
          </div>
          <strong><g:formatNumber number="${session.totalAfterFeesAndTaxes}" type="currency" currencyCode="USD" /></strong>
        </li>

        </ul>
        </div>

        <hr>
          <button class="btn btn-lg btn-primary btn-block" id="submitPayment" disabled>Purchase Tickets</button>

        <div class="text-center my-3 collapse" id="ticket_spinner">
        <i class="fas fa-spinner fa-spin fa-5x "></i>
        </div>
        </div>
        </div>
        </div>

<asset:javascript src="client.js"/>
<asset:javascript src="hosted-fields.js"/>

<g:javascript>
      var form = document.querySelector('#purchase-confirmation-form');
        var agreementSelected = false

      braintree.client.create({
        authorization: "${clientToken}",
      }, function (clientErr, clientInstance) {
        if (clientErr) {
          console.error(clientErr);
          return;
        }

        // This example shows Hosted Fields, but you can also use this
        // client instance to create additional components here, such as
        // PayPal or Data Collector.

        braintree.hostedFields.create({
          client: clientInstance,
          fields: {
            number: {
              selector: '#card-number',
              placeholder: '4111 1111 1111 1111'
            },
            cvv: {
              selector: '#cvv',
              placeholder: '123'
            },
            expirationDate: {
              selector: '#expiration-date',
              placeholder: '10/2019'
            }
          }
        }, function (hostedFieldsErr, hostedFieldsInstance) {
          if (hostedFieldsErr) {
            console.error(hostedFieldsErr);
            return;
          }

          var button = document.getElementById("submitPayment");
          button.removeAttribute('disabled');

          button.addEventListener('click', function (event) {
            event.preventDefault();

        if(!areFieldsValid()){
            $("#fillOutRequiredFieldsModal").modal('toggle')
        } else {

            if(!agreementSelected) {
                $('#ticket-police-and-information').modal('toggle')
            }

            if(agreementSelected){
                $("#ticket_spinner").addClass("show")
                hostedFieldsInstance.tokenize(function (tokenizeErr, payload) {
                      if (tokenizeErr) {
                        console.error(tokenizeErr);

                        switch(tokenizeErr.code){
                            case "HOSTED_FIELDS_FIELDS_EMPTY":
                                $(fillOutRequiredFieldsModal).modal('toggle')
                                break
                            default:
                                $("#shake-form").animate({left: '-=10px'}, 20);
                                $("#shake-form").animate({left: '+=10px'}, 20);
                                $("#alert_box").animate({opacity: '0'}, 200);
                                $("#alert_box").empty()
                                $("#alert_box").addClass("alert alert-danger")
                                $("#alert_box").animate({opacity: '1'}, 200);
                                $("#alert_box").append("<h3 class=m-3><h4 class='alert-heading'>There was an error processing your payment :(</h4><p> " + tokenizeErr.message + "</p>")
                            break
                        }

                        $("#ticket_spinner").removeClass("show")
                        return
                      }

                        $.post("processPayment",
                        { nonce: payload.nonce,
                         first_name: document.getElementById("first_name").value,
                         last_name: document.getElementById("last_name").value,
                         email_address: document.getElementById("email_address").value,
                         phone_number: document.getElementById("phone_number").value
                         })
                        .done(function(data) {
                            if(data.success) {
                                    console.log(data)
                                    $("#alert_box").empty()
                                    $("#alert_box").removeClass("alert alert-danger")
                                    document.location.href = "/sale/status/" + data.id
                            } else {
                                    $("#shake-form").animate({left: '-=10px'}, 20);
                                    $("#shake-form").animate({left: '+=10px'}, 20);
                                    $("#alert_box").animate({opacity: '0'}, 200);
                                    $("#alert_box").empty()
                                    $("#alert_box").addClass("alert alert-danger")
                                    $("#alert_box").animate({opacity: '1'}, 200);
                                    $("#alert_box").append("<h3 class=m-3><h4 class='alert-heading'>There was an error processing your payment :(</h4><p> " + data.message + "</p>")
                            }
                            $("#ticket_spinner").removeClass("show")
                        })
                });
            }

            }
          }, false);
        });
      });

      var areFieldsValid = function(){
        return $("#first_name").val() && $("#last_name").val() &&
        $("#email_address").val() != "" && $("#phone_number").val()
      }

      var confirmAgreement = function(){
        if($("#agreement").is(':checked')) {
            $("#ticket-police-and-information").modal('toggle')
            agreementSelected = true
            $("#submitPayment").trigger("click")
        }
      }

</g:javascript>

<div class="modal fade" id="fillOutRequiredFieldsModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Some fields are still blank :(</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Please fill out all of the fields before attempting to purchase tickets.
      </div>
      <div class="modal-footer">
        <button onclick="$('#fillOutRequiredFieldsModal').modal('toggle')" type="button" class="btn btn-primary">Okay!</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="ticket-police-and-information" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Ticket Policies & Information</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
<ul>
<li>Reservations are highly recommended.</li>
<li>Tickets may be purchased at the door based on availability.</li>
<li>Tickets may be purchased in advance online or during business hours on the phone at ${config.coordinator_phone_number} or in person.</li>
<li>Box Office hours: Box office will open an hour prior to the doors open day of fight.</li>
</ul>
<p>All reservations must be secured with an credit card and tickets may be picked up at the Box Office the day of the fight. You will need a state issued ID at the box office to pick up Online Tickets.</p>
<p>All sales are final and will be charged at the time the reservation is made. Show times may vary from production to production. For specifics dates and times, please contact us at ${config.coordinator_email}.</p>
<p>For reservations made online, a service fee is added to each ticket.</p>
      </div>
      <div class="form-check">
      <input type='checkbox' onclick="$('#agreementButton').removeAttr('disabled')" name="agreement" id="agreement"/>
      <label class="form-check-label" for="agreement">I have read and understand these policies.</label>
      </div>

      <div class="modal-footer">
        <button disabled onclick="confirmAgreement()" id="agreementButton" type="button" class="btn btn-primary">Okay!</button>
      </div>
    </div>
  </div>
</div>


    </body>
</html>