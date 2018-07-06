package com.github.sms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import com.github.handler.CustomAuthenticationFailureHandler;
import com.github.handler.CustomAuthenticationSuccessHandler;

/**
 * 使用手机号验证码方式登录的安全配置
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
@Component
public class SmsCodeAuthenticationSecurityConfig
		extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

	@Autowired
	private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(HttpSecurity builder) throws Exception {

		// 1.自定义短信验证码登录的过滤器,并配置登录成功与失败处理器
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
		smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManager);
		// 1.设置权限拦截过滤器
		builder.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		// 2.设置权限校验提供者
		builder.authenticationProvider(smsCodeAuthenticationProvider);
	}

}
