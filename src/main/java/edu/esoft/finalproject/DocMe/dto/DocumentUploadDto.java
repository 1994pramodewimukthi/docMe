package edu.esoft.finalproject.DocMe.dto;

import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.entity.RecordStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentUploadDto implements Serializable {

    private Integer documentUploadTempId ;
    private Integer docCategoryMasterId;
    private String documentName;
    private String headLine;
    private String documentDescription;
    private List<String> acessTypes = new ArrayList<>();
    private RecordStatus recordStatus;
    private String inpDateTime;
    @DateTimeFormat(pattern = AppConstant.DATE_FORMAT)
    private Date publishDate;
    @DateTimeFormat(pattern = AppConstant.DATE_FORMAT)
    private Date expireDate;
    private String path;
    private MultipartFile attachment;
    private List<SystemRoleDto> systemRoleDtos = new ArrayList<>();
    private String systemRoleId;

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    public List<SystemRoleDto> getSystemRoleDtos() {
        return systemRoleDtos;
    }

    public void setSystemRoleDtos(List<SystemRoleDto> systemRoleDtos) {
        this.systemRoleDtos = systemRoleDtos;
    }

    public Integer getDocCategoryMasterId() {
        return docCategoryMasterId;
    }

    public void setDocCategoryMasterId(Integer docCategoryMasterId) {
        this.docCategoryMasterId = docCategoryMasterId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getInpDateTime() {
        return inpDateTime;
    }

    public void setInpDateTime(String inpDateTime) {
        this.inpDateTime = inpDateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
    }

    public List<String> getAcessTypes() {
        return acessTypes;
    }

    public void setAcessTypes(List<String> acessTypes) {
        this.acessTypes = acessTypes;
    }

    public Integer getDocumentUploadTempId() {
        return documentUploadTempId;
    }

    public void setDocumentUploadTempId(Integer documentUploadTempId) {
        this.documentUploadTempId = documentUploadTempId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}
