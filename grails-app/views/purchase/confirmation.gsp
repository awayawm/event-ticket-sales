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
        <div class="col-md-8 order-md-1">

                <g:if test="${flash.message}">
                    <p>
                    <div class="alert ${flash.class}">
                        ${flash.message}
                    </div>
                    </p>
                </g:if>

        <form action="/purchase/processPayment" id="purchase-confirmation-form" method="post">

        <div class="row">
        <div class="col-md-6 mb-3">
            <label for="first_name"><h6>First Name</h6></label>
            <input required class="form-control" type="text" name="first_name" id="first_name" placeholder="First Name"/>
        </div>

        <div class="col-md-6 mb-3">
            <label for="last_name"><h6>Last name</h6></label>
            <input required class="form-control" type="text" name="last_name" id="last_name" placeholder="Last Name"/>
        </div>
        </div>

        <div class="py-2">
        <label for="email_address"><h6>Email Address</h6></label>
        <input required class="form-control" type="text" name="email_address" id="email_address" placeholder="Your Email Address"/>
        </div>

        <div class="py-2">
            <label for="phone_number"><h6>Phone Number</h6></label>
            <input required class="form-control" type="text" name="phone_number" id="phone_number" placeholder="###-###-#####"/>
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
              <div>
                  <h6 class="my-0">${item.ticketObject.name}</h6>
                  <small class="text-muted">${item.ticketObject.description}</small>
              </div>
              <span>${item.quantity} @ <g:formatNumber number="${item.ticketObject.price}" type="currency" currencyCode="USD" /></span>
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
        </div>
        </div>
        </div>

<asset:javascript src="client.js"/>
<asset:javascript src="hosted-fields.js"/>

<g:javascript>
      var form = document.querySelector('#purchase-confirmation-form');

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

            hostedFieldsInstance.tokenize(function (tokenizeErr, payload) {
              if (tokenizeErr) {
                console.error(tokenizeErr);
                return;
              }

                $.post("processPayment",
                { nonce: payload.nonce,
                 first_name: document.getElementById("first_name").value,
                 last_name: document.getElementById("last_name").value,
                 email_address: document.getElementById("email_address").value,
                 phone_number: document.getElementById("phone_number").value
                 })
                .done(function(data) {
                    console.log(data)
                })
            });
          }, false);
        });
      });
</g:javascript>

    </body>
</html>