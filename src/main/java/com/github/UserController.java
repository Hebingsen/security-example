package com.github;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	/**
	 * 获取当前的用户信息
	 * @param authentication
	 * @return
	 */
	@GetMapping("/user")
	public Object user(Authentication authentication) {
		return authentication.getPrincipal();
	}
}
