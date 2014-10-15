package com.springsecuritymodule.controllers;

import java.io.BufferedReader;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/**
	 * El login nunca puede ser de tipo RequestMethod.GET, siempre ha de ser POST. Se usa GET para poder lanzar la llamada desde el navegador.
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "uc_login", method = RequestMethod.POST)
	public void loginMethod(
			HttpServletRequest request, HttpServletResponse response) {
		System.out
				.println("LoginController - loginMethod(HttpServletRequest request, HttpServletResponse response) - 1");

		verParametersNames(request);
		System.out
		.println("LoginController - loginMethod(HttpServletRequest request, HttpServletResponse response) - 2");
		//verAttributeNames(request);
//		RequestDispatcher rd = request
//				.getRequestDispatcher("/spring_security_module/j_spring_security_check");
//		System.out
//		.println("LoginController - loginMethod(HttpServletRequest request, HttpServletResponse response) - 3");
//		try {
//			rd.forward(request, response);
//		} catch (ServletException e) {
//			System.out.println("ServletException");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("IOException");
//			e.printStackTrace();
//		}

		System.out
		.println("LoginController - loginMethod(HttpServletRequest request, HttpServletResponse response) - 4");
	}

	/**
	 * Allows send json information through a form.
	 * @return a html form
	 */
	@RequestMapping(value = "helloJson")
	public String helloJson() {
		System.out
				.println("UserController - helloJson(HttpServletRequest request, HttpServletResponse response)");
		return "helloJson";
	}

	/**
	 * Receives json information and shows it in console.
	 * @param jsonRecived
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "seeJson", method = RequestMethod.POST)
	@ResponseBody
	public void seeJson(@RequestBody String jsonRecived,
			HttpServletRequest request, HttpServletResponse response) {
		System.out
				.println("UserController - seejson(@RequestBody String jsonRecived, HttpServletRequest request, HttpServletResponse response)");
		System.out.println("JSON: " + jsonRecived);
		verContenidoBuffer(request);
		verParametersNames(request);
		verAttributeNames(request);
	}

	@RequestMapping(value = "uc_logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		System.out
				.println("LoginController - logout(HttpServletRequest request, HttpServletResponse response)");
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

	@RequestMapping(value="verAttributeNames")
	public void verAttributeNames(HttpServletRequest request) {
		System.out.println("UserController - verAttributeNames(HttpServletRequest request)");
		Enumeration<String> enu = request.getAttributeNames();
		int y = 0;
		while (enu.hasMoreElements()) {
			System.out.println(++y + ": " + enu.nextElement());
			System.out.println("   : "
					+ request.getAttribute(enu.nextElement()));
		}
		System.out.println("nombre: " + request.getAttribute("username"));
		System.out.println("password: " + request.getAttribute("password"));
	}
	
	@RequestMapping(value="verParametersNames")
	public void verParametersNames(HttpServletRequest request) {
		System.out.println("UserController - verParametersNames(HttpServletRequest request)");
		Enumeration<String> enu = request.getParameterNames();
		int y = 0;
//		while (enu.hasMoreElements()) {
//			System.out.println(++y + ": " + enu.nextElement());
//			System.out.println("   : "
//					+ request.getAttribute(enu.nextElement()));
//		}

		System.out.println("nombre: " + request.getParameter("username"));
		System.out.println("password: " + request.getParameter("password"));
	}
	
	/**
	 * Shows the buffer's stuff.
	 * @param request
	 */
	@RequestMapping(value="verBuffer")
	public String verContenidoBuffer(HttpServletRequest request) {
		System.out.println("Buffer: ");
		 StringBuilder sb = new StringBuilder();
		    BufferedReader reader = null;
			try {
				reader = request.getReader();
			} catch (IOException e) {
				System.out.println("Exception 1");
				e.printStackTrace();
			}
		    try {
		        String line;
		        try {
					while ((line = reader.readLine()) != null) {
					    sb.append(line).append('\n');
					}
				} catch (IOException e) {
					System.out.println("Exception 2");
					e.printStackTrace();
				}
		    } finally {
		        try {
					reader.close();
				} catch (IOException e) {
					System.out.println("Exception 3");
					e.printStackTrace();
				}
		    }
		    System.out.println(sb.toString());
		    return "happyending";
	}
	
	@RequestMapping(value="verPrincipal")
	public String verPrincipal(HttpServletRequest request){
		SecurityContext context = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(context != null){
			System.out.println(context.getAuthentication().getName());			
		}else{
			System.out.println("Principal es null");
		}
		return "happyending";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout, Model model) {
 

		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
		}
 
		if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}
 
		return "login";
	}
	@RequestMapping(value="jsonLogin", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response){
		return "happyending";
	}
}
