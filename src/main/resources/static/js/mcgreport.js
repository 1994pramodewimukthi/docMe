/**
 * Created by Pramode Wimukthi
 */
var webixtable;

$(function () {

    $(window).on("orientationchange resize", function () {
        webixtable.refresh();
        webixtable.resize();
    });

    var agentStatus = $("#agentStatus").val();
    webixtable = webix.ui({
        container: "webixtablecontainer",
        view: "treetable",//datatable treetable
        id: "reporttable",
        minHeight: 200,
        autoheight: true,
        resizeColumn: true,
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
            {id: "agentName", header: [{text: "Full Name"},{content: "textFilter", placeholder: "Search"}],adjust: true, sort: "string"},
            {id: "docName", header: [{text: "Agreement Name", css: "alligncenter"}, {content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            {id: "docIssueDateString", header: [{text: "Agreement Issue Date"}], adjust: true, width: 120, sort: "string"},
            {id: "docSignDateString", header: [{text: "Agreement Sign Date"}], adjust: true, sort: "string"},
            {id: "signStatus", header: [{text: "Sign Status", css:'alligncenter'}], adjust: true, fillspace: true, minWidth: 150, sort: "string"}
        ],
        url: CONTEXT_PATH + "/mcg/reportJSON"

    });
});

// function loadData() {
//     var agentStatus = $("#agentStatus").val();
//     webixtable.clearAll();
//     webixtable.load(CONTEXT_PATH + "/mcgreport/mcgwebixview?agentStatus=" + agentStatus);
// }

function exportToExcel() {
    webix.toExcel($$('reporttable'), {
        filename: "Agreement Status Report",
        orientation: "portrait",
        docHeader: {
            text: "Agreement Status List",
            textAlign: "left",
            color: 0x663399,
            fontSize: 15
        },
        autowidth: true,
        filterHTML: true

    });

}

function exportToPdf() {
    webix.toPDF($$('reporttable'), {
        filename: "Agreement Status Report",
        orientation: "portrait",
        docHeader: {
            text: "Agreement Status List",
            textAlign: "left",
            color: 0x663399,
            fontSize: 15
        },
        autowidth: true,
        filterHTML: true

    });

}

function viewDoc(docId, agentCode) {
    if (null != docId) {
        window.open(CONTEXT_PATH + '/mcgreport/viewDoc/' + agentCode + '/' + docId);
    }
}