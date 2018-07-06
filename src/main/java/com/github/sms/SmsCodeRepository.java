package com.github.sms;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmsCodeRepository extends MongoRepository<SmsCode,Long>{

	SmsCode findByPhone(String phone);

	SmsCode findByPhoneAndCode(String phone, String smsCode);
	
}
