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