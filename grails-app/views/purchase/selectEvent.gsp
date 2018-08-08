<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>${title}</title>
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
                            <div class="col-sm text-center border rounded m-4 shadow-lg bg-gradient-light">
                                    <h1 class="my-4 display-4">${event.name}</h1>
                                        <img class="my-3 shadow-sm img-fluid rounded max-width: 300px; max-height: 300px;" src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}">
                                     <h5>Fights Start @ <span class="text-muted"><g:formatDate date="${event.eventStarts}" format="${dateFormat}"/></span></h5>
                                        <p class="lead text-muted my-3">
                                        ${event.description}
                                        </p>
                                    <button class="bg-secondary btn btn-lg py-3 shadow mb-4 text-white" onclick="document.location.href='/purchase/${event.shortURL}'">Purchase Tickets</button>
                            </div>
                        </g:if>
                </g:each>
            </g:if>
            </div>

    </body>
</html>