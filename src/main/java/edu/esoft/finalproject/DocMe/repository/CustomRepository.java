package edu.esoft.finalproject.DocMe.repository;

import edu.esoft.finalproject.DocMe.dto.McgReportDto;
import edu.esoft.finalproject.DocMe.dto.SystemMenuItemWithPrivilegesDto;

import java.util.List;

public interface CustomRepository {

    List<SystemMenuItemWithPrivilegesDto> getPrivilegedMenuForUserRole(Integer id);

    List msgReportJson();

}
