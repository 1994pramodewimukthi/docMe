package edu.esoft.finalproject.DocMe.service;

import edu.esoft.finalproject.DocMe.dto.UserDto;

public interface UserService {

    Long userSave(UserDto userDto) throws Exception;
    UserDto getUser(String  userId) throws Exception;
}
