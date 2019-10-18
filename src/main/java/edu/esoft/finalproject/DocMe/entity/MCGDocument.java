package edu.esoft.finalproject.DocMe.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Audited
@Table(name = "mcg_document")
public class MCGDocument implements Serializable {

    @Id
    @Column(name = "doc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer docId;
    @ManyToOne
    @JoinColumn(name = "cat_id")
    private MCGCategory catId;
    @Column(name = "doc_version", length = 30)
    private String docVersion;
    @Column(name = "doc_name")
    private String docName;
    @Column(name = "footer_hight")
    private Integer footerHight;
    @Column(name = "file_store_name")
    private String fileStoreName;
    @Column(name = "acknowledgement", length = 5000)
    private String acknowledgement;
    @Column(name = "input_user")
    private String inputUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "input_date_time")
    private Date inputDateTime;
    @Column(name = "status")
    private String status;
    @Column(name = "start_page")
    private Integer startPage;
    @Column(name = "page_count")
    private Integer pageCount;
    @Column(name = "skip_date_count", length = 3)
    private String skipDateCount;


    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public MCGCategory getCatId() {
        return catId;
    }

    public void setCatId(MCGCategory catId) {
        this.catId = catId;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }



    public Integer getFooterHight() {
        return footerHight;
    }

    public void setFooterHight(Integer footerHight) {
        this.footerHight = footerHight;
    }

    public String getFileStoreName() {
        return fileStoreName;
    }

    public void setFileStoreName(String fileStoreName) {
        this.fileStoreName = fileStoreName;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }

    public Date getInputDateTime() {
        return inputDateTime;
    }

    public void setInputDateTime(Date inputDateTime) {
        this.inputDateTime = inputDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getSkipDateCount() {
        return skipDateCount;
    }

    public void setSkipDateCount(String skipDateCount) {
        this.skipDateCount = skipDateCount;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }


}
