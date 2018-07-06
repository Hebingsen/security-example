package com.github.sms;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟接收手机号并发送随机的6位短信验证码(伪代码)
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
@Slf4j
@Component
public class SmsCodeSendUtil {

	@Autowired
	private SmsCodeRepository smsCodeRepository;

	/**
	 * 模拟根据手机号发送短信验证码
	 * 
	 * @param phone
	 * @return
	 */
	public String sendSmsCode(String phone) {

		// 根据手机号查询是否已生成过验证码,如果是则直接返回,暂时不做验证码过时的操作
		SmsCode smsCode = smsCodeRepository.findByPhone(phone);
		if (smsCode != null) {
			log.info("该手机号 : " + phone + "已生成过一次验证码");
		} else {
			// 生成新的验证码
			smsCode = SmsCode.builder().phone(phone).desc("手机登录验证码")
					.code(String.valueOf((new Random().nextInt(899999) + 100000))).build();
			smsCodeRepository.insert(smsCode);
			log.info("手机号为" + phone + "的短信验证码发送成功...");
		}

		return smsCode.getCode();
	}

}
