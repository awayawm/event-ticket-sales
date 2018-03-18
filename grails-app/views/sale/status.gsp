<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>event-ticket-sales</title>
    </head>
    <body>

    <div class="row">
        <div class="col">
            <g:if test="${flash.message}">
                <p>
                <div class="alert ${flash.class}">
                    ${flash.message}
                </div>
                </p>
            </g:if>
        </div>
    </div>

    <div class="container-fluid" style="max-width:960px;">


    <div class="clearfix">
      <div class="float-left">
      <h5>Customer Information</h5>
      <h6>${sale.customerName}</h6>
      <h6>${sale.emailAddress}</h6>
      <h6>${sale.phoneNumber}</h6>
      </div>
      <div class="float-right text-right">
      <h5>Event Information</h5>
      <h6>${sale.event.name}</h6>
      <h6>${sale.event.address}</h6>
      <h6>Doors Open: <g:formatDate date="${sale.event.doorsOpen}" type="datetime" style="MEDIUM"/></h6>
      <h6>Fight Starts: <g:formatDate date="${sale.event.eventStarts}" type="datetime" style="MEDIUM"/></h6>
      </div>
    </div>

        <div class="row">
        <div class="col text-center">

        <h1 class="m-5">Thank you for your purchase.</h1>
        <p class="lead mb-4">Your ticket is now being emailed to you.  You can download it here using this link</p>

<table class="table">
  <thead>
    <tr>
      <th class="text-left" scope="col">Name</th>
      <th class="text-left" scope="col">Description</th>
      <th class="text-right" scope="col">Quantity</th>
      <th class="text-right" scope="col">Price</th>
    </tr>
  </thead>
  <tbody>

    <g:each in="${tickets}">
          <tr>
      <th class="text-left">${it.name}</th>
      <td class="text-left">${it.description}</td>
      <td class="text-right">${it.quantity}</td>
      <td class="text-right"><g:formatNumber number="${Double.valueOf(it.price)}" type="currency" currencyCode="USD" /></td>
    </tr>
    </g:each>
          <tr>
      <th></th>
      <td></td>
      <td class="text-right"><strong>Fees and Taxes</strong></td>
      <td class="text-right"><g:formatNumber number="${sale.taxes + sale.totalSurcharge}" type="currency" currencyCode="USD" /></td>
    </tr>
          <tr>
      <th></th>
      <td></td>
      <td class="text-right"><strong>Total</strong></td>
      <td class="text-right"><strong><g:formatNumber number="${sale.totalAfterFeesAndTaxes}" type="currency" currencyCode="USD" /></strong></td>
    </tr>
  </tbody>
</table>


    <div class="clearfix my-5">
      <div class="float-left">
      </div>
      <div class="float-right text-right">
        <h5>Payment Status: <small class="text-muted">${sale.salesStatus.status}</small></h5>
       </div>
    </div>

        </div>
        </div>
    </div>

    </body>
</html>