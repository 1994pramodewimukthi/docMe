package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.UserDto;
import edu.esoft.finalproject.DocMe.dto.UserRoleTableDto;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    Long userSave(UserDto userDto) throws Exception;

    Long userLogin(UserDto userDto, HttpSession session) throws Exception;

    UserDto getUser(String  userId) throws Exception;

    List<UserRoleTableDto> getAllUsers() throws Exception;
}
