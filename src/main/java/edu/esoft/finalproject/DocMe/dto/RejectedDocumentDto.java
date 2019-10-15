package edu.esoft.finalproject.DocMe.dto;


public class RejectedDocumentDto {
    private int docId;
    private String docName;
    private String catName;
    private String rejectedReson;
    private String resubmit;
    private String rejecteduser;
    private String rejectedtime;

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getRejecteduser() {
        return rejecteduser;
    }

    public void setRejecteduser(String rejecteduser) {
        this.rejecteduser = rejecteduser;
    }

    public String getRejectedReson() {
        return rejectedReson;
    }

    public void setRejectedReson(String rejectedReson) {
        this.rejectedReson = rejectedReson;
    }

    public String getResubmit() {
        return resubmit;
    }

    public void setResubmit(String resubmit) {
        this.resubmit = resubmit;
    }

    public String getRejectedtime() {
        return rejectedtime;
    }

    public void setRejectedtime(String rejectedtime) {
        this.rejectedtime = rejectedtime;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}