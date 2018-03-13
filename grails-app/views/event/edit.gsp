<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Edit Event</title>
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
    <button class="btn btn-primary" onclick="document.location.href='/admin/event/index'">Back to Event Index</button>
    </p>

        <h2>Edit Event</h2>

    <div class="row">
        <div class="col">
            <form action="/admin/event/edit" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${event.id}"/>
                <div class="form-group">
                    <label for="name"><h5>Event Name</h5></label>
                    <input type="text" class="form-control" name="name" value="${event.name}" required />
                    <small class="form-text text-muted">Name of the event</small>
                </div>
                <div class="form-group">
                    <label for="shortURL"><h5>Short URL</h5></label>
                    <input type="text" class="form-control" name="shortURL" value="${event.shortURL}" required />
                    <small class="form-text text-muted">purchase tickets for event at /purchase/battle-at-the-bay</small>
                </div>
                <div class="form-group">
                    <label for="description"><h5>Description</h5></label>
                    <input type="text" class="form-control" name="description" value="${event.description}" required />
                    <small class="form-text text-muted">A short description of the event.  The customer sees this.</small>

                </div>
                <div class="form-group">
                    <label for="tickets"><h5>Associated Tickets</h5></label>
                    <select class="form-control" name="tickets" multiple />
                    <g:each in="${tickets}" var="ticket">
                    <option ${event.tickets.contains(ticket) ? "selected" : ""} value="${ticket.id}">${ticket.name}</option>
                    </g:each>
                    </select>
                    <small class="form-text text-muted">These tickets will be available for purchase for this event.  Control or Shift select to associate multiple tickets.</small>
                </div>

                <div class="form-group">
                    <label for="ticket_ticketImage"><h5>Address</h5></label>
                    <input type="text" class="form-control" name="address"  value="${event.address}" required/>
                    <small class="form-text text-muted">Address of the event.  This is displayed on the ticket</small>
                 </div>

                <div class="row">
                    <div class="col">
                        <h5>Current Poster: <span class="text-muted">${event.posterName}</span></h5>
                        <img src="data:${event.posterContentType};base64,${event.posterBytes.encodeBase64()}" style="max-width: 400px; max-height: 400px;">
                    </div>
                </div>

                 <div class="form-group">
                    <label for="poster"><h5>Replace Poster Image</h5></label>
                    <input type="file" class="form-control-file" name="poster" />
                    <small class="form-text text-muted">Poster Image is displayed on customer landing pages</small>
                 </div>

                <h5>Doors currently open at: <span class="text-muted"><g:formatDate date="${event.doorsOpen}" type="datetime" style="MEDIUM"/></span></h5>
                <div class="form-group">
                    <label for="doorsOpen"><h5>Doors Open</h5></label>
                    <input id="datetimepicker" type="text" name="doorsOpen" />
                    <small class="form-text text-muted">Date and time that the doors open for the event.  Current value is kept if no new value is seleted.</small>
                </div>

                 <h5>Event currently starting at: <span class="text-muted"><g:formatDate date="${event.eventStarts}" type="datetime" style="MEDIUM"/></span></h5>
                <div class="form-group">
                    <label for="eventStarts"><h5>Event Starts</h5></label>
                    <input id="datetimepicker2" type="text" name="eventStarts" />
                    <small class="form-text text-muted">Date and time when the event starts.  Current value is kept if no new value is seleted.</small>
                </div>

                <h5>Ticket sales are currently stopping at: <span class="text-muted"><g:formatDate date="${event.stopTicketSalesAt}" type="datetime" style="MEDIUM"/></span></h5>
                <div class="form-group">
                    <label for="stopTicketSalesAt"><h5>Stop Ticket Sales At</h5></label>
                    <input id="datetimepicker3" type="text" name="stopTicketSalesAt" />
                    <small class="form-text text-muted">Date and time that ticket sales for this event will end.  The event will be automatically disabled at the selected date and time.  Current value is kept if no new value is seleted.</small>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="enabled" checked="${event.enabled == true ? "true" : "false"}" />
                    <label for="enabled" class="form-check-label">Event Enabled</label>
                    <small class="form-text text-muted">Tickets can only be sold for enabled events</small>
                </div>
                <div class="form-group">
                    <input type="submit" class="btn btn-primary" value="Edit Event"/>
                </div>


            </form>
        </div>
    </div>
    </body>
</html>