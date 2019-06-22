package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "doc_category_mst")
public class DocCategoryMaster implements Serializable {

    private Integer docCategoryMstId;
    private DocCategoryMaster parentDocCategoryMst;
    private String docCategoryName;
    private String docCategoryDescription;
    private int sortingOrder;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;
    private String authUserId;
    private Date authDateTime;
    private String reason;

    private List<DocCategoryMaster> subCategoryDocList;

    public DocCategoryMaster() {
    }


    public DocCategoryMaster(Integer docCategoryMstId) {
        this.docCategoryMstId = docCategoryMstId;
    }

    @Id
    @Column(name = "doc_category_mst_id")
    public Integer getDocCategoryMstId() {
        return docCategoryMstId;
    }

    public void setDocCategoryMstId(Integer docCategoryMstId) {
        this.docCategoryMstId = docCategoryMstId;
    }

    @ManyToOne
    @JoinColumn(name = "parent_doc_category_mst_id")
    public DocCategoryMaster getParentDocCategoryMst() {
        return parentDocCategoryMst;
    }

    public void setParentDocCategoryMst(DocCategoryMaster parentDocCategoryMst) {
        this.parentDocCategoryMst = parentDocCategoryMst;
    }

    public String getDocCategoryName() {
        return docCategoryName;
    }

    public void setDocCategoryName(String docCategoryName) {
        this.docCategoryName = docCategoryName;
    }

    public int getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(int sortingOrder) {
        this.sortingOrder = sortingOrder;
    }


    @ManyToOne
    @JoinColumn(name = "record_status")
    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getInpUserId() {
        return inpUserId;
    }

    public void setInpUserId(String inpUserId) {
        this.inpUserId = inpUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inp_date_time")
    public Date getInpDateTime() {
        return inpDateTime;
    }

    public void setInpDateTime(Date inpDateTime) {
        this.inpDateTime = inpDateTime;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "auth_date_time")
    public Date getAuthDateTime() {
        return authDateTime;
    }

    public void setAuthDateTime(Date authDateTime) {
        this.authDateTime = authDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    @OneToMany(mappedBy = "parentDocCategoryMst", fetch = FetchType.LAZY)
    public List<DocCategoryMaster> getSubCategoryDocList() {
        return subCategoryDocList;
    }

    public void setSubCategoryDocList(List<DocCategoryMaster> subCategoryDocList) {
        this.subCategoryDocList = subCategoryDocList;
    }

    @Column(name = "doc_category_description")
    public String getDocCategoryDescription() {
        return docCategoryDescription;
    }

    public void setDocCategoryDescription(String docCategoryDescription) {
        this.docCategoryDescription = docCategoryDescription;
    }
}
