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

    public SystemMenuItem() {
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
