package edu.esoft.finalproject.DocMe.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @RequestMapping(value = "/")
    public ModelAndView rootLocation() {
        return new ModelAndView("redirect:/ui/login");
    }
}
