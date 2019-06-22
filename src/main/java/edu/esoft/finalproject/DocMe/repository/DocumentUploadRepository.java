package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.DocumentUploadTemp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentUploadRepository extends CrudRepository<DocumentUploadTemp, Long> {
    DocumentUploadTemp findByDocumentUploadTempId(Integer documentUploadTempId);

    DocumentUploadTemp findByDocCategoryMasterDocCategoryMstId(int docCatagoryId);

    void deleteByDocumentUploadTempId(int id);

    Iterable<DocumentUploadTemp> findAllByRecordStatusStatusIdNot(int id);

    Iterable<DocumentUploadTemp> findByRecordStatusStatusIdAndChannel(int id, String channel);

    Iterable<DocumentUploadTemp> findByRecordStatusStatusId(int id);

    @Query(value = "SELECT MAX(doc_upload_temp_id) AS doc_upload_temp_id  FROM doc_upload_temp", nativeQuery = true)
    public Integer findMaxId();

    @Query(value = "SELECT * FROM doc_upload_temp WHERE (record_status =6 OR record_status=5) AND channel=?1 ORDER BY doc_upload_temp_id", nativeQuery = true)
    public List<DocumentUploadTemp> findAllByRecordStatusPendingDeletByChanel(String channelType);

    @Query(value = "SELECT * FROM doc_upload_temp WHERE record_status =6 OR record_status=5 ORDER BY doc_upload_temp_id", nativeQuery = true)
    public List<DocumentUploadTemp> findAllByRecordStatusPendingDelet();

}
