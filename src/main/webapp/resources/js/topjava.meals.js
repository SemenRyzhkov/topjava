const mealsAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const mtx = {
    ajaxUrl: mealsAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(mtx,
        $("#mealTable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );


});