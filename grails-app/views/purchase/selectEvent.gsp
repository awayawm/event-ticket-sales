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


      <div class="row">
            <g:if test="${events}">
                <g:each in="${events}" var="event">
                        <g:if test="${event.enabled == true}">
                            <div class="col-sm text-center border rounded m-4">
                                    <h1 class="my-4">${event.name}</h1>
                                    <p>
                                        <img class="img-fluid rounded max-width: 300px; max-height: 300px;" src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}">
                                    </p>
                                     <h5>Fights Start: <span class="text-muted"><g:formatDate date="${event.eventStarts}" type="datetime" style="MEDIUM"/></span></h5>
                                        <p class="lead text-muted">
                                        ${event.description}
                                        </p>
                                    <button class="btn btn-primary btn-lg mb-4" onclick="document.location.href='/purchase/${event.shortURL}'">Purchase Tickets</button>
                            </div>
                        </g:if>
                </g:each>
            </g:if>
            </div>

    </body>
</html>