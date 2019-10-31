package edu.esoft.finalproject.DocMe.dto;

public class SystemMenuItemPrivilegeDto {
    private Integer id;
    private Integer systemMenuItemId;
    private Integer parentMenuItemId;
    private String systemMenuItemName;

    public Integer getParentMenuItemId() {
        return parentMenuItemId;
    }

    public void setParentMenuItemId(Integer parentMenuItemId) {
        this.parentMenuItemId = parentMenuItemId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private String systemRoleId;
    private boolean viewPrivilege;
    private boolean authorizationPrivilege;
    private boolean savePrivilege;
    private boolean deletePrivilege;
    private boolean updatePrivilege;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
    }

    public String getSystemMenuItemName() {
        return systemMenuItemName;
    }

    public void setSystemMenuItemName(String systemMenuItemName) {
        this.systemMenuItemName = systemMenuItemName;
    }

    public SystemMenuItemPrivilegeDto() {
    }

    public Integer getSystemMenuItemId() {
        return systemMenuItemId;
    }

    public void setSystemMenuItemId(Integer systemMenuItemId) {
        this.systemMenuItemId = systemMenuItemId;
    }

    public boolean isViewPrivilege() {
        return viewPrivilege;
    }

    public void setViewPrivilege(boolean viewPrivilege) {
        this.viewPrivilege = viewPrivilege;
    }

    public boolean isAuthorizationPrivilege() {
        return authorizationPrivilege;
    }

    public void setAuthorizationPrivilege(boolean authorizationPrivilege) {
        this.authorizationPrivilege = authorizationPrivilege;
    }

    public boolean isSavePrivilege() {
        return savePrivilege;
    }

    public void setSavePrivilege(boolean savePrivilege) {
        this.savePrivilege = savePrivilege;
    }

    public boolean isDeletePrivilege() {
        return deletePrivilege;
    }

    public void setDeletePrivilege(boolean deletePrivilege) {
        this.deletePrivilege = deletePrivilege;
    }

    public boolean isUpdatePrivilege() {
        return updatePrivilege;
    }

    public void setUpdatePrivilege(boolean updatePrivilege) {
        this.updatePrivilege = updatePrivilege;
    }
}
