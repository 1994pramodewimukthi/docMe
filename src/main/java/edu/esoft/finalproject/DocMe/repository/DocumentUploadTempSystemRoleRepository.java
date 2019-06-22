package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.DocumentUploadTempSystemRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentUploadTempSystemRoleRepository extends CrudRepository<DocumentUploadTempSystemRole, Long> {

    List<DocumentUploadTempSystemRole> findByDocumentUploadTempDocumentUploadTempId(int id);

    void deleteAllByDocumentUploadTempDocumentUploadTempId(int id);


}
