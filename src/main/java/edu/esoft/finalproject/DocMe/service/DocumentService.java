package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;

import java.util.List;

public interface DocumentService {

    List<DocCategoryMasterWebix> createCategoryWebixTable() throws Exception;
}
