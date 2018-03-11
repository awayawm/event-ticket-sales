<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Tickets list</title>
    </head>
    <body>
    <p>
        <div class="row">
            <div class="col">
                <g:render template="/shared/adminnav"/>
            </div>
        </div>
        </p>
    <p>
        <button class="btn btn-primary" onclick="document.location.href='/ticket/create'">Create</button>
    </p>

        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>

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
              <td>edit, delete</td>
            </tr>
        </g:each>
      </tbody>
    </table>
    </body>
</html>