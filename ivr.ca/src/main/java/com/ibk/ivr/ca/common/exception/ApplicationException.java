package com.ibk.ivr.ca.common.exception;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = -2926781436546085719L;
	
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	private int code;
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public ApplicationException(int status) {
		super();
		this.status = status;
	}

	public ApplicationException(int status, int code) {
		super();
		this.status = status;
		this.code = code;
	}
	
	public ApplicationException(String message) {
		super(message);
	}
	
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ApplicationException(int status, String message) {
		super(message);
		this.status = status;
	}
	
	public ApplicationException(int status, String message, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
	
	public ApplicationException(int status, Throwable cause) {
		super(cause);
		this.status = status;
	}
}