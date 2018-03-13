<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Create Ticket</title>
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


    <h2>Create a ticket</h2>
    <p>
    Tickets are associated to events and can be purchased by customers.
    <ul>
    <li>Ticket name: The name of the ticket as it appears as an available ticket for an event</li>
    <li>Ticket description: Description of the ticket that appears under the name of the associate event ticket list</li>
    <li>Ticket price: The price of the ticket</li>
    <li>Ticket image: The background image used in the dynamically generated ticket emailed to the customer</li>
    <li>Ticket logo: The logo of the organization.  Gets stamped on the ticket.</li>
    </ul>
    </p>

    <div class="row">
        <div class="col">
            <form action="/admin/ticket/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="ticket_name"><h5>Ticket Name</h5></label>
                    <input type="text" class="form-control" id="ticket_name" name="ticket_name" placeholder="ex. General Admission" required />
                    <small class="form-text text-muted">The name of the ticket</small>
                </div>
                <div class="form-group">
                    <label for="ticket_description"><h5>Ticket Description</h5></label>
                    <input type="text" class="form-control" id="ticket_description" name="ticket_description" placeholder="Enjoy a stadium view of the ring." required />
                    <small class="form-text text-muted">Description of the ticket</small>
                </div>
                <div class="form-group">
                    <label for="ticket_price"><h5>Ticket Price</h5></label>
                    <input type="text" class="form-control" id="ticket_price" name="ticket_price" placeholder="ex. 50.00" required />
                    <small class="form-text text-muted">The price of the ticket with two places after the decimal like 15.50</small>
                </div>
                <div class="form-group">
                    <label for="ticket_ticketImage"><h5>Upload Ticket Background Image</h5></label>
                    <input type="file" class="form-control-file" id="ticket_ticketImage" name="ticket_ticketImage" required/>
                    <small class="form-text text-muted">background image of the dynamically rendered ticket.  Try to upload something rectangular</small>
                 </div>
                <div class="form-group">
                    <label for="ticket_ticketLogo"><h5>Upload Ticket Logo</h5></label>
                    <input type="file" class="form-control-file" id="ticket_ticketLogo" name="ticket_ticketLogo" required/>
                    <small class="form-text text-muted">logo that appears on the left side of the ticket.  Try to use something square shaped. </small>
                </div>
                <input type="submit" class="btn btn-primary" value="Create Ticket"/>
            </div>
        </form>
    </div>
    </body>
</html>