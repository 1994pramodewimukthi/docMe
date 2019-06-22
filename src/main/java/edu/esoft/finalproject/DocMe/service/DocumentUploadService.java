package edu.esoft.finalproject.DocMe.service;


import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.entity.User;

import java.util.List;

public interface DocumentUploadService {

    List<DocCategoryMasterWebix> createCategoryWebixTableWithUploadDocumentAll(User user) throws Exception;

    public int uploadDocumentToCategory(DocumentUploadDto documentUploadDto, User user, int catagoryId) throws Exception;
}
