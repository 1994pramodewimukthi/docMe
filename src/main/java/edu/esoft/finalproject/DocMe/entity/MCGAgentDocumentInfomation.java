package edu.esoft.finalproject.DocMe.entity;

import org.hibernate.envers.Audited;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Audited
@Table(name = "mcg_agent_document_info")
public class MCGAgentDocumentInfomation implements Serializable {

    @EmbeddedId
    private MCGAgentDocumentInfomationEmbed mcgAgentDocumentInfomationEmbed;
    private Date signDate;
    private String status;
    private Date docIssueDate;
    private String fileName;

    public MCGAgentDocumentInfomation() {
    }

    public MCGAgentDocumentInfomationEmbed getMcgAgentDocumentInfomationEmbed() {
        return mcgAgentDocumentInfomationEmbed;
    }

    public void setMcgAgentDocumentInfomationEmbed(MCGAgentDocumentInfomationEmbed mcgAgentDocumentInfomationEmbed) {
        this.mcgAgentDocumentInfomationEmbed = mcgAgentDocumentInfomationEmbed;
    }


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDocIssueDate() {
        return docIssueDate;
    }

    public void setDocIssueDate(Date docIssueDate) {
        this.docIssueDate = docIssueDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
