package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "doc_category_temp")
public class DocCategoryTemp implements Serializable {

    private Integer docCategoryTempId;
    private Integer parentDocCategoryTemp;
    private String docCategoryName;
    private String docCategoryDescription;
    private int sortingOrder;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;
    private String authUserId;
    private Date authDateTime;
    private String reason;

    public DocCategoryTemp() {
    }

    public DocCategoryTemp(Integer docCategoryTempId) {
        this.docCategoryTempId = docCategoryTempId;
    }

//    private List<DocCategoryTemp> subCategoryDocList;

    @Id
    @Column(name = "doc_category_temp_id")
    public Integer getDocCategoryTempId() {
        return docCategoryTempId;
    }

    public void setDocCategoryTempId(Integer docCategoryTempId) {
        this.docCategoryTempId = docCategoryTempId;
    }

    public Integer getParentDocCategoryTemp() {
        return parentDocCategoryTemp;
    }

    public void setParentDocCategoryTemp(Integer parentDocCategoryTemp) {
        this.parentDocCategoryTemp = parentDocCategoryTemp;
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

//    @OneToMany(mappedBy = "parentDocCategoryTemp", fetch = FetchType.LAZY)
//    public List<DocCategoryTemp> getSubCategoryDocList() {
//       return subCategoryDocList;
//    }
//
//    public void setSubCategoryDocList(List<DocCategoryTemp> subCategoryDocList) {
//        this.subCategoryDocList = subCategoryDocList;
//    }

    @Column(name = "doc_category_description")
    public String getDocCategoryDescription() {
        return docCategoryDescription;
    }

    public void setDocCategoryDescription(String docCategoryDescription) {
        this.docCategoryDescription = docCategoryDescription;
    }
}
