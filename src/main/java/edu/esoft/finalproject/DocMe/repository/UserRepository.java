package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
