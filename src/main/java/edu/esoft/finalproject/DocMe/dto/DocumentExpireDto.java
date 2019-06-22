package edu.esoft.finalproject.DocMe.dto;

public class DocumentExpireDto {
    private String id;
    private String expireYear;
    private String newExpireYear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public String getNewExpireYear() {
        return newExpireYear;
    }

    public void setNewExpireYear(String newExpireYear) {
        this.newExpireYear = newExpireYear;
    }
}
