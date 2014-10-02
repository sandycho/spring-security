package com.springsecuritymodule.controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springsecuritymodule.entities.User;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@RequestMapping("/users")
@Controller
@RooWebScaffold(path = "users", formBackingObject = User.class)
public class UserController {

	@RequestMapping(value = "welcome")
	public String welcomePage(Model model) {
		System.out.println("UserController - welcomePage()");
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is welcome page!");
		return "hello";

	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		System.out.println("UserController - adminPage()");
		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "This is protected page!");

		return "admin";

	}

	@RequestMapping(value = "uc_login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		System.out
				.println("LoginController - login(HttpServletRequest request, HttpServletResponse response)");
		verContenidoRequest(request);
		RequestDispatcher rd = request
				.getRequestDispatcher("/j_spring_security_check");
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "uc_logout", method = RequestMethod.POST)
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("LoginController - logout(HttpServletRequest request, HttpServletResponse response)");
		verContenidoRequest(request);
		RequestDispatcher rd = request
				.getRequestDispatcher("/j_spring_security_logout");

		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "happyEnding", method = RequestMethod.GET)
	public String happyEnding(HttpServletRequest request, Model model) {
		System.out.println("UserController - happyEnding(Model model)");

		model.addAttribute("title", "Spring Security Hello World");
		model.addAttribute("message", "Redirección correcta");
		return "happyending";
	}

	@RequestMapping(value = "loginPage", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request) {
		return "login";
	}

	public void verContenidoRequest(HttpServletRequest request) {
		Enumeration<String> enu = request.getAttributeNames();
		int y = 0;
		while (enu.hasMoreElements()) {
			System.out.println(++y + ": " + enu.nextElement());
			System.out.println("   : "
					+ request.getAttribute(enu.nextElement()));
		}
	}
}
