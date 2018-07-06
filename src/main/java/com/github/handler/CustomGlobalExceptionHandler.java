package com.github.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.core.ResponseResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的全局异常处理器
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
@Slf4j
@RestControllerAdvice
public class CustomGlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseResult defaultExceptionHandler(Exception e) {
		log.error("发生系统异常:" + e.getMessage());
		return ResponseResult.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg(e.getMessage()).build();
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseResult runtimeExceptionHandler(RuntimeException e) {
		log.error("发生系统异常:" + e.getMessage());
		return ResponseResult.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg(e.getMessage()).build();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseResult illegalArgumentExceptionHandler(IllegalArgumentException e) {
		log.error("发生系统异常:" + e.getMessage());
		return ResponseResult.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg(e.getMessage()).build();
	}
}
