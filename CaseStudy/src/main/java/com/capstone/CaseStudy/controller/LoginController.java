package com.capstone.CaseStudy.controller;

import com.capstone.CaseStudy.database.dao.UserDAO;
import com.capstone.CaseStudy.database.entity.User;
import com.capstone.CaseStudy.form.NewUserDTO;
import com.capstone.CaseStudy.security.AuthenticatedUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @GetMapping("/login")
    public ModelAndView theLoginPage() {
        ModelAndView response = new ModelAndView();
        response.setViewName("login");
        return response;
    }



    @GetMapping("/register")
    public ModelAndView signup() {
        ModelAndView response = new ModelAndView();
        response.setViewName("register");
        return response;
    }


    @PostMapping("/register/submit")
    public ModelAndView signup(@Valid NewUserDTO form, BindingResult bindingResult, HttpSession session) {
        ModelAndView response = new ModelAndView();
        if ( bindingResult.hasErrors() ) {
            response.setViewName("register");
            response.addObject("bindingResult", bindingResult);
            response.addObject("userDTO", form);

        } else {
            User user = new User();

            user.setEmail(form.getEmail());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());

            // first we need to ecrypt the incoming password before saving it to the database
            // the password in the form is in plain text but we want to save to the database encrypted
            String encryptedPassword = passwordEncoder.encode(form.getPassword());
            user.setPassword(encryptedPassword);

            userDAO.save(user);

            // since this is a new user we can manually authenticate them for the first time
            authenticatedUserService.changeLoggedInUsername(session,form.getEmail(),form.getPassword());

            // redirect
            response.setViewName("redirect:/dashboard");
        }

        return response;
    }

}
