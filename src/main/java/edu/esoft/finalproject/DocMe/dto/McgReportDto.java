package edu.esoft.finalproject.DocMe.dto;

import java.io.Serializable;
import java.util.Date;

public class McgReportDto implements Serializable {

    private String agentName;
    private String docName;
    private Date docIssueDate;
    private String docIssueDateString;
    private Date docSignDate;
    private String docSignDateString;
    private String signStatus;


    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Date getDocIssueDate() {
        return docIssueDate;
    }

    public void setDocIssueDate(Date docIssueDate) {
        this.docIssueDate = docIssueDate;
    }

    public String getDocIssueDateString() {
        return docIssueDateString;
    }

    public void setDocIssueDateString(String docIssueDateString) {
        this.docIssueDateString = docIssueDateString;
    }

    public Date getDocSignDate() {
        return docSignDate;
    }

    public void setDocSignDate(Date docSignDate) {
        this.docSignDate = docSignDate;
    }

    public String getDocSignDateString() {
        return docSignDateString;
    }

    public void setDocSignDateString(String docSignDateString) {
        this.docSignDateString = docSignDateString;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }
}
