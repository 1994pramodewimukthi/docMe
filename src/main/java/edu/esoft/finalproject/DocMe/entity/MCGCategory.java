package edu.esoft.finalproject.DocMe.entity;



import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Audited
@Table(name = "mcg_category")
public class MCGCategory implements Serializable {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer catId;
    @Column(name = "category_name", length = 255)
    private String categoryName;
    @Column(name = "input_User")
    private String inputUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "input_date_time")
    private Date inputDateTime;


    public MCGCategory() {
    }


    public MCGCategory(Integer catId) {
        this.catId = catId;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }

    public Date getInputDateTime() {
        return inputDateTime;
    }

    public void setInputDateTime(Date inputDateTime) {
        this.inputDateTime = inputDateTime;
    }
}
