package com.ibk.ivr.ca;

import java.security.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

@Controller
public class LocalController {
	
	@RequestMapping(value = {"admin", "/admin"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Object obj = request.getSession().getAttribute(Constants.SESSION);
		if(obj == null) {
			UsrVO vo = new UsrVO(); 
			vo.setUsrId(1);
			vo.setAuthyGrpCd("1");
			vo.setEmpNm("관리자");
			vo.setDelAltv(1);
			request.getSession().setAttribute(Constants.SESSION, vo);
		}

		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		
		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
		pbeEnc.setPassword("jasyptPass"); // PBE 값(XML PASSWORD설정)
		
		String password = pbeEnc.encrypt("QWer1234!");
		System.out.println(password);
		System.out.println(pbeEnc.decrypt(password));
		
//		Security.addProvider(new BouncyCastleProvider());
//		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
//		
//		pbeEnc.setProviderName("BC"); // jce provider name
//		pbeEnc.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC"); // AES 256bit도 가능
//		pbeEnc.setPassword("jasyptPass");
//		 
//		String password = pbeEnc.encrypt("QWer1234!");
//		
//		System.out.println(password);
//		System.out.println(pbeEnc.decrypt(password));
		
		return "redirect:/main";
	}
}
