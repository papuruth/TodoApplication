package com.todo.todo.controller;

import com.todo.todo.co.UserCo;
import com.todo.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("/save")
    public String saveUser(Model model, @ModelAttribute("userCo") UserCo userCo) {
        try {
            userService.addUser(userCo);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "User already exists");
            return "login/register";
        }
        model.addAttribute("successMessage", "User saved successfully");
        return "login/login";
    }

    /*
        @PostMapping("/edit/{id}")
        public ResponseDTO editUser(@PathVariable("id") Long id, @RequestBody UserCo userCo) {
            try {
                userService.updateUser(id, userCo);
            } catch (RuntimeException e) {
                return new ResponseDTO(true, "User is not available");
            }
            return new ResponseDTO(true, "Successfully edited profile");
        }

        @PostMapping("/delete/{id}")
        public ResponseDTO deleteUser(@PathVariable("id") Long id) {
            try {
                userService.deleteUser(id);
            } catch (RuntimeException e) {
                return new ResponseDTO(false, "Unable to delete the user");
            }
            return new ResponseDTO(true, "user successfully deleted");
        }
    */
    @PostMapping("/authentication")
    public String authenticateUser(Model model, @ModelAttribute("userCo") UserCo userCo) {
        boolean status;
        try {
            status = userService.validateUser(userCo);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "While validating user got some error " + e.getMessage());
            return "login/login";
        }
        if (status) {
            return "redirect:/todo/list";
        } else {
            model.addAttribute("errorMessage", "Username or Password invalid.");
            return "login/login";
        }
    }
}
