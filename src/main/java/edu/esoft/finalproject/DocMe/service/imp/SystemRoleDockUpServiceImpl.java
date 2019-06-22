package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.entity.SystemRole;
import edu.esoft.finalproject.DocMe.repository.SystemRoleDockUpRepository;
import edu.esoft.finalproject.DocMe.service.SystemRoleDockUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemRoleDockUpServiceImpl implements SystemRoleDockUpService {

    @Autowired
    SystemRoleDockUpRepository systemRoleDockUpRepository;

    @Override
    public SystemRole getSystemRoleById(int id) {
        return systemRoleDockUpRepository.findBySystemRoleId(id);
    }
}
