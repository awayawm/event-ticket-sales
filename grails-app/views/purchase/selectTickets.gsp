<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
       <title>${title}</title>
    </head>
    <body>

        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>

      <div class="row">
            <div class="col">

        <div class="shadow-lg my-4 mx-2 px-2 py-1 bg-gradient-light rounded">
      <h1 class="mt-1 mb-3 ml-2 display-4">${event.name}</h1>
      <hr>
        <p class="lead ml-3">Doors open: <span class="text-muted"><g:formatDate date="${event.doorsOpen}" format="EEEEE, MMM dd, h:mm a"/></span></p>
        <p class="lead ml-3 pb-2">Fights Start: <span class="text-muted"><g:formatDate date="${event.eventStarts}" format="EEEEE, MMM dd, h:mm a"/></span></p>
       <form id="event-form" action="/purchase/confirmation" method="post" enctype="multipart/form-data">
        </div>

       <g:each in="${event.tickets}" var="ticket">
        <g:if test="${ticket.quantity > 0}">
        <div class="card m-2 bg-gradient-light shadow-lg mt-4 pb-2">
          <div class="card-body">
            <h2 class="card-title">${ticket.name}</h2>
            <div>
<p class="lead text-muted mb-2 float-left">${ticket.description}</p>

           <p class="lead float-right"><g:formatNumber number="${ticket.price}" type="currency" currencyCode="USD" />
            <g:if test="${ticket.quantity <= 10}"><small class="text-danger">${ticket.quantity} Tickets Remaining!</small></g:if>
            </p>
            </div>

           <select class="form-control" name="ticket_${ticket.id}">
               <g:each name="tickets_${ticket.id}" var="num" in="[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]" >
             <option value="${num}">${num} ticket${num == 1 ? "" : "s"}</option>
             </g:each>
           </select>

           </div>
           </div>
            </g:if>
       </g:each>
           </form>

           <button onclick="didCustomerSelectTickets()" class="shadow-lg btn btn-secondary btn-lg py-3 mx-2 my-4" />Purchase Tickets</button>

            </div>
            <div class="col text-center">
                <img class="shadow-lg my-4 rounded img-fluid max-width: 700px; max-height: 700px;" src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}">
                  <button class="shadow-lg my-3 btn btn-secondary btn-lg" onclick="document.location.href='/'">Back to Select Events</button>
            </div>
    </div>

<script>
var didCustomerSelectTickets = function(){

    var sum = 0
    $("option:selected").each(function() {
        sum += $(this).val()
    })

    if(sum > 0){
        $('#event-form').submit()
    } else {
        $('#pleaseSelectTicketsModel').modal('toggle')
    }
}
var returnToTickets = function() {
    $('#pleaseSelectTicketsModel').modal('toggle')
}
</script>

<div class="modal fade" id="pleaseSelectTicketsModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Opps!</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        Please select some tickets before pressing the purchase button
      </div>
      <div class="modal-footer">
        <button onclick="returnToTickets()" type="button" class="btn btn-primary">Okay!</button>
      </div>
    </div>
  </div>
</div>

    </body>
</html>