package edu.esoft.finalproject.DocMe.service;


import edu.esoft.finalproject.DocMe.dto.DocAuthDto;
import edu.esoft.finalproject.DocMe.dto.DocCategoryMasterWebix;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.entity.DocumentUploadMaster;
import edu.esoft.finalproject.DocMe.entity.DocumentUploadTemp;
import edu.esoft.finalproject.DocMe.entity.User;

import java.util.List;

public interface DocumentUploadService {

    List<DocCategoryMasterWebix> createCategoryWebixTableWithUploadDocumentAll(User user) throws Exception;

    public int uploadDocumentToCategory(DocumentUploadDto documentUploadDto, User user, int catagoryId) throws Exception;

    public List<DocAuthDto> getAllTempDock(User user) throws Exception;

    public DocumentUploadTemp searchTempDocumentById(int id) throws Exception;

    public boolean authDocument(int id, User user) throws Exception;

    public void rejectDocument(String reson, int id, User user) throws Exception;

    int deleteRejectDocument(int document_id, User user) throws Exception;

    boolean deleteMsterDocument(int docId, User user) throws Exception;

    int deleteAuthorizationDocument(int document_id) throws Exception;

    boolean deleteTempDocument(int docId, User user) throws Exception;

    Object getAllRejectedDoc(User user) throws Exception;

    DocumentUploadMaster searchMstDocumentById(int id) throws Exception;
}
