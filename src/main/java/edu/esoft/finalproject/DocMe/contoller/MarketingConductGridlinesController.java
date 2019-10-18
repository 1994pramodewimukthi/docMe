package edu.esoft.finalproject.DocMe.contoller;


import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.AppURL;
import edu.esoft.finalproject.DocMe.config.EmailMessageConstant;
import edu.esoft.finalproject.DocMe.config.MessageConstant;
import edu.esoft.finalproject.DocMe.dto.*;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.service.MCGDocumentUploadService;
import edu.esoft.finalproject.DocMe.service.MarketingConductGridlinesService;
import edu.esoft.finalproject.DocMe.service.MessageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

@RestController
@RequestMapping(value = AppURL.MCG)
public class MarketingConductGridlinesController {

    private static final Long SUCSESS = 1L;
    private static final Long ERROR = 0L;
    private static final String IS_SUCSESS = "isSucsess";
    private static final String MSG = "msg";
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MarketingConductGridlinesController.class);
    @Autowired
    private MarketingConductGridlinesService marketingConductGridlinesService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MCGDocumentUploadService mcgDocumentUploadService;


    @GetMapping(value = AppURL.MCG_VIEW_UPDATE_CATEGORY)
    public ModelAndView viewUpdateCategory(@PathVariable("catId") String catId) {
        ModelAndView modelAndView = new ModelAndView("/ui/mcg/category_update_modal");

        try {
            modelAndView.addObject("documentCategoryDto", marketingConductGridlinesService.getCategoryById(catId));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(value = AppURL.MCG_SAVE_CATEGORY)
    public ModelAndView saveCategory(@ModelAttribute("documentCategoryDto") DocumentCategoryDto documentCategoryDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/ui/mcg/add-agreement-type");

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            Long result = marketingConductGridlinesService.saveCategory(documentCategoryDto, user);
            modelAndView.addObject("action", "ADD");
            if (result.equals(SUCSESS)) {
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.INFO_MESSAGE_SUCCESSFULLY_SAVED));
            } else if (result > ERROR) {
                modelAndView.addObject(IS_SUCSESS, false);
                DocumentCategoryDto dto = new DocumentCategoryDto();
                BeanUtils.copyProperties(documentCategoryDto, dto);
                modelAndView.addObject("documentCategoryDto", dto);
                modelAndView.addObject("mcgDocumentCategorys", marketingConductGridlinesService.getAllCategorys());
                modelAndView.addObject(MSG, messageService.getSystemMessage(result));
            } else {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(EmailMessageConstant.MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
        }
        return modelAndView;
    }

    @PostMapping(value = AppURL.MCG_UPDATE_CATEGORY)
    public ModelAndView updateCategory(@ModelAttribute("documentCategoryDto") DocumentCategoryDto documentCategoryDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/ui/mcg/add-agreement-type");

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            modelAndView.addObject("action", "UPDATE");
            Long result = marketingConductGridlinesService.updateCategory(documentCategoryDto, user);
            if (result.equals(SUCSESS)) {
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.INFO_MESSAGE_SUCCESSFULLY_UPDATED));
            } else if (result > ERROR) {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(result));
            } else {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(EmailMessageConstant.IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
        }
        return modelAndView;
    }


    @PostMapping(AppURL.MCG_UPLOAD_NEW_DOCUMENT)
    public ModelAndView uploadDocument(@ModelAttribute("mcgDocumentUploadDto") McgDocumentUploadDto mcgDocumentUploadDto, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView(AppURL.MCG_ADD_DOCUMENT_MODEL);
        User user = (User) session.getAttribute(AppConstant.USER);

        try {
            Long result = mcgDocumentUploadService.mcgDocumentUpload(mcgDocumentUploadDto, user);
            ArrayList<DocumentCategoryDto> categoryList = (ArrayList<DocumentCategoryDto>) marketingConductGridlinesService.getAllCategorys();
            modelAndView.addObject("categoryList", categoryList);
            modelAndView.addObject("mcgDocumentUploadDto", mcgDocumentUploadDto);
            if (result.equals(SUCSESS)) {
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPLOAD_SUCCESS));
            } else if (result > ERROR) {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(result));
            } else {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPLOAD_FAILD));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPLOAD_FAILD));
        }
        return modelAndView;
    }


    @RequestMapping(value = AppURL.MCG_VIEW_PDF, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView viewPdf(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(AppURL.MCG_ADD_DOCUMENT_VIEW_MODEL);
        User user = (User) request.getSession().getAttribute(AppConstant.USER);
        try {
            if (null != user) {
                McgPdfDto mcgPdfDto = mcgDocumentUploadService.viewMCGDocument(user);
                if (null != mcgPdfDto) {
                    modelAndView.addObject("mcgPdfDto", mcgPdfDto);
                }
            } else {
                modelAndView = new ModelAndView(AppURL.REDIRECT_LOGIN_URL);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject("mcgPdfDto", new McgPdfDto(""));
        }
        return modelAndView;
    }

    @RequestMapping(value = AppURL.MCG_VIEW_PDF_DIRECT, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView viewPdfDirect(HttpServletRequest request) {
        final ModelAndView modelAndView = new ModelAndView(AppURL.MCG_ADD_DOCUMENT_VIEW_MODEL);
        User user = (User) request.getSession().getAttribute("USER");
        try {
            modelAndView.addObject("mcgPdfDto", mcgDocumentUploadService.viewMCGDocument(user));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(AppURL.MCG_UPLOAD_NEW_SIGNATURE)
    public ModelAndView uploadSignature(@ModelAttribute("mcgPdfDto") McgPdfDto mcgPdfDto, HttpSession session) {

        final ModelAndView modelAndView = new ModelAndView(AppURL.MCG_ADD_DOCUMENT_VIEW_MODEL);

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            final McgPdfDto mcgPdfDtoAfterSign = mcgDocumentUploadService.singInDocument(mcgPdfDto, user);

            if (null == mcgPdfDtoAfterSign) {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_SIGN_FAILED));
                McgPdfDto mcgPdfDto1 = mcgDocumentUploadService.viewMCGDocument(user);
                modelAndView.addObject("mcgPdfDto", mcgPdfDto1);
            } else {
                modelAndView.addObject("mcgPdfDto", mcgPdfDtoAfterSign);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject("mcgPdfDto", new McgPdfDto(""));
        }

        return modelAndView;
    }

    @PostMapping(AppURL.MCG_UPLOAD_SIGN_PDF)
    public ModelAndView uploadSignPdf(@ModelAttribute("mcgPdfDto") McgPdfDto mcgPdfDto, HttpSession session) {
        final ModelAndView modelAndView = new ModelAndView(AppURL.MCG_ADD_DOCUMENT_VIEW_MODEL);

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            Long result = mcgDocumentUploadService.uploadSignPdf(mcgPdfDto, user);

            mcgPdfDto.setPdfFile(mcgPdfDto.getPdfFile());
            modelAndView.addObject("mcgPdfDto", mcgPdfDto);
            if (result.equals(SUCSESS)) {
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPLOAD_SUCCESS));
            } else {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPLOAD_FAILD));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject("mcgPdfDto", new McgPdfDto(""));
        }
        return modelAndView;
    }


    @GetMapping(AppURL.MCG_GET_DOCUMENT_VERSION)
    public String getLatestVersion(@PathVariable("catId") String catId) {
        String latestVersion = "";
        try {
            latestVersion = mcgDocumentUploadService.getLatestVersion(catId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return latestVersion;
    }



    @PostMapping(value = AppURL.MCG_SAVE_DOC_UPDATE)
    public ModelAndView saveDocumentUpdate(@ModelAttribute("mcgDocumentUpdateDto") McgDocumentUpdateDto mcgDocumentUpdateDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/ui/category/updateCurrentAgreement");

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            Long result = mcgDocumentUploadService.saveDocumentUpdate(mcgDocumentUpdateDto, user);
            modelAndView.addObject("mcgDocumentUpdateDto", mcgDocumentUploadService.getValidDocument(user));

            if (result.equals(SUCSESS)) {
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_SUCCESSFULLY_UPDATED));
            } else if (result > ERROR) {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(result));
            } else {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPDATE_FAILED));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPDATE_FAILED));
        }

        return modelAndView;
    }



    //view doc list related to user role
    @GetMapping(value = AppURL.MCG_VIEW_RELATED_DOC)
    public ModelAndView viewSignedDocument(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView(AppURL.MCG_RELATED_DOCUMENT);

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            modelAndView.addObject("mcgDocumentUploadDtos", mcgDocumentUploadService.getAllDocumentForUser(user));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return modelAndView;
    }

    @GetMapping(value = AppURL.MCG_VIEW_DOCUMENT_IS_VALID)
    public Boolean viewDocumentIsValid(@PathVariable("docId") String docId) {
        Boolean pdfIsValid = false;
        try {
            pdfIsValid = mcgDocumentUploadService.getPdfIsValid(docId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return pdfIsValid;
    }

    //open PDF file using doc id
    @GetMapping(value = AppURL.MCG_VIEW_UPLOADED_DOCUMENT)
    public ModelAndView viewDocument(HttpSession session, @PathVariable("docId") String docId) {
        ModelAndView modelAndView = new ModelAndView(AppURL.MCG_VIEW_SELECT_DOCUMENT);

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            McgPdfDto mcgPdfDto = mcgDocumentUploadService.getPdf(user, docId);
            if (null != mcgPdfDto) {
                modelAndView.addObject("mcgPdfDto", mcgPdfDto);
            } else {
                modelAndView = new ModelAndView("redirect:" + AppURL.MCG + AppURL.MCG_VIEW_PDF);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView = new ModelAndView(AppURL.ERROR_PAGE_404_URL);
        }
        return modelAndView;
    }

    //open PDF file using doc id and agent code
    @GetMapping(value = AppURL.MCG_VIEW_DOCUMENT_REPORT)
    public ModelAndView viewDocumentForReport(@PathVariable("agentCode") String agentCode, @PathVariable("docId") String docId) {
        ModelAndView modelAndView = new ModelAndView(AppURL.MCG_VIEW_SELECT_DOCUMENT);
        try {
            McgPdfDto mcgPdfDto = mcgDocumentUploadService.getPdfForReport(agentCode, docId);
            if (null != mcgPdfDto) {
                modelAndView.addObject("mcgPdfDto", mcgPdfDto);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView = new ModelAndView(AppURL.ERROR_PAGE_404_URL);
        }
        return modelAndView;
    }

    @PostMapping(value = AppURL.MCG_PREVIEW_DOCUMENT)
    public String preview(@ModelAttribute("mcgDocumentUploadDto") McgDocumentUploadDto mcgDocumentUploadDto, HttpSession session) {
        String result = "";

        User user = (User) session.getAttribute(AppConstant.USER);
        try {
            result = mcgDocumentUploadService.previewPdf(mcgDocumentUploadDto, user);
            long parseLong = Long.parseLong(result);
            return Base64.getEncoder().encodeToString(messageService.getSystemMessage(parseLong).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return result;
        }
    }

    @GetMapping(AppURL.MCG_SKIP_SIGN_PDF)
    public ModelAndView skipSign(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = null;
        try {
            User user = (User) request.getSession().getAttribute(AppConstant.USER);
            if (mcgDocumentUploadService.skipSign(user)) {
                session.setAttribute("mcgSkip", true);
                modelAndView = new ModelAndView("redirect:/user/home");
            } else {
                session.setAttribute("mcgSkip", false);
                modelAndView = new ModelAndView(AppURL.MCG_REDIRECT_VIEW_AGREEMENT);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(value = AppURL.MCG_SAVE_EXPIRE)
    public ModelAndView saveExpireYear(@ModelAttribute("documentExpireDto") DocumentExpireDto documentExpireDto, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/ui/category/UpdateRetentionPeriod");

        try {
            User user = (User) request.getSession().getAttribute(AppConstant.USER);
            Long result = marketingConductGridlinesService.saveExpireYear(documentExpireDto, user);
            modelAndView.addObject("documentExpireDto", marketingConductGridlinesService.getExpireYear());
            if (result.equals(SUCSESS)) {
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.INFO_MESSAGE_SUCCESSFULLY_UPDATED));
            } else {
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPDATE_FAILED));
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject(IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.DOCUMENT_UPLOAD_FAILD));
        }
        return modelAndView;
    }
}
