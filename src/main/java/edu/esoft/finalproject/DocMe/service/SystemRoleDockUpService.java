package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.SystemRoleDto;
import edu.esoft.finalproject.DocMe.entity.SystemRole;

import java.util.List;

public interface SystemRoleDockUpService {
    public SystemRole getSystemRoleById(int id) throws Exception;

    boolean addSystemRole(SystemRoleDto systemRoleDto) throws Exception;

    List<SystemRoleDto> getAllSystemRoles() throws Exception;
}
