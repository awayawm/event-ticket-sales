$(document).ready(function() {
    $("#deleteTicketConfirmDialogCancel").on("click", function(){
        $("#deleteTicketConfirmDialog").hide()
    })
    $("#deleteTicketConfirmDialogConfirm").on("click", function(){
        document.location.href="/ticket/delete/"+deleteId
    })

    $("#deleteEventConfirmDialogCancel").on("click", function(){
        $("#deleteEventConfirmDialog").hide()
    })
    $("#deleteEventConfirmDialogConfirm").on("click", function(){
        document.location.href="/event/delete/"+deleteId
    })

    jQuery('#datetimepicker').datetimepicker();
    jQuery('#datetimepicker2').datetimepicker();
    jQuery('#datetimepicker3').datetimepicker();

})

var deleteId = 0

var showTicketDeleteConfirmDialog = function(id){
    $("#deleteTicketConfirmDialog").show()
    deleteId = id
}

var showEventDeleteConfirmDialog = function(id){
    $("#deleteEventConfirmDialog").show()
    deleteId = id
}

      var form = document.querySelector('#my-sample-form');
      var submit = document.querySelector('input[type="submit"]');

      braintree.client.create({
        authorization: 'sandbox_g42y39zw_348pk9cgf3bgyw2b'
      }, function (clientErr, clientInstance) {
        if (clientErr) {
          console.error(clientErr);
          return;
        }

        // This example shows Hosted Fields, but you can also use this
        // client instance to create additional components here, such as
        // PayPal or Data Collector.

        braintree.hostedFields.create({
          client: clientInstance,
          styles: {
            'input': {
              'color': '#3A3A3A',
              'transition': 'color 160ms linear',
              '-webkit-transition': 'color 160ms linear'
                    },
                    ':focus': {
                      'color': '#333333'
                    },
                    '.invalid': {
                      'color': '#FF0000'
                    }
          },
          fields: {
            number: {
              selector: '#card-number',
              placeholder: '4111 1111 1111 1111'
            },
            cvv: {
              selector: '#cvv',
              placeholder: '123'
            },
            expirationDate: {
              selector: '#expiration-date',
              placeholder: '10/2019'
            }
          }
        }, function (hostedFieldsErr, hostedFieldsInstance) {
          if (hostedFieldsErr) {
            console.error(hostedFieldsErr);
            return;
          }

          submit.removeAttribute('disabled');

          form.addEventListener('submit', function (event) {
            event.preventDefault();

            hostedFieldsInstance.tokenize(function (tokenizeErr, payload) {
              if (tokenizeErr) {
                console.error(tokenizeErr);
                return;
              }

              // If this was a real integration, this is where you would
              // send the nonce to your server.
              console.log('Got a nonce: ' + payload.nonce);
            });
          }, false);
        });
      });