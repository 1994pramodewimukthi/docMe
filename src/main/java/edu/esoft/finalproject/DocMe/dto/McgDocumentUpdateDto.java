package edu.esoft.finalproject.DocMe.dto;

public class McgDocumentUpdateDto {
    private String docId;
    private String docName;
    private String footerHeight;
    private String skipDateCount;
    private String startPage;
    private String pageCount;
    private String docStatus;
    private String docSignStatus;
    private String docOpenNewTab;
    private String remainingDates;
    private String activeRole;
    private String uploadedDate;


    public String getDocId() {
        return docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFooterHeight() {
        return footerHeight;
    }

    public void setFooterHeight(String footerHeight) {
        this.footerHeight = footerHeight;
    }

    public String getSkipDateCount() {
        return skipDateCount;
    }

    public void setSkipDateCount(String skipDateCount) {
        this.skipDateCount = skipDateCount;
    }

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getDocSignStatus() {
        return docSignStatus;
    }

    public void setDocSignStatus(String docSignStatus) {
        this.docSignStatus = docSignStatus;
    }

    public String getDocOpenNewTab() {
        return docOpenNewTab;
    }

    public void setDocOpenNewTab(String docOpenNewTab) {
        this.docOpenNewTab = docOpenNewTab;
    }

    public String getRemainingDates() {
        return remainingDates;
    }

    public void setRemainingDates(String remainingDates) {
        this.remainingDates = remainingDates;
    }

    public String getActiveRole() {
        return activeRole;
    }

    public void setActiveRole(String activeRole) {
        this.activeRole = activeRole;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}
