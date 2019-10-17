package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;

@Entity
@Table(name = "system_menu_item_privilege")
public class SystemMenuItemPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer systemMenuItemId;
    private String systemRoleId;
    private boolean viewPrivilege;
    private boolean authorizationPrivilege;
    private boolean savePrivilege;
    private boolean deletePrivilege;
    private boolean updatePrivilege;

    public SystemMenuItemPrivilege() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSystemMenuItemId() {
        return systemMenuItemId;
    }

    public void setSystemMenuItemId(Integer systemMenuItemId) {
        this.systemMenuItemId = systemMenuItemId;
    }

    public String getSystemRoleId() {
        return systemRoleId;
    }

    public void setSystemRoleId(String systemRoleId) {
        this.systemRoleId = systemRoleId;
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
