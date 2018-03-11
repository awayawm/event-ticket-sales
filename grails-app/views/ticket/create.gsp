<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'ticket.label', default: 'Ticket')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-ticket" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-ticket" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.ticket}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.ticket}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>

        </div>

        <form>
            <div class="form-group">
                <label for="ticket_name">Ticket Name</label>
                <input type="text" class="form-control" id="ticket_name" placeholder="ex. General Admission"/>
                <label for="ticket_price">Ticket Price</label>
                <input type="text" class="form-control" id="ticket_price" placeholder="ex. 30.00"/>
                <label for="ticket_description">Ticket Description</label>
                <input type="text" class="form-control" id="ticket_description" placeholder="Enjoy a stadium view of the ring."/>
            </div>
            <div class="form-group">
            <label for="ticket_ticketImage">Upload Ticket Image</label>
            <input type="file" class="form-control-file" id="ticket_ticketImage"/>
            </div>
            <input type="submit" class="btn btn primary" value="create"/>
        </form>

    </body>
</html>
