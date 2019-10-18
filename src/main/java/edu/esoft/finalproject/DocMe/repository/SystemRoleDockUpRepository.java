package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.SystemRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SystemRoleDockUpRepository extends CrudRepository<SystemRole, Integer> {

    SystemRole findBySystemRoleId(int id);

    List<SystemRole> findAllBySystemRoleStatus(String status);
}
