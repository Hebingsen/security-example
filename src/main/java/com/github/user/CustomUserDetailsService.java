package com.github.user;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户详情service业务层
 * 
 * @作者 hebingsen
 * @时间 2018年7月4日
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (Objects.isNull(username)) {
			throw new IllegalArgumentException("Illegal Argument : username ");
		}

		User user = userRepository.findByUsername(username);

		if (Objects.isNull(user)) {
			throw new UsernameNotFoundException("User Is Not Found : username = " + username);
		}

		CustomUserDetails userDetails = new CustomUserDetails(user);
		return userDetails;
	}

}
