package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "system_menu_item")
public class SystemMenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String systemMenuName;
    private Date inputDateTime;
    private String inputUser;
    private Integer parentMenuId;
    private String url;

    public SystemMenuItem() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemMenuName() {
        return systemMenuName;
    }

    public void setSystemMenuName(String systemMenuName) {
        this.systemMenuName = systemMenuName;
    }

    public Date getInputDateTime() {
        return inputDateTime;
    }

    public void setInputDateTime(Date inputDateTime) {
        this.inputDateTime = inputDateTime;
    }

    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }
}
