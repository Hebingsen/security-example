package com.github.core;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult {

	private String msg;
	private int code;
	private Object data;

	public String toJson() {
		return new Gson().toJson(this);
	}

	public static ResponseResult ok(String msg) {
		return ResponseResult.builder().msg(msg).code(HttpStatus.OK.value()).build();
	}

	public static ResponseResult ok(String msg, Object data) {
		return ResponseResult.builder().msg(msg).code(HttpStatus.OK.value()).data(data).build();
	}

	public static void main(String[] args) {
		ResponseResult ok = ResponseResult.ok("123");
		System.out.println(ok);
	}

}
