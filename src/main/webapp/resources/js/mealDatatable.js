/**
 * Created by Mikhail on 30.04.2016.
 */
var ajaxUrl = 'ajax/admin/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type : "POST",
        url: ajaxUrl + 'filter',
        data : $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function(date, type, row) {
                    if(type == 'display') {
                        return date.replace('T', ' ');
                    }
                    return date;
                }
            },
            {
                "data": "decription"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
                $(row).addClass(data.exceed ? "exceeded" : "normal");
        },
        "initComplete": function() {
            $('#filter').submit(function() {
                updateTable();
                return false;
            });
            makeEditable();
        }
    });
});