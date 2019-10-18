package edu.esoft.finalproject.DocMe.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemRolePrivilagesWrapperDto implements Serializable {
    private String systemRoleId;
    private List<SystemMenuItemPrivilegeDto> systemMenuItemPrivilegeDtos = new ArrayList<>();

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    public List<SystemMenuItemPrivilegeDto> getSystemMenuItemPrivilegeDtos() {
        return systemMenuItemPrivilegeDtos;
    }

    public void setSystemMenuItemPrivilegeDtos(List<SystemMenuItemPrivilegeDto> systemMenuItemPrivilegeDtos) {
        this.systemMenuItemPrivilegeDtos = systemMenuItemPrivilegeDtos;
    }
}
