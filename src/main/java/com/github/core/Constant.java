package com.github.core;

import lombok.Data;

/**
 * 
 * @作者 hebingsen
 * @时间 2018年7月6日
 */
public class Constant {

	public enum SecurityConst {
		UserSource_InMemory(1, "InMemory"), 
		UserSource_UserDetails(2, "UserDetails"),
		Parameter_Username(3,"username"),
		Parameter_Password(4,"password")
		;

		private int code;
		private String value;

		private SecurityConst(int code, String value) {
			this.code = code;
			this.value = value;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
