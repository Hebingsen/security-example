package com.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.github.handler.CustomAuthenticationFailureHandler;
import com.github.handler.CustomAuthenticationSuccessHandler;
import com.github.user.CustomUserDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		// auth.inMemoryAuthentication().withUser("hebingsen").password("123456").roles("USER");
		auth.userDetailsService(customUserDetailsService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		// 当请求需要身份认证时，默认跳转的url
		.loginPage("/authentication/require")
		// 默认的用户名密码登录请求处理url
		.loginProcessingUrl("/authentication/form")
		.successHandler(customAuthenticationSuccessHandler)
		.failureHandler(customAuthenticationFailureHandler)
		.and().authorizeRequests()
		.antMatchers("/authentication/**").permitAll() 
		.anyRequest().authenticated();
		
		http.csrf().disable();
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

}
