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


        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>

        <div class="row my-4">
            <div class="col">
                <g:render template="/shared/adminnav"/>
            </div>
        </div>

    <div class="row">
    <div class="col">

    <g:if test="${sales}">
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">id</th>
          <th scope="col">customer name</th>
          <th scope="col">uuid</th>
          <th scope="col">event name</th>
          <th scope="col">total after fees/taxes</th>
          <th scope="col">status</th>
          <th scope="col">phone number</th>
          <th scope="col">email</th>
          <th scope="col">date created</th>
          <th scope="col">Split Enabled</th>
          <th scope="col">Functions</th>
        </tr>
      </thead>
      <tbody>
        <g:each in="${sales}">
            <tr>
              <th>${it.id}</th>
              <td>${it.customerName}</td>
              <td><a href="/sale/status/${it.uuid}">${it.uuid}</a></td>
              <td>${it.event.name}</td>
              <td>${it.totalAfterFeesAndTaxes}</td>
              <td>${it.salesStatus.status}<p class="my-2 text-center small"><g:formatDate date="${it.salesStatus.lastUpdated}" type="datetime" style="MEDIUM"/></p></td>
              <td>${it.phoneNumber}</td>
              <td>${it.emailAddress}</td>
              <td><g:formatDate date="${it.dateCreated}" type="datetime" style="MEDIUM"/></td>
              <td>${it.allocationEnabled}</td>
              <td><!--<i class="fas fa-edit"></i> <i class="fas fa-trash"></i>--></td>
            </tr>
        </g:each>
        </g:if>
        <g:else>
        <h2>No Sales created yet.</h2>
        </g:else>
      </tbody>
    </table>

    </div>
    </div>

    </body>
</html>