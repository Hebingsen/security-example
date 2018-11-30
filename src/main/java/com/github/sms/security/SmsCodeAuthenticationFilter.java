package com.github.sms.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信验证码登录过滤器
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
@Slf4j
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	// 构造短信验证码的登录URL
	protected SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/authentication/phone-login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

		// 判断是否是post方式请求登录接口
		if (!"post".equalsIgnoreCase(request.getMethod())) {
			log.info("当前请求方式 : " + request.getMethod());
			throw new AuthenticationServiceException("只支持post方式提交");
		}

		// 获取请求参数手机号
		String phone = request.getParameter("phone");
		if (StringUtils.isEmpty(phone)) {
			log.error("当前登录手机号为空 : " + phone);
			throw new AuthenticationServiceException("当前登录手机号为空");
		}

		// 获取验证码
		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code)) {
			log.error("当前登录验证码为空 : " + code);
			throw new AuthenticationServiceException("验证码为空");
		}

		// 创建短信验证码的登录令牌信息sms-token
		SmsCodeAuthenticationToken smsCodeAuthenticationToken = new SmsCodeAuthenticationToken(phone, code);

		// 将请求信息request合并到登录令牌信息sms-token中
		smsCodeAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));

		// 返回登录信息,交给鉴权管理器authenticationManager映射到对应的authenticationProvider进行鉴权
		return this.getAuthenticationManager().authenticate(smsCodeAuthenticationToken);
	}

}
