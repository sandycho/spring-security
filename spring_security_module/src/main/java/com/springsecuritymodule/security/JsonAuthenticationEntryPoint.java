package com.springsecuritymodule.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class JsonAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private String key = "";
	private String message = "";
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		System.out.println("JsonAuthenticationEntryPoint - commence");
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			response.getWriter().write(
					String.format("{\"%s\": \"%s\"}", key, message));
		} catch (IOException e) {
		}
	}
	public void setKey(String key){
		this.key = key;
	}
	public void setMessage(String message){
		this.message = message;
	}
}
