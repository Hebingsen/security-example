package com.github.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.core.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录失败处理器
 * 
 * @作者 hebingsen
 * @时间 2018年7月5日
 */
@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		log.info("登录失败....");
		String result = ResponseResult.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.msg(exception.getMessage()).build().toJson();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(result);
	}

}
