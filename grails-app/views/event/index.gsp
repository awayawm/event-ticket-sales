<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Tickets list</title>
    </head>
    <body>

    <div class="modal hide" id="deleteEventConfirmDialog" tabindex="-1">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
        <div class="modal-header">

            <h5 class="modal-title">Confirm Deletion?</h5>
            </div>
            <div class="modal-footer">
          <button class="btn btn-primary" id="deleteEventConfirmDialogConfirm">Confirm</button>
          <button class="btn btn-primary" id="deleteEventConfirmDialogCancel">Cancel</button>
          </div>
        </div>
      </div>
    </div>

    <p>
        <div class="row">
            <div class="col">
                <g:render template="/shared/adminnav"/>
            </div>
        </div>
        </p>
    <p>
        <button class="btn btn-primary" onclick="document.location.href='/admin/event/create'">Create</button>
    </p>

        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>

    <g:if test="${events}">
    <table class="table">
      <thead>
        <tr>
          <th scope="col">id</th>
          <th scope="col">name</th>
          <th scope="col">shortURL</th>
          <th scope="col">description</th>
          <th scope="col">address</th>
          <th scope="col">Poster Image</th>
          <th scope="col">Doors Open</th>
          <th scope="col">Event Starts</th>
          <th scope="col">Stop Sales</th>
          <th scope="col">Enabled</th>
          <th scope="col">Tickets</th>
          <th scope="col">Functions</th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${events}">
            <tr>
              <th>${it.id}</th>
              <td>${it.name}</td>
              <td>${it.shortURL}</td>
              <td>${it.description}</td>
              <td>${it.address}</td>
              <td><img src="data:${it.posterContentType};base64,${it.posterBytes.encodeBase64()}" style="max-width: 200px; max-height: 200px;"></td>
              <td>${it.doorsOpen}</td>
              <td>${it.eventStarts}</td>
              <td>${it.stopTicketSalesAt}</td>
              <td>${it.enabled}</td>
              <td><ul><g:each in="${it.tickets}" var="ticket"><li>${ticket.name}</li></g:each></ul></td>
              <td><i class="fas fa-edit" onclick="document.location.href='/admin/event/edit/${it.id}'"></i> <i class="fas fa-trash" onclick="showEventDeleteConfirmDialog(${it.id})"></i></td>
            </tr>
        </g:each>
        </g:if>
        <g:else>
        <h2>No Events created yet.</h2>
        </g:else>
      </tbody>
    </table>
    </body>
</html>