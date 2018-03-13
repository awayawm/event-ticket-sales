<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Tickets list</title>
    </head>
    <body>

    <div class="modal hide" id="deleteTicketConfirmDialog" tabindex="-1">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
        <div class="modal-header">

            <h5 class="modal-title">Confirm Deletion?</h5>
            </div>
            <div class="modal-footer">
          <button class="btn btn-primary" id="deleteTicketConfirmDialogConfirm">Confirm</button>
          <button class="btn btn-primary" id="deleteTicketConfirmDialogCancel">Cancel</button>
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
        <button class="btn btn-primary" onclick="document.location.href='/admin/ticket/create'">Create</button>
    </p>

        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>


    <g:if test="${tickets}">
    <table class="table">
      <thead>
        <tr>
          <th scope="col">id</th>
          <th scope="col">name</th>
          <th scope="col">description</th>
          <th scope="col">price</th>
          <th scope="col">Ticket Background Image</th>
          <th scope="col">Ticket Logo Image</th>
          <th scope="col">Functions</th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${tickets}">
            <tr>
              <th>${it.id}</th>
              <td>${it.name}</td>
              <td>${it.description}</td>
              <td>${it.price}</td>
              <td><img src="data:${it.ticketImageContentType};base64,${it.ticketImageBytes.encodeBase64()}" style="max-width: 200px; max-height: 200px;"></td>
              <td><img src="data:${it.ticketLogoContentType};base64,${it.ticketLogoBytes.encodeBase64()}" style="max-width: 200px; max-height: 200px;"></td>
              <td><i class="fas fa-edit" onclick="document.location.href='/admin/ticket/edit/${it.id}'"></i> <i class="fas fa-trash" onclick="showTicketDeleteConfirmDialog(${it.id})"></i></td>
            </tr>
        </g:each>
        </g:if>
        <g:else>
        <h2>No tickets created yet.</h2>
        </g:else>
      </tbody>
    </table>
    </body>
</html>