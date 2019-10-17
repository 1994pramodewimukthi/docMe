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
                    return '<button class="btn btn-success btn-table addbtn btnModify" type="button" value="' + obj.systemRoleId + '" onclick="loadModalWithData(this.value)">Modify System Role</button>';
                }
            }

        ],

        url: CONTEXT_PATH + "/user/get-all-system-role",

    });


});






