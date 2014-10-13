package com.springsecuritymodule.security;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	private String jsonPassword;
	private String jsonUsername;

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = null;

		if ("application/json".equals(request.getHeader("Content-Type"))) {
			password = this.jsonPassword;
		} else {
			password = super.obtainPassword(request);
		}

		return password;
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = null;

		if ("application/json".equals(request.getHeader("Content-Type"))) {
			username = this.jsonUsername;
		} else {
			username = super.obtainUsername(request);
		}

		return username;
	}
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        if ("application/json".equals(request.getHeader("Content-Type"))) {
            try {
                /*
                 * HttpServletRequest can be read only once
                 */
                StringBuffer sb = new StringBuffer();
                String line = null;

                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null){
                    sb.append(line);
                }

                //json transformation
                ObjectMapper mapper = new ObjectMapper();
                LoginRequest loginRequest = mapper.readValue(sb.toString(), LoginRequest.class);

                this.jsonUsername = loginRequest.getUsername();
                this.jsonPassword = loginRequest.getPassword();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.attemptAuthentication(request, response);
    }
	
	private final class LoginRequest{
		String username;
		String password;
		public String getUsername(){
			return this.username;
		}
		public String getPassword(){
			return this.password;
		}
	}
}
