package com.github.user;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

	//protected Long id;
	protected String username;// 用户名
	protected String password;// 密码
	protected String phone;//手机号

}
