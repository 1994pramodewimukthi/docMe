package edu.esoft.finalproject.DocMe.dto;

public class DocAuthDto {
    private int docId;
    private String docName;
    private String catagoryName;
    private String authorizeButton;
    private String rejectButton;
    private String viewButton;
    private String inputuser;
    private String inputtime;

    public DocAuthDto() {
    }


    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getInputuser() {
        return inputuser;
    }

    public void setInputuser(String inputuser) {
        this.inputuser = inputuser;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getAuthorizeButton() {
        return authorizeButton;
    }

    public void setAuthorizeButton(String authorizeButton) {
        this.authorizeButton = authorizeButton;
    }

    public String getRejectButton() {
        return rejectButton;
    }

    public void setRejectButton(String rejectButton) {
        this.rejectButton = rejectButton;
    }

    public String getViewButton() {
        return viewButton;
    }

    public void setViewButton(String viewButton) {
        this.viewButton = viewButton;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }
}
