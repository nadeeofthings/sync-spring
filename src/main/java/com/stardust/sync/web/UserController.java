package com.stardust.sync.web;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.stardust.sync.model.User;
import com.stardust.sync.service.SecurityService;
import com.stardust.sync.service.UserService;
import com.stardust.sync.util.GeneratePdfReport;
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
    
    @GetMapping({"/billing"})
    public String billing(Model model) {
        return "billing";
    }
    
    @RequestMapping(value = "/generateElec", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> electricalR() {

        ByteArrayInputStream bis = GeneratePdfReport.electricalR();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=electricityBill.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
    @RequestMapping(value = "/generateAC", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> airconR() {

        ByteArrayInputStream bis = GeneratePdfReport.airconR();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=acBill.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
    @GetMapping({"/test"})
    public String test(Model model) {
        return "blank";
    }
}

