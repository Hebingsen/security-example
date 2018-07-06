package com.github;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	
	@GetMapping("/user")
	public Object user(Authentication authentication) {
		return authentication.getPrincipal();
	}
}
