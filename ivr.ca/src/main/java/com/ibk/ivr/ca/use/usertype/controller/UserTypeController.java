package com.ibk.ivr.ca.use.usertype.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.use.usertype.service.UserTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 연령별,개인/사업자 서비스 이용현황  Controller
 * 
 * table : TBCA_DLY_SRVC_USER_STAT (일별_서비스_이용자_현황)
 *         TBCA_DLY_SRVC_AGE_STAT (일별_서비스_연령_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/24/2019
 * @deprecated UseService 와 통합
 */
@Slf4j
//@Controller
//@RequestMapping(value = "/use/usertype", method=RequestMethod.POST)
public class UserTypeController {
	
	@Autowired
	private UserTypeService service;
	
	/**
	 * 연령별,개인/사업자 서비스 이용현황 index
	 * default 로 금주 데이타 조회
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/use/usertype/service_usertype_index";
	}

	/**
	 * 이용자 구분 서비스 이용현황 TOP-10
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/user")
	public @ResponseBody ResponseVO selectListSrvcUserStat(@ModelAttribute RequestVO requestVO) throws Exception {
		List<Map<String, Object>> result = service.getListSrvcUserStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListSrvcUserStat result count {}", requestVO.getSize());

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 연령버 서비스 이용현황 TOP-10
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/age")
	public @ResponseBody ResponseVO selectListSrvcAgeStat(@ModelAttribute RequestVO requestVO) throws Exception {
		List<Map<String, Object>> result = service.getListSrvcAgeStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListSrvcAgeStat result count {}", requestVO.getSize());

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
}
