package edu.esoft.finalproject.DocMe.service.imp;

import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.MessageConstant;
import edu.esoft.finalproject.DocMe.dto.UserDto;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.repository.UserRepository;
import edu.esoft.finalproject.DocMe.service.UserService;
import edu.esoft.finalproject.DocMe.utility.Cryptography;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UserSeveiceImp implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    private Cryptography cryptography;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Long SUCCESS = 1L;
    private Long ERROR = 0L;

    @Override
    public Long userSave(UserDto userDto) throws Exception {

        User user = new User();

        List<User> byUserName = userRepository.findByUserName(userDto.getUserName());
        if (byUserName.isEmpty()) {

            Long valid = validate(userDto);

            if (valid.equals(SUCCESS)) {
//                user.setId(Integer.parseInt(userDto.getId()));
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setAddressLine1(userDto.getAddressLine1());
                user.setAddressLine2(userDto.getAddressLine2());
                user.setAddressLine3(userDto.getAddressLine3());
                user.setDateOfBirth(simpleDateFormat.parse(userDto.getDateOfBirth()));
                user.setEmail(userDto.getEmail());
                user.setMobile(userDto.getMobile());
                user.setNic(userDto.getNic());
                user.setUserName(userDto.getUserName());
                if (null != userDto.getPassword()) {
                    user.setPassword(cryptography.encrypt(userDto.getPassword().trim()));
                }
                User save = userRepository.save(user);

                if (null != save) {
                    return SUCCESS;
                }
            } else {
                return valid;
            }
        } else {
            return MessageConstant.DUPLICATE_USER;
        }
        return ERROR;
    }

    private Long validate(UserDto userDto) {

        if (userDto.getUserName().isEmpty()
                || userDto.getFirstName().isEmpty()
                || userDto.getLastName().isEmpty()
                || userDto.getAddressLine1().isEmpty()
                || userDto.getAddressLine2().isEmpty()
                || userDto.getEmail().isEmpty()
                || userDto.getPassword().isEmpty()
                || userDto.getDateOfBirth().isEmpty()
                || userDto.getNic().isEmpty()
                || userDto.getMobile().isEmpty())
            return MessageConstant.INVALID_INPUTS;
        return SUCCESS;

    }

    @Override
    public Long userLogin(UserDto userDto, HttpSession session) throws Exception {
        String encrypt = cryptography.encrypt(userDto.getPassword().trim());
        User user = userRepository.findByUserNameAndPassword(userDto.getUserName(), encrypt);
        if (null != user) {
            session.setAttribute(AppConstant.USER, user);
            return SUCCESS;
        }
        return MessageConstant.INVALID_LOGINS;
    }

    @Override
    public UserDto getUser(String userId) throws Exception {
        User user = userRepository.findOne(Integer.parseInt(userId));
        if (null != user) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId().toString());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setAddressLine1(user.getAddressLine1());
            userDto.setAddressLine2(user.getAddressLine2());
            userDto.setAddressLine3(user.getAddressLine3());
            userDto.setDateOfBirth(simpleDateFormat.format(user.getDateOfBirth()));
            userDto.setEmail(user.getEmail());
            userDto.setMobile(user.getMobile());
            userDto.setNic(user.getNic());
            userDto.setUserName(user.getUserName());
            userDto.setPassword(user.getPassword());
            return userDto;
        }
        return null;
    }
}
