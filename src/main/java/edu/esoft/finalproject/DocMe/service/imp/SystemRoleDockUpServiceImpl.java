package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.contoller.UserController;
import edu.esoft.finalproject.DocMe.dto.SystemRoleDto;
import edu.esoft.finalproject.DocMe.entity.SystemRole;
import edu.esoft.finalproject.DocMe.repository.SystemRoleDockUpRepository;
import edu.esoft.finalproject.DocMe.service.SystemRoleDockUpService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SystemRoleDockUpServiceImpl implements SystemRoleDockUpService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SystemRoleDockUpServiceImpl.class);

    @Autowired
    SystemRoleDockUpRepository systemRoleDockUpRepository;

    @Override
    public SystemRole getSystemRoleById(int id) {
        return systemRoleDockUpRepository.findBySystemRoleId(id);
    }

    @Override
    public boolean addSystemRole(SystemRoleDto systemRoleDto) throws Exception {
        SystemRole systemRole = new SystemRole();

        try {
            systemRole.setSystemRoleName(systemRoleDto.getSystemRoleName());
            systemRole.setSystemRoleStatus(systemRoleDto.getSystemRoleStatus());
            systemRole.setInpDateTime(new Date());
            systemRole.setInpUserId(systemRoleDto.getInpUserId());

            systemRoleDockUpRepository.save(systemRole);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<SystemRoleDto> getAllSystemRoles() throws Exception {
        List<SystemRoleDto> systemRoleDtoList = new ArrayList<>();
        try {
            Iterable<SystemRole> all = systemRoleDockUpRepository.findAll();
            all.forEach(systemRole -> {
                SystemRoleDto systemRoleDto = new SystemRoleDto();

                systemRoleDto.setInpUserId(systemRole.getInpUserId());
                systemRoleDto.setInpDateTime(systemRole.getInpDateTime());
                systemRoleDto.setSystemRoleId(systemRole.getSystemRoleId());
                systemRoleDto.setSystemRoleStatus(systemRole.getSystemRoleStatus());
                systemRoleDto.setSystemRoleName(systemRole.getSystemRoleName());

                systemRoleDtoList.add(systemRoleDto);
            });
            return systemRoleDtoList;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

}
