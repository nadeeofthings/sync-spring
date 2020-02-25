package com.stardust.sync.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.stardust.sync.model.User;
import com.stardust.sync.service.SecurityService;
import com.stardust.sync.service.UserService;
import com.stardust.sync.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    
    @GetMapping("/property")
    public String property(Model model, String id) {
        if (id != null) {
            model.addAttribute("id", id);

        return "levels";
        }else {
        	return "index";
        }
        
        
        
    }
    
    @GetMapping("/unit")
    public String unit(Model model, String id, String unit) {
        if (id != null && unit != null) {
            model.addAttribute("id", id);
        	model.addAttribute("unit", unit);
        return "apartment";
        }else {
        return "index";
        }
    }


    @GetMapping({"/", "/dashboard"})
    public String welcome(Model model) {
        return "index";
    }
    
    @GetMapping({"/test"})
    public String test(Model model) {
        return "blank";
    }
}

