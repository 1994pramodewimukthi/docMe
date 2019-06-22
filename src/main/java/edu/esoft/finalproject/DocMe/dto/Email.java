package edu.esoft.finalproject.DocMe.dto;

import java.io.Serializable;

public class Email  implements Serializable {
    private String emailLogId = "25";
    private String fromAddress = "from address";
    private String toAddress= " to address";
    private String ccAddress= "cc address";
    private String subject= "subject";
    private String emailMessage= "email";

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
}
