package com.ibk.ivr.ca.common.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonHandlerExceptionResolver  implements HandlerExceptionResolver {
	
	public String statusName = "javax.servlet.error.status";
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String messageName = "javax.servlet.error.message";
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	
	public String codeName = "javax.servlet.error.code";
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public String urlView = "error/error";
	public void setUrlView(String urlView) {
		this.urlView = urlView;
	}
	
	public String jsonView = "error/error_json";
	public void setJsonView(String jsonView) {
		this.jsonView = jsonView;
	}
	
	@Autowired
	protected MessageSource messageSource;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		
		if(e instanceof ApplicationException) {
			return resolveApplicationException(request, response, handle, (ApplicationException)e, modelAndView, request.getLocale());
		} else {
			return resolveException(request, response, handle, e, modelAndView, request.getLocale());
		}
	}
	
	/**
	 * 삭제 오류 시 메시지 처리 하기(to do)
	 * @param request
	 * @param response
	 * @param handle
	 * @param e
	 * @param modelAndView
	 * @param locale
	 * @return
	 */
	public ModelAndView resolveApplicationException(HttpServletRequest request, HttpServletResponse response, Object handle, ApplicationException e, ModelAndView modelAndView, Locale locale) {
		if (e.getStatus() != 0) 
			modelAndView.addObject(statusName, e.getStatus());
		else 
			modelAndView.addObject(statusName, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		String message = messageSource.getMessage(e.getMessage(), null, null, locale);	
		modelAndView.addObject(messageName, message == null ? e.getMessage() : message);
		modelAndView.addObject(codeName, e.getCode());
		
		if(request.getMethod().equals("GET")) {
			modelAndView.setViewName(urlView);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			modelAndView.setViewName(jsonView);
		}
		
		log.error(e.getMessage());
		return modelAndView;
	}
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception e, ModelAndView modelAndView, Locale locale) {
		if(e instanceof HttpMediaTypeNotAcceptableException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_NOT_ACCEPTABLE);
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else if(e instanceof HttpMediaTypeNotSupportedException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else if(e instanceof BindException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_BAD_REQUEST);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else if(e instanceof HttpRequestMethodNotSupportedException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else if(e instanceof MethodArgumentNotValidException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_BAD_REQUEST);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else if(e instanceof TypeMismatchException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_BAD_REQUEST);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else if(e instanceof java.sql.SQLException) {
			modelAndView.addObject(statusName, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			modelAndView.addObject("javax.servlet.error.code", 0);
		} else {
			//시스템 오류
			modelAndView.addObject(statusName, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			modelAndView.addObject(codeName, 0);
		}
		String message = messageSource.getMessage(e.getMessage(), null, null, locale);	
		modelAndView.addObject(messageName, message == null ? e.getMessage() : message);
		
		if(request.getMethod().equals("GET")) {
			response.setStatus(HttpServletResponse.SC_OK);
			modelAndView.setViewName(urlView);
		} else {
			modelAndView.setViewName(jsonView);
		}
		
		log.error(e.getMessage(), e);
		return modelAndView;
	}
}
