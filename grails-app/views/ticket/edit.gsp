<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Edit Ticket</title>
    </head>
    <body>

    <p>
    <div class="row">
        <div class="col">
            <g:render template="/shared/adminnav" />
        </div>
    </div>
    </p>

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

    <p>
    <button class="btn btn-primary" onclick="document.location.href='/admin/ticket/index'">Back to Ticket Index</button>
    </p>

        <h2>Edit Ticket</h2>

    <div class="row">
        <div class="col">
            <form action="/admin/ticket/edit" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${id}"/>
                <div class="form-group">
                    <label for="ticket_name"><h5>Ticket Name</h5></label>
                    <input type="text" class="form-control" id="ticket_name" name="ticket_name" value="${ticket_name}" required />
                    <small class="form-text text-muted">The name of the ticket</small>
                </div>
                <div class="form-group">
                    <label for="ticket_description"><h5>Ticket Description</h5></label>
                    <input type="text" class="form-control" id="ticket_description" name="ticket_description" value="${ticket_description}" required />
                    <small class="form-text text-muted">Description of the ticket</small>
                </div>
                <div class="form-group">
                    <label for="ticket_price"><h5>Ticket Price</h5></label>
                    <input type="text" class="form-control" id="ticket_price" name="ticket_price" value="${ticket_price}" required />
                    <small class="form-text text-muted">The price of the ticket with two places after the decimal like 15.50</small>
                </div>
                <div class="form-group">
                    <label for="ticket_price"><h5>Ticket Quantity</h5></label>
                    <input type="text" class="form-control" id="ticket_quantity" name="ticket_quantity" value="${ticket_quantity}" required />
                    <small class="form-text text-muted">Number of tickets available for purchase</small>
                </div>


                <div class="row">
                    <div class="col">
                        <h5>Current Ticket Background Image: <span class="text-muted">${ticketImageName}</span></h5>
                        <img src="data:${ticketImageContentType};base64,${ticketImageBytes.encodeBase64()}" style="max-width: 200px; max-height: 200px;">
                    </div>
                    <div class="col">
                        <h5>Current Ticket Logo: <span class="text-muted">${ticketLogoName}</span></h5>
                        <img src="data:${ticketLogoContentType};base64,${ticketLogoBytes.encodeBase64()}" style="max-width: 200px; max-height: 200px;">
                    </div>
                </div>


                <div class="form-group">
                    <label for="ticket_ticketImage"><h5>Change Ticket Background Image</h5></label>
                    <input type="file" class="form-control-file" id="ticket_ticketImage" name="ticket_ticketImage" />
                    <small class="form-text text-muted">background image of the dynamically rendered ticket.  Try to upload something rectangular</small>
                 </div>
                <div class="form-group">
                    <label for="ticket_ticketLogo"><h5>Change Ticket Logo</h5></label>
                    <input type="file" class="form-control-file" id="ticket_ticketLogo" name="ticket_ticketLogo" />
                    <small class="form-text text-muted">logo that appears on the left side of the ticket.  Try to use something square shaped. </small>
                </div>
                <input type="submit" class="btn btn-primary" value="Create Ticket"/>
            </div>
        </form>
    </div>
    </div>
    </body>
</html>