package com.springsecuritymodule.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecuritymodule.Utils.utilHttpServletRequest;

public class JsonUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	private String jsonPassword;
	private String jsonUsername;

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = null;

		if ("application/json".equals(request.getHeader("Content-Type"))) {
			System.out
					.println("JsonUsernamePasswordAuthenticationFilter - obtainPassword");
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
			System.out
					.println("JsonUsernamePasswordAuthenticationFilter - obtainUsername");
			username = this.jsonUsername;
		} else {
			username = super.obtainUsername(request);
		}

		return username;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		utilHttpServletRequest.verParametersNames(request);
		utilHttpServletRequest.verAttributeNames(request);
		System.out
				.println("JsonUsernamePasswordAuthenticationFilter - attemptAuthentication");
		if ("application/x-www-form-urlencoded".equals(request.getHeader("Content-Type"))) {
			System.out
					.println("JsonUsernamePasswordAuthenticationFilter - attemptAuthentication - if");
			BufferedReader reader = null;

			/*
			 * HttpServletRequest can be read only once
			 */
			StringBuffer sb = new StringBuffer();
			try {
				String line = null;

				reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (Exception e) {
				System.out.println("IOException xx-0");
				e.printStackTrace();
			}finally{
				try {

					System.out.println("CLOSED");
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("IOException xx-1");
					e.printStackTrace();
				}
			}


			// json transformation
			ObjectMapper mapper = new ObjectMapper();
			LoginRequest loginRequest = null;
			try {
				
				loginRequest = mapper.readValue(sb.toString(),
						LoginRequest.class);

				System.out.println("Mapeado");
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.jsonUsername = loginRequest.getUsername();
			this.jsonPassword = loginRequest.getPassword();
			
		} else {
			System.out
					.println("JsonUsernamePasswordAuthenticationFilter - attemptAuthentication - No JSON");
		}

		return super.attemptAuthentication(request, response);
	}

	private final class LoginRequest{
		private String username;
		private String password;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out
				.println("JsonUsernamePasswordAuthenticationFilter - doFilter");
		//
		utilHttpServletRequest.verHeader((HttpServletRequest) req);
		utilHttpServletRequest.verAttributeNames((HttpServletRequest) req);
		utilHttpServletRequest.verParametersNames((HttpServletRequest) req);
		utilHttpServletRequest.verContenidoBuffer((HttpServletRequest) req);
		if (req.getParameter("password") != null) {
			attemptAuthentication((HttpServletRequest) req,
					(HttpServletResponse) res);
		}
		//
		super.doFilter(req, res, chain);
	}
}
