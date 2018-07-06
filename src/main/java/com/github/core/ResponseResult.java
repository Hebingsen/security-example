package com.github.core;

import org.springframework.http.HttpStatus;

import com.google.gson.Gson;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseResult {

	private String msg = HttpStatus.OK.getReasonPhrase();
	private int code = HttpStatus.OK.value();
	private Object data;

	public String toJson() {
		return new Gson().toJson(this);
	}
}
