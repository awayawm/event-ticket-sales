    <head>
        <meta name="layout" content="main" />
        <title>Create Ticket</title>
    </head>
    <body>
    <div class="row">
        <div class="col">
            <g:render template="/shared/adminnav"/>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <form action="/ticket/create" method="post">
                <div class="form-group">
                    <label for="ticket_name"><h5>Ticket Name</h5></label>
                    <input type="text" class="form-control" id="ticket_name" placeholder="ex. General Admission" required />
                </div>
                <div class="form-group">
                    <label for="ticket_price"><h5>Ticket Price</h5></label>
                    <input type="text" class="form-control" id="ticket_price" placeholder="ex. 50.00" required />
                </div>
                <div class="form-group">
                    <label for="ticket_description"><h5>Ticket Description</h5></label>
                    <input type="text" class="form-control" id="ticket_description" placeholder="Enjoy a stadium view of the ring." required />
                </div>
                <div class="form-group">
                    <label for="ticket_ticketImage"><h5>Upload Ticket Image</h5></label>
                    <input type="file" class="form-control-file" id="ticket_ticketImage" required/>
                </div>
                <input type="submit" class="btn btn primary" value="create"/>
            </div>
        </form>
    </div>
