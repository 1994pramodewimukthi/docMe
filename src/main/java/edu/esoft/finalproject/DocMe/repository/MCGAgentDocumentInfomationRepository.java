package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.MCGAgentDocumentInfomation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MCGAgentDocumentInfomationRepository extends CrudRepository<MCGAgentDocumentInfomation, Integer> {

    @Query(value = "SELECT * FROM mcg_agent_document_info WHERE doc_id = ?1 AND agent_code = ?2 AND status = 'SIGN'", nativeQuery = true)
    MCGAgentDocumentInfomation findIsSignByDocIdAndAgentCode(int docId, String agentCode);

    @Query(value = "SELECT * FROM mcg_agent_document_info WHERE doc_id = ?1 AND agent_code = ?2 AND status = 'PENDING'", nativeQuery = true)
    MCGAgentDocumentInfomation findPendingByAndDocIdAndAgentCode(int docId, String agentCode);


}
