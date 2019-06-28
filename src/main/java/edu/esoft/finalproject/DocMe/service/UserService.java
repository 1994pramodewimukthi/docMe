package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.UserDto;

import javax.servlet.http.HttpSession;

public interface UserService {

    Long userSave(UserDto userDto) throws Exception;

    Long userLogin(UserDto userDto, HttpSession session) throws Exception;

    UserDto getUser(String  userId) throws Exception;
}
