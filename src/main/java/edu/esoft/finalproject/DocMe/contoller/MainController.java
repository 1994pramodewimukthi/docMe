package edu.esoft.finalproject.DocMe.contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RestController
public class MainController extends WebMvcConfigurerAdapter {

    @RequestMapping(value = "/")
    public ModelAndView rootLocation() {
        return new ModelAndView("redirect:/ui/login");
    }

}
