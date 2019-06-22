package edu.esoft.finalproject.DocMe.config;

public class NotificationMessage {

    //isSucsess = 1 => true
    //isSucsess = 0 => flase

    private int isSucsess;
    private String message;

    public NotificationMessage() {

    }

    public NotificationMessage(int isSucsess, String message) {
        this.isSucsess = isSucsess;
        this.message = message;
    }

    public int getIsSucsess() {
        return isSucsess;
    }

    public void setIsSucsess(int isSucsess) {
        this.isSucsess = isSucsess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
