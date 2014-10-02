package com.springsecuritymodule.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.springsecuritymodule.config.core.JdbcTokenRepositoryImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		System.out.println("SecurityConfig - configureGlobal(AuthenticationManagerBuilder auth)");

		auth.inMemoryAuthentication().withUser("mkyong").password("123")
				.roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("123")
				.roles("ADMIN");
		
//		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select email, password, enabled from user where email=?").authoritiesByUsernameQuery("select email, role from user where email=?");
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("SecurityConfig - configure(HttpSecurity http)");

		http.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/welcome/**").access("hasRole('ROLE_USER')")
			.and()
				.formLogin().loginPage("/loginPage").loginProcessingUrl("/uc_login")
			.and()
				.csrf().disable()
			
				.logout().logoutUrl("/uc_logout").logoutSuccessUrl("/happyEnding").deleteCookies("JSESSIONID")
			.and()
				.rememberMe().tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(1209600)
//				.and().sessionManagement().
				;

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler 
                savedRequestAwareAuthenticationSuccessHandler() {
 
               SavedRequestAwareAuthenticationSuccessHandler auth 
                    = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
}
