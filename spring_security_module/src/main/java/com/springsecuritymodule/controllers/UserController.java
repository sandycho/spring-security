package com.springsecuritymodule.controllers;
import com.springsecuritymodule.entities.User;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/users")
@Controller
@RooWebScaffold(path = "users", formBackingObject = User.class)
public class UserController {
	
	@RequestMapping(value ="/welcome")
	public String welcomePage(Model model) {
		System.out.println("UserController - welcomePage()");
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is welcome page!");
		return "hello";
 
	}
 
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		System.out.println("UserController - adminPage()");
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is protected page!");
 
		return "admin";
 
	}
}
