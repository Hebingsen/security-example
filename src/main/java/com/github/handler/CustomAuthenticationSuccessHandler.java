package com.github.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.github.core.ResponseResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录成功处理器
 * 
 * @作者 hebingsen
 * @时间 2018年7月5日
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("登录成功....");

		String result = ResponseResult.builder().code(HttpStatus.OK.value()).msg(HttpStatus.OK.getReasonPhrase())
				.data(authentication).build().toJson();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(result);
	}

}
