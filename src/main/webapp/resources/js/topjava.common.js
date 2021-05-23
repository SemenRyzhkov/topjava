let form;

function makeEditable(tx, datatableApi) {
    tx.datatableApi = datatableApi;

    form = $('#detailsForm');
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            deleteRow(tx, $(this).closest('tr').attr("id"));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(tx, id) {
    $.ajax({
        url: tx.ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        updateTable(tx);
        successNoty("Deleted");
    });
}

function updateTable(tx) {
    $.get(tx.ajaxUrl, function (data) {
        tx.datatableApi.clear().rows.add(data).draw();
    });
}

function save(tx) {
    const form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: tx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable(tx);
        successNoty("Saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}