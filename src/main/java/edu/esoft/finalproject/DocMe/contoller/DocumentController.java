package edu.esoft.finalproject.DocMe.contoller;

import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @GetMapping(value = "/view")
    public ModelAndView viewPage(){
        ModelAndView modelAndView = new ModelAndView("/document/categorycreation");
        return modelAndView;
    }

    @GetMapping(value = "/list")
    @ResponseBody
    public Object getList() {
        DocCategoryMasterWebix categoryMasters = new DocCategoryMasterWebix();
        try {

            categoryMasters.setData(documentService.createCategoryWebixTable());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryMasters;
    }
}
