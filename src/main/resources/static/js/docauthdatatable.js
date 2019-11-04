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
            {id: "docId", header: [{text: "Document ID"}],adjust: true, sort: "string"},
            {id: "docName", header: [{text: "Document Name", css: "alligncenter"}, {content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            {id: "catagoryName", header: [{text: "Category Name"},{content: "textFilter", placeholder: "Search"}], adjust: true, width: 120, sort: "string"},
            // {id: "parentCategoryName", header: [{text: "Parent Category Name"}], adjust: true, width: 160, sort: "string"},
            // {id: "chanel", header: [{text: "Channel", css: "alligncenter"}], fillspace: true, css: "alligncenter", minWidth: 100, sort: "string"},
            {id: "inputuser", header: [{text: "Input User"}], adjust: true, sort: "string"},
            {id: "inputtime", header: [{text: "Input Date", css:'alligncenter'}], css:'alligncenter', fillspace: true, minWidth: 150, sort: "string"},
            // {id: "rejectButton", header: [{text: "Reject", css: "alligncenter"}], fillspace: true, css: "alligncenter", minWidth: 100},
            {id: "authorizeButton", header: [{text: "Authorization", css: "alligncenter"}], fillspace: true, css: "alligncenter", minWidth: 100}

        ],
        url: CONTEXT_PATH + "/documentUploadController/getPendingDocList"
    });


});


function authRec(id) {
    swal({
        title: 'Are you sure you want to authorize this Document ?',
        text: "",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Authorize',
        closeOnClickOutside: false,
    }).then(function () {
        // window.location.replace(CONTEXT_PATH + '/documentUploadController/authDocumemt?docId=' + id);
        jQuery.ajax({
            url: CONTEXT_PATH +  '/documentUploadController/authDocumemt?docId=' + id,
            type: 'GET',
            success: function (data) {
                if (data.isSucsess == '1') {
                    swal({
                        title: data.message,
                        type: 'success',
                        showConfirmButton: true,
                        allowOutsideClick: false,
                        closeOnClickOutside: false,
                    }).then(function () {
                        location.reload();
                    });
                } else {
                    swal({
                        title: data.message,
                        type: 'error',
                        showConfirmButton: true,
                        allowOutsideClick: false,
                        closeOnClickOutside: false,
                    }).then(function () {
                        location.reload();
                    });
                }

            },
            error: function (data) {
                console.log(data);
            }
        });
    });
}

function rejectRec(id) {
    swal({
        title: 'Please Enter Reject Reason',
        input: 'text',
        showCancelButton: true,
        confirmButtonText: 'Reject',
        showLoaderOnConfirm: true,
        showConfirmButton: true,
        closeOnClickOutside: false,
        preConfirm: function (reason) {
            return new Promise(function (resolve, reject) {
                setTimeout(function () {
                    if (!reason) {
                        swal.showValidationError('Decline reason is required and cannot be empty.')
                        reject ();
                    } else if (/[^a-zA-Z0-9,. \-\/]/.test(reason)) {
                        swal.showValidationError('Special characters are not allowed.')
                        reject ();
                        // swal.showValidationMessage('Special characters are not allowed.')
                    } else if (reason.length > 255) {
                        swal.showValidationError('Transfer reason only can contain 255 characters.')
                        reject ();
                    }
                    resolve();
                }, 2000)
            })
        },
        allowOutsideClick: false
    }).then(function (reason) {
        jQuery.ajax({
            url: CONTEXT_PATH + '/documentUploadController/rejectDocumemt?docId=' + id + '&reson=' + reason,
            type: 'GET',
            success: function (data) {
                if (data.isSucsess == 1) {
                    swal({
                        title: data.message,
                        type: 'success',
                        showConfirmButton: true,
                        allowOutsideClick: false,
                        closeOnClickOutside: false,
                    }).then(function () {
                        location.reload();
                    });
                } else {
                    swal({
                        title: data.message,
                        type: 'error',
                        showConfirmButton: true,
                        allowOutsideClick: false,
                        closeOnClickOutside: false,
                    }).then(function () {
                        location.reload();
                    });
                }

            },
            error: function (data) {
                console.log(data);
            }
        });
    })

}

function viewRec(id) {
    var url = CONTEXT_PATH + '/documentUploadController/viewDocumemt?docId=' + id;
    window.location.href = url;
}





