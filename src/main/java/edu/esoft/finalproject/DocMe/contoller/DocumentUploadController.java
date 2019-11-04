package edu.esoft.finalproject.DocMe.contoller;


import edu.esoft.finalproject.DocMe.config.*;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.dto.SystemRoleDto;
import edu.esoft.finalproject.DocMe.entity.*;
import edu.esoft.finalproject.DocMe.service.DocumentUploadSFTPService;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import edu.esoft.finalproject.DocMe.service.SystemRoleDockUpService;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(AppURL.DOCUMENT_UPLOAD_CONTROLLER)//documentUploadController
public class DocumentUploadController {

    private static final String PDF = ".pdf";
    private static final String MB = "MB";
    private static final int DOC_SIZE = 25;
    private static final String APPLICATION_PDF = "application/pdf";
    private static final String COMMON_ERROR_MSG = "System Error, Please Contact System administrator";
    private static final int SUCSESS = 1;
    private static final int RECODE_EXSIST_TEMP = 2;
    private static final int ERROR = 0;
    private static final int VIRUS_FILE_MESSAGE = 3;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocumentUploadController.class);
    //    @Autowired
//    StorageService storageService;
//    @Autowired
//    SystemRoleService systemRoleService;
    @Autowired
    private DocumentUploadService documentUploadService;
    //    @Autowired
//    private SystemMenuItemService menuItemService;
    @Autowired
    private DocumentUploadSFTPService documentUploadSFTPService;
    //    @Autowired
//    private MessageService messageService;
//    @Autowired
//    AccessUserTypeService accessUserTypeService;
    @Autowired
    private CommonFunction commonFunction;

    @Autowired
    private SystemRoleDockUpService systemRoleDockUpService;

    @Value("${document.multipart.magic-extension-types}")
    private String DOCUMENT_VALID_FILE_MAGIC_TYPES;

    @Value("${document.multipart.max-file-size}")
    private Long DOCUMENT_MAX_FILE_SIZE;

    @RequestMapping(value = AppURL.DOCUMENT_UPLOAD_WEBIX_CAT, method = RequestMethod.GET)
    public Object loadAuthorizedUser(@ModelAttribute("user") User user,HttpSession session) {
        DocCategoryMasterWebix categoryMasters = new DocCategoryMasterWebix();
        try {
            user = (User) session.getAttribute(AppConstant.USER);
            categoryMasters.setData(documentUploadService.createCategoryWebixTableWithUploadDocumentAll(user));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return categoryMasters;
    }

    @RequestMapping(value = AppURL.DOCUMENT_UPLOAD_SAVE, method = RequestMethod.POST)
    public ModelAndView saveDocument(@ModelAttribute("docUploadTempForm") DocumentUploadDto documentUploadDto, ModelAndView modelAndView,
                                     @RequestParam(value = "catId") int catagoryId,HttpSession session) {
        try {
            User user = (User) session.getAttribute(AppConstant.USER);
            if (!documentUploadDto.getAttachment().getOriginalFilename().equals(AppConstant.STRING_EMPTY)) {
                if (commonFunction.checkIsValidFileType(documentUploadDto.getAttachment(), DOCUMENT_VALID_FILE_MAGIC_TYPES.split(AppConstant.STRING_COMMA))) {
                    if (commonFunction.isFileSizeSufficient(documentUploadDto.getAttachment(), DOCUMENT_MAX_FILE_SIZE)) {

                        if (APPLICATION_PDF.equals(documentUploadDto.getAttachment().getContentType())) {
                            documentUploadService.uploadDocumentToCategory(documentUploadDto, user, catagoryId);
                            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, true);
                            modelAndView.addObject(EmailMessageConstant.MSG, "Document Successfully submited for authorization");
                        } else {

                        }
                    } else {
                        modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                        modelAndView.addObject(EmailMessageConstant.MSG, "File Is too large, Maximum file size is " + DOCUMENT_MAX_FILE_SIZE + MB + " MB");
                    }
                } else {
                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                    modelAndView.addObject(EmailMessageConstant.MSG, "Please upload valid document");//EXEIED_DOC_SIZE
                }
            } else {
                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                modelAndView.addObject(EmailMessageConstant.MSG, "Please Select File");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(EmailMessageConstant.MSG, "Something went wrong. Please contact system administrator");
        }
        DocumentUploadDto documentUploadDto1 = new DocumentUploadDto();
        modelAndView.addObject("documentUploadDto", documentUploadDto1);
        if (documentUploadDto.getDocumentUploadTempId() == null && documentUploadDto.getDocCategoryMasterId() == null) {
            modelAndView.setViewName("/ui/document/document-creation");
        } else if (documentUploadDto.getDocumentUploadTempId() != null && documentUploadDto.getDocCategoryMasterId() == null) {
            modelAndView.setViewName("/ui/document/document-resubmit");
        } else if (documentUploadDto.getDocCategoryMasterId() != null) {
            modelAndView.setViewName("/ui/document/document-creation");
        }
//        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
//        docCategoryTemp.setSystemMenuItemChannel(new SystemMenuItemChannel());
//        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;

    }

    @RequestMapping(value = AppURL.DOCUMENT_UPLOAD_LIST)//getPendingDocList
    public Object getPendingList(@ModelAttribute("user") User user) {
        try {
            return documentUploadService.getAllTempDock(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Object();
        }
    }


    @RequestMapping(value = "/authDocumemt")
    public NotificationMessage authDocument(@RequestParam(value = "docId") int docId, @ModelAttribute("user") User user, ModelAndView modelAndView) {
        try {
            documentUploadService.authDocument(docId, user);
            return new NotificationMessage(1, "Record Successfully Authorized");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new NotificationMessage(0, "System Error, Please Contact System administrator");
        }

    }

    @RequestMapping(value = "/rejectDocumemt")
    public NotificationMessage RejectDocument(@RequestParam(value = "docId") int docId, @RequestParam(value = "reson") String reson, @ModelAttribute("user") User user,HttpSession session) {
        try {
             user = (User) session.getAttribute(AppConstant.USER);
            documentUploadService.rejectDocument(reson, docId, user);
            return new NotificationMessage(1, "Record Successfully rejected");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new NotificationMessage(0, "System Error, Please Contact System administrator");
        }
    }

    @RequestMapping(value = "/viewDocumemt")
    public HttpEntity<byte[]> downloadAttachmentByFilePath(@RequestParam(value = "docId") int docId) throws IOException {

        try {
            DocumentUploadTemp documentUploadTemp = documentUploadService.searchTempDocumentById(docId);
            InputStream inputStream = documentUploadSFTPService.viewUploadedFile(docId, AppConstant.TEMP);
            byte[] fileAttachment = IOUtils.toByteArray(inputStream);
            HttpHeaders headers = new HttpHeaders();
            String mimeType = APPLICATION_PDF;
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentLength(fileAttachment.length);
            headers.setContentDispositionFormData("attachment", documentUploadTemp.getDocumentName() + PDF);
            return new HttpEntity<byte[]>(fileAttachment, headers);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/delete_reject_doc")
    public NotificationMessage deleteRejectDocument(
            @RequestParam("document_id") int document_id, NotificationMessage
            notificationMessage, @ModelAttribute("user") User user) {
        try {
            int result = documentUploadService.deleteRejectDocument(document_id, user);
            if (result == SUCSESS) {
                notificationMessage.setIsSucsess(1);
                notificationMessage.setMessage("Record Rejected successfully");
            } else {
                notificationMessage.setIsSucsess(0);
                notificationMessage.setMessage(COMMON_ERROR_MSG);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            notificationMessage.setIsSucsess(0);
            notificationMessage.setMessage(COMMON_ERROR_MSG);
        }
        return notificationMessage;
    }


    @RequestMapping(value = "/delete_master_document")
    public NotificationMessage deleteMstDocument(@RequestParam("docID") int docId,
                                                 @ModelAttribute("user") User user, NotificationMessage notificationMessage) {
        try {
            boolean isTrue = documentUploadService.deleteMsterDocument(docId, user);
            if (isTrue) {
                notificationMessage.setIsSucsess(SUCSESS);
                notificationMessage.setMessage("Record successfully deleted");
            } else {
                notificationMessage.setIsSucsess(ERROR);
                notificationMessage.setMessage(COMMON_ERROR_MSG);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            notificationMessage.setIsSucsess(ERROR);
            notificationMessage.setMessage(COMMON_ERROR_MSG);
        }
        return notificationMessage;
    }

    @RequestMapping("/delete_auth_doc")
    public NotificationMessage deleteAuthorizationDocument(
            @RequestParam("document_id") int document_id, NotificationMessage notificationMessage) {
        try {
            int result = documentUploadService.deleteAuthorizationDocument(document_id);
            if (result == SUCSESS) {
                notificationMessage.setIsSucsess(1);
                notificationMessage.setMessage("Record submited for authorization");
            } else if (result == RECODE_EXSIST_TEMP) {
                notificationMessage.setIsSucsess(0);
                notificationMessage.setMessage(COMMON_ERROR_MSG);
            } else {
                notificationMessage.setIsSucsess(0);
                notificationMessage.setMessage(COMMON_ERROR_MSG);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            notificationMessage.setIsSucsess(0);
            notificationMessage.setMessage(COMMON_ERROR_MSG);
        }
        return notificationMessage;
    }

    @RequestMapping(value = "/delete_document")
    public NotificationMessage deleteTempDocument(@RequestParam(value = "docID") int docId,
                                                  @ModelAttribute("user") User user, NotificationMessage notificationMessage) {
        try {
            documentUploadService.deleteTempDocument(docId, user);
            notificationMessage.setIsSucsess(SUCSESS);
            notificationMessage.setMessage("Document successfully deleted");
        } catch (Exception e) {
            notificationMessage.setIsSucsess(ERROR);
            notificationMessage.setMessage(COMMON_ERROR_MSG);
        }
        return notificationMessage;
    }

    @RequestMapping(value = "/getRejectedList")
    public Object searchDefermentListByAgent(@ModelAttribute("user") User user) {
        try {
            return documentUploadService.getAllRejectedDoc(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Object();
        }
    }

    @RequestMapping(value = "/modifyResubmitDocument")
    public ModelAndView modifResubmityDoc(@RequestParam(value = "id") int id, @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            List<SystemRole> systemRoles = new ArrayList<>();
            List<AccessUserType> accessUserTypes = new ArrayList<>();
            /*try {
                accessUserTypes = accessUserTypeService.searchAllByChannelCode(user);
                for (AccessUserType accessUserType : accessUserTypes) {
                    List<SystemRole> roles = systemRoleService.searchAllUserRolesByAccessUserType(accessUserType);
                    for (SystemRole role : roles) {
                        systemRoles.add(role);
                    }
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }*/

            modelAndView.addObject("systemList", systemRoles);
            DocumentUploadTemp documentUploadTemp = documentUploadService.searchTempDocumentById(id);
            DocumentUploadDto documentUploadDto = new DocumentUploadDto();
            documentUploadDto.setDocumentUploadTempId(documentUploadTemp.getDocumentUploadTempId());
            documentUploadDto.setDocCategoryMasterId(documentUploadTemp.getDocCategoryMaster().getDocCategoryMstId());
            documentUploadDto.setDocumentName(documentUploadTemp.getDocumentName());
            documentUploadDto.setHeadLine(documentUploadTemp.getHeadline());
            documentUploadDto.setDocumentDescription(documentUploadTemp.getDocumentDescription());
            documentUploadDto.setSystemRoleId(documentUploadTemp.getSystemRoleId());
            List<String> acessTypes = documentUploadDto.getAcessTypes();

            List<SystemRoleDto> allSystemRoles = systemRoleDockUpService.getAllActiveSystemRoles();
            documentUploadDto.setSystemRoleDtos(allSystemRoles);
            /*for (DocumentUploadTempSystemRole documentUploadTempSystemRole : documentUploadTemp.getDocumentUploadTempSystemRoles()) {
                acessTypes.add(Integer.toString(documentUploadTempSystemRole.getSystemRole().getSystemRoleId()));
            }*/
            documentUploadDto.setAcessTypes(acessTypes);
            documentUploadDto.setInpDateTime(Utility.getDateString(documentUploadTemp.getInpDateTime(), AppConstant.DATE_FORMAT));
            documentUploadDto.setPublishDate(documentUploadTemp.getPublishDate());
            documentUploadDto.setExpireDate(documentUploadTemp.getExpireDate());
            documentUploadDto.setPath(documentUploadTemp.getPath());
            modelAndView.addObject("documentUploadDto", documentUploadDto);
            modelAndView.addObject("accessList", documentUploadDto.getAcessTypes());
            modelAndView.setViewName("ui/document/documentresubmitviwer");
//            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, true);
//            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_SUCCESSFULLY_UPDATED));
            return modelAndView;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
//            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPDATE_FAILED));
            return modelAndView;
        }
    }

    @RequestMapping(value = "/modifyDocument")
    public ModelAndView modifyDoc(@RequestParam(value = "id") int id,HttpSession session) {
        try {
            User user = (User) session.getAttribute(AppConstant.USER);
            ModelAndView modelAndView = new ModelAndView();
            List<SystemRole> systemRoles = new ArrayList<>();
            List<AccessUserType> accessUserTypes = new ArrayList<>();
            /*try {
                accessUserTypes = accessUserTypeService.searchAllByChannelCode(user);
                for (AccessUserType accessUserType : accessUserTypes) {
                    List<SystemRole> roles = systemRoleService.searchAllUserRolesByAccessUserType(accessUserType);
                    for (SystemRole role : roles) {
                        systemRoles.add(role);
                    }
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }*/
            modelAndView.addObject("systemList", systemRoles);
            DocumentUploadMaster documentUploadTemp = documentUploadService.searchMstDocumentById(id);
            DocumentUploadDto documentUploadDto = new DocumentUploadDto();
            documentUploadDto.setDocumentUploadTempId(documentUploadTemp.getDocumentUploadMstId());
            documentUploadDto.setDocCategoryMasterId(documentUploadTemp.getDocCategoryMaster().getDocCategoryMstId());
            documentUploadDto.setDocumentName(documentUploadTemp.getDocumentName());
            documentUploadDto.setHeadLine(documentUploadTemp.getHeadline());
            documentUploadDto.setDocumentDescription(documentUploadTemp.getDocumentDescription());

            List<SystemRoleDto> allSystemRoles = systemRoleDockUpService.getAllActiveSystemRoles();
            documentUploadDto.setSystemRoleDtos(allSystemRoles);

            List<String> acessTypes = documentUploadDto.getAcessTypes();
            for (DocumentUploadMasterSystemRole documentUploadTempSystemRole : documentUploadTemp.getDocumentUploadMasterSystemRoles()) {
                acessTypes.add(Integer.toString(documentUploadTempSystemRole.getSystemRole().getSystemRoleId()));
            }
            documentUploadDto.setAcessTypes(acessTypes);
            documentUploadDto.setInpDateTime(Utility.getDateString(documentUploadTemp.getInpDateTime(), AppConstant.DATE_FORMAT));
            documentUploadDto.setPublishDate(documentUploadTemp.getPublishDate());
            documentUploadDto.setExpireDate(documentUploadTemp.getExpireDate());
            documentUploadDto.setPath(documentUploadTemp.getPath());
            documentUploadDto.setSystemRoleId(documentUploadTemp.getSystemRoleId());
            modelAndView.addObject("documentUploadDto", documentUploadDto);
            modelAndView.addObject("accessList", documentUploadDto.getAcessTypes());
            modelAndView.setViewName("ui/document/documentresubmitviwer");
            return modelAndView;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ModelAndView();
        }
    }

    @RequestMapping(value = "/viewMSTDocumemt")
    public HttpEntity<byte[]> downloadMstAttachmentByFilePath(@RequestParam(value = "docId") int docId) throws IOException {

        try {
            DocumentUploadMaster documentUploadTemp = documentUploadService.searchMstDocumentById(docId);
            InputStream inputStream = documentUploadSFTPService.viewUploadedFile(docId, AppConstant.MST);
            byte[] fileAttachment = IOUtils.toByteArray(inputStream);
            HttpHeaders headers = new HttpHeaders();
            String mimeType = APPLICATION_PDF;
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentLength(fileAttachment.length);
            headers.setContentDispositionFormData("attachment", documentUploadTemp.getDocumentName() + PDF);  //newsfeed save with prefix saver name
            return new HttpEntity<byte[]>(fileAttachment, headers);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }


    @RequestMapping(value ="/document_upload_webix_email", method = RequestMethod.GET)
    public Object loadDocForSend(@ModelAttribute("user") User user,HttpSession session) {
        DocCategoryMasterWebix categoryMasters = new DocCategoryMasterWebix();
        try {
            user = (User) session.getAttribute(AppConstant.USER);
            categoryMasters.setData(documentUploadService.createCategoryWebixTableWithUploadDocumentAll(user));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return categoryMasters;
    }
}
