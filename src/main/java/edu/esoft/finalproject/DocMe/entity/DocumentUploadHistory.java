package edu.esoft.finalproject.DocMe.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "doc_upload_hst")
public class DocumentUploadHistory implements Serializable {

    private Integer documentUploadHistoryId;
    private Integer documentUploadId;
    private Integer docCategoryMaster;
    private String documentName;
    private String documentDescription;
    private String headline;
    private RecordStatus recordStatus;
    private String inputUserId;
    private Date inpDateTime;
    private String authUserId;
    private Date authDateTime;
//    private AccessUserType accessUserType;
    private String reason;
    private Date publishDate;
    private Date expireDate;
    private String channel;
    private String path;
    private String tableType;
    private String actionUserId;
    private Date actionDateTime;
    private String systemRoleId;

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    @Id
    @Column(name = "doc_upload_hst_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getDocumentUploadHistoryId() {
        return documentUploadHistoryId;
    }

    public void setDocumentUploadHistoryId(Integer documentUploadHistoryId) {
        this.documentUploadHistoryId = documentUploadHistoryId;
    }

    @Column(name = "doc_upload_id")
    public Integer getDocumentUploadId() {
        return documentUploadId;
    }

    public void setDocumentUploadId(Integer documentUploadId) {
        this.documentUploadId = documentUploadId;
    }

    @Column(name = "doc_category_id")
    public Integer getDocCategoryMaster() {
        return docCategoryMaster;
    }

    public void setDocCategoryMaster(Integer docCategoryMaster) {
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

    /*@ManyToOne(optional = false)
    @JoinColumn(name = "access_user_type_id")
    public AccessUserType getAccessUserType() {
        return accessUserType;
    }

    public void setAccessUserType(AccessUserType accessUserType) {
        this.accessUserType = accessUserType;
    }*/

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

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
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
