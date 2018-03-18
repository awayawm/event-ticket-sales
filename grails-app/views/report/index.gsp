<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title>Dashboard</title>
    </head>
    <body>

    <p>
        <div class="row">
            <div class="col">
                <g:render template="/shared/adminnav"/>
            </div>
        </div>
        </p>

        <g:if test="${flash.message}">
            <p>
            <div class="alert ${flash.class}">
                ${flash.message}
            </div>
            </p>
        </g:if>
        <h2>Reports</h2>

    </table>
    </body>
</html>