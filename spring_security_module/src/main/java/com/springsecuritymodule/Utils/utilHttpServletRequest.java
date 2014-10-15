package com.springsecuritymodule.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

public class utilHttpServletRequest {
	/**
	 * Shows the buffer's stuff.
	 * @param request
	 */
	public static String verContenidoBuffer(HttpServletRequest request) {
		System.out.println("verContenidoBuffer - Buffer: ");
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
					System.out.println("CLOSED");
					reader.close();
				} catch (IOException e) {
					System.out.println("Exception 3");
					e.printStackTrace();
				}
		    }
		    System.out.println(sb.toString());
		    return "happyending";
	}
	
	public static void verAttributeNames(HttpServletRequest request) {
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
	

	public static void verParametersNames(HttpServletRequest request) {
		System.out.println("UserController - verParametersNames(HttpServletRequest request)");
		Enumeration<String> enu = request.getParameterNames();
		int y = 0;
//		while (enu.hasMoreElements()) {
//			System.out.println(++y + ": " + enu.nextElement());
//			System.out.println("   : "
//					+ request.getAttribute(enu.nextElement()));
//		}

		System.out.println("nombre: " + request.getParameter("j_username"));
		System.out.println("password: " + request.getParameter("j_password"));
	}
	public static void verHeader(HttpServletRequest request){
		System.out.println("UserController - verHeader(HttpServletRequest request)");
		System.out.println(request.getHeader("Content-Type"));
	}
}
