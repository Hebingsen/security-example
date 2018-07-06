package com.github;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.user.User;
import com.github.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityExampleApplicationTests {
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void contextLoads() {
		User user = new User().setPassword("123456").setUsername("test");
		userRepository.insert(user);
	}

}
