package com.ibk.ivr.ca.common.mail;

import java.io.StringWriter;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMailSenderServiceImpl implements MailSenderService {
	
	/**
	 * spring JavaMailSender.
	 */
	private JavaMailSender mailSender;
	/**
	 * template engine.
	 */
    private VelocityEngine velocityEngine;
    /**
     * 보내는 메일 정보.
     */
    private String from;
    /**
     * 보내는 메일 정보.
     */
    private String personal;
    
    /**
     * 생성자.
     */
	public TestMailSenderServiceImpl() {
	}
	
	/**
	 * JavaMailSender.
	 * @param mailSender JavaMailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
	
	/**
	 * JavaMailSender.
	 * @param velocityEngine JavaMailSender
	 */
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    /**
     * 보내는 메일주소.
     * @param from 보내는 메일주소
     */
	public void setFrom(String from) {
		this.from = from;
	}
    
    /**
     * 보내는 메일주소.
     * @param from 보내는 메일주소
     */
	public void setPersonal(String personal) {
		this.personal = personal;
	}

	/**
     * 이메일 전송.
     * 
     * @param emal 이메일
     * @param key 백업키
     * @param subject 메일제목
     * @param template 메일template
	 * @throws UnsupportedEncodingException 
     */
	public void send(final String toAddress, final String toPersonal, final Map<String, Object> model, final String subject, final String template) {
		new Thread(() -> {
			try {
				MimeMessagePreparator preparator = new MimeMessagePreparator() {
		            public void prepare(MimeMessage mimeMessage) throws Exception {
		                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		                message.setFrom(new InternetAddress(from, personal));
		                message.setTo(new InternetAddress(toAddress, toPersonal));
		                message.setSubject(subject);
		                
		                StringWriter result = new StringWriter();
		        		velocityEngine.mergeTemplate(template, "UTF-8", new VelocityContext(model), result);
		                
		                message.setText(result.toString(), true);
		                log.info("mailFrom:{} -> mailTo:{}", from, toAddress);
		            }
		        };			        
				mailSender.send(preparator);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
        }).start();
	}
	
	@Override
	public void send(String empNr, String empNm, String subject, String content) throws Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(new InternetAddress(from, personal));
                message.setTo(new InternetAddress(empNr, empNm));
                message.setSubject(subject);
                message.setText(content, true);
                log.info("mailFrom:{} -> mailTo:{}", from, empNr);
            }
        };			        
		mailSender.send(preparator);
	}
	
	@Override
	public void send(String empNr, String empNm, String subject, String template, Map<String, Object> model) throws Exception {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(new InternetAddress(from, personal));
                message.setTo(new InternetAddress(empNr, empNm));
                message.setSubject(subject);
                
                StringWriter result = new StringWriter();
        		velocityEngine.mergeTemplate(template, "UTF-8", new VelocityContext(model), result);
                
                message.setText(result.toString(), true);
                log.info("mailFrom:{} -> mailTo:{}", from, empNr);
            }
        };			        
		mailSender.send(preparator);
	}
}
