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
            {id: "docId", header: [{text: "Document ID"}], adjust: true, sort: "string"},
            {id: "docName", header: [{text: "Document Name", css: "alligncenter"}, {content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            {id: "catName", header: [{text: "Category Name"},{content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            {id: "rejectedReson", header: [{text: "Rejected Reason"}], width: 250, sort: "string"},
            {id: "rejecteduser", header: [{text: "Rejected User"}],width: 250, adjust: true, width: 120, sort: "string"},
            {
                id: "rejectedtime",
                header: [{text: "Rejected Date", css: 'alligncenter'}],
                fillspace: true,
                css: 'alligncenter',
                minWidth: 150,
                sort: "string"
            },
            {
                id: "resubmit",
                header: [{text: "Resubmit", css: "alligncenter"}],
                fillspace: true,
                css: "alligncenter",
                minWidth: 100
            }

        ],
        url: CONTEXT_PATH + "/documentUploadController/getRejectedList"
    });


});


function resubmitRec(id) {
    resubmitDoc(id);
}





