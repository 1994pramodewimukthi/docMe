package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    public List<User> findByUserName(String userName);

    public User findByUserNameAndPassword(String username, String password);
}
