package edu.esoft.finalproject.DocMe.dto;

import java.util.List;

public class AuthRejectDocumentWebix {

    List<AuthRejectDocumentWebix> data;
    private int categoryId;
    private String categoryName;
    private String parentCategoryName;
    private String chanel;
    private String authButton;
    private String rejectButton;
    private String inputUser;
    private String inputDateTime;
    private String reason;
    private int autorizationStatus;
    private boolean optionView;
    private boolean optionInsert;
    private boolean optionModify;
    private boolean optionDelete;
    private boolean optionGrant;
    private boolean optionActive;
    private boolean optionAuthorize;

    public AuthRejectDocumentWebix() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getAuthButton() {
        return authButton;
    }

    public void setAuthButton(String authButton) {
        this.authButton = authButton;
    }

    public String getRejectButton() {
        return rejectButton;
    }

    public void setRejectButton(String rejectButton) {
        this.rejectButton = rejectButton;
    }

    public String getInputUser() {
        return inputUser;
    }

    public void setInputUser(String inputUser) {
        this.inputUser = inputUser;
    }

    public String getInputDateTime() {
        return inputDateTime;
    }

    public void setInputDateTime(String inputDateTime) {
        this.inputDateTime = inputDateTime;
    }

    public List<AuthRejectDocumentWebix> getData() {
        return data;
    }

    public void setData(List<AuthRejectDocumentWebix> data) {
        this.data = data;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getAutorizationStatus() {
        return autorizationStatus;
    }

    public void setAutorizationStatus(int autorizationStatus) {
        this.autorizationStatus = autorizationStatus;
    }


    public boolean isOptionView() {
        return optionView;
    }

    public void setOptionView(boolean optionView) {
        this.optionView = optionView;
    }

    public boolean isOptionInsert() {
        return optionInsert;
    }

    public void setOptionInsert(boolean optionInsert) {
        this.optionInsert = optionInsert;
    }

    public boolean isOptionModify() {
        return optionModify;
    }

    public void setOptionModify(boolean optionModify) {
        this.optionModify = optionModify;
    }

    public boolean isOptionDelete() {
        return optionDelete;
    }

    public void setOptionDelete(boolean optionDelete) {
        this.optionDelete = optionDelete;
    }

    public boolean isOptionGrant() {
        return optionGrant;
    }

    public void setOptionGrant(boolean optionGrant) {
        this.optionGrant = optionGrant;
    }

    public boolean isOptionActive() {
        return optionActive;
    }

    public void setOptionActive(boolean optionActive) {
        this.optionActive = optionActive;
    }

    public boolean isOptionAuthorize() {
        return optionAuthorize;
    }

    public void setOptionAuthorize(boolean optionAuthorize) {
        this.optionAuthorize = optionAuthorize;
    }

}
