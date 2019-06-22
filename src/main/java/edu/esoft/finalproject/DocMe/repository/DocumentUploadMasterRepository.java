package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.DocumentUploadMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentUploadMasterRepository extends CrudRepository<DocumentUploadMaster, Long> {

    @Query(value = "SELECT * FROM doc_upload_mst WHERE doc_category_mst_id =?1", nativeQuery = true)
    public List<DocumentUploadMaster> findAllByDocCategoryMaster(int categoryId);

    @Query(value = "SELECT * FROM doc_upload_mst WHERE doc_category_mst_id =?1 AND record_status = 7 AND publish_date<= ?2 AND expire_date > ?2", nativeQuery = true)
    public List<DocumentUploadMaster> findAllByDocCategoryMasterAndPublishDateAndExpireDate(int categoryId, String date);

    @Query(value = "SELECT * FROM doc_upload_mst WHERE doc_category_mst_id =?1 AND record_status = 7", nativeQuery = true)
    public List<DocumentUploadMaster> findAllByDocCategoryMasterInActive(int categoryId);


    List<DocumentUploadMaster> findByRecordStatusStatusId(int id);

    DocumentUploadMaster findByDocumentUploadMstId(int id);

    @Query(value = "SELECT MAX(doc_upload_mst_id) AS doc_upload_mst_id  FROM doc_upload_mst", nativeQuery = true)
    public Integer findMaxId();

    void deleteByDocumentUploadMstId(int id);
}
