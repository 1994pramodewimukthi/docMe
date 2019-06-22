package edu.esoft.finalproject.DocMe.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class MCGAgentDocumentInfomationEmbed implements Serializable {

    @Id
    @NotNull
    private String agentCode;
    @Id
    @NotNull
    private Integer docId;

    public MCGAgentDocumentInfomationEmbed() {
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }
}
