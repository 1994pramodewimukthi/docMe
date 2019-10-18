package edu.esoft.finalproject.DocMe.dto;

import javax.xml.crypto.Data;
import java.util.Date;

public class SystemMenuItemDto {
    private Integer id;
    private String systemMenuName;
    private Date inputDateTime;
    private String inputUser;

    public SystemMenuItemDto() {
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
