package edu.esoft.finalproject.DocMe.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemMenuItemWithPrivilegesDto {

    private String systemRoleId;
    private List<HashMap> systemMenuItemPrivilegeDtos = new ArrayList<>();

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    public List<HashMap> getSystemMenuItemPrivilegeDtos() {
        return systemMenuItemPrivilegeDtos;
    }

    public void setSystemMenuItemPrivilegeDtos(List<HashMap> systemMenuItemPrivilegeDtos) {
        this.systemMenuItemPrivilegeDtos = systemMenuItemPrivilegeDtos;
    }
}