package com.ibk.ivr.ca.common.sms;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSmsSenderServiceImpl implements SmsSenderService {

	@Override
	public String send(String phone, String message) throws Exception {
		if(log.isDebugEnabled())
			log.debug("{} : {} ", phone, message);
		
		ByteBuffer inputBuffer = ByteBuffer.wrap(message.getBytes());
		
		//c 서버는 대부분 'KSC5601'
		//Charset KSC5601 = Charset.forName("KSC5601");
		
		CharBuffer data = StandardCharsets.UTF_8.decode(inputBuffer);
		ByteBuffer outputBuffer = StandardCharsets.UTF_8.encode(data);
		String result = new String(outputBuffer.array());
		
		try {
			System.out.println(result);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return "1";
	}
	
	public static void main(String[] args) {
		TestSmsSenderServiceImpl sms = new TestSmsSenderServiceImpl();
		try {
			sms.send("010111122223333", "한글abc1234");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
