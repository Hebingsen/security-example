package com.github;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.core.ResponseResult;
import com.github.sms.SmsCodeSendUtil;
import com.github.user.User;
import com.github.user.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PermissionController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SmsCodeSendUtil smsCodeSendUtil;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 当用户未登录,进行访问时的处理接口
	 * @return
	 */
	@RequestMapping("/authentication/require")
	public Object requireAuthenication() throws IOException {
		log.info("当前用户未登录");
		return ResponseResult.builder().code(HttpStatus.UNAUTHORIZED.value()).msg("访问的服务需要身份认证，请先登录").build().toJson();
	}

	/**
	 * 用户注册
	 * @param username
	 * @param phone
	 * @param password
	 * @return
	 */
	@PostMapping("/authentication/register")
	public ResponseResult registerUser(String username, String phone, String password) {
		User user = new User().setUsername(username).setPhone(phone).setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.insert(user);
		return ResponseResult.ok("注册成功");
	}
	
	@GetMapping("/authentication/smscode/send")
	public void sendSmsCode(String phone) {
		String smsCode = smsCodeSendUtil.sendSmsCode(phone);
		ResponseResult.ok("验证码已发送", smsCode);
	}
	
	
}
