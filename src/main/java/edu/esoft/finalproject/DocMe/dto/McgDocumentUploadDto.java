package edu.esoft.finalproject.DocMe.dto;

import org.springframework.web.multipart.MultipartFile;

public class McgDocumentUploadDto {
    private String catId;
    private String documentVersion;
    private String documentValidRank;
    private String documentFooterHeight;
    private MultipartFile documentFile;
    private String documentAcknowledgement;
    private String startPage;
    private String pageCount;
    private String skipDateCount;

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }


    public String getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
    }

    public String getDocumentValidRank() {
        return documentValidRank;
    }

    public void setDocumentValidRank(String documentValidRank) {
        this.documentValidRank = documentValidRank;
    }

    public String getDocumentFooterHeight() {
        return documentFooterHeight;
    }

    public void setDocumentFooterHeight(String documentFooterHeight) {
        this.documentFooterHeight = documentFooterHeight;
    }

    public MultipartFile getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(MultipartFile documentFile) {
        this.documentFile = documentFile;
    }

    public String getDocumentAcknowledgement() {
        return documentAcknowledgement;
    }

    public void setDocumentAcknowledgement(String documentAcknowledgement) {
        this.documentAcknowledgement = documentAcknowledgement;
    }

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    public String getSkipDateCount() {
        return skipDateCount;
    }

    public void setSkipDateCount(String skipDateCount) {
        this.skipDateCount = skipDateCount;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }


}
