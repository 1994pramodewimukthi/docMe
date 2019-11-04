/**
 * Created by Pramode Wimukthi
 */

$(function () {
    var chanelId = $("#chanel").val();
    var isView;
    var isSend;
    var $header = $('#header');
    var $titlediv = $('.basetitle');
    var $footer = $('#footer');
    var $content = $('#webixtablecontainerDocUpload');
    var $window = $(window).on('resize', function () {
        var height = $(this).height() - $header.height() + $footer.height() + $titlediv.height() - 295;
        $content.height(height);
    }).trigger('resize'); //on page load

    $(window).on("orientationchange resize", function () {
        grida.refresh();
        grida.resize();
    });

    var grida = webix.ui({
        container: "webixtablecontainerDocUpload",
        view: "treetable",
        id: "categorydatable",minHeight: 50,
        select: "cell",
        type: {
            folder: function (obj) {
                if (obj.tableConfig === 0) {
                    return "<div class='webix_tree_folder' ></div>"; //tree level 1 icon
                } else if (obj.tableConfig === 1) {
                    return "<div class='webix_tree_file'></div>"; //tree level 2 icon
                }
            }
        },
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
            onItemClick: function (id, e, trg) {
                if ($(trg).context.innerHTML != "") {
                    var columnName = id.column;
                    if (isView === true) {
                        if (columnName === "view") {
                            viewRec(grida.getItem(id.row).docCategoryMstId);
                        }
                    }
                    if (isSend === true) {
                        if (columnName === "send") {
                            emailSend(grida.getItem(id.row).docCategoryMstId);
                        }
                    }

                }
                if ($(trg).context.lastElementChild.className == "webix_tree_file") {
                    var customcerRef = grida.getItem(id.row).docCategoryMstId;
                    viewRec(customcerRef)
                }
            }
        },

        columns: [
            {id: "docCategoryMstId", header: [{text: ""}], hidden: true},
            {
                id: "docCategoryName",
                header: [{text: "Category"}],
                fillspace: true,
                minWidth: 200,
                template: "{common.treetable()} #docCategoryName#",
                sort: "string"
            },
            {
                id: "view",
                header: [{text: "Action", css: "alligncenter"}],
                // template: "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Document'>",
                template: function (obj) {
                    if (obj.tableConfig === 1) {
                        if (obj.optionModify === true) {
                            isView = true;
                            return "<input class='btn btn-success btn-table modbtn' type='button' value='View Document' onclick='modifyDocument(obj.docCategoryMstId)'>";
                        } else {
                            isView = false;
                            return "<input class='btn btn-success btn-table modbtn' type='button' value='View Document' disabled>";
                        }
                    } else if (obj.tableConfig === 0) {
                        return "";
                    }

                },
                css: "padding_less",
                width: 200
            },
            {
                id: "send",
                header: [{text: "Action", css: "alligncenter"}],
                // template: "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Document'>",
                template: function (obj) {
                    if (obj.tableConfig === 1) {
                        if (obj.optionModify === true) {
                            isSend = true;
                            return "<input class='btn btn-primary btn-table modbtn' type='button' value='Send via Email'>";
                        } else {
                            isSend = false;
                            return "<input class='btn btn-primary btn-table modbtn' type='button' value='Send via Email' disabled>";
                        }
                    } else if (obj.tableConfig === 0) {
                        return "";
                    }

                },
                css: "padding_less",
                width: 200
            }
        ],

        url: CONTEXT_PATH + "/documentUploadController/document_upload_webix_email"
    });


});

function viewRec(id) {
    var url = CONTEXT_PATH + '/documentUploadController/viewMSTDocumemt?docId=' + id;
    window.location.href = url;
    // window.location.replace(CONTEXT_PATH + 'documentUploadController/viewDocumemt?docId=' + id);
}