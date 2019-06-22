package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.MCGDocumentExpire;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MCGDocumentExpireRepository extends CrudRepository<MCGDocumentExpire, Integer> {
}
