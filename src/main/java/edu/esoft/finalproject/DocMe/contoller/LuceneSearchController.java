package edu.esoft.finalproject.DocMe.contoller;

import edu.esoft.finalproject.DocMe.dto.ResultDto;
import edu.esoft.finalproject.DocMe.service.DocumentUploadService;
import edu.esoft.finalproject.DocMe.service.LuceneSearchService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/lucene")
public class LuceneSearchController {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LuceneSearchController.class);

    @Autowired
    private LuceneSearchService luceneSearchService;

    private static final String PDF = ".pdf";
    private static final String APPLICATION_PDF = "application/pdf";

    @GetMapping(value = "/search-by-key-view")
    public ModelAndView keyWordSerchView() {
        ModelAndView modelAndView = new ModelAndView("/ui/lucene/search-doc-by-keyword");
        ResultDto result = new ResultDto();
        modelAndView.addObject("resultDto", result);
        return modelAndView;
    }

    @GetMapping(value = "/searchKey")
    public ModelAndView searchKey(@RequestParam("key") String key) {
        ModelAndView modelAndView = new ModelAndView("/ui/lucene/search-table-content");
        try {
            ResultDto result = luceneSearchService.luenceKeyWordSearch(key);
            modelAndView.addObject("resultDto", result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = "/preview")
    public HttpEntity<byte[]> searchKey(@RequestParam("page") String page, @RequestParam("key") String key, @RequestParam("filename") String filename, HttpServletResponse response) {
        try {
            byte[] data = luceneSearchService.preview(Integer.parseInt(page), key, filename);

            HttpHeaders headers = new HttpHeaders();
            String mimeType = APPLICATION_PDF;
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentLength(data.length);
            headers.setContentDispositionFormData("attachment", filename + PDF);  //newsfeed save with prefix saver name
            return new HttpEntity<byte[]>(data, headers);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}
