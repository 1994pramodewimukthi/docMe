package edu.esoft.finalproject.DocMe.contoller;

import edu.esoft.finalproject.DocMe.config.AppConstant;
import edu.esoft.finalproject.DocMe.config.MessageConstant;
import edu.esoft.finalproject.DocMe.dto.SystemRoleDto;
import edu.esoft.finalproject.DocMe.dto.SystemRolePrivilagesWrapperDto;
import edu.esoft.finalproject.DocMe.dto.UserDto;
import edu.esoft.finalproject.DocMe.entity.DocCategoryTemp;
import edu.esoft.finalproject.DocMe.entity.SystemRole;
import edu.esoft.finalproject.DocMe.entity.User;
import edu.esoft.finalproject.DocMe.service.MessageService;
import edu.esoft.finalproject.DocMe.service.SystemRoleDockUpService;
import edu.esoft.finalproject.DocMe.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Long SUCCESS = 1L;
    private static final String IS_SUCSESS = "isSucsess";
    private static final String MSG = "msg";
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    private MessageService messageService;

    @Autowired
    private SystemRoleDockUpService systemRoleDockUpService;

    @GetMapping(value = "/register")
    public ModelAndView userView() {
        ModelAndView modelAndView = new ModelAndView("/user/register");
        UserDto userDto = new UserDto();
        modelAndView.addObject("userDetailsDto", userDto);
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView userSave(@ModelAttribute("userDetailsDto") UserDto userDto, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("/user/register");
        try {
            Long save = userService.userSave(userDto);

            if (save.equals(SUCCESS)) {
                if (userDto.getUserEdit().equals("true")) {
                    modelAndView.addObject("userDetailsDto", userDto);
                } else {
                    userDto = new UserDto();
                    modelAndView.addObject("userDetailsDto", userDto);
                }
                modelAndView.addObject(IS_SUCSESS, true);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.INFO_MESSAGE_SUCCESSFULLY_SAVED));
            } else {
                modelAndView.addObject("userDetailsDto", userDto);
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(save));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject("userDetailsDto", userDto);
            modelAndView.addObject(IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        return modelAndView;
    }

    @GetMapping(value = "/update/{userId}")
    public ModelAndView updateUser(@PathVariable("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView("/user/register");
        try {
            UserDto userDto = userService.getUser(userId);
            if (null == userDto) {
                userDto = new UserDto();
            } else {
                userDto.setUserEdit("true");
            }
            modelAndView.addObject("userDetailsDto", userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @GetMapping(value = "/update")
    public ModelAndView update(HttpSession session) {
        User user = (User) session.getAttribute(AppConstant.USER);
        return new ModelAndView("redirect:/user/update/" + user.getId());
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("userDetailsDto") UserDto userDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/ui/login");
        try {
            Long login = userService.userLogin(userDto, session);
            if (login.equals(SUCCESS)) {
                modelAndView = new ModelAndView("redirect:/user/home");
            } else {
                userDto.setPassword("");
                modelAndView.addObject("userDetailsDto", userDto);
                modelAndView.addObject(IS_SUCSESS, false);
                modelAndView.addObject(MSG, messageService.getSystemMessage(login));
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            modelAndView.addObject("userDetailsDto", userDto);
            modelAndView.addObject(IS_SUCSESS, false);
            modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.ERROR_ADMINISTRATOR_FOR_MORE_DETAIL));
        }
        return modelAndView;
    }


    @GetMapping(value = "/home")
    public ModelAndView home() {

        ModelAndView modelAndView = new ModelAndView("/ui/home");
        DocCategoryTemp docCategoryTemp = new DocCategoryTemp();
        modelAndView.addObject("docCategoryTemp", docCategoryTemp);
        return modelAndView;
    }

    @PostMapping("/add-system-role")
    public ModelAndView addNewSystemRole(@ModelAttribute("systemRoleStatus") SystemRoleDto systemRoleDto, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/ui/system/system-role-privilege");
        String message = messageService.getSystemMessage(MessageConstant.SYSTEM_ROLE_FAILED_TO_ADD);
        try {
            User user = (User) session.getAttribute("user");
            systemRoleDto.setInpUserId(user.getId().toString());

            if (systemRoleDto.getSystemRoleId() != 0) {
                message = messageService.getSystemMessage(MessageConstant.SYSTEM_ROLE_UPDATED_SUCCESSFULLY);
            } else {
                message = messageService.getSystemMessage(MessageConstant.SYSTEM_ROLE_ADDED_SUCCESSFULLY);
            }

            boolean result = systemRoleDockUpService.addSystemRole(systemRoleDto);
            if (result) {
                modelAndView.addObject(IS_SUCSESS, result);
                modelAndView.addObject(MSG, message);
            } else {
                modelAndView.addObject(IS_SUCSESS, result);
                modelAndView.addObject(MSG, message);
            }
            modelAndView.addObject("systemRoleDto", new SystemRoleDto());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = "/get-all-system-role")
    public ResponseEntity getAllSystemRoles() {
        List<SystemRoleDto> allSystemRoles = new ArrayList<>();
        try {
            allSystemRoles = systemRoleDockUpService.getAllSystemRoles();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return ResponseEntity.ok(allSystemRoles);
    }

    @GetMapping(value = "/find-system-role-by-id/{id}")
    public ModelAndView getSystemRoleById(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            SystemRole systemRole = systemRoleDockUpService.getSystemRoleById(id);
            modelAndView.addObject("systemRoleDto", (null != systemRole) ? systemRole : new SystemRoleDto());
            modelAndView.setViewName("/ui/system/systemroleviewer");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping(value = "/get-all-system-menu-item-privileges/{id}")
    public ModelAndView getSystemMenuItemPrivileges(@PathVariable("id") String id) {
        ModelAndView modelAndView = new ModelAndView();

        try {
            SystemRolePrivilagesWrapperDto systemRolePrivileges = systemRoleDockUpService.getAllSystemMenuItemPrivilagesForSystemRoleId(id);
            modelAndView.addObject("systemRolePrivileges", systemRolePrivileges);
            modelAndView.setViewName("/ui/system/changeprivilege");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping(value = "/change-system-role-privilege")
    public ModelAndView changeSystemRolePrivileges(@ModelAttribute("systemRolePrivileges") SystemRolePrivilagesWrapperDto systemRolePrivileges) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            boolean result = systemRoleDockUpService.changeSystemRolePrivileges(systemRolePrivileges);

            modelAndView.setViewName("/ui/system/system-role-privilege");
            modelAndView.addObject("systemRoleDto", new SystemRoleDto());

            if (result) {
                modelAndView.addObject(IS_SUCSESS, result);
                modelAndView.addObject(MSG, messageService.getSystemMessage(MessageConstant.SYSTEM_ROLE_PRIVILEGE_UPDATED_SUCCESSFULLY));
            } else {
                modelAndView.addObject(IS_SUCSESS, result);
                modelAndView.addObject(MSG,  messageService.getSystemMessage(MessageConstant.SYSTEM_ERROR_PLEASE_CONTACT_SYSTEM_ADMIN));
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return modelAndView;
    }
}
