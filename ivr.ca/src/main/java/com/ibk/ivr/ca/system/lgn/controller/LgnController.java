package com.ibk.ivr.ca.system.lgn.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.lgn.service.LgnService;
import com.ibk.ivr.ca.system.lgnhist.service.LgnHistService;
import com.ibk.ivr.ca.system.lgnhist.vo.LgnHistVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

import com.initech.eam.nls.CookieManager;

@Slf4j
@Controller
@RequestMapping(value = "/lgn", method=RequestMethod.POST)
public class LgnController {
	
    @Autowired
    private LgnService service;
    
	@Autowired
	private LgnHistService lgnHistService;

    @Value("#{propertyConfigurer['constants.session.time']}")
    private int sessionTimeout; 

	@RequestMapping(value = "/sso")
	public @ResponseBody ResponseVO sso(HttpServletRequest request) throws Exception {
		CookieManager.setEncStatus(true);
		
		String encTicket = (String)request.getParameter("ticket");
		if (encTicket !=null) {
			encTicket = URLDecoder.decode(encTicket, "UTF-8");
		}
		
		log.info("Login ticket : {}", encTicket);
		
		int firstIndex = encTicket.indexOf("&&");
		String encTicket2 = encTicket.substring(0, firstIndex);
		String hmac = encTicket.substring(firstIndex + 2);
		
		//인증티켓과 hmac 값 추출
		ResponseVO responseVO = new ResponseVO();
		String empNr = null;
	    String ip = request.getRemoteAddr();
		try {
			@SuppressWarnings("rawtypes")
			List res = CookieManager.readClientTicket(encTicket2, hmac);
		    empNr = (String)res.get(0);
			log.info("Login empNr : {}", empNr);
			
		    UsrVO usrVO = service.sso(empNr, ip);
		    if(usrVO != null) 
		    	responseVO.setData(usrVO);
		    else
		    	responseVO.setStatus(HttpServletResponse.SC_NOT_FOUND);
		    
		    try {
				LgnHistVO lgnhVO = new LgnHistVO();
				lgnhVO.setEmpNr(empNr);
				lgnhVO.setLgnIp(ip);
				lgnhVO.setLgnDt(DateUtil.getDate());
				if(usrVO != null) {
					lgnhVO.setLgnStsSecd("200");
					lgnhVO.setUsrId(usrVO.getUsrId());
				} else {
					lgnhVO.setLgnStsSecd("404");
					lgnhVO.setUsrId(0);
				}
				if(log.isDebugEnabled())
					log.debug("LoginhistVO : {}", lgnhVO.toString());

				lgnHistService.insert(lgnhVO);
				
				if(usrVO != null) {
					request.getSession().setAttribute(Constants.SESSION, usrVO);
					request.getSession().setMaxInactiveInterval(sessionTimeout);
				}
			} catch(Exception e) {
				throw e;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			responseVO.setMessage(e.getMessage());
			responseVO.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			try {
				LgnHistVO lgnhVO = new LgnHistVO();
				lgnhVO.setEmpNr(empNr);
				lgnhVO.setLgnIp(ip);
				lgnhVO.setLgnDt(DateUtil.getDate());
				lgnhVO.setLgnStsSecd("500");
				lgnhVO.setUsrId(0);
				if(log.isDebugEnabled())
					log.debug("LoginhistVO : {}", lgnhVO.toString());

				lgnHistService.insert(lgnhVO);
			} catch(Exception e1) {
				log.error(e1.getMessage(), e1);
			}
		}
		return responseVO;
	}

	@RequestMapping(value = "/logout")
	public @ResponseBody ResponseVO logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsrVO usrVO = (UsrVO)request.getSession().getAttribute(Constants.SESSION);
		if(usrVO != null) {
			try {
				service.logout(usrVO);
			} catch(Exception e) {
				log.error(e.getMessage(), e);
			}

			request.getSession().removeAttribute(Constants.SESSION);
		}
		request.getSession().invalidate();
		return new ResponseVO();
	}
}
