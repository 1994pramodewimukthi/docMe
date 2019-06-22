package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
}
