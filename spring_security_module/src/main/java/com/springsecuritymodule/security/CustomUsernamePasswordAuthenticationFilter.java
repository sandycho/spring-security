package com.springsecuritymodule.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
@Override
protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
	// TODO Auto-generated method stub
	System.out.println(authResult.getName());
	super.successfulAuthentication(request, response, chain, authResult);
	System.out.println("CustomUsernamePasswordAuthenticationFilter - successfulAuthentication(..)");
}
@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

	System.out.println("CustomUsernamePasswordAuthenticationFilter - pass - " + obtainPassword(request) + " " + obtainUsername(request));
		super.unsuccessfulAuthentication(request, response, failed);
		System.out.println("CustomUsernamePasswordAuthenticationFilter - unsuccessfulAuthentication(..)");
	}
@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

	System.out.println("CustomUsernamePasswordAuthenticationFilter - attemptAuthentication - pass - " + obtainPassword(request) + " " + obtainUsername(request));		return super.attemptAuthentication(request, response);
	}
}
