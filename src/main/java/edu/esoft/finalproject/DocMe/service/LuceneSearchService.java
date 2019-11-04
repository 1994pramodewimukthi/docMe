package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.ResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LuceneSearchService {

    void createLuinceSearch(MultipartFile file,String fileName) throws IOException;

    ResultDto luenceKeyWordSearch(String key)throws Exception;

    byte[] preview(int page, String key, String filename)throws Exception;
}
