package com.github.sms;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 短信验证码
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
@Data
@Builder
@Accessors(chain = true)
public class SmsCode {

	private String code;
	private String phone;
	private String desc;

}
