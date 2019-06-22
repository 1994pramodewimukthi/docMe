package edu.esoft.finalproject.DocMe.contoller;


import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.AppURL;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
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
//
//    @Autowired
//    private CommonFunction commonFunction;

//    @Value("${document.multipart.magic-extension-types}")
//    private String DOCUMENT_VALID_FILE_MAGIC_TYPES;
//
//    @Value("${document.multipart.max-file-size}")
//    private Long DOCUMENT_MAX_FILE_SIZE;

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
}
