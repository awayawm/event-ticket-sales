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

    <form action="/" id="my-sample-form" method="post">

      <label for="card-number"><h3>Card Number</h3></label>
      <div class="form-control" style="height: 40px" id="card-number"></div>

      <label for="cvv"><h3>CVV</h3></label>
      <div class="form-control" style="height: 40px" id="cvv"></div>

      <label for="expiration-date"><h3>Expiration Date</h3></label>
      <div class="form-control" style="height: 40px" id="expiration-date"></div>

    <p>
      <input class="btn btn-lg btn-primary" type="submit" value="Pay" disabled />
    </p>

    </form>

        </center>
        </div>
        </div>

    </body>
</html>