<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>event-ticket-sales</title>
    </head>
    <body>
  <div class="row">
    <div class="col-md-8 order-md-1">
        <h1>Purchase Tickets</h1>
        </div>
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
        <label for="first_name"><h3>First Name</h3></label>
        <input required class="form-control" type="text" name="first_name" id="first_name" placeholder="First Name"/>
        </div>
        <div class="col-md-6 mb-3">
        <label for="last_name"><h3>Last name</h3></label>
        <input required class="form-control" type="text" name="last_name" id="last_name" placeholder="Last Name"/>
        </div>
        </div>

        <label for="email_address"><h3>Email Address</h3></label>
        <input required class="form-control" type="text" name="email_address" id="email_address" placeholder="Your Email Address"/>

        <label for="phone_number"><h3>Phone Number</h3></label>
        <input required class="form-control" type="text" name="phone_number" id="phone_number" placeholder="###-###-#####"/>

          <label for="card-number"><h3>Card Number</h3></label>
          <div class="form-control" style="height: 40px" id="card-number"></div>

        <div class="row">
        <div class="col-md-6 mb-3">
            <label for="cvv"><h3>CVV</h3></label>
            <div class="form-control" style="height: 40px" id="cvv"></div>
        </div>
        <div class="col-md-6 mb-3">
          <label for="expiration-date"><h3>Expiration Date</h3></label>
          <div class="form-control" style="height: 40px" id="expiration-date"></div>
        </div>
        </div>

            </form>
        </div>

        <div class="col-md-4 order-md-2 mb-4">

        <h3 class="text-muted">Your Cart</h3>

        <div class="list-group">

        <g:each in="${itemMapList}" var="item">
          <button type="button" class="list-group-item list-group-item-action">
          ${item.ticketObject.name} - ${item.quantity} @ ${item.ticketObject.price}
          </button>
        </g:each>

        <button type="button" class="list-group-item list-group-item-action">
        Taxes and Fees
        </button>
        <button type="button" class="list-group-item list-group-item-action">
        Total - <g:formatNumber number="${total}" type="currency" currencyCode="USD" />
        </button>
        <hr>
          <button class="btn btn-lg btn-primary" id="submitPayment" disabled>Purchase Tickets</button>
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
                    console.log("success: " + data)
                })
                .fail(function(data) {
                    console.log("failure: " + data)
                })

            });
          }, false);
        });
      });
</g:javascript>

    </body>
</html>