package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.dto.SystemMenuItemPrivilegeDto;
import edu.esoft.finalproject.DocMe.dto.SystemRoleDto;
import edu.esoft.finalproject.DocMe.dto.SystemRolePrivilagesWrapperDto;
import edu.esoft.finalproject.DocMe.entity.SystemMenuItem;
import edu.esoft.finalproject.DocMe.entity.SystemMenuItemPrivilege;
import edu.esoft.finalproject.DocMe.entity.SystemRole;
import edu.esoft.finalproject.DocMe.repository.SystemMenuItemPrivilegeRepository;
import edu.esoft.finalproject.DocMe.repository.SystemMenuItemRepository;
import edu.esoft.finalproject.DocMe.repository.SystemRoleDockUpRepository;
import edu.esoft.finalproject.DocMe.service.SystemRoleDockUpService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SystemRoleDockUpServiceImpl implements SystemRoleDockUpService {

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SystemRoleDockUpServiceImpl.class);

    @Autowired
    private SystemRoleDockUpRepository systemRoleDockUpRepository;

    @Autowired
    private SystemMenuItemPrivilegeRepository systemMenuItemPrivilegeRepository;

    @Autowired
    private SystemMenuItemRepository systemMenuItemRepository;

    @Override
    public SystemRole getSystemRoleById(int id) {
        return systemRoleDockUpRepository.findBySystemRoleId(id);
    }

    @Override
    public boolean addSystemRole(SystemRoleDto systemRoleDto) throws Exception {
        SystemRole systemRole = new SystemRole();

        try {
            if (systemRoleDto.getSystemRoleId() != 0) {
                systemRole.setSystemRoleId(systemRoleDto.getSystemRoleId());
            }
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

    @Override
    public SystemRolePrivilagesWrapperDto getAllSystemMenuItemPrivilagesForSystemRoleId(String id) throws Exception {
        List<SystemMenuItemPrivilegeDto> systemMenuItemPrivilegeListDtos = new ArrayList<>();
        SystemRolePrivilagesWrapperDto systemRolePrivilagesWrapperDto = new SystemRolePrivilagesWrapperDto();
        try {
            Iterable<SystemMenuItem> menuItems = systemMenuItemRepository.findAll();
            for (SystemMenuItem menuItem : menuItems) {
                SystemMenuItemPrivilege menu = systemMenuItemPrivilegeRepository.findBySystemRoleIdAndSystemMenuItemId(id, menuItem.getId());
                SystemMenuItemPrivilegeDto systemMenuItemPrivilegeDto = new SystemMenuItemPrivilegeDto();

                if (null != menu) {
                    BeanUtils.copyProperties(menu, systemMenuItemPrivilegeDto);
                }

                systemMenuItemPrivilegeDto.setSystemMenuItemName(menuItem.getSystemMenuName());
                systemMenuItemPrivilegeDto.setSystemMenuItemId(menuItem.getId());
                systemMenuItemPrivilegeListDtos.add(systemMenuItemPrivilegeDto);

            }
            systemRolePrivilagesWrapperDto.setSystemMenuItemPrivilegeDtos(systemMenuItemPrivilegeListDtos);
            systemRolePrivilagesWrapperDto.setSystemRoleId(id);
            return systemRolePrivilagesWrapperDto;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean changeSystemRolePrivileges(SystemRolePrivilagesWrapperDto systemRolePrivileges) throws Exception {
        List<SystemMenuItemPrivilegeDto> systemMenuItemPrivilegeDtos = systemRolePrivileges.getSystemMenuItemPrivilegeDtos();
        try {
            for (SystemMenuItemPrivilegeDto systemMenuItemPrivilegeDto : systemMenuItemPrivilegeDtos) {
                SystemMenuItemPrivilege systemMenuItemPrivilege = new SystemMenuItemPrivilege();
                systemMenuItemPrivilege.setSystemRoleId(systemRolePrivileges.getSystemRoleId());
                BeanUtils.copyProperties(systemMenuItemPrivilegeDto, systemMenuItemPrivilege);
                systemMenuItemPrivilegeRepository.save(systemMenuItemPrivilege);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

}
