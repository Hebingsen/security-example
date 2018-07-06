package com.github.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.core.ResponseResult;

@RestController
public class SmsCodeController {

	@Autowired
	private SmsCodeSendUtil smsCodeSendUtil;

	@GetMapping("/authentication/smscode/send")
	public void sendSmsCode(String phone) {
		String smsCode = smsCodeSendUtil.sendSmsCode(phone);
		ResponseResult.ok("验证码已发送", smsCode);
	}

}
