package edu.esoft.finalproject.DocMe.entity;


import javax.persistence.*;

@Entity
@Table(name = "document_upload_temp_system_role")
public class DocumentUploadTempSystemRole {
    private Long documentUploadTempSystemRoleId;
    private SystemRole systemRole;
    private DocumentUploadTemp documentUploadTemp;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_upload_temp_system_role_id")
    public Long getDocumentUploadTempSystemRoleId() {
        return documentUploadTempSystemRoleId;
    }

    public void setDocumentUploadTempSystemRoleId(Long documentUploadTempSystemRoleId) {
        this.documentUploadTempSystemRoleId = documentUploadTempSystemRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "system_role_id")
    public SystemRole getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(SystemRole systemRole) {
        this.systemRole = systemRole;
    }

    @ManyToOne
    @JoinColumn(name = "doc_upload_temp_id")
    public DocumentUploadTemp getDocumentUploadTemp() {
        return documentUploadTemp;
    }

    public void setDocumentUploadTemp(DocumentUploadTemp documentUploadTemp) {
        this.documentUploadTemp = documentUploadTemp;
    }
}
