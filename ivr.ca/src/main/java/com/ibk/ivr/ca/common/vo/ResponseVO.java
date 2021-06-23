package com.ibk.ivr.ca.common.vo;

import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResponseVO extends DataVO {

	private static final long serialVersionUID = 2402098354790684081L;

	/**
	 * 상태코드.
	 * 
	 * 200 : 성공
	 * 400 : 실패
	 * 401 : 비번오류(로그인 시), 인증실패
	 * 404 : 이메일오류(로그인 시), 이메일중복(회원가입 시), 회원미존재
	 * 500 : 서버오류
	 * 
	 * @return 상태코드
	 */
	private int status;

	/**
	 * 결과 메시지.
	 */
	private String message;
	
	/**
	 * 결과 데이터.
	 */
	private Object data;
	
	public ResponseVO() {
		status = HttpServletResponse.SC_OK;
	}
	
	public ResponseVO(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	/**
	 * 상태코드.
	 * 
	 * 200 : 성공
	 * 400 : 실패
	 * 401 : 비번오류(로그인 시), 인증실패
	 * 404 : 이메일오류(로그인 시), 이메일중복(회원가입 시), 회원미존재
	 * 500 : 서버오류
	 * 
	 * @param status 상태코드
	 */
    @com.fasterxml.jackson.annotation.JsonSetter
	public void setStatus(int status) {
		this.status = status;
	}
}
