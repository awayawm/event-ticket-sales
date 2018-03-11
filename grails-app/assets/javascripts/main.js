$(document).ready(function() {
    $("#deleteTicketConfirmDialogCancel").on("click", function(){
        $("#deleteTicketConfirmDialog").hide()
    })
    $("#deleteTicketConfirmDialogConfirm").on("click", function(){
        document.location.href="/ticket/delete/"+deleteId
    })
})

var deleteId = 0

var showTicketDeleteConfirmDialog = function(id){
    $("#deleteTicketConfirmDialog").show()
    deleteId = id
}