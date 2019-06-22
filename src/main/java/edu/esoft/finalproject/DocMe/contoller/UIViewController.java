package edu.esoft.finalproject.DocMe.contoller;

import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.AppURL;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.dto.Email;
import edu.esoft.finalproject.DocMe.dto.UserDto;
import edu.esoft.finalproject.DocMe.entity.DocCategoryTemp;
import edu.esoft.finalproject.DocMe.utility.ActiveMQEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.JMSException;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/ui")
public class UIViewController {

    @Autowired
    private ActiveMQEmail activeMQEmail;

    @GetMapping(value = "/view")
    public ModelAndView viewPage() {
        ModelAndView modelAndView = new ModelAndView("/document/categorycreation");
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
        try {
            activeMQEmail.sendFromEmail(new Email());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @GetMapping(value = "/categoryCreation")
    public ModelAndView getCategoryCreationPage() {

        ModelAndView modelAndView = new ModelAndView("/ui/category-creation");
        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;
    }

    @GetMapping(value = "/categoryAuth")
    public ModelAndView getCategoryAuthPage() {

        ModelAndView modelAndView = new ModelAndView("/ui/category-auth");
        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;
    }

    @RequestMapping(AppURL.CATEGORY_RESUBMIT)
    public ModelAndView viewCategoryResubmitList5() {
        ModelAndView modelAndView = new ModelAndView("/ui/category-resubmit");
        return modelAndView;
    }

    @RequestMapping(AppURL.DOCUMENT_UPLOAD)
    public ModelAndView viewDocumentUploadList() {
        ModelAndView modelAndView = new ModelAndView("/ui/document-creation");
        DocumentUploadDto documentUploadDto = new DocumentUploadDto();
        modelAndView.addObject("documentUploadDto", documentUploadDto);
        return modelAndView;
    }

    @GetMapping(value = "/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView("/user/register");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/ui/login");
    }
}
