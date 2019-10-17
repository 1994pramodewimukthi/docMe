package edu.esoft.finalproject.DocMe.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "system_role")
public class SystemRole implements Serializable {
    private int systemRoleId;
    private String systemRoleName;
    private String systemRoleStatus;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;

    //    private Set<SystemRoleSystemMenuItem> systemRoleSystemMenuItems;
    private AccessUserType accessUserType;

    public SystemRole() {
    }

    public SystemRole(int systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_role_id")
    public int getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(int systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    @Column(name = "system_role_name")
    public String getSystemRoleName() {
        return systemRoleName;
    }

    public void setSystemRoleName(String systemRoleName) {
        this.systemRoleName = systemRoleName;
    }

    @Column(name = "system_role_status")
    public String getSystemRoleStatus() {
        return systemRoleStatus;
    }

    public void setSystemRoleStatus(String systemRoleStatus) {
        this.systemRoleStatus = systemRoleStatus;
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

/*    @OneToMany(mappedBy = "systemRole", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "record_status IN (1,3,4)")
    @JsonIgnore
    public Set<SystemRoleSystemMenuItem> getSystemRoleSystemMenuItems() {
        return systemRoleSystemMenuItems;
    }

    public void setSystemRoleSystemMenuItems(Set<SystemRoleSystemMenuItem> systemRoleSystemMenuItems) {
        this.systemRoleSystemMenuItems = systemRoleSystemMenuItems;
    }*/

    @ManyToOne
    @JoinColumn(name = "access_user_type")
    public AccessUserType getAccessUserType() {
        return accessUserType;
    }

    public void setAccessUserType(AccessUserType accessUserType) {
        this.accessUserType = accessUserType;
    }
}
