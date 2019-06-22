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
            }
        },

        columns: [
            {id: "categoryId", header: [{text: "Category ID"}],adjust: true, sort: "string"},
            {id: "categoryName", header: [{text: "Category Name", css: "alligncenter"}, {content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            {id: "parentCategoryName", header: [{text: "Parent Category Name"}], adjust: true, width: 160, sort: "string"},
            // {id: "chanel", header: [{text: "Channel", css: "alligncenter"}], fillspace: true, css: "alligncenter", minWidth: 100, sort: "string"},
            {id: "inputUser", header: [{text: "Input User"}], adjust: true, sort: "string"},
            {id: "authButton", header: [{text: "Authorization", css: "alligncenter"}], fillspace: true, css: "alligncenter", minWidth: 100},
            // {id: "rejectButton", header: [{text: "Reject", css: "alligncenter"}], fillspace: true, css: "alligncenter", minWidth: 100},
            {id: "inputDateTime", header: [{text: "Input Date", css:'alligncenter'}], css:'alligncenter', fillspace: true, minWidth: 150, sort: "string"}

        ],
        url: CONTEXT_PATH + "/documentManagement/get_pending_ath_lst"
    });


});






