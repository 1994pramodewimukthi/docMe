package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.SystemRole;
import org.springframework.data.repository.CrudRepository;

public interface SystemRoleDockUpRepository extends CrudRepository<SystemRole, Integer> {

    SystemRole findBySystemRoleId(int id);
}
