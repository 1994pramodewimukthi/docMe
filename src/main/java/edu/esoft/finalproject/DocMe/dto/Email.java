package edu.esoft.finalproject.DocMe.dto;

import java.io.InputStream;
import java.io.Serializable;

public class Email  implements Serializable {
    private String docId;
    private String emailLogId;
    private String fromAddress;
    private String toAddress;
    private String ccAddress;
    private String subject;
    private String emailMessage;
    private String body;
    private String documentNAme;
    private String type;
    private byte[] docInputStream;

    public String getEmailLogId() {
        return emailLogId;
    }

    public void setEmailLogId(String emailLogId) {
        this.emailLogId = emailLogId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getDocumentNAme() {
        return documentNAme;
    }

    public void setDocumentNAme(String documentNAme) {
        this.documentNAme = documentNAme;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getDocInputStream() {
        return docInputStream;
    }

    public void setDocInputStream(byte[] docInputStream) {
        this.docInputStream = docInputStream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
