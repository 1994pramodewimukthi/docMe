var grida;

$(function () {
    var chanelId = $("#chanel").val();
    var isModify;
    var isAdd;
    $("#chanelId").val(chanelId);
    var $header = $('#header');
    var $titlediv = $('.basetitle');
    var $footer = $('#footer');
    var $content = $('#webixtablecontainer');
    var $window = $(window).on('resize', function () {
        var height = $(this).height() - $header.height() + $footer.height() + $titlediv.height() - 330;
        $content.height(height);
    }).trigger('resize'); //on page load

    $(window).on("orientationchange resize", function () {
        grida.refresh();
        grida.resize();
    });

    grida = webix.ui({
        container: "webixtablecontainer",
        view: "treetable",
        id: "categorydatable",
        minHeight: 50,
        select: "cell",
        type: {
            folder: function (obj) {
                if (obj.sortingOrder >= 0) {
                    return "<div class='webix_tree_folder' ></div>"; //tree level 1 icon
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
                    this.showOverlay('TABLE_NO_DATA');
                    this.clearAll();
                }
            },
            onItemClick: function (id, e, trg) {
                var columnName = id.column;
                if (isAdd === true) {
                    if (columnName === "add") {
                        addCategory(grida.getItem(id.row).docCategoryMstId);
                    }
                }
                if (isModify === true) {
                    if (columnName === "modify") {
                        modifyCategory(grida.getItem(id.row).docCategoryMstId);
                    }
                }
            }
        },

        columns: [
            {id: "docCategoryMstId", header: [{text: ""}], hidden: true},
            {
                id: "docCategoryName",
                header: [{text: "Category", css: "alligncenter"}, {content: "textFilter", placeholder: "Search"}],
                fillspace: true,
                minWidth: 200,
                template: "{common.treetable()} #docCategoryName#",
                sort: "string"
            },
            {
                id: "add",
                header: [{text: "Action", css: "alligncenter"}],
                // template: "<input class='btn btn-success btn-table addbtn' type='button' value='Add Sub Category'>",
                template: function (obj) {
                    if (obj.optionInsert === true) {
                        isAdd = true;
                        // return "<input class='btn btn-success btn-table addbtn' type='button' value='Add Sub Category'>";
                        return "<input class='btn btn-success btn-table addbtn' type='button' value='Add Sub Category'>";
                    } else {
                        isAdd = false;
                        return "<input class='btn btn-success btn-table addbtn' type='button' value='Add Sub Category' disabled>";
                    }
                },
                css: "padding_less",
                width: 200
            },
            {
                id: "modify",
                header: [{text: "Action", css: "alligncenter"}],
                // template: "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Category'>",
                template: function (obj) {
                    if (obj.optionModify === true) {
                        isModify = true;
                        return "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Category'>";
                    } else {
                        isModify = false;
                        return "<input class='btn btn-success btn-table modbtn' type='button' value='Modify Category' disabled>";
                    }
                },
                css: "padding_less",
                width: 200
            }

        ],

        url: CONTEXT_PATH + "/document/list"
    });


});




