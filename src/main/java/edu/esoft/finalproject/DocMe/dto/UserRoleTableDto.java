package edu.esoft.finalproject.DocMe.dto;

import java.io.Serializable;

public class UserRoleTableDto implements Serializable {
    private Integer id;
    private String userName;
    private String email;
    private String systemRoleName;
    private String systemRoleStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
