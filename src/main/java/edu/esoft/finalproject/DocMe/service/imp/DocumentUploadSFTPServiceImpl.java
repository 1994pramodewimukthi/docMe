package edu.esoft.finalproject.DocMe.service.imp;

import com.jcraft.jsch.*;
import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.dto.DocumentUploadDto;
import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.entity.DocumentUploadMaster;
import edu.esoft.finalproject.DocMe.entity.DocumentUploadTemp;
import edu.esoft.finalproject.DocMe.repository.DocCategoryMasterRepository;
import edu.esoft.finalproject.DocMe.repository.DocumentUploadMasterRepository;
import edu.esoft.finalproject.DocMe.repository.DocumentUploadRepository;
import edu.esoft.finalproject.DocMe.service.DocumentUploadSFTPService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

@Service
@Transactional
public class DocumentUploadSFTPServiceImpl implements DocumentUploadSFTPService {

    private static final String PDF = ".pdf";
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DocumentUploadSFTPServiceImpl.class);
    @Autowired
    DocCategoryMasterRepository docCategoryMasterRepository;
    @Autowired
    DocumentUploadMasterRepository documentUploadMasterRepository;
    @Autowired
    DocumentUploadRepository documentUploadRepository;


    @Value("${sftp.username}")
    private String sftpUsername;
    @Value("${sftp.host}")
    private String sftpHost;
    @Value("${sftp.port}")
    private int sftpPort;
    @Value("${sftp.password}")
    private String sftpPassword;
    @Value("${sftp.path.life}")
    private String sftpPathLife;
    @Value("${sftp.path.banca}")
    private String sftpPathBanca;
    @Value("${sftp.path.other}")
    private String sftpPathOther;

    @Value("${sftp.path.viruscheck}")
    private String virusCheackTempPath;

    @Override
    public String uploadFile(DocumentUploadDto documentUploadDto) throws Exception {
        try {
            String savePath = "";
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            MultipartFile file = documentUploadDto.getAttachment();

            sftpChannel.cd(sftpPathOther);

            InputStream inputStream = file.getInputStream();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timestampTime = "%" + timestamp.getTime();
            sftpChannel.put(inputStream, documentUploadDto.getDocumentName() + timestampTime + PDF);


            MultipartFile file1 = documentUploadDto.getAttachment();
            InputStream inputStream1 = file1.getInputStream();

            DocCategoryMaster catagory = docCategoryMasterRepository.findByDocCategoryMstId(documentUploadDto.getDocCategoryMasterId());
            sftpChannel.put(inputStream1, documentUploadDto.getDocumentName() + timestampTime + PDF);
            sftpChannel.rm(virusCheackTempPath + documentUploadDto.getDocumentName() + timestampTime + PDF);
            return documentUploadDto.getDocumentName() + timestampTime + PDF;
        } catch (JSchException | SftpException | IOException e) {
            throw e;
        }
    }

    @Override
    public InputStream viewUploadedFile(int docId, String tbleType) throws Exception {
        DocumentUploadTemp byDocumentUploadTempId = new DocumentUploadTemp();
        DocumentUploadMaster dto = new DocumentUploadMaster();
        DocCategoryMaster catagory;
        String path = "";
        if (AppConstant.MST.equals(tbleType)) {
            dto = documentUploadMasterRepository.findByDocumentUploadMstId(docId);
            InputStream inputStream = null;
            Channel channel = null;
            ChannelSftp sftpChannel = null;
            Session session = null;
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(sftpPassword);
                session.connect();

                channel = session.openChannel("sftp");
                channel.connect();
                sftpChannel = (ChannelSftp) channel;
                catagory = dto.getDocCategoryMaster();
                sftpChannel.cd(sftpPathOther);
                path = sftpPathOther;
                inputStream = sftpChannel.get(path.concat(dto.getPath()));
                return inputStream;
            } catch (JSchException | SftpException ex) {
                LOGGER.error(ex.getMessage());
                throw ex;
            }
        } else if (AppConstant.TEMP.equals(tbleType)) {
            byDocumentUploadTempId = documentUploadRepository.findByDocumentUploadTempId(docId);
            catagory = byDocumentUploadTempId.getDocCategoryMaster();
            InputStream inputStream = null;
            Channel channel = null;
            ChannelSftp sftpChannel = null;
            Session session = null;
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(sftpPassword);
                session.connect();

                channel = session.openChannel("sftp");
                channel.connect();
                sftpChannel = (ChannelSftp) channel;
                sftpChannel.cd(sftpPathOther);
                path = sftpPathOther;
                inputStream = sftpChannel.get(path.concat(byDocumentUploadTempId.getPath()));
                return inputStream;
            } catch (JSchException | SftpException ex) {
                LOGGER.error(ex.getMessage());
                throw ex;
            }
        }
        return null;
    }
}
