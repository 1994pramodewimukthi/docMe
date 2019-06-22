package edu.esoft.finalproject.DocMe.contoller;


import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.AppURL;
import edu.esoft.finalproject.DocMe.config.CommonFunction;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppURL.DOCUMENT_UPLOAD_CONTROLLER)
public class DocumentUploadController {

    private static final String PDF = ".pdf";
    private static final String MB = "MB";
    private static final int DOC_SIZE = 25;
    private static final String APPLICATION_PDF = "application/pdf";
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
//    @Autowired
//    private DocumentUploadSFTPService documentUploadSFTPService;
//    @Autowired
//    private MessageService messageService;
//    @Autowired
//    AccessUserTypeService accessUserTypeService;
    @Autowired
    private CommonFunction commonFunction;

    @Value("${document.multipart.magic-extension-types}")
    private String DOCUMENT_VALID_FILE_MAGIC_TYPES;

    @Value("${document.multipart.max-file-size}")
    private Long DOCUMENT_MAX_FILE_SIZE;

    @RequestMapping(value = AppURL.DOCUMENT_UPLOAD_WEBIX_CAT, method = RequestMethod.GET)
    public Object loadAuthorizedUser(@ModelAttribute("user") User user) {
        DocCategoryMasterWebix categoryMasters = new DocCategoryMasterWebix();
        try {
            categoryMasters.setData(documentUploadService.createCategoryWebixTableWithUploadDocumentAll(user));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return categoryMasters;
    }

//    @RequestMapping(value = AppURL.DOCUMENT_UPLOAD_SAVE, method = RequestMethod.POST)
//    public ModelAndView saveDocument(@ModelAttribute("docUploadTempForm") DocumentUploadDto documentUploadDto, @ModelAttribute("user") User user, ModelAndView modelAndView, @RequestParam(value = "catId") int catagoryId, @ModelAttribute(AppConstant.SESSION_NAVIGATION_MENU_MAP) Map<Integer, NavigationMenuDto> navigationMenuMap) {
//        try {
//            if (!documentUploadDto.getAttachment().getOriginalFilename().equals(AppConstant.STRING_EMPTY)) {
//                if (commonFunction.checkIsValidFileType(documentUploadDto.getAttachment(), DOCUMENT_VALID_FILE_MAGIC_TYPES.split(AppConstant.STRING_COMMA))) {
//                    if (commonFunction.isFileSizeSufficient(documentUploadDto.getAttachment(), DOCUMENT_MAX_FILE_SIZE)) {
//
////                    if (APPLICATION_PDF.equals(documentUploadDto.getAttachment().getContentType())) {
//                        int i = documentUploadService.uploadDocumentToCategory(documentUploadDto, user, catagoryId);
//                        if (i == VIRUS_FILE_MESSAGE) {
//                            List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//                            modelAndView.addObject("chanelList", chanelList);
//                            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//                            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.VIRUS_DETECTED_ERROR_MESSAGE));
//                        } else if (i == SUCSESS) {
//                            List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//                            modelAndView.addObject("chanelList", chanelList);
//                            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, true);
//                            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.SUCCESSFULLY_SUBMITED_FOR_AUTH));
//                        } else {
//
//                        }
//                    } else {
//                        List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//                        modelAndView.addObject("chanelList", chanelList);
//                        modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//                        modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.FILE_SIZE_BIGGER_THAN_PARAM)+DOCUMENT_MAX_FILE_SIZE+MB);
//                    }
//                } else {
//                    List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//                    modelAndView.addObject("chanelList", chanelList);
//                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//                    modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.FILE_TYPE_EXTENTION));//EXEIED_DOC_SIZE
//                }
//            } else {
//                documentUploadService.uploadDocumentToCategory(documentUploadDto, user, catagoryId);
//                List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//                modelAndView.addObject("chanelList", chanelList);
//                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
//            }
//        } catch (VirusScannerException ex) {
//            LOGGER.error(ex.getMessage());
//            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.VIRUS_SCAN_EXEPTION));
//            List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//            modelAndView.addObject("chanelList", chanelList);
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
//            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
//            List<SystemMenuItemChannel> chanelList = menuItemService.findAllMenuItemChanelNotBoth();
//            modelAndView.addObject("chanelList", chanelList);
//        }
//        DocumentUploadDto documentUploadDto1 = new DocumentUploadDto();
//        modelAndView.addObject("documentUploadDto", documentUploadDto1);
//        modelAndView.setViewName("docmanagement/docupload/docupload");
//        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
//        docCategoryTemp.setSystemMenuItemChannel(new SystemMenuItemChannel());
//        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
//        setNavigationMenuListByModelAndView(modelAndView, navigationMenuMap);
//        return modelAndView;
//
//    }
}
