package edu.esoft.finalproject.DocMe.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;

import java.io.InputStream;

public interface DocumentUploadSFTPService {

    String uploadFile(DocumentUploadDto documentUploadDto) throws Exception;

    InputStream viewUploadedFile(int docId, String tbleType) throws Exception;
}
