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

           <h3>$${ticket.price}</h3>

           <select class="form-control" name="">
               <g:each name="tickets_${ticket.id}" var="num" in="[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]" >
             <option value="${num}">${num} ticket${num == 1 ? "" : "s"}</option>
             </g:each>
           </select>

           </div>
           </div>
           </p>

       </g:each>
<hr>
        <button class="btn btn-primary btn-lg" onclick="document.location.href='/purchase/${event.shortURL}'">Purchase Tickets</button>
        </form>
            </div>
            <div class="col">
                <center>
                    <p>
                        <img class="img-fluid" src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}" style="max-width: 700px; max-height: 700px;">
                    </p>
                    <button class="btn btn-primary btn-lg" onclick="document.location.href='/'">Back to Select Events</button>
                </center>
            </div>
    </div>


    </body>
</html>