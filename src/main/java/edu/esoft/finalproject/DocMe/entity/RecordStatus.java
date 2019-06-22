package edu.esoft.finalproject.DocMe.entity;

import javax.persistence.*;

@Entity
@Table(name = "record_status")
public class RecordStatus {
    private int statusId = 0;
    private String statusDescription = "";

    public RecordStatus() {
    }

    public RecordStatus(int statusId) {
        this.statusId = statusId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Column(name = "status_description")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
