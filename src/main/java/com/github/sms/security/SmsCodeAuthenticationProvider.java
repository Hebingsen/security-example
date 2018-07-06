package com.github.sms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.github.sms.SmsCode;
import com.github.sms.SmsCodeRepository;
import com.github.user.CustomUserDetails;
import com.github.user.User;
import com.github.user.UserRepository;

/**
 * 短信验证码登录方式权限校验者
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
@Component
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SmsCodeRepository smsCodeRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// 获取到当前鉴权请求消息
		SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;

		// 根据当前登录信息的手机号查询数据库的用户信息
		String phone = (String) smsCodeAuthenticationToken.getPrincipal();
		User user = userRepository.findByPhone(phone);
		if (user == null) {
			throw new InternalAuthenticationServiceException("该手机号对应的用户信息不存在");
		}

		// 根据验证码与手机号查询MongoDB数据库校验验证码是否正确
		SmsCode smsCode = smsCodeRepository.findByPhoneAndCode(phone, smsCodeAuthenticationToken.getSmsCode());
		if (smsCode == null) {
			throw new InternalAuthenticationServiceException("该验证码或手机不存在");
		}

		// 鉴权通过后,创建新的鉴权通过的令牌信息
		SmsCodeAuthenticationToken resultAuthenticationToken = new SmsCodeAuthenticationToken(
				new CustomUserDetails(user), null);
		resultAuthenticationToken.setDetails(smsCodeAuthenticationToken.getDetails());

		// 返回最后的令牌信息
		return resultAuthenticationToken;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// 当属于短信验证码登录的令牌校验时才会返回true
		return SmsCodeAuthenticationToken.class.isAssignableFrom(arg0);
	}

}
