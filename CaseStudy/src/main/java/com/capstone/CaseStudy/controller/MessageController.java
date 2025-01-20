package com.capstone.CaseStudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {

    @GetMapping("/messages")
    public ModelAndView MessagePage() {
        ModelAndView response = new ModelAndView();
        response.setViewName("messages");
        return response;
    }
}
