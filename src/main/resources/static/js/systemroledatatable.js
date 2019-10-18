var grida

$(function () {

    var $header = $('#header');
    var $titlediv = $('.site-content-title');
    var $footer = $('#footer');
    var $content = $('#webixtablecontainer');
    var $window = $(window).on('resize', function () {
        var height = $(this).height() - $header.height() + $footer.height() + $titlediv.height() - 270;
        $content.height(height);
    }).trigger('resize'); //on page load

    $(window).on("orientationchange resize", function () {
        grida.refresh();
        grida.resize();
    });

    grida = webix.ui({
        container: "webixtablecontainer",
        view: "datatable",
        id: "catauthdatable", minHeight: 50,
        select: "cell",
        on: {
            onBeforeLoad: function () {
                this.showOverlay("Loading...");
            },
            onAfterLoad: function () {
                this.hideOverlay();
                if (!this.count()) {
                    this.showOverlay(TABLE_NO_DATA);
                    this.clearAll();
                }
            },

        },

        columns: [
            {
                id: "systemRoleName",
                header: [{text: "Role Name"}, {content: "textFilter", placeholder: "Search"}],
                adjust: true,
                sort: "string",
                fillspace: true
            },
            {
                id: "systemRoleStatus",
                header: [{text: "System Role Status"}],
                adjust: true,
                sort: "string",
                fillspace: true
            },
            {
                id: "systemRoleId", header: "Action",
                adjust: true,
                sort: "string",
                fillspace: true,
                template: function (obj) {
                    let  data = obj;
                    return '<button class="btn btn-success btn-table addbtn btnModify" type="button" value="' + obj.systemRoleId + '"  onclick="loadModalWithData(this.value) ">Modify System Role</button>';
                }
            },
            {
                id: "systemRoleId", header: "Action",
                adjust: true,
                sort: "string",
                fillspace: true,
                template: function (obj) {
                    let  data = obj;
                    return '<button class="btn btn-success btn-table addbtn btnModify" type="button" value="' + obj.systemRoleId + '" onclick="getDataToDataTable(this.value)">Set Role privileges</button>';
                }
            }

        ],

        url: CONTEXT_PATH + "/user/get-all-system-role",

    });

});

function loadModalWithData(id) {
    $.ajax({
        url: CONTEXT_PATH + "/user/find-system-role-by-id/" + id,
        type: 'GET',
        cache: false,
        dataType: 'html',
        success: function (result) {
            $('#modifyDiv').html(result);
            $('#modifySystemRole').modal('show');
            return false;
        },
        error: function (result) {

        }

    });
}

function getDataToDataTable(id) {

    $.ajax({
        url: CONTEXT_PATH + "/user/get-all-system-menu-item-privileges/" + id,
        type: 'GET',
        dataType:'html',
        success: function (result) {
            $('#modifyDiv').html(result);
            $('#modifySystemPrivilages').modal('show');
            return false;
        },
        error: function (result) {

        }

    });
}

function setDataToDataTable(tableData) {

    $('#allPoliciesDataTable').dataTable({
        searching: false,
        paging: false,
        bInfo: false,
        "columnDefs": [

            {
                "targets": 1,
                className: 'center',
                render: function (data) {
                    return '<input type="checkbox" name="vehicle1" checked="' + data + '">';
                }
            },
            {
                "targets": 2,
                className: 'center',
                render: function (data) {
                    return '<input type="checkbox" name="vehicle1" checked="' + data + '">';
                }
            },
            {
                "targets": 3,
                className: 'center',
                render: function (data) {
                    return '<input type="checkbox" name="vehicle1" checked="' + data + '">';
                }
            },
            {
                "targets": 4,
                className: 'center',
                render: function (data) {
                    return '<input type="checkbox" name="vehicle1" checked="' + data + '">';
                }
            },
            {
                "targets": 5,
                className: 'center',
                render: function (data) {
                    return '<input type="checkbox" name="vehicle1" checked="' + data + '">';
                }
            },
        ],
        data: tableData,
        columns: [
            {data: "systemMenuName"},
            {data: "viewPrivilege"},
            {data: "authorizationPrivilege"},
            {data: "savePrivilege"},
            {data: "deletePrivilege"},
            {data: "updatePrivilege"}

        ],
    })


}




