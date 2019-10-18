package edu.esoft.finalproject.DocMe.service.imp;

import com.jcraft.jsch.*;
import edu.esoft.finalproject.DocMe.config.ImageScaler;
import edu.esoft.finalproject.DocMe.config.MessageConstant;
import edu.esoft.finalproject.DocMe.config.Utility;
import edu.esoft.finalproject.DocMe.dto.McgDocumentUpdateDto;
import edu.esoft.finalproject.DocMe.dto.McgDocumentUploadDto;
import edu.esoft.finalproject.DocMe.dto.McgPdfDto;
import edu.esoft.finalproject.DocMe.entity.*;
import edu.esoft.finalproject.DocMe.repository.MCGAgentDocumentInfomationRepository;
import edu.esoft.finalproject.DocMe.repository.MCGCategoryRepository;
import edu.esoft.finalproject.DocMe.repository.MCGDocumentExpireRepository;
import edu.esoft.finalproject.DocMe.repository.MCGDocumentRepository;
import edu.esoft.finalproject.DocMe.service.MCGDocumentUploadService;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MCGDocumentUploadServiceImpl implements MCGDocumentUploadService {

    private static final String PDF = ".pdf";
    private static final String VALID = "VALID";
    private static final String INVALID = "INVALID";
    private static final String ACTIVE = "ACTIVE";
    private static final String INACTIVE = "INACTIVE";
    private static final Long SUCSESS = 1L;
    private static final Long ERROR = 0L;
    private static final Long SUP_ADMIN = 1L;
    private static final Long ADMIN = 2L;
    private static final Long LP_AGENCY = 3L;
    private static final Long UM_AGENCY = 4L;
    private static final Long DD_AGENCY = 5L;
    private static final Long LP_BANCA = 6L;
    private static final Long UM_BANCA = 7L;
    private static final Long DD_BANCA = 8L;
    private static final Long HEAD_AGENCY = 9L;
    private static final Long HEAD_BANCA = 10L;
    private static final Long COMPANY = 11L;
    private static final Long ADMIN_AGENCY = 12L;
    private static final Long ADMIN_BANCA = 13L;
    private static final String SIGN = "SIGN";
    private static final String PENDING = "PENDING";
    private static final String sampleSig = "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";
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MCGDocumentUploadServiceImpl.class);
    @Autowired
    MCGDocumentRepository mcgDocumentRepository;
    @Autowired
    MCGCategoryRepository mcgCategoryRepository;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MCGAgentDocumentInfomationRepository mcgAgentDocumentInfomationRepository;
    @Autowired
    private MCGDocumentExpireRepository mcgDocumentExpireRepository;
    @Value("${sftp.username}")
    private String sftpUsername;
    @Value("${sftp.host}")
    private String sftpHost;
    @Value("${sftp.port}")
    private int sftpPort;
    @Value("${sftp.password}")
    private String sftpPassword;
    @Value("${sftp.path.mcg.signed}")
    private String sftpMcgSignedPath;
    @Value("${sftp.path.mcg.uploaded}")
    private String sftpMcgUploadedPath;
    @Value("${mcg.pdfbox.portrait.width}")
    private int portraitWidth;
    @Value("${mcg.pdfbox.landscape.width}")
    private int landscapeWidth;
    @Value("${sftp.path.viruscheck}")
    private String virusCheackTempPath;
    @Value("${mcg.pdf.file.size}")
    private String maxFileSize;

    @Override
    public Long mcgDocumentUpload(McgDocumentUploadDto uploadDto, User user) throws Exception {
        try {
            // TODO =>  Implimentation area for the multipal document upload option
            Long valid = validateUploadDocument(uploadDto);
            if (valid.equals(SUCSESS)) {

                Iterable<MCGDocument> mcgDocuments = mcgDocumentRepository.findAll();

                for (MCGDocument mcgDocument : mcgDocuments) {
                    if (mcgDocument.getDocVersion().equalsIgnoreCase(uploadDto.getDocumentVersion())) {
                        return MessageConstant.INVALIDE_DOC_VERSION;
                    }
                    mcgDocument.setStatus(INVALID);
                    mcgDocumentRepository.save(mcgDocument);
                }

                MCGCategory mcgCategory = mcgCategoryRepository.findOne(Integer.parseInt(uploadDto.getCatId()));

                String fileName = mcgCategory.getCategoryName().trim() + "_" + uploadDto.getDocumentVersion().trim() + PDF;
                InputStream inputStream1 = uploadDto.getDocumentFile().getInputStream();
                InputStream inputStream2 = uploadDto.getDocumentFile().getInputStream();

                String storeDocName = SFTPFileUpload(inputStream1, inputStream2, fileName, sftpMcgUploadedPath);

                MCGDocument mcgDocument = new MCGDocument();

                mcgDocument.setCatId(new MCGCategory(Integer.parseInt(uploadDto.getCatId().trim())));
                mcgDocument.setDocVersion(uploadDto.getDocumentVersion().trim());
                mcgDocument.setAccessUserType(uploadDto.getSystemRoleId().trim());
                mcgDocument.setFooterHight(Integer.parseInt(uploadDto.getDocumentFooterHeight().trim()));
                if (null != storeDocName) {
                    mcgDocument.setFileStoreName(storeDocName.trim());
                }
                mcgDocument.setAcknowledgement(uploadDto.getDocumentAcknowledgement().replace("\r\n", " ").trim());
                mcgDocument.setInputUser(user.getFirstName().trim());
                mcgDocument.setInputDateTime(new Date());
                mcgDocument.setStatus(VALID);
                mcgDocument.setDocName(uploadDto.getDocumentFile().getOriginalFilename().trim());
                mcgDocument.setSkipDateCount(uploadDto.getSkipDateCount().trim());

                if (!uploadDto.getStartPage().isEmpty()) {
                    mcgDocument.setStartPage(Integer.parseInt(uploadDto.getStartPage()));
                }
                mcgDocument.setPageCount(Integer.parseInt(uploadDto.getPageCount()));

                MCGDocument save = mcgDocumentRepository.save(mcgDocument);
                if (null != save) {
                    return SUCSESS;
                } else {
                    return ERROR;
                }
            } else {
                return valid;
            }
        } catch (JSchException | SftpException | IOException e) {
            LOGGER.error(e.getMessage());
            return ERROR;
        }
    }

    private Long validateUploadDocument(McgDocumentUploadDto mcgDocumentUploadDto) {
        if (mcgDocumentUploadDto.getSystemRoleId().trim().isEmpty() || Integer.parseInt(mcgDocumentUploadDto.getSystemRoleId().trim()) < 0) {
            return MessageConstant.INVALIDE_USER_RANK;
        }

        if (mcgDocumentUploadDto.getCatId().trim().isEmpty() || Integer.parseInt(mcgDocumentUploadDto.getCatId().trim()) <= 0) {
            return MessageConstant.INVALIDE_DOC_NAME;
        }

        if (!mcgDocumentUploadDto.getDocumentVersion().trim().isEmpty() && mcgDocumentUploadDto.getDocumentVersion().trim().length() <= 30) {
            for (char c : mcgDocumentUploadDto.getDocumentVersion().trim().toCharArray()) {
                if (!Character.isDigit(c)
                        && !Character.isAlphabetic(c)
                        && !Character.isWhitespace(c)
                        && c != ')'
                        && c != '('
                        && c != '_'
                        && c != ','
                        && c != '.') {
                    return MessageConstant.INVALIDE_DOC_VERSION;
                }
            }
        } else {
            return MessageConstant.INVALIDE_DOC_VERSION;
        }

        if (!mcgDocumentUploadDto.getDocumentFile().getName().trim().isEmpty()) {
            for (char c : mcgDocumentUploadDto.getDocumentFile().getName().trim().toCharArray()) {
                if (!Character.isDigit(c)
                        && !Character.isAlphabetic(c)
                        && !Character.isWhitespace(c)
                        && c != ')'
                        && c != '('
                        && c != '_'
                        && c != ','
                        && c != '.') {
                    return MessageConstant.INVALIDE_DOC;
                }
            }
        } else {
            return MessageConstant.INVALIDE_DOC;
        }

        if (!mcgDocumentUploadDto.getDocumentFooterHeight().trim().isEmpty()
                && StringUtils.countOccurrencesOf(mcgDocumentUploadDto.getDocumentFooterHeight().trim(), ".") == 0) {
            for (Character c : mcgDocumentUploadDto.getDocumentFooterHeight().replace(".", "").toCharArray()) {
                if (!Character.isDigit(c)) {
                    return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
                }
            }
            if (Integer.parseInt(mcgDocumentUploadDto.getDocumentFooterHeight()) < 8 || Integer.parseInt(mcgDocumentUploadDto.getDocumentFooterHeight()) > 26) {
                return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
            }
        } else {
            return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
        }

        if (!mcgDocumentUploadDto.getSkipDateCount().trim().isEmpty() && mcgDocumentUploadDto.getSkipDateCount().trim().length() <= 3
                && StringUtils.countOccurrencesOf(mcgDocumentUploadDto.getSkipDateCount().trim(), ".") == 0) {
            for (Character c : mcgDocumentUploadDto.getSkipDateCount().replace(".", "").toCharArray()) {
                if (!Character.isDigit(c)) {
                    return MessageConstant.INVALIDE_DOC_SKIP_DATE_COUNT;
                }
            }
            if (Integer.parseInt(mcgDocumentUploadDto.getSkipDateCount()) <= 0) {
                return MessageConstant.INVALIDE_DOC_SKIP_DATE_COUNT;
            }
        } else {
            return MessageConstant.INVALIDE_DOC_SKIP_DATE_COUNT;
        }

        if (!mcgDocumentUploadDto.getStartPage().trim().isEmpty()) {
            if (StringUtils.countOccurrencesOf(mcgDocumentUploadDto.getStartPage().trim(), ".") == 0) {
                for (Character c : mcgDocumentUploadDto.getStartPage().replace(".", "").toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return MessageConstant.INVALIDE_DOC_START_PAGE;
                    }
                }
                if (Integer.parseInt(mcgDocumentUploadDto.getStartPage()) < 0 || Integer.parseInt(mcgDocumentUploadDto.getPageCount()) < Integer.parseInt(mcgDocumentUploadDto.getStartPage())) {
                    return MessageConstant.INVALIDE_DOC_START_PAGE;
                }
            } else {
                return MessageConstant.INVALIDE_DOC_START_PAGE;
            }
        }
        if (!mcgDocumentUploadDto.getPageCount().trim().isEmpty()) {
            if (StringUtils.countOccurrencesOf(mcgDocumentUploadDto.getPageCount().trim(), ".") == 0) {
                for (Character c : mcgDocumentUploadDto.getPageCount().replace(".", "").toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return MessageConstant.INVALIDE_DOC_PAGE_COUNT;
                    }
                }
            } else {
                return MessageConstant.INVALIDE_DOC_PAGE_COUNT;
            }
        }

        if (!mcgDocumentUploadDto.getDocumentAcknowledgement().trim().isEmpty() && mcgDocumentUploadDto.getDocumentAcknowledgement().trim().length() <= 1000) {
            for (char c : mcgDocumentUploadDto.getDocumentAcknowledgement().trim().toCharArray()) {
                if (!Character.isDigit(c)
                        && !Character.isAlphabetic(c)
                        && !Character.isWhitespace(c)
                        && c != ')'
                        && c != '('
                        && c != ','
                        && c != '_'
                        && c != '.') {
                    return MessageConstant.INVALIDE_DOC_ACKNOWLAGEMENT;
                }
            }
        } else {
            return MessageConstant.INVALIDE_DOC_ACKNOWLAGEMENT;
        }

        if (!mcgDocumentUploadDto.getDocumentFile().isEmpty()
                && mcgDocumentUploadDto.getDocumentFile().getSize() <= (Long.valueOf(maxFileSize.trim()) * 1024 * 1024)) {
            try {
                PDDocument doc = PDDocument.load(new ByteArrayInputStream(mcgDocumentUploadDto.getDocumentFile().getBytes()));

                for (Character c : mcgDocumentUploadDto.getStartPage().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        doc.close();
                        return MessageConstant.INVALIDE_DOC;
                    }
                }

                if (doc.isEncrypted()) {
                    doc.close();
                    return MessageConstant.INVALIDE_DOC;
                }

                if (!mcgDocumentUploadDto.getStartPage().trim().isEmpty() && doc.getNumberOfPages() < Integer.parseInt(mcgDocumentUploadDto.getStartPage())) {
                    doc.close();
                    return MessageConstant.INVALIDE_DOC;
                }

                if (doc.getNumberOfPages() < Integer.parseInt(mcgDocumentUploadDto.getPageCount())) {
                    doc.close();
                    return MessageConstant.INVALIDE_DOC_PAGE_COUNT;
                }
                doc.close();

            } catch (Exception e) {
                return MessageConstant.INVALIDE_DOC;
            }
        } else {
            return MessageConstant.INVALIDE_DOC;
        }

        return SUCSESS;
    }

    @Override
    public McgPdfDto viewMCGDocument(User user) throws Exception {
        try {
            McgPdfDto mcgPdfDto = new McgPdfDto();
            Calendar calender = Calendar.getInstance();

            MCGDocument mcgDocument = mcgDocumentRepository.findDocumentByStatusAndRoleId(VALID, user.getSystemRoleId());
            if (null != mcgDocument) {
                InputStream inputStream = viewUploadedDocument(sftpMcgUploadedPath, mcgDocument.getFileStoreName());
                String encodeString = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));
                mcgPdfDto.setDocId(mcgDocument.getDocId().toString().trim());
                mcgPdfDto.setAcknowledgement(mcgDocument.getAcknowledgement().trim());
                mcgPdfDto.setPdfFile(encodeString.trim());
                mcgPdfDto.setDocName(mcgDocument.getCatId().getCategoryName().trim() + " " + mcgDocument.getDocVersion().trim());
                mcgPdfDto.setSignStatus(ERROR.toString().trim());

                calender.setTime(mcgDocument.getInputDateTime());
                calender.add(Calendar.DAY_OF_MONTH, Integer.parseInt((null == mcgDocument.getSkipDateCount()) ? "0" : mcgDocument.getSkipDateCount()) + 1);
                mcgPdfDto.setRemainingDates(Long.toString((Utility.getDiffDays(new Date(), calender.getTime()) >= 0) ? Utility.getDiffDays(new Date(), calender.getTime()) : 0));
            }
            return mcgPdfDto;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public McgPdfDto singInDocument(McgPdfDto mcgPdfDto, User user) throws Exception {
        MCGAgentDocumentInfomation mcgAgentDocumentInfomation = new MCGAgentDocumentInfomation();
        MCGAgentDocumentInfomationEmbed mcgAgentDocumentInfomationEmbed = new MCGAgentDocumentInfomationEmbed();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream baosForMainSignature = new ByteArrayOutputStream();
        ByteArrayOutputStream baosForFooterSignature = new ByteArrayOutputStream();
        ImageScaler imageScaler = new ImageScaler();
        Calendar calender = Calendar.getInstance();
        String userName;
        MCGDocument mcgDocument = mcgDocumentRepository.findOne(Integer.parseInt(mcgPdfDto.getDocId()));

        String base64Image = mcgPdfDto.getSignatureFile().split(",")[1];
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
        BufferedImage inputFile;
        try {

            inputFile = ImageIO.read(new ByteArrayInputStream(imageBytes));
            BufferedImage resizeImageMainSignature = imageScaler.getScaledInstance(inputFile, inputFile.getWidth() / 4, inputFile.getHeight() / 4, true);
            BufferedImage resizeImageFooterSignature = imageScaler.getScaledInstance(inputFile, inputFile.getWidth() / 6, inputFile.getHeight() / 6, true);
            ImageIO.write(resizeImageMainSignature, "png", baosForMainSignature);
            ImageIO.write(resizeImageFooterSignature, "png", baosForFooterSignature);

            PDDocument pdDocument = PDDocument.load(Base64.getDecoder().decode(mcgPdfDto.getPdfFile()));
            PDPageTree pdPages = pdDocument.getDocumentCatalog().getPages();
            PDPage pdPage = pdPages.get(pdPages.getCount() - 1);

            PDRectangle pdPageMediaBox = pdPage.getMediaBox();
            int rotation = pdPage.getRotation();

            boolean isLandscape = pdPageMediaBox.getWidth() > pdPageMediaBox.getHeight();
            if (rotation == 90 || rotation == 270) {
                isLandscape = !isLandscape;
            }

            PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, pdPage, PDPageContentStream.AppendMode.APPEND, true, true);

            String[] splitTexts = org.apache.commons.text.WordUtils.wrap(mcgDocument.getAcknowledgement(), ((isLandscape) ? landscapeWidth : portraitWidth)).split("\\r?\\n");
            pageContentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 11);

            int height = mcgDocument.getFooterHight() * 29; //* 29 for convert cm to points

            for (String splitText : splitTexts) {
                height -= 12;
                pageContentStream.beginText();
                pageContentStream.newLineAtOffset(50, height);
                pageContentStream.showText(splitText);
                pageContentStream.endText();
            }

            PDImageXObject mainSignature = PDImageXObject.createFromByteArray(pdDocument, baosForMainSignature.toByteArray(), "mainSignature.png");
            PDImageXObject footerSignature = PDImageXObject.createFromByteArray(pdDocument, baosForFooterSignature.toByteArray(), "footerSignature.png");

            pageContentStream.setFont(PDType1Font.HELVETICA, 12);

            pageContentStream.beginText();
            height -= 20;
            pageContentStream.newLineAtOffset(pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);
            pageContentStream.showText("Signature of " + user.getFirstName().concat(" ").concat(user.getLastName()));
            pageContentStream.endText();

            height -= (12 + mainSignature.getHeight());
            pageContentStream.drawImage(mainSignature, pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);

            pageContentStream.beginText();
            height -= 17;
            pageContentStream.newLineAtOffset(pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);
            pageContentStream.showText(user.getFirstName().concat(" ").concat(user.getLastName()));
            pageContentStream.endText();

            pageContentStream.beginText();
            height -= 20;
            pageContentStream.newLineAtOffset(pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);
            pageContentStream.showText(Utility.getDateString(new Date(), "dd/MM/yyyy"));
            pageContentStream.endText();

            pageContentStream.close();

            if (null != mcgDocument.getStartPage() && mcgDocument.getStartPage() > 0) {
                for (int i = mcgDocument.getStartPage(); i < pdPages.getCount(); i++) {
                    pdPage = pdPages.get(i - 1);
                    pdPageMediaBox = pdPage.getMediaBox();
                    pageContentStream = new PDPageContentStream(pdDocument, pdPage, PDPageContentStream.AppendMode.APPEND, true, true);
                    pageContentStream.drawImage(footerSignature, pdPageMediaBox.getWidth() - footerSignature.getWidth() - 100, footerSignature.getHeight() - 10f);
                    pageContentStream.close();
                }
            }

            pdDocument.save(byteArrayOutputStream);
            pdDocument.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

        mcgPdfDto.setPdfFile(Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()).trim());
        mcgPdfDto.setAcknowledgement(mcgDocument.getAcknowledgement().trim());
        mcgPdfDto.setDocName(mcgDocument.getCatId().getCategoryName().trim() + " " + mcgDocument.getDocVersion().trim());

        calender.setTime(mcgDocument.getInputDateTime());
        calender.add(Calendar.DAY_OF_MONTH, Integer.parseInt((null == mcgDocument.getSkipDateCount()) ? "0" : mcgDocument.getSkipDateCount()) + 1);
        mcgPdfDto.setRemainingDates(Long.toString((Utility.getDiffDays(new Date(), calender.getTime()) >= 0) ? Utility.getDiffDays(new Date(), calender.getTime()) : 0));

        mcgAgentDocumentInfomationEmbed.setAgentCode(user.getId().toString().trim());
        mcgAgentDocumentInfomationEmbed.setDocId(mcgDocument.getDocId());

        mcgAgentDocumentInfomation.setMcgAgentDocumentInfomationEmbed(mcgAgentDocumentInfomationEmbed);
        mcgAgentDocumentInfomation.setDocIssueDate(mcgDocument.getInputDateTime());
        mcgAgentDocumentInfomation.setSignDate(new Date());
        mcgAgentDocumentInfomation.setStatus(PENDING);
        mcgPdfDto.setSignStatus(SUCSESS.toString());

        mcgAgentDocumentInfomationRepository.save(mcgAgentDocumentInfomation);
        return mcgPdfDto;
    }

    @Override
    public boolean isMCGDocAvilable(User user) throws Exception {
        MCGDocument doc = mcgDocumentRepository.findDocumentByStatusAndRoleId(VALID, user.getSystemRoleId());
        if (null != doc) {
            MCGAgentDocumentInfomation info = mcgAgentDocumentInfomationRepository.findIsSignByDocIdAndAgentCode(doc.getDocId(), user.getId().toString());
            return null == info;
        } else {
            return false;
        }
    }

    @Override
    public boolean skipSign(User user) throws Exception {
        Calendar calendar = Calendar.getInstance();
        MCGDocument doc = mcgDocumentRepository.findDocumentByStatusAndRoleId(VALID, user.getSystemRoleId());
        calendar.setTime(doc.getInputDateTime());
        calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt((null == doc.getSkipDateCount()) ? "0" : doc.getSkipDateCount()) + 1);
        return Utility.getDiffDays(new Date(), calendar.getTime()) >= 0;
    }

    @Override
    public Long uploadSignPdf(McgPdfDto mcgPdfDto, User user) {
        try {
            MCGAgentDocumentInfomation pendingInfo = mcgAgentDocumentInfomationRepository.findPendingByAndDocIdAndAgentCode(Integer.parseInt(mcgPdfDto.getDocId()), user.getId().toString());

            if (null != pendingInfo) {
                String fileName = mcgPdfDto.getDocName() + "_" + user.getId() + ".pdf";
                InputStream inputStream1 = new ByteArrayInputStream(Base64.getDecoder().decode(mcgPdfDto.getPdfFile()));
                InputStream inputStream2 = new ByteArrayInputStream(Base64.getDecoder().decode(mcgPdfDto.getPdfFile()));

                String storeDocName = SFTPFileUpload(inputStream1, inputStream2, fileName, sftpMcgSignedPath);

                if (null != storeDocName && !storeDocName.trim().isEmpty()) {
                    pendingInfo.setStatus(SIGN.trim());
                    pendingInfo.setFileName(storeDocName);
                    mcgAgentDocumentInfomationRepository.save(pendingInfo);
                }
            } else {
                return ERROR;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ERROR;
        }
        return SUCSESS;
    }


    private String SFTPFileUpload(InputStream inputStream1, InputStream inputStream2, String fileName, String savePath) throws JSchException, SftpException, IOException {
        try {

            Channel channel = getConnectionWithSftp().openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            sftpChannel.cd(savePath);
            sftpChannel.put(inputStream1, fileName);
            return fileName;
        } catch (JSchException | SftpException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }


    public InputStream viewUploadedDocument(String folderPath, String savePath) throws SftpException, JSchException {
        InputStream inputStream = null;
        Channel channel = null;
        ChannelSftp sftpChannel = null;
        try {
            channel = getConnectionWithSftp().openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            sftpChannel.cd(folderPath);
            inputStream = sftpChannel.get(folderPath.concat(savePath));
            return inputStream;
        } catch (JSchException | SftpException ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        }

    }


    public String getLatestVersion(String catId) throws Exception {
        try {
            return mcgDocumentRepository.findVersionByCatId(catId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public McgDocumentUpdateDto getValidDocument(User user) throws Exception {
        try {
            MCGDocument doc = mcgDocumentRepository.findDocumentByStatusAndRoleId(VALID, user.getSystemRoleId());
            McgDocumentUpdateDto mcgDocumentUpdateDto = new McgDocumentUpdateDto();
            if (null != doc) {
                mcgDocumentUpdateDto.setDocId(doc.getDocId().toString());
                mcgDocumentUpdateDto.setDocName(doc.getFileStoreName());
                mcgDocumentUpdateDto.setFooterHeight(doc.getFooterHight().toString());
                mcgDocumentUpdateDto.setStartPage((null != doc.getStartPage()) ? doc.getStartPage().toString() : "");
                mcgDocumentUpdateDto.setSkipDateCount(doc.getSkipDateCount());
                mcgDocumentUpdateDto.setPageCount(doc.getPageCount().toString());
                mcgDocumentUpdateDto.setUploadedDate(simpleDateFormat.format(doc.getInputDateTime()));
            }
            return mcgDocumentUpdateDto;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Long saveDocumentUpdate(McgDocumentUpdateDto mcgDocumentUpdateDto, User user) {

        try {
            MCGDocument doc = mcgDocumentRepository.findOne(Integer.parseInt(mcgDocumentUpdateDto.getDocId()));
            Long valid = validateUpdateDocument(mcgDocumentUpdateDto, doc);

            if (valid.equals(SUCSESS)) {
                doc.setDocId(Integer.parseInt(mcgDocumentUpdateDto.getDocId()));
                doc.setFooterHight(Integer.parseInt(mcgDocumentUpdateDto.getFooterHeight()));
                doc.setStartPage((null != mcgDocumentUpdateDto.getStartPage() && !mcgDocumentUpdateDto.getStartPage().equals("")) ? Integer.parseInt(mcgDocumentUpdateDto.getStartPage()) : null);
                doc.setSkipDateCount(mcgDocumentUpdateDto.getSkipDateCount());

                MCGDocument save = mcgDocumentRepository.save(doc);
            } else {
                return valid;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ERROR;
        }
        return SUCSESS;
    }

    @Override
    public Boolean getPdfIsValid(String docId) throws Exception {
        MCGDocument mcgDocument = null;
        try {
            mcgDocument = mcgDocumentRepository.findOne(Integer.parseInt(docId));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
        return (null != mcgDocument);
    }

    @Override
    public McgPdfDto getPdf(User user, String docId) throws Exception {

        McgPdfDto mcgPdfDto = new McgPdfDto();
        try {
            MCGDocument mcgDocument = mcgDocumentRepository.findOne(Integer.parseInt(docId));
            MCGAgentDocumentInfomation signByDocIdAndAgentCode = mcgAgentDocumentInfomationRepository.findIsSignByDocIdAndAgentCode(mcgDocument.getDocId(), user.getId().toString());
            if (!mcgDocument.getStatus().equals(VALID) || !isMCGDocAvilable(user)) {
                String fileName = (null != signByDocIdAndAgentCode) ? signByDocIdAndAgentCode.getFileName() : mcgDocument.getFileStoreName();
                String path = (null != signByDocIdAndAgentCode) ? sftpMcgSignedPath : sftpMcgUploadedPath;

                InputStream inputStream = viewUploadedDocument(path, fileName);
                String encodeString = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));

                mcgPdfDto.setPdfFile(encodeString);
            } else {
                return null;
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            mcgPdfDto.setPdfFile("");
        }
        return mcgPdfDto;
    }

    @Override
    public McgPdfDto getPdfForReport(String agentCode, String docId) throws Exception {

        McgPdfDto mcgPdfDto = new McgPdfDto();
        try {
            MCGDocument mcgDocument = mcgDocumentRepository.findOne(Integer.parseInt(docId));
            MCGAgentDocumentInfomation signByDocIdAndAgentCode = mcgAgentDocumentInfomationRepository.findIsSignByDocIdAndAgentCode(mcgDocument.getDocId(), agentCode);

            String fileName = (null != signByDocIdAndAgentCode) ? signByDocIdAndAgentCode.getFileName() : mcgDocument.getFileStoreName();
            String path = (null != signByDocIdAndAgentCode) ? sftpMcgSignedPath : sftpMcgUploadedPath;

            InputStream inputStream = viewUploadedDocument(path, fileName);
            String encodeString = Base64.getEncoder().encodeToString(IOUtils.toByteArray(inputStream));

            mcgPdfDto.setDocName(mcgDocument.getFileStoreName());
            mcgPdfDto.setPdfFile(encodeString);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            mcgPdfDto.setPdfFile("");
        }
        return mcgPdfDto;
    }

    @Override
    public List<McgDocumentUpdateDto> getAllDocument() throws Exception {
        List<McgDocumentUpdateDto> mcgDocumentUploadDtos = null;

        try {
            Iterable<MCGDocument> mcgDocuments = mcgDocumentRepository.findAllDocumentsDesc();
            mcgDocumentUploadDtos = new ArrayList<>();
            for (MCGDocument mcgDocument : mcgDocuments) {
                McgDocumentUpdateDto mcgDocumentUpdateDto = new McgDocumentUpdateDto();
                mcgDocumentUpdateDto.setDocId(mcgDocument.getDocId().toString());
                mcgDocumentUpdateDto.setDocName(mcgDocument.getFileStoreName().split(".pdf")[0]);
                mcgDocumentUpdateDto.setSkipDateCount(mcgDocument.getSkipDateCount());
                if (mcgDocument.getStatus().equals(VALID)) {
                    mcgDocumentUpdateDto.setDocStatus(ACTIVE);
                } else {
                    mcgDocumentUpdateDto.setDocStatus(INACTIVE);
                }
                mcgDocumentUpdateDto.setUploadedDate(simpleDateFormat.format(mcgDocument.getInputDateTime()));
                mcgDocumentUpdateDto.setPageCount((null != mcgDocument.getPageCount()) ? mcgDocument.getPageCount().toString() : "");

                mcgDocumentUploadDtos.add(mcgDocumentUpdateDto);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return mcgDocumentUploadDtos;
    }

    @Override
    public List<McgDocumentUpdateDto> getAllDocumentForUser(User user) throws Exception {
        List<McgDocumentUpdateDto> mcgDocumentUploadDtos = null;
        Calendar calender = Calendar.getInstance();
        try {
            Iterable<MCGDocument> mcgDocuments = null;
            mcgDocuments = mcgDocumentRepository.findAll();

            mcgDocumentUploadDtos = new ArrayList<>();
            for (MCGDocument mcgDocument : mcgDocuments) {
                McgDocumentUpdateDto mcgDocumentUpdateDto = new McgDocumentUpdateDto();
                MCGAgentDocumentInfomation signByDocIdAndAgentCode = mcgAgentDocumentInfomationRepository.findIsSignByDocIdAndAgentCode(mcgDocument.getDocId(), user.getId().toString());

                mcgDocumentUpdateDto.setDocId(mcgDocument.getDocId().toString());
                mcgDocumentUpdateDto.setDocName(mcgDocument.getFileStoreName().split(".pdf")[0]);
                mcgDocumentUpdateDto.setSkipDateCount(mcgDocument.getSkipDateCount());
                if (mcgDocument.getStatus().equals(VALID)) {
                    mcgDocumentUpdateDto.setDocStatus(ACTIVE);
                } else {
                    mcgDocumentUpdateDto.setDocStatus(INACTIVE);
                }

                mcgDocumentUpdateDto.setPageCount((null != mcgDocument.getPageCount()) ? mcgDocument.getPageCount().toString() : "");
                mcgDocumentUpdateDto.setDocSignStatus((null != signByDocIdAndAgentCode) ? "SIGNED" : "UNSIGNED");
                mcgDocumentUpdateDto.setDocOpenNewTab(String.valueOf(mcgDocument.getStatus().equals(VALID) && isMCGDocAvilable(user)));

                calender.setTime(mcgDocument.getInputDateTime());
                calender.add(Calendar.DAY_OF_MONTH, Integer.parseInt((null == mcgDocument.getSkipDateCount()) ? "0" : mcgDocument.getSkipDateCount()) + 1);
                mcgDocumentUpdateDto.setRemainingDates(Long.toString((Utility.getDiffDays(new Date(), calender.getTime()) >= 0) ? Utility.getDiffDays(new Date(), calender.getTime()) : 0));
                mcgDocumentUpdateDto.setUploadedDate(simpleDateFormat.format(mcgDocument.getInputDateTime()));

                mcgDocumentUploadDtos.add(mcgDocumentUpdateDto);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
        return mcgDocumentUploadDtos;
    }

    private Long validateUpdateDocument(McgDocumentUpdateDto mcgDocumentUpdateDto, MCGDocument doc) {
        if (!mcgDocumentUpdateDto.getFooterHeight().trim().isEmpty()
                && StringUtils.countOccurrencesOf(mcgDocumentUpdateDto.getFooterHeight().trim(), ".") == 0) {
            for (Character c : mcgDocumentUpdateDto.getFooterHeight().replace(".", "").toCharArray()) {
                if (!Character.isDigit(c)) {
                    return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
                }
            }
            if (Integer.parseInt(mcgDocumentUpdateDto.getFooterHeight()) < 8 || Integer.parseInt(mcgDocumentUpdateDto.getFooterHeight()) > 26) {
                return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
            }
        } else {
            return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
        }

        if (!mcgDocumentUpdateDto.getSkipDateCount().trim().isEmpty() && mcgDocumentUpdateDto.getSkipDateCount().trim().length() <= 3
                && StringUtils.countOccurrencesOf(mcgDocumentUpdateDto.getSkipDateCount().trim(), ".") == 0) {
            for (Character c : mcgDocumentUpdateDto.getSkipDateCount().replace(".", "").toCharArray()) {
                if (!Character.isDigit(c)) {
                    return MessageConstant.INVALIDE_DOC_SKIP_DATE_COUNT;
                }
            }
            if (Integer.parseInt(mcgDocumentUpdateDto.getSkipDateCount()) <= 0) {
                return MessageConstant.INVALIDE_DOC_SKIP_DATE_COUNT;
            }
        } else {
            return MessageConstant.INVALIDE_DOC_SKIP_DATE_COUNT;
        }

        if (!mcgDocumentUpdateDto.getStartPage().trim().isEmpty()) {
            if (StringUtils.countOccurrencesOf(mcgDocumentUpdateDto.getStartPage().trim(), ".") == 0) {
                for (Character c : mcgDocumentUpdateDto.getStartPage().replace(".", "").toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return MessageConstant.INVALIDE_DOC_START_PAGE;
                    }
                }
                if (Integer.parseInt(mcgDocumentUpdateDto.getStartPage()) < 0 || doc.getPageCount() < Integer.parseInt(mcgDocumentUpdateDto.getStartPage())) {
                    return MessageConstant.INVALIDE_DOC_START_PAGE;
                }
            } else {
                return MessageConstant.INVALIDE_DOC_START_PAGE;
            }
        }
        return SUCSESS;

    }


    private Session getConnectionWithSftp() throws JSchException {
        Session session;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftpUsername, sftpHost, sftpPort);
            session.setPassword(sftpPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
        } catch (JSchException e) {
            throw e;
        }
        return session;
    }

    @Scheduled(cron = "${mcg.pdf.file.expirecron}")
    public void deleteExpireDoc() {
        Channel channel = null;
        ChannelSftp sftpChannel = null;
        Calendar calender = Calendar.getInstance();
        try {
            channel = getConnectionWithSftp().openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            sftpChannel.cd(sftpMcgSignedPath);

            Iterable<MCGAgentDocumentInfomation> mcgAgentDocumentInfomations = mcgAgentDocumentInfomationRepository.findAll();
            MCGDocumentExpire mcgDocumentExpire = mcgDocumentExpireRepository.findOne(1);
            Date date = new Date();
            for (MCGAgentDocumentInfomation mcgAgentDocumentInfomation : mcgAgentDocumentInfomations) {
                if (!mcgAgentDocumentInfomation.getStatus().equals("DELETED")) {
                    calender.setTime(mcgAgentDocumentInfomation.getSignDate());
                    calender.add(Calendar.YEAR, Integer.parseInt(mcgDocumentExpire.getExpireYear()));
                    if ((Utility.getDiffDays(date, calender.getTime())) <= 0) {
                        sftpChannel.rm(mcgAgentDocumentInfomation.getFileName());
                        mcgAgentDocumentInfomation.setStatus("DELETED");
                    }
                    mcgAgentDocumentInfomationRepository.save(mcgAgentDocumentInfomation);
                }
            }
        } catch (JSchException | SftpException ex) {
            LOGGER.error(ex.getMessage());
        }
    }


    public String previewPdf(McgDocumentUploadDto mcgDocumentUploadDto, User user) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream baosForMainSignature = new ByteArrayOutputStream();
        ByteArrayOutputStream baosForFooterSignature = new ByteArrayOutputStream();
        ImageScaler imageScaler = new ImageScaler();

        byte[] imageBytes = DatatypeConverter.parseBase64Binary(sampleSig);
        BufferedImage inputFile;

        try {
            Long valid = validatePreview(mcgDocumentUploadDto);
            if (valid.equals(SUCSESS)) {
                inputFile = ImageIO.read(new ByteArrayInputStream(imageBytes));
                BufferedImage resizeImageMainSignature = imageScaler.getScaledInstance(inputFile, inputFile.getWidth() / 4, inputFile.getHeight() / 4, true);
                BufferedImage resizeImageFooterSignature = imageScaler.getScaledInstance(inputFile, inputFile.getWidth() / 6, inputFile.getHeight() / 6, true);
                ImageIO.write(resizeImageMainSignature, "png", baosForMainSignature);
                ImageIO.write(resizeImageFooterSignature, "png", baosForFooterSignature);

                PDDocument pdDocument = PDDocument.load(mcgDocumentUploadDto.getDocumentFile().getInputStream());
                PDPageTree pdPages = pdDocument.getDocumentCatalog().getPages();
                PDPage pdPage = pdPages.get(pdPages.getCount() - 1);

                PDRectangle pdPageMediaBox = pdPage.getMediaBox();
                int rotation = pdPage.getRotation();

                boolean isLandscape = pdPageMediaBox.getWidth() > pdPageMediaBox.getHeight();
                if (rotation == 90 || rotation == 270) {
                    isLandscape = !isLandscape;
                }

                PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, pdPage, PDPageContentStream.AppendMode.APPEND, true, true);

                String[] splitTexts = org.apache.commons.text.WordUtils.wrap(mcgDocumentUploadDto.getDocumentAcknowledgement(), ((isLandscape) ? landscapeWidth : portraitWidth)).split("\\r?\\n");
                pageContentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 11);

                int height = Integer.parseInt(mcgDocumentUploadDto.getDocumentFooterHeight()) * 29; //* 29 for convert cm to points

                for (String splitText : splitTexts) {
                    height -= 12;
                    pageContentStream.beginText();
                    pageContentStream.newLineAtOffset(50, height);
                    pageContentStream.showText(splitText);
                    pageContentStream.endText();
                }

                PDImageXObject mainSignature = PDImageXObject.createFromByteArray(pdDocument, baosForMainSignature.toByteArray(), "mainSignature.png");
                PDImageXObject footerSignature = PDImageXObject.createFromByteArray(pdDocument, baosForFooterSignature.toByteArray(), "footerSignature.png");

                pageContentStream.setFont(PDType1Font.HELVETICA, 12);

                pageContentStream.beginText();
                height -= 20;
                pageContentStream.newLineAtOffset(pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);
                pageContentStream.showText("Signature of Sample User");
                pageContentStream.endText();

                height -= (12 + mainSignature.getHeight());
                pageContentStream.drawImage(mainSignature, pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);

                pageContentStream.beginText();
                height -= 17;
                pageContentStream.newLineAtOffset(pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);
                pageContentStream.showText("Sample User");
                pageContentStream.endText();

                pageContentStream.beginText();
                height -= 20;
                pageContentStream.newLineAtOffset(pdPageMediaBox.getWidth() - mainSignature.getWidth() - 100, height);
                pageContentStream.showText(Utility.getDateString(new Date(), "dd/MM/yyyy"));
                pageContentStream.endText();

                pageContentStream.close();

                if (!mcgDocumentUploadDto.getStartPage().isEmpty() && Integer.parseInt(mcgDocumentUploadDto.getStartPage()) > 0) {
                    for (int i = Integer.parseInt(mcgDocumentUploadDto.getStartPage()); i < pdPages.getCount(); i++) {
                        pdPage = pdPages.get(i - 1);
                        pdPageMediaBox = pdPage.getMediaBox();
                        pageContentStream = new PDPageContentStream(pdDocument, pdPage, PDPageContentStream.AppendMode.APPEND, true, true);
                        pageContentStream.drawImage(footerSignature, pdPageMediaBox.getWidth() - footerSignature.getWidth() - 100, footerSignature.getHeight() - 10f);
                        pageContentStream.close();
                    }
                }

                pdDocument.save(byteArrayOutputStream);
                pdDocument.close();
                return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray()).trim();

            } else {
                return valid.toString();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return String.valueOf(MessageConstant.DOCUMENT_GENERATE_FAILED);
        }
    }

    private Long validatePreview(McgDocumentUploadDto mcgDocumentUploadDto) throws Exception {
        if (!mcgDocumentUploadDto.getDocumentFooterHeight().trim().isEmpty()
                && StringUtils.countOccurrencesOf(mcgDocumentUploadDto.getDocumentFooterHeight().trim(), ".") == 0) {
            for (Character c : mcgDocumentUploadDto.getDocumentFooterHeight().replace(".", "").toCharArray()) {
                if (!Character.isDigit(c)) {
                    return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
                }
            }
            if (Integer.parseInt(mcgDocumentUploadDto.getDocumentFooterHeight()) < 8 || Integer.parseInt(mcgDocumentUploadDto.getDocumentFooterHeight()) > 26) {
                return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
            }
        } else {
            return MessageConstant.INVALIDE_DOC_FOOTER_HEIGHT;
        }

        if (!mcgDocumentUploadDto.getStartPage().trim().isEmpty()) {
            if (StringUtils.countOccurrencesOf(mcgDocumentUploadDto.getStartPage().trim(), ".") == 0) {
                for (Character c : mcgDocumentUploadDto.getStartPage().replace(".", "").toCharArray()) {
                    if (!Character.isDigit(c)) {
                        return MessageConstant.INVALIDE_DOC_START_PAGE;
                    }
                }
                if (Integer.parseInt(mcgDocumentUploadDto.getStartPage()) < 0 || Integer.parseInt(mcgDocumentUploadDto.getPageCount()) < Integer.parseInt(mcgDocumentUploadDto.getStartPage())) {
                    return MessageConstant.INVALIDE_DOC_START_PAGE;
                }
            } else {
                return MessageConstant.INVALIDE_DOC_START_PAGE;
            }
        }
        if (!mcgDocumentUploadDto.getDocumentAcknowledgement().trim().isEmpty() && mcgDocumentUploadDto.getDocumentAcknowledgement().trim().length() <= 1000) {
            for (char c : mcgDocumentUploadDto.getDocumentAcknowledgement().trim().toCharArray()) {
                if (!Character.isDigit(c)
                        && !Character.isAlphabetic(c)
                        && !Character.isWhitespace(c)
                        && c != ')'
                        && c != '('
                        && c != ','
                        && c != '_'
                        && c != '.') {
                    return MessageConstant.INVALIDE_DOC_ACKNOWLAGEMENT;
                }
            }
        } else {
            return MessageConstant.INVALIDE_DOC_ACKNOWLAGEMENT;
        }

        if (!mcgDocumentUploadDto.getDocumentFile().isEmpty()
                && mcgDocumentUploadDto.getDocumentFile().getSize() <= (Long.valueOf(maxFileSize.trim()) * 1024 * 1024)) {
            try {
                PDDocument doc = PDDocument.load(new ByteArrayInputStream(mcgDocumentUploadDto.getDocumentFile().getBytes()));

                for (Character c : mcgDocumentUploadDto.getStartPage().toCharArray()) {
                    if (!Character.isDigit(c)) {
                        doc.close();
                        return MessageConstant.INVALIDE_DOC;
                    }
                }

                if (doc.isEncrypted()) {
                    doc.close();
                    return MessageConstant.INVALIDE_DOC;
                }

                if (!mcgDocumentUploadDto.getStartPage().trim().isEmpty() && doc.getNumberOfPages() < Integer.parseInt(mcgDocumentUploadDto.getStartPage())) {
                    doc.close();
                    return MessageConstant.INVALIDE_DOC;
                }
                doc.close();

            } catch (Exception e) {
                return MessageConstant.INVALIDE_DOC;
            }
        } else {
            return MessageConstant.INVALIDE_DOC;
        }

        return SUCSESS;
    }
}
