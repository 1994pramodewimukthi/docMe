package edu.esoft.finalproject.DocMe.entity;


import javax.persistence.*;

@Entity
@Table(name = "document_upload_master_system_role")
public class DocumentUploadMasterSystemRole {
    private Long documentUploadMasterSystemRoleId;
    private SystemRole systemRole;
    private DocumentUploadMaster documentUploadMaster;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_upload_master_system_role_id")
    public Long getDocumentUploadMasterSystemRoleId() {
        return documentUploadMasterSystemRoleId;
    }

    public void setDocumentUploadMasterSystemRoleId(Long documentUploadMasterSystemRoleId) {
        this.documentUploadMasterSystemRoleId = documentUploadMasterSystemRoleId;
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
    @JoinColumn(name = "doc_upload_mst_id")
    public DocumentUploadMaster getDocumentUploadMaster() {
        return documentUploadMaster;
    }

    public void setDocumentUploadMaster(DocumentUploadMaster documentUploadMaster) {
        this.documentUploadMaster = documentUploadMaster;
    }
}
