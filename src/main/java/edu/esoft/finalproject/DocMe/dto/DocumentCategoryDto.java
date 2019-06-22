package edu.esoft.finalproject.DocMe.dto;

import java.io.Serializable;

public class DocumentCategoryDto implements Serializable {

    private String categoryId;
    private String categoryName;
    private String categoryInputUser;
    private String categoryInputDateTime;

    public DocumentCategoryDto() {

    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryInputUser() {
        return categoryInputUser;
    }

    public void setCategoryInputUser(String categoryInputUser) {
        this.categoryInputUser = categoryInputUser;
    }

    public String getCategoryInputDateTime() {
        return categoryInputDateTime;
    }

    public void setCategoryInputDateTime(String categoryInputDateTime) {
        this.categoryInputDateTime = categoryInputDateTime;
    }
}
