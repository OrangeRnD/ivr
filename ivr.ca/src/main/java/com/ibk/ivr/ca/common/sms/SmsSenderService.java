package com.ibk.ivr.ca.common.sms;

public interface SmsSenderService {
	public String send(String phone, String message) throws Exception;
}
