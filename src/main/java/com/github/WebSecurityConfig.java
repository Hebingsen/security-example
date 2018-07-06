package com.github;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

import com.github.core.Constant;
import com.github.core.Constant.SecurityConst;
import com.github.handler.CustomAuthenticationFailureHandler;
import com.github.handler.CustomAuthenticationSuccessHandler;
import com.github.sms.security.SmsCodeAuthenticationSecurityConfig;
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
	
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		SecurityConst securityConst = Constant.SecurityConst.UserSource_UserDetails;
		switch (securityConst) {
		case UserSource_InMemory:
			auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN").and().withUser("hebingsen")
					.password("123456").roles("USER");
			break;
		case UserSource_UserDetails:
			auth.userDetailsService(customUserDetailsService);
			break;
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// 当用户未登录时,会自动跳转该接口,web端可以指定跳转到登录页面,app端可以通过该接口返回需要让用户登录的响应信息
		FormLoginConfigurer<HttpSecurity> loginConfig = http.formLogin().loginPage("/authentication/require");
		
		// 默认的用户名与密码登录提交的action/接口地址,只支持post方式提交,默认用户名:username,密码:password,
		// 这个地址映射的接口不需要我们写,spring security已经帮我们实现好了
		loginConfig.loginProcessingUrl("/authentication/basic-login");
		
		// 当用户登录成功后进行的处理操作,web端可以跳转到登录成功的页面,app端可以通过该成功处理器,向用户返回登录成功的用户信息
		loginConfig.successHandler(customAuthenticationSuccessHandler);
		
		// 当用户登录失败后进行的处理操作,web端可以跳转到登录失败的页面,app端可以通过该失败处理器,向用户返回登录失败的提示信息
		loginConfig.failureHandler(customAuthenticationFailureHandler);
		
		// 配置不需要拦截权限的URL规则
		loginConfig.and().authorizeRequests().antMatchers("/authentication/**").permitAll()
		// 其他请求一律需要进行鉴权
		.anyRequest().authenticated();
		
		// 禁止csrf
		http.csrf().disable();
		
		// 接下来实现手机号接收验证码进行登录
		http.apply(smsCodeAuthenticationSecurityConfig);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

}
