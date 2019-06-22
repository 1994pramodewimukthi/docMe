package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.AccessUserTypeDto;
import edu.esoft.finalproject.DocMe.dto.DocumentCategoryDto;
import edu.esoft.finalproject.DocMe.dto.DocumentExpireDto;
import edu.esoft.finalproject.DocMe.entity.User;

import java.util.List;

public interface MarketingConductGridlinesService {

    Long saveCategory(DocumentCategoryDto documentCategoryDto, User user) throws Exception;

    Long saveExpireYear(DocumentExpireDto documentExpireDto, User user) throws Exception;

    List<DocumentCategoryDto> getAllCategorys() throws Exception;

    DocumentCategoryDto getCategoryById(String catId) throws Exception;

    Long updateCategory(DocumentCategoryDto documentCategoryDto, User user) throws Exception;

    DocumentExpireDto getExpireYear() throws Exception;

}
