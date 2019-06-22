package edu.esoft.finalproject.DocMe.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "access_user_type")
public class AccessUserType {
    private Long accessUserTypeId;
    private String accessUserTypeCode;
    private String accessUserTypeName;
    private String agentClass;
    private String agentChannel;
    private String groupUserType;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;

    private SystemLoginParameter systemLoginParameter;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "access_user_type_id")
    public Long getAccessUserTypeId() {
        return accessUserTypeId;
    }

    public void setAccessUserTypeId(Long accessUserTypeId) {
        this.accessUserTypeId = accessUserTypeId;
    }

    @Column(name = "access_user_type_code")
    public String getAccessUserTypeCode() {
        return accessUserTypeCode;
    }

    public void setAccessUserTypeCode(String accessUserTypeCode) {
        this.accessUserTypeCode = accessUserTypeCode;
    }

    @Column(name = "access_user_type_name")
    public String getAccessUserTypeName() {
        return accessUserTypeName;
    }

    public void setAccessUserTypeName(String accessUserTypeName) {
        this.accessUserTypeName = accessUserTypeName;
    }

    @Column(name = "agent_class")
    public String getAgentClass() {
        return agentClass;
    }

    public void setAgentClass(String agentClass) {
        this.agentClass = agentClass;
    }

    @Column(name = "agent_channel")
    public String getAgentChannel() {
        return agentChannel;
    }

    public void setAgentChannel(String agentChannel) {
        this.agentChannel = agentChannel;
    }

    @Column(name = "group_user_type")
    public String getGroupUserType() {
        return groupUserType;
    }

    public void setGroupUserType(String groupUserType) {
        this.groupUserType = groupUserType;
    }

    @ManyToOne
    @JoinColumn(name = "record_status")
    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Column(name = "inp_user_id")
    public String getInpUserId() {
        return inpUserId;
    }

    public void setInpUserId(String inpUserId) {
        this.inpUserId = inpUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inp_date_time")
    public Date getInpDateTime() {
        return inpDateTime;
    }

    public void setInpDateTime(Date inpDateTime) {
        this.inpDateTime = inpDateTime;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "system_login_parameter_id")
    public SystemLoginParameter getSystemLoginParameter() {
        return systemLoginParameter;
    }

    public void setSystemLoginParameter(SystemLoginParameter systemLoginParameter) {
        this.systemLoginParameter = systemLoginParameter;
    }
}
