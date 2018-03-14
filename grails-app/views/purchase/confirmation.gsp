<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>event-ticket-sales</title>
    </head>
    <body>

  <div class="row">
    <div class="col">
    <center>

        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>
        <h1>Purchase Tickets</h1>

          <label class="hosted-fields--label" for="fullName">Full Name</label>
          <div fullname="fullName" class="hosted-field"></div>

          <label class="hosted-fields--label" for="emailAddress">Email Address</label>
          <div id="emailAddress" class="hosted-field"></div>

          <label class="hosted-fields--label" for="phoneNumber">Phone Number</label>
          <div id="phoneNumber" class="hosted-field"></div>

        <div class="demo-frame">
          <form action="/purchase/processPayment" method="post" id="cardForm" >

            <label class="hosted-fields--label" for="fullName">Full Name</label>
            <input type="text" name="fullName" class="hosted-field"></div>

            <label class="hosted-fields--label" for="card-number">Card Number</label>
            <div id="card-number" class="hosted-field"></div>

            <label class="hosted-fields--label" for="expiration-date">Expiration Date</label>
            <div id="expiration-date" class="hosted-field"></div>

            <label class="hosted-fields--label" for="cvv">CVV</label>
            <div id="cvv" class="hosted-field"></div>

            <label class="hosted-fields--label" for="postal-code">Postal Code</label>
            <div id="postal-code" class="hosted-field"></div>

            <div class="button-container">
            <input type="submit" class="button button--small button--green" value="Purchase" id="submit"/>
            </div>
          </form>
        </div>

        <script src="https://js.braintreegateway.com/web/3.31.0/js/client.js"></script>
        <script src="https://js.braintreegateway.com/web/3.31.0/js/hosted-fields.js"></script>


        </div>
        </div>
        </center>
    </body>
</html>