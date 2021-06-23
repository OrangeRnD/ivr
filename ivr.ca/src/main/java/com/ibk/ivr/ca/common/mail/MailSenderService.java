package com.ibk.ivr.ca.common.mail;

import java.util.Map;

public interface MailSenderService {
	public void send(String empNr, String empNm, String subject, String template, Map<String, Object> model) throws Exception;
	
	public void send(String empNr, String empNm, String subject, String content) throws Exception;
}
