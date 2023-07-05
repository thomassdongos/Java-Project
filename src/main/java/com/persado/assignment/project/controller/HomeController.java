package com.persado.assignment.project.controller;

import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	
    @RequestMapping("/")
    ModelAndView index() {

    	ModelAndView model = new ModelAndView();
        model.addObject("now", LocalDateTime.now());
        model.setViewName("home/index");
        return model;
    }
}
