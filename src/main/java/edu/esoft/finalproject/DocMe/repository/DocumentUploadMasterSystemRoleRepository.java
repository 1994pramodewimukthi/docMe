package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.DocumentUploadMasterSystemRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentUploadMasterSystemRoleRepository extends CrudRepository<DocumentUploadMasterSystemRole, Long> {

    @Query(value = "SELECT * FROM document_upload_master_system_role WHERE doc_upload_mst_id=?1 AND system_role_id=?2", nativeQuery = true)
    public DocumentUploadMasterSystemRole findByDocumentUploadMasterAndSystemRole(int masterId, int roleId);

    List<DocumentUploadMasterSystemRole> findAllByDocumentUploadMasterDocumentUploadMstId(int id);

    void deleteAllByDocumentUploadMasterDocumentUploadMstId(int id);
}