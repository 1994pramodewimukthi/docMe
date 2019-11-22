package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.MCGDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MCGDocumentRepository extends CrudRepository<MCGDocument, Integer> {


    @Query(value = "SELECT * FROM mcg_document WHERE status=?1 ", nativeQuery = true)
    ArrayList<MCGDocument> findAllDocumentByStatus(String status) throws Exception;

    @Query(value = "SELECT TOP 1 * FROM mcg_document WHERE status=?1 AND (rank_code = ?2 OR rank_code ='0') ORDER BY doc_id DESC", nativeQuery = true)
    MCGDocument findDocumentByStatusAndRank(String status, String rank) throws Exception;

    @Query(value = "SELECT * FROM mcg_document WHERE rank_code = ?1 OR rank_code ='0' ORDER BY doc_id DESC", nativeQuery = true)
    ArrayList<MCGDocument> findDocumentByRank(String rank) throws Exception;

    @Query(value = "SELECT * FROM mcg_document WHERE input_date_time >=?1  AND rank_code IN (0,?2) ORDER BY doc_id DESC ", nativeQuery = true)
    ArrayList<MCGDocument> findDocumentByRankAndAppointDate(String date, String rank) throws Exception;

    @Query(value = "SELECT * FROM mcg_document WHERE status=?1 AND rank_code= ?2", nativeQuery = true)
    MCGDocument findDocumentByStatusAndRoleId(String status, String roleId) throws Exception;

    @Query(value = "SELECT * FROM mcg_document WHERE status=?1 ", nativeQuery = true)
    MCGDocument findDocumentByStatus(String status) throws Exception;

    @Query(value = "SELECT doc_version FROM mcg_document WHERE cat_id =?1 ORDER BY doc_id DESC LIMIT 1", nativeQuery = true)
    String findVersionByCatId(String catId) throws Exception;

    @Query(value = "SELECT * FROM mcg_document ORDER BY doc_id DESC", nativeQuery = true)
    ArrayList<MCGDocument> findAllDocumentsDesc();

    @Query(value = "SELECT * FROM mcg_document WHERE input_date_time >=?1 ORDER BY doc_id DESC", nativeQuery = true)
    ArrayList<MCGDocument> findAllDocumentsByAppointDateDesc(String date);
}
