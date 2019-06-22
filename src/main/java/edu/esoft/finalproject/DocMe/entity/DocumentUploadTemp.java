package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "doc_upload_temp")
public class DocumentUploadTemp implements Serializable {

    private Integer documentUploadTempId;
    private DocCategoryMaster docCategoryMaster;
    private String documentName;
    private String headline;
    private String documentDescription;
    private RecordStatus recordStatus;
    private String inputUserId;
    private Date inpDateTime;
    private String authUserId;
    private Date authDateTime;
    private String reason;
    private Date publishDate;
    private Date expireDate;
    private String channel;
    private String path;
    private String actionUserId;
    private Date actionDateTime;


    public DocumentUploadTemp() {
    }

    public DocumentUploadTemp(Integer documentUploadTempId) {
        this.documentUploadTempId = documentUploadTempId;
    }

    public DocumentUploadTemp(DocCategoryMaster docCategoryMaster) {
        this.docCategoryMaster = docCategoryMaster;
    }

    @Id
    @Column(name = "doc_upload_temp_id")
    public Integer getDocumentUploadTempId() {
        return documentUploadTempId;
    }

    public void setDocumentUploadTempId(Integer documentUploadTempId) {
        this.documentUploadTempId = documentUploadTempId;
    }

    @ManyToOne
    @JoinColumn(name = "doc_category_mst_id")
    public DocCategoryMaster getDocCategoryMaster() {
        return docCategoryMaster;
    }

    public void setDocCategoryMaster(DocCategoryMaster docCategoryMaster) {
        this.docCategoryMaster = docCategoryMaster;
    }

    @Column(name = "document_name")
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Column(name = "document_description")
    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    @ManyToOne
    @JoinColumn(name = "record_status")
    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Column(name = "input_user_id")
    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    @Column(name = "input_date_time")
    public Date getInpDateTime() {
        return inpDateTime;
    }

    public void setInpDateTime(Date inpDateTime) {
        this.inpDateTime = inpDateTime;
    }

    @Column(name = "auth_user_id")
    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }

    @Column(name = "auth_date_time")
    public Date getAuthDateTime() {
        return authDateTime;
    }

    public void setAuthDateTime(Date authDateTime) {
        this.authDateTime = authDateTime;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "publish_date")
    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Column(name = "expire_date")
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(String actionUserId) {
        this.actionUserId = actionUserId;
    }

    public Date getActionDateTime() {
        return actionDateTime;
    }

    public void setActionDateTime(Date actionDateTime) {
        this.actionDateTime = actionDateTime;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
