package edu.esoft.finalproject.DocMe.dto;

import java.io.InputStream;

public class McgPdfDto {

    private String docId;
    private String docName;
    private String pdfFile;
    private String signatureFile;
    private String acknowledgement;
    private String remainingDates;
    private InputStream inputStream;
    private String signStatus;

    public McgPdfDto() {
    }

    public McgPdfDto(String empty) {
        this.docId = empty;
        this.docName = empty;
        this.pdfFile = empty;
        this.signatureFile = empty;
        this.acknowledgement = empty;
        this.remainingDates = empty;
        this.signStatus = empty;
    }

    public String getSignatureFile() {
        return signatureFile;
    }

    public void setSignatureFile(String signatureFile) {
        this.signatureFile = signatureFile;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getRemainingDates() {
        return remainingDates;
    }

    public void setRemainingDates(String remainingDates) {
        this.remainingDates = remainingDates;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
