package edu.esoft.finalproject.DocMe.contoller;


import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.AppURL;
import edu.esoft.finalproject.DocMe.config.CommonFunction;
import edu.esoft.finalproject.DocMe.config.EmailMessageConstant;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = AppURL.DOCUMENT_UPLOAD_SAVE, method = RequestMethod.POST)
    public ModelAndView saveDocument(@ModelAttribute("docUploadTempForm") DocumentUploadDto documentUploadDto,
                                     @ModelAttribute("user") User user, ModelAndView modelAndView,
                                     @RequestParam(value = "catId") int catagoryId) {
        try {
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
                modelAndView.addObject(EmailMessageConstant.MSG, "Something went wrong. Please contact system administrator");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(EmailMessageConstant.MSG, "Something went wrong. Please contact system administrator");
        }
        DocumentUploadDto documentUploadDto1 = new DocumentUploadDto();
        modelAndView.addObject("documentUploadDto", documentUploadDto1);
        modelAndView.setViewName("/ui/document-creation");
//        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
//        docCategoryTemp.setSystemMenuItemChannel(new SystemMenuItemChannel());
//        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;

    }
}
