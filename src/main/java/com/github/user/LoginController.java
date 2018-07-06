package com.github.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.core.ResponseResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

	/**
	 * 当需要身份认证的时候，跳转过来
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/authentication/require")
	public Object requireAuthenication(HttpServletRequest request, HttpServletResponse response) throws IOException {

		log.info("当前用户未登录");
		return ResponseResult.builder().code(HttpStatus.UNAUTHORIZED.value()).msg("访问的服务需要身份认证，请先登录登录").build()
				.toJson();
	}
}
