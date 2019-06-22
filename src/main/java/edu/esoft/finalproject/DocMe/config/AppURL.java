package edu.esoft.finalproject.DocMe.config;

public class AppURL {

    public final static String REDIRECT_LOGIN_URL = "redirect:/login";
    public final static String ERROR_PAGE_404_URL = "404";
    final public static String MCG = "/mcg";


    final public static String MCG_EXPIRE = "/mcgExpire";
    final public static String MCG_REPORT = "/mcgreport";

    final public static String MCG_SAVE_EXPIRE = "/mcgSaveExpire";
    final public static String MCG_DOC_UPDATE = "/mcgDocUpdate";
    final public static String MCG_VIEW_UPLOADED_DOC = "/mcgViewUploadedDoc";
    final public static String MCG_VIEW_RELATED_DOC = "/mcgViewRelatedDoc";
    final public static String MCG_SAVE_DOC_UPDATE = "/updateDocument";


    final public static String MCG_ADD_CATEGORY = "/addCategory";
    final public static String MCG_REPORT_VIEW = "/mcgreportview";
    final public static String MCG_WEBIX_VIEW = "/mcgwebixview";
    final public static String MCG_SAVE_CATEGORY = "/saveCategory";
    final public static String MCG_UPDATE_CATEGORY = "/updateCategory";
    final public static String MCG_VIEW_UPDATE_CATEGORY = "/viewUpdateCategory/{catId}";
    final public static String MCG_VIEW_DOCUMENT_IS_VALID = "/viewDocIsValid/{docId}";
    final public static String MCG_VIEW_UPLOADED_DOCUMENT = "/viewDoc/{docId}";
    final public static String MCG_VIEW_DOCUMENT_REPORT = "/viewDoc/{agentCode}/{docId}";
    final public static String MCG_ADD_NEW_DOCUMENT = "/addDocument";
    final public static String MCG_PREVIEW_DOCUMENT = "/previewDocument";
    final public static String MCG_UPLOAD_NEW_DOCUMENT = "/uploadDocument";
    final public static String MCG_VIEW_PDF = "/viewAgreement";
    final public static String MCG_VIEW_PDF_DIRECT = "/viewAgreementDirect";
    final public static String MCG_VIEW_AGREEMENT = "/mcg/viewAgreement";
    final public static String MCG_REDIRECT_VIEW_AGREEMENT = "redirect:/mcg/viewAgreement";
    final public static String MCG_UPLOAD_NEW_SIGNATURE = "/uploadSignature";
    final public static String MCG_UPLOAD_SIGN_PDF = "/uploadSignPdf";
    final public static String MCG_SKIP_SIGN_PDF = "/skipSign";
    final public static String MCG_GET_DOCUMENT_VERSION = "/getLatestVersion/{catId}";

    final public static String MCG_VIEW_UPDATE_CATEGORY_MODEL = "rpmc/category_update_modal";
    final public static String MCG_ADD_CATEGORY_MODEL = "rpmc/add_category";
    final public static String MCG_ADD_DOCUMENT_MODEL = "rpmc/add_document";
    final public static String MCG_ADD_DOCUMENT_VIEW_MODEL = "rpmc/doc_view_for_sign";
    final public static String MCG_ADD_DOCUMENT_REPORT_VIEW_MODEL = "rpmc/report";
    final public static String MCG_ADD_DOCUMENT_EXPIRE_DATE = "rpmc/doc_expire_date";
    final public static String MCG_UPDATE_DOCUMENT = "rpmc/update_doc";
    final public static String MCG_UPLOADED_DOCUMENT = "rpmc/view_uploaded_doc";
    final public static String MCG_RELATED_DOCUMENT = "rpmc/view_signed_doc";
    final public static String MCG_VIEW_SELECT_DOCUMENT = "rpmc/mcg_doc_view";

    public final static String DOCUMENT_MANAGEMENT = "/documentManagement";
    public final static String SAVE = "/save";
    public final static String UPDATE = "/update";

    final public static String DOCUMENT_CATEGORY_MODIFY_CONTENT = "/load_category_Modify_content";
    final public static String DOCUMENT_CATEGORY_MODIFY = "/modifycategory";

    final public static String GET_PENDING_AUTH_LIST = "/get_pending_ath_lst";
    final public static String AUTHORIZED_CATEGRY = "/authorized_category";
    final public static String REJECT_CATEGORY = "/reject_category/{category_id}/{reason}";
    final public static String RESUBMIT = "/resubmit";
    final public static String CATEGORY_RESUBMIT = "/categoryResubmit";
    final public static String GET_REJECTED_LIST = "/get_rejected_lst";
    public final static String UPDATE_CRESUBMIT_CATEGORY = "/updateresubmitcategory";
    final public static String DOCUMENT_UPLOAD = "/documentUpload";

    final public static String DOCUMENT_UPLOAD_WEBIX_CAT = "/document_upload_webix_Category";
    final public static String DOCUMENT_UPLOAD_CONTROLLER = "/documentUploadController";
    final public static String DOCUMENT_UPLOAD_SAVE = "/save";
}
