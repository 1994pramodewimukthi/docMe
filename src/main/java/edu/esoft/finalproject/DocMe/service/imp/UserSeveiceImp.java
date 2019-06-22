package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.dto.UserDto;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.repository.UserRepository;
import edu.esoft.finalproject.DocMe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class UserSeveiceImp implements UserService {


    @Autowired
    UserRepository userRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Long SUCCESS = 1L;
    private Long ERROR = 0L;

    @Override
    public Long userSave(UserDto userDto) throws Exception {

        User user = new User();

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddressLine1(userDto.getAddressLine1());
        user.setAddressLine2(userDto.getAddressLine2());
        user.setAddressLine3(userDto.getAddressLine3());
        user.setDateOfBirth(simpleDateFormat.parse(userDto.getDateOfBirth()));
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setNic(userDto.getNic());

        User save = userRepository.save(user);

        if (null != save) {
            return SUCCESS;
        }
        return ERROR;
    }

    @Override
    public UserDto getUser(String userId) throws Exception {
        User user = userRepository.findOne(Integer.parseInt(userId));
        if (null != user) {
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setAddressLine1(user.getAddressLine1());
            userDto.setAddressLine2(user.getAddressLine2());
            userDto.setAddressLine3(user.getAddressLine3());
            userDto.setDateOfBirth(simpleDateFormat.format(user.getDateOfBirth()));
            userDto.setEmail(user.getEmail());
            userDto.setMobile(user.getMobile());
            userDto.setNic(user.getNic());
            return userDto;
        }
        return null;
    }
}
