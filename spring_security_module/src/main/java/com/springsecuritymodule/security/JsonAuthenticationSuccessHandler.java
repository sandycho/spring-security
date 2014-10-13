package com.springsecuritymodule.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class JsonAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {
@Override
public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response, Authentication authentication)
		throws IOException, ServletException {
	if ("application/json".equals(request.getHeader("Content-Type"))) {
        /*
         * USED if you want to AVOID redirect to LoginSuccessful.htm in JSON authentication
         */         
        response.getWriter().print("{\"responseCode\":\"SUCCESS\"}");
        response.getWriter().flush();
    } else {
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
}
