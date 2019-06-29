package edu.esoft.finalproject.DocMe.contoller;

import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.AppURL;
import edu.esoft.finalproject.DocMe.config.ModuleConstant;
import edu.esoft.finalproject.DocMe.dto.*;
import edu.esoft.finalproject.DocMe.entity.DocCategoryTemp;
import edu.esoft.finalproject.DocMe.service.MarketingConductGridlinesService;
import edu.esoft.finalproject.DocMe.utility.ActiveMQEmail;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/ui")
public class UIViewController {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MarketingConductGridlinesController.class);

    @Autowired
    private ActiveMQEmail activeMQEmail;

    @Autowired
    private MarketingConductGridlinesService marketingConductGridlinesService;

    @GetMapping(value = "/view")
    public ModelAndView viewPage() {
        ModelAndView modelAndView = new ModelAndView("/document/categorycreation");
        return modelAndView;
    }

    @GetMapping(value = "/uploadAgreement")
    public ModelAndView getUploadAgreement() {
        ModelAndView modelAndView = new ModelAndView("/ui/upload-agreement");
        return modelAndView;
    }

    @GetMapping(value = "/addAgreementType")
    public ModelAndView getAgreementType() {
        ModelAndView modelAndView = new ModelAndView("/ui/add-agreement-type");
        return modelAndView;
    }

    @GetMapping(value = "/login")
    public ModelAndView getLogin(HttpSession session) {
        ModelAndView modelAndView;
        if (null != session.getAttribute(AppConstant.USER)) {
            modelAndView = new ModelAndView("redirect:/ui/categoryCreation");
        } else {
            UserDto userDto = new UserDto();
            modelAndView = new ModelAndView("/ui/login");
            modelAndView.addObject("userDetailsDto", userDto);
        }
        return modelAndView;
    }

    @GetMapping(value = "/categoryCreation")
    public ModelAndView getCategoryCreationPage() {

        ModelAndView modelAndView = new ModelAndView("/ui/category/category-creation");
        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;
    }

    @GetMapping(value = "/categoryAuth")
    public ModelAndView getCategoryAuthPage() {

        ModelAndView modelAndView = new ModelAndView("/ui/category/category-auth");
        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;
    }

    @RequestMapping(AppURL.CATEGORY_RESUBMIT)
    public ModelAndView viewCategoryResubmitList5() {
        ModelAndView modelAndView = new ModelAndView("/ui/category/category-resubmit");
        return modelAndView;
    }

    //documentUpload
    @RequestMapping(AppURL.DOCUMENT_UPLOAD)
    public ModelAndView viewDocumentUploadList() {
        ModelAndView modelAndView = new ModelAndView("/ui/document/document-creation");
        DocumentUploadDto documentUploadDto = new DocumentUploadDto();

        modelAndView.addObject("documentUploadDto", documentUploadDto);
        return modelAndView;
    }

    @GetMapping(value = "/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView("/user/register");
        return modelAndView;
    }

    @GetMapping(value = "/documentAuth")
    public ModelAndView getDocumentAuthPage() {
        ModelAndView modelAndView = new ModelAndView("/ui/document/document-auth");
        return modelAndView;
    }

    @GetMapping(value = "/documentResubmit")
    public ModelAndView getDocumentRejectedPage() {
        ModelAndView modelAndView = new ModelAndView("/ui/document/document-resubmit");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/ui/login");
    }


    @RequestMapping("/send_document")
    public ModelAndView viewDocumentListForSendEmail() {
        ModelAndView modelAndView = new ModelAndView("/ui/document-send-by-email");
        Email email = new Email();
        modelAndView.addObject("email", email);
        return modelAndView;
    }

    @RequestMapping("/agreementCategory")
    public ModelAndView agreementCategory() {
        ModelAndView modelAndView = new ModelAndView("/ui/add-agreement-type");
        try {
            modelAndView.addObject("mcgDocumentCategorys", marketingConductGridlinesService.getAllCategorys());
            modelAndView.addObject("documentCategoryDto", new DocumentCategoryDto());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(AppURL.MCG_ADD_NEW_DOCUMENT)
    public ModelAndView addDocument() {
        ModelAndView modelAndView = new ModelAndView("/ui/upload-agreement");

        try {
            McgDocumentUploadDto mcgDocumentUploadDto = new McgDocumentUploadDto();
            mcgDocumentUploadDto.setDocumentAcknowledgement(ModuleConstant.ACKNOWLEDGMENT);
            ArrayList<DocumentCategoryDto> categoryList = (ArrayList<DocumentCategoryDto>) marketingConductGridlinesService.getAllCategorys();
            modelAndView.addObject("categoryList", categoryList);
            modelAndView.addObject("mcgDocumentUploadDto", mcgDocumentUploadDto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }
}
