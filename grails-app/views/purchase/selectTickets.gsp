<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>event-ticket-sales</title>
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
            <form action="/purchase/confirmation" method="post" enctype="multipart/form-data">
            <h1>${event.name}</h2>
            <hr>
             <h5>Fights Start: <span class="text-muted"><g:formatDate date="${event.eventStarts}" type="datetime" style="MEDIUM"/></span></h5>
            <h5>Doors open: <span class="text-muted"><g:formatDate date="${event.doorsOpen}" type="datetime" style="MEDIUM"/></span></h5>
            <hr>
       <g:each in="${event.tickets}" var="ticket">

           <p>
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">${ticket.name}</h5>
<h6 class="card-subtitle mb-2 text-muted">${ticket.description}</h6>

           <h3><g:formatNumber number="${ticket.price}" type="currency" currencyCode="USD" />
            <g:if test="${ticket.quantity <= 10}"><small class="text-danger">${ticket.quantity} Tickets Remaining!</small></g:if>
            </h3>

           <select class="form-control" name="">
               <g:each name="tickets_${ticket.id}" var="num" in="[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]" >
             <option value="${num}">${num} ticket${num == 1 ? "" : "s"}</option>
             </g:each>
           </select>

           </div>
           </div>
           </p>

       </g:each>
<hr>
        <input type="submit" class="btn btn-primary btn-lg" value="Purchase Tickets"/>
        </form>
            </div>
            <div class="col">
                <center>
                    <p>
                        <img class="rounded img-fluid max-width: 700px; max-height: 700px;" src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}">
                    </p>
                    <button class="btn btn-primary btn-lg" onclick="document.location.href='/'">Back to Select Events</button>
                </center>
            </div>
    </div>


    </body>
</html>