var grida;

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
        id: "catauthdatable",minHeight: 50,
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
            onColumnResize: function () {
                this.adjustRowHeight("reason", true);
                this.render();
            }
        },

        columns: [
            {id: "categoryId", header: [{text: "Category ID"}], adjust: true, sort: "string"},
            {id: "categoryName", header: [{text: "Category Name", css: "alligncenter"}, {content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            {id: "parentCategoryName", header: [{text: "Parent Category Name"}], adjust: true, width: 170, sort: "string"},
            {
                id: "chanel",
                header: [{text: "Channel", css: "alligncenter"}],
                css: "alligncenter",
                fillspace: true,
                minWidth: 70,
                sort: "string"
            },
            {id: "reason", header: [{text: "Rejected Reason", css: 'alligncenter'}], width: 250, css: 'alligncenter', sort: "string"},
            {
                id: "authButton",
                header: [{text: "Resubmit", css: "alligncenter"}],
                css: "alligncenter",
                fillspace: true,
                minWidth: 100
            },
            {id: "inputUser", header: [{text: "Input User"}], adjust: true, sort: "string"},
            {
                id: "inputDateTime",
                header: [{text: "Input Date", css: 'alligncenter'}],
                css: 'alligncenter',
                fillspace: true,
                minWidth: 150,
                sort: "string"
            }

        ],

        fixedRowHeight: false,
        rowLineHeight: 30,
        rowHeight: 30,

        url: CONTEXT_PATH + "/documentManagement/get_rejected_lst"
    });


});