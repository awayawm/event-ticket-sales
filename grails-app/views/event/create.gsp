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
        <button class="btn btn-primary" onclick="document.location.href='/event/index'">Back to Event Index</button>
        </p>


    <h2>Create an Event</h2>
    <p>
    Event information is associated with tickets that customers purchase
    <ul>
    <li>Event name: Name of the event ex. Battle at the Bay</li>
    <li>Short URL: short to access ticket sales from ex. /purchase/battle-at-the-bay</li>
    <li>Description: Description of the event</li>
    <li>Address: Address of event.  This will be printed on the ticket</li>
    <li>Poster: Posters gets displayed on customer landing pages</li>
    <li>Doors open at: Date and Time that doors open.  Printed on ticket</li>
    <li>Event starts at: Date and Time that event starts.  Printed on ticket</li>
    <li>Stop Ticket Sales At: Time and date that ticket sales become disabled</li>
    <li>Enabled: Ticket sales are turned on when an event is enabled.  Unchecking enabled will make ticket sales not possible.</li>
    </ul>
    </p>

    <div class="row">
        <div class="col">
            <form action="/event/create" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name"><h5>Event Name</h5></label>
                    <input type="text" class="form-control" name="name" placeholder="ex. Battle at the Bay" required />
                    <small class="form-text text-muted">Name of the event</small>
                </div>
                <div class="form-group">
                    <label for="shortURL"><h5>Short URL</h5></label>
                    <input type="text" class="form-control" name="shortURL" placeholder="battle-at-the-bay" required />
                    <small class="form-text text-muted">purchase tickets for event at /purchase/battle-at-the-bay</small>
                </div>
                <div class="form-group">
                    <label for="description"><h5>Description</h5></label>
                    <input type="text" class="form-control" name="description" placeholder="Enjoy the view of Kirksville Bay as 16 fighters battle for the ultimate prize!" required />
                    <small class="form-text text-muted">A short description of the event.  The customer sees this.</small>
                </div>
                <div class="form-group">
                    <label for="ticket_ticketImage"><h5>Address</h5></label>
                    <input type="text" class="form-control" name="address" placeholder="13 W. Broadway, Kirksville, MO 63501" required/>
                    <small class="form-text text-muted">Address of the event.  This is displayed on the ticket</small>
                 </div>
                 <div class="form-group">
                    <label for="poster"><h5>Poster Image</h5></label>
                    <input type="file" class="form-control-file" name="poster" required/>
                    <small class="form-text text-muted">Poster Image is displayed on customer landing pages</small>
                 </div>
                <div class="form-group">
                    <label for="doorsOpen"><h5>Doors Open</h5></label>
                    <input id="datetimepicker" type="text" name="doorsOpen" required />
                    <small class="form-text text-muted">Date and time that the doors open for the event</small>
                </div>
                <div class="form-group">
                    <label for="eventStarts"><h5>Event Starts</h5></label>
                    <input id="datetimepicker2" type="text" name="eventStarts" required />
                    <small class="form-text text-muted">Date and time when the event starts</small>
                </div>
                <div class="form-group">
                    <label for="stopTicketSalesAt"><h5>Stop Ticket Sales At</h5></label>
                    <input id="datetimepicker3" type="text" name="stopTicketSalesAt" required />
                    <small class="form-text text-muted">Date and time that ticket sales for this event will end.  The event will be automatically disabled at the selected date and time</small>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="enabled" required />
                    <label for="enabled" class="form-check-label">Event Enabled</label>
                    <small class="form-text text-muted">Tickets can only be sold for enabled events</small>
                </div>
                <div class="form-group">
                    <input type="submit" class="btn btn-primary" value="Create Event"/>
                </div>
            </div>
        </form>
    </div>
    </body>
</html>