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
            <g:if test="${events}">
                <g:each in="${events}" var="event">
                        <g:if test="${event.enabled == true}">
                            <div class="col-sm">
                                <center>
                                    <h1>${event.name}</h2>
                                    <p>
                                        <img src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}" style="max-width: 300px; max-height: 300px;">
                                    </p>
                                     <h5>Fights Start: <span class="text-muted"><g:formatDate date="${event.eventStarts}" type="datetime" style="MEDIUM"/></span></h5>
                                    <p>
                                        <h3 class="text-muted">
                                        ${event.description}
                                        </h3>
                                    </p>
                                    <button class="btn btn-primary btn-lg" onclick="document.location.href='/purchase/${event.shortURL}'">Purchase Tickets</button>
                                </center>
                            </div>
                        </g:if>
                </g:each>
            </g:if>
    </div>

    </body>
</html>