package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.DocumentUploadHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentUploadHistoryRepository extends CrudRepository<DocumentUploadHistory, Long> {
    List<DocumentUploadHistory> findByRecordStatusStatusId(int statusId);
}