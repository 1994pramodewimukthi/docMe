package edu.esoft.finalproject.DocMe.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import edu.esoft.finalproject.DocMe.dto.McgDocumentUpdateDto;
import edu.esoft.finalproject.DocMe.dto.McgDocumentUploadDto;
import edu.esoft.finalproject.DocMe.dto.McgPdfDto;
import edu.esoft.finalproject.DocMe.entity.User;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

public interface MCGDocumentUploadService {
    Long mcgDocumentUpload(McgDocumentUploadDto mcgDocumentUploadDto, User user) throws Exception;

    McgPdfDto viewMCGDocument(User user) throws Exception;

    McgPdfDto singInDocument(McgPdfDto mcgPdfDto, User user) throws Exception;

    boolean isMCGDocAvilable(User user) throws Exception;

    boolean skipSign(User user) throws Exception;

    Long uploadSignPdf(McgPdfDto mcgPdfDto, User user) throws Exception;

    String getLatestVersion(String catId) throws Exception;

    McgDocumentUpdateDto getValidDocument(User user) throws Exception;

    Long saveDocumentUpdate(McgDocumentUpdateDto mcgDocumentUpdateDto, User user) throws Exception;

    public InputStream viewUploadedDocument(String folderPath, String savePath) throws SftpException, JSchException;

    Boolean getPdfIsValid(String docId) throws Exception;

    McgPdfDto getPdf(User user, String docId) throws Exception;

    McgPdfDto getPdfForReport(String agentCode, String docId) throws Exception;

    List<McgDocumentUpdateDto> getAllDocument() throws Exception;

    List<McgDocumentUpdateDto> getAllDocumentForUser(User user) throws Exception;

    String previewPdf(McgDocumentUploadDto mcgDocumentUploadDto, User user);

}
