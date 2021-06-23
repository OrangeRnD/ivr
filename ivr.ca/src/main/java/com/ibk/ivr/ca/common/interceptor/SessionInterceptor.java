package com.ibk.ivr.ca.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private MessageSource messages;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("requst addr {}", request.getRemoteAddr());
		log.info("requst URI {}", request.getRequestURI());
		log.info("requst sessionId {}", request.getRequestedSessionId());
		
		HttpSession session = request.getSession();
				
		if(!request.isRequestedSessionIdValid())
			return sendError(request, response, handler);
		
		UsrVO as = (UsrVO)session.getAttribute(Constants.SESSION);
		if(as == null) {
			//sso 처리 하기...
			return sendError(request, response, handler);
		} else {
			if(as.getUsrId() == 0) {
				session.removeAttribute(Constants.SESSION);
				return sendError(request, response, handler);
			}
		}
		return true;
	}
	
	public boolean sendError(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("UNAUTHORIZED requst getMethod {}", request.getMethod());
		if(request.getMethod().equals("GET"))
			response.sendRedirect(request.getContextPath().concat("/"));
		else
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, messages.getMessage("error.auth.unauthorized", null, request.getLocale()));
		return false;
	}

	/**
	 * 성공한 경우에만 handler(함수) 가 호출되고 실행된다.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	/**
	 * 오류가 나도 마지막에 호출됨
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
	}
}
