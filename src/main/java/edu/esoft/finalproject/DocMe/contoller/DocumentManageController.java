package edu.esoft.finalproject.DocMe.contoller;


import edu.esoft.finalproject.DocMe.config.*;
import edu.esoft.finalproject.DocMe.dto.*;
import edu.esoft.finalproject.DocMe.entity.*;
import edu.esoft.finalproject.DocMe.service.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(AppURL.DOCUMENT_MANAGEMENT)
public class DocumentManageController {
    private static final int SUCSESS = 1;
    private static final int ERROR_EXIST_IN_HIRARCHY = 3;
    private static final int ERROR = 0;
    private static final int RECODE_EXSIST_TEMP = 2;
    private static final int OTHER = 2;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocumentManageController.class);

    @Autowired
    private DocCategoryService docCategoryService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private DocumentUploadService documentUploadService;
    @Autowired
    private DocumentUploadSFTPService documentUploadSFTPService;

    @Autowired
    private SystemRoleDockUpService systemRoleDockUpService;

    @RequestMapping(value = AppURL.SAVE, method = RequestMethod.POST)
    public ModelAndView saveCategory(@ModelAttribute("docCategoryTemp") DocCategoryTemp docCategoryTemp1, BindingResult bindingResult, ModelAndView modelAndView, @ModelAttribute("user") User user) {
        try {
            DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
            int checkCatagorySortingExist = docCategoryService.checkCatagorySortingExistTemp(docCategoryTemp1);
            modelAndView.addObject("docCategoryTemp", docCategoryTemp);
            modelAndView.setViewName("/ui/category/category-creation");
            docCategoryTemp1.setInpUserId(user.getUserName());
            int result = docCategoryService.createNewCategory(docCategoryTemp1);
            if (result == SUCSESS) {
                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, true);
                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.RECORD_SUCCESSFULLY_SUBMITTED_FOR_AUTHORIZATION));
            } else if (result == OTHER) {
                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.CATEGORY_NAME_SHOULDNT_DUPLICATE));
            } else {
                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        return modelAndView;
    }

    @RequestMapping(AppURL.DOCUMENT_CATEGORY_MODIFY_CONTENT)
    public ModelAndView loadModifyCategory(@RequestParam(AppConstant.CATEGORY_ID) int category_id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            DocCategoryMaster master = docCategoryService.loadModifyCategory(category_id);
            if (master.getParentDocCategoryMst() == null) {
                master.setParentDocCategoryMst(new DocCategoryMaster());
            }
            modelAndView.addObject("master", master);
            modelAndView.setViewName("/ui/category/modifycategorycontent");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = AppURL.DOCUMENT_CATEGORY_MODIFY, method = RequestMethod.POST)
    public ModelAndView ModifyCategory(@ModelAttribute("master") DocCategoryMaster master, BindingResult bindingResult, ModelAndView modelAndView, @ModelAttribute("user") User user) {
        try {
            master.setInpUserId(user.getUserName());
            int checkCatagorySortingExist = docCategoryService.checkCatagorySortingExistMst(master);
            if (SUCSESS == checkCatagorySortingExist) {
                int result = docCategoryService.ModifyCategory(master);
                if (result == SUCSESS) {
                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, true);
                    modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.RECORD_SUCCESSFULLY_SUBMITTED_FOR_AUTHORIZATION));
                } else if (result == RECODE_EXSIST_TEMP) {
                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                    modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.FAILED_SUBMITTED_FOR_AUTHORIZATION));
                } else {
                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                    modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
                }
            } else if (RECODE_EXSIST_TEMP == checkCatagorySortingExist || ERROR_EXIST_IN_HIRARCHY == checkCatagorySortingExist) {
                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.SORTING_ORDER_ALREADY_EXIST));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        modelAndView.setViewName("/ui/category/category-creation");
        return modelAndView;
    }

    @RequestMapping(value = AppURL.GET_PENDING_AUTH_LIST, method = RequestMethod.GET)
    public AuthRejectCategoryWebix getPendingAthLst(@ModelAttribute("user") User user) {
        try {
            return docCategoryService.getPendingCategoryList(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping("/authRejectModalViwer")
    public ModelAndView athRejectCategoryModalContent(@RequestParam(AppConstant.CATEGORY_ID) int category_id, @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("/ui/category/authrizeviwer");
        try {
            DocCategoryTemp categoryTemp = docCategoryService.getCategoryById(category_id);
            if (categoryTemp.getParentDocCategoryTemp() == null) {
                categoryTemp.setParentDocCategoryTemp(0);
            }
            modelAndView.addObject("temp", categoryTemp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;

    }

    @RequestMapping(value = AppURL.AUTHORIZED_CATEGRY, method = RequestMethod.GET)
    public NotificationMessage autorizedCategory(@RequestParam(AppConstant.CATEGORY_ID) int category_id, @ModelAttribute("user") User user) {
        NotificationMessage notificationMessage = new NotificationMessage();
        try {
            int result = docCategoryService.authCategory(category_id, user.getUserName());
            if (result == SUCSESS) {
                notificationMessage.setIsSucsess(1);
                notificationMessage.setMessage(messageService.getSystemMessage(MessageConstant.CATEGORY_AUTHORIZE_SUCSESSFULLY));
            } else {
                notificationMessage.setIsSucsess(0);
                notificationMessage.setMessage(messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            notificationMessage.setIsSucsess(0);
            notificationMessage.setMessage(messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        return notificationMessage;
    }

    @RequestMapping(value = AppURL.REJECT_CATEGORY, method = RequestMethod.GET)
    public NotificationMessage rejectCategory(@PathVariable(AppConstant.REJECT_REASON) String reason, @PathVariable(AppConstant.CATEGORY_ID) int category_id, @ModelAttribute("user") User user) {
        NotificationMessage notificationMessage = new NotificationMessage();
        try {
            int result = docCategoryService.rejectCategory(category_id, reason, user.getUserName());
            if (result == SUCSESS) {
                notificationMessage.setIsSucsess(1);
                notificationMessage.setMessage(messageService.getSystemMessage(MessageConstant.CATEGORY_REJECT_SUCSESSFULLY));
            } else {
                notificationMessage.setIsSucsess(0);
                notificationMessage.setMessage(messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            notificationMessage.setIsSucsess(0);
            notificationMessage.setMessage(messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        return notificationMessage;
    }


    @GetMapping(AppURL.RESUBMIT)
    public ModelAndView Resubmit(@RequestParam(AppConstant.CATEGORY_ID) int category_id, @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("/ui/category/categoryresubmit1");
        try {
            DocCategoryTemp categoryTemp = docCategoryService.getCategoryById(category_id);
            if (categoryTemp.getParentDocCategoryTemp() == null) {
                categoryTemp.setParentDocCategoryTemp(0);
            }
            modelAndView.addObject("temp", categoryTemp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;

    }

    @RequestMapping(value = AppURL.GET_REJECTED_LIST, method = RequestMethod.GET)
    public AuthRejectCategoryWebix getRejectedLst() {
        try {
            return docCategoryService.getRejectedLst();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = AppURL.UPDATE_CRESUBMIT_CATEGORY, method = RequestMethod.POST)
    public ModelAndView UpdateRejecedCategory(@ModelAttribute("temp") DocCategoryTemp temp, ModelAndView modelAndView, @ModelAttribute("user") User user) {
        try {
            temp.setInpUserId(user.getUserName());
            int checkCatagorySortingExist = docCategoryService.checkCatagorySortingExistTemp(temp);
            if (SUCSESS == checkCatagorySortingExist) {
                int result = docCategoryService.updateRejectedCategory(temp);
                if (result == SUCSESS) {
                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, true);
                    modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.RECORD_SUCCESSFULLY_SUBMITTED_FOR_AUTHORIZATION));
//                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.CATEGORY_RESUBMIT_SUCSESSFULLY));
                } else {
                    modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                    modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
                }
            } else if (RECODE_EXSIST_TEMP == checkCatagorySortingExist || ERROR_EXIST_IN_HIRARCHY == checkCatagorySortingExist) {
                modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.SORTING_ORDER_ALREADY_EXIST));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        modelAndView.setViewName("/ui/category/category-resubmit");
        return modelAndView;
    }

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

    @RequestMapping("/authRejectDocModalViwer")
    public ModelAndView athRejectDocModalContent(@RequestParam(value = "docId") int docId, @ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("/ui/document/documentauthrizeviwer");
        try {
            List<AccessUserType> accessUserTypes = new ArrayList<>();
            /*try {
                accessUserTypes = accessUserTypeService.searchAllByChannelCode(user);
                for (AccessUserType accessUserType : accessUserTypes) {
                    List<SystemRole> roles = systemRoleService.searchAllUserRolesByAccessUserType(accessUserType);
                    for (SystemRole role : roles) {
                        systemRoles.add(role);
                    }
                }

            } catch (MisynJDBCException e) {
                LOGGER.error(e.getMessage());
            }*/
            List<SystemRoleDto> allActiveSystemRoles = systemRoleDockUpService.getAllActiveSystemRoles();

            modelAndView.addObject("systemList", allActiveSystemRoles);
            DocumentUploadTemp documentUploadTemp = documentUploadService.searchTempDocumentById(docId);
            DocumentUploadDto documentUploadDto = new DocumentUploadDto();
            documentUploadDto.setDocumentUploadTempId(documentUploadTemp.getDocumentUploadTempId());
            documentUploadDto.setDocCategoryMasterId(documentUploadTemp.getDocCategoryMaster().getDocCategoryMstId());
            documentUploadDto.setDocumentName(documentUploadTemp.getDocumentName());
            documentUploadDto.setHeadLine(documentUploadTemp.getHeadline());
            documentUploadDto.setDocumentDescription(documentUploadTemp.getDocumentDescription());
            documentUploadDto.setSystemRoleId(documentUploadTemp.getSystemRoleId());
            List<String> acessTypes = documentUploadDto.getAcessTypes();
            for (DocumentUploadTempSystemRole documentUploadTempSystemRole : documentUploadTemp.getDocumentUploadTempSystemRoles()) {
                acessTypes.add(Integer.toString(documentUploadTempSystemRole.getSystemRole().getSystemRoleId()));
            }
            documentUploadDto.setAcessTypes(acessTypes);
            documentUploadDto.setRecordStatus(documentUploadTemp.getRecordStatus());
            documentUploadDto.setInpDateTime(Utility.getDateString(documentUploadTemp.getInpDateTime(), AppConstant.DATE_FORMAT));
            documentUploadDto.setPublishDate(documentUploadTemp.getPublishDate());
            documentUploadDto.setExpireDate(documentUploadTemp.getExpireDate());
            documentUploadDto.setPath(documentUploadTemp.getPath());
            modelAndView.addObject("documentUploadDto", documentUploadDto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;

    }


    @RequestMapping(value = "/email-send", method = RequestMethod.POST)
    public ModelAndView sendEmail(@ModelAttribute("email") Email email, ModelAndView modelAndView) {
        try {
            InputStream inputStream = documentUploadSFTPService.viewUploadedFile(Integer.parseInt(email.getDocId()), AppConstant.MST);
            email.setDocInputStream(IOUtils.toByteArray(inputStream));
            int result = documentUploadService.sendEmail(email);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        return modelAndView;
    }

}
