/**
 * Created by asiri on 25/10/17.
 */

$(function () {
    var chanelId = $("#chanel").val();
    var isModify;
    var isAdd;
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
                    if (isAdd === true) {
                        if (columnName === "add") {
                            uploadDocument(grida.getItem(id.row).docCategoryMstId);
                        }
                    }
                    if (isModify === true) {
                        if (columnName === "modify") {
                            modifyDocument(grida.getItem(id.row).docCategoryMstId);
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
                id: "add",
                header: [{text: "Action", css: "alligncenter"}],
                // template: "<input class='btn btn-success btn-table modbtn' type='button' value='Add Document'>",
                template: function (obj) {
                    if (obj.tableConfig === 0) {
                        if (obj.optionInsert === true) {
                            isAdd = true;
                            return "<input class='btn btn-success btn-table addbtn' type='button' value='Add Document'>";
                        } else {
                            isAdd = false;
                            return "<input class='btn btn-success btn-table addbtn' type='button' value='Add Document' disabled>";
                        }
                    } else if (obj.tableConfig === 1) {
                        return "";
                    }

                },
                css: "padding_less",
                width: 200
            },
            {
                id: "modify",
                header: [{text: "Action", css: "alligncenter"}],
                // template: "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Document'>",
                template: function (obj) {
                    if (obj.tableConfig === 1) {
                        if (obj.optionModify === true) {
                            isModify = true;
                            return "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Document' onclick='modifyDocument(obj.docCategoryMstId)'>";
                        } else {
                            isModify = false;
                            return "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Document' disabled>";
                        }
                    } else if (obj.tableConfig === 0) {
                        return "";
                    }

                },
                css: "padding_less",
                width: 200
            }

        ],

        url: CONTEXT_PATH + "/documentUploadController/document_upload_webix_Category?chanel_id=" + chanelId
    });


});

// function viewRec(id) {
//     var url = CONTEXT_PATH + '/documentUploadController/viewMSTDocumemt?docId=' + id;
//     window.location.href = url;
//     // window.location.replace(CONTEXT_PATH + 'documentUploadController/viewDocumemt?docId=' + id);
// }