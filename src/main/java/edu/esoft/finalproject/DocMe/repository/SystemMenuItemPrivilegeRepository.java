package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.entity.SystemMenuItemPrivilege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SystemMenuItemPrivilegeRepository extends CrudRepository<SystemMenuItemPrivilege, Integer> {

    List<SystemMenuItemPrivilege> findAllBySystemRoleId(String id);

    SystemMenuItemPrivilege findBySystemRoleIdAndSystemMenuItemId(String id,Integer menuItemId);
}
