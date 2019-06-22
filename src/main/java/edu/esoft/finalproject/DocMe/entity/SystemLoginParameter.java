package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "system_login_parameter")
public class SystemLoginParameter implements Serializable {
    private int systemLoginParameterId;
    //  private AccessUserType accessUserType;
    private int loginAttempt;
    private int pageTimeout;
    private int passwordExpiryDays;
    private int lastLoginExpiryDays;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "system_login_parameter_id")
    public int getSystemLoginParameterId() {
        return systemLoginParameterId;
    }

    public void setSystemLoginParameterId(int systemLoginParameterId) {
        this.systemLoginParameterId = systemLoginParameterId;
    }

    /*
        @OneToOne(mappedBy = "systemLoginParameter")
        public AccessUserType getAccessUserType() {
            return accessUserType;
        }

        public void setAccessUserType(AccessUserType accessUserType) {
            this.accessUserType = accessUserType;
        }*/
    @Column(name = "login_attempt")
    public int getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    @Column(name = "page_timeout")
    public int getPageTimeout() {
        return pageTimeout;
    }

    public void setPageTimeout(int pageTimeout) {
        this.pageTimeout = pageTimeout;
    }

    @Column(name = "password_expiry_days")
    public int getPasswordExpiryDays() {
        return passwordExpiryDays;
    }

    public void setPasswordExpiryDays(int passwordExpiryDays) {
        this.passwordExpiryDays = passwordExpiryDays;
    }

    @Column(name = "last_login_expiry_days")
    public int getLastLoginExpiryDays() {
        return lastLoginExpiryDays;
    }

    public void setLastLoginExpiryDays(int lastLoginExpiryDays) {
        this.lastLoginExpiryDays = lastLoginExpiryDays;
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
}
