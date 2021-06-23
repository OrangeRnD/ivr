package com.ibk.ivr.ca;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

@Controller
public class IndexController {
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Object obj = request.getSession().getAttribute(Constants.SESSION);
		if(obj == null) 
			return "/index";
		
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Object obj = request.getSession().getAttribute(Constants.SESSION);
		if(obj == null) 
			return "/login";
		
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/searchCall", method = RequestMethod.GET)
	public void searchCall(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		UsrVO vo = new UsrVO(); 
		vo.setUsrId(4);
		vo.setAuthyGrpCd("4");
		vo.setEmpNm("상담원");
		vo.setDelAltv(4);
		request.getSession().setAttribute(Constants.SESSION, vo);
		
		RequestDispatcher rd = request.getRequestDispatcher("/use/call/popup");
		rd.forward(request, response);
	}
}
