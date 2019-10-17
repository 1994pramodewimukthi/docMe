package edu.esoft.finalproject.DocMe.dto;


import edu.esoft.finalproject.DocMe.entity.RecordStatus;

import java.io.Serializable;
import java.util.Date;

public class SystemRoleDto implements Serializable {
    private int systemRoleId;
    private String systemRoleName;
    private String systemRoleStatus;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;

    public SystemRoleDto(int systemRoleId, String systemRoleName, String systemRoleStatus, RecordStatus recordStatus, String inpUserId, Date inpDateTime) {
        this.systemRoleId = systemRoleId;
        this.systemRoleName = systemRoleName;
        this.systemRoleStatus = systemRoleStatus;
        this.recordStatus = recordStatus;
        this.inpUserId = inpUserId;
        this.inpDateTime = inpDateTime;
    }

    public SystemRoleDto() {
    }

    public int getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(int systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    public String getSystemRoleName() {
        return systemRoleName;
    }

    public void setSystemRoleName(String systemRoleName) {
        this.systemRoleName = systemRoleName;
    }

    public String getSystemRoleStatus() {
        return systemRoleStatus;
    }

    public void setSystemRoleStatus(String systemRoleStatus) {
        this.systemRoleStatus = systemRoleStatus;
    }

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

    public Date getInpDateTime() {
        return inpDateTime;
    }

    public void setInpDateTime(Date inpDateTime) {
        this.inpDateTime = inpDateTime;
    }
}
