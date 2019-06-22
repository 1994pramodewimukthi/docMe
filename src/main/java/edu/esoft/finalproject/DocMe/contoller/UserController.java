package edu.esoft.finalproject.DocMe.contoller;

import edu.esoft.finalproject.DocMe.dto.UserDto;
import edu.esoft.finalproject.DocMe.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final Long SUCCESS = 1L;
    private static final String IS_SUCSESS = "isSucsess";
    private static final String MSG = "msg";

    @Autowired
    UserService userService;

    @GetMapping(value = "/register")
    public ModelAndView userView() {
        ModelAndView modelAndView = new ModelAndView("/user/user_register");
        UserDto userDto = new UserDto();
        modelAndView.addObject("userDetailsDto", userDto);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView userSave(@ModelAttribute("userDetailsDto") UserDto userDto) {
        ModelAndView modelAndView = new ModelAndView("/user/user_register");
        try {
            Long save = userService.userSave(userDto);

            if (save.equals(SUCCESS)) {
                userDto = new UserDto();
                modelAndView.addObject("userDetailsDto", userDto);
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, "Done");
            } else {
                modelAndView.addObject("userDetailsDto", userDto);
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, "Faild");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject("userDetailsDto", userDto);
            modelAndView.addObject(IS_SUCSESS, true);
            modelAndView.addObject(MSG, "Faild");
        }
        return modelAndView;
    }

    @GetMapping(value = "/update/{userId}")
    public ModelAndView updateUser(@PathVariable("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView("/user/user_register");
        try {
            UserDto userDto = userService.getUser(userId);
            if (null == userDto) {
                userDto = new UserDto();
            }
            modelAndView.addObject("userDetailsDto", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

}
