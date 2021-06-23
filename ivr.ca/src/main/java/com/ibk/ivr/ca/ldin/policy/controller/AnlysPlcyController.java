package com.ibk.ivr.ca.ldin.policy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.exception.ApplicationException;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.ldin.policy.service.AnlysPlcyService;
import com.ibk.ivr.ca.ldin.policy.vo.AnlysPlcyVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 실시간 인입추이 정책설정 controller
 * 
 * table : TBCA_ANLYS_PLCY (분석_정책)
 * 		   TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/policy", method=RequestMethod.POST)
public class AnlysPlcyController {

	@Autowired
	private AnlysPlcyService service;

	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		RequestVO requestVO = new RequestVO();
		requestVO.setPageNo(1);
		requestVO.setPagingRow(8);
		requestVO.getParam().put("ANLYS_PLCY_SECD", "1");
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", requestVO.getSize());

		model.addAttribute(Constants.PAGING, requestVO);
		model.addAttribute(Constants.RESULT, result);
		return "/index/ldin/policy/ldin_policy_index";
	}

	/**
	 * 분석 정책 설정 정보 리스트 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		requestVO.setPagingRow(8);
		requestVO.getParam().put("ANLYS_PLCY_SECD", "1");		
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", requestVO.getSize());

		model.addAttribute(Constants.PAGING, requestVO);
		model.addAttribute(Constants.RESULT, result);
		return "/ldin/policy/ldin_policy_list";
	}

	/**
	 * 분석 정책 설정 정보 등록
	 *
	 * @param vo 분석 정책 설정 정보 등록 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody ResponseVO insert(@ModelAttribute AnlysPlcyVO vo, HttpServletRequest request) throws Exception {
		//user id, reg dt 세팅하기
		vo.setRegrId(((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
		vo.setRegnDt(DateUtil.getDate());
		
		if(vo.getBizdtSecd() == null || "".equals(vo.getBizdtSecd()))
			vo.setBizdtSecd("1");

		ResponseVO responseVO = new ResponseVO();
		int result = 0;
		try {
			result = service.insert(vo);
		} catch(ApplicationException e) {
			responseVO.setStatus(e.getStatus());
			return responseVO;
		}

		if(result == 0)
			throw new ApplicationException(HttpServletResponse.SC_BAD_REQUEST, Constants.ERROR_INSERT);

		return responseVO;
	}	

	/**
	 * 분석 정책 설정 정보 수정
	 *
	 * @param vo 분석 정책 설정 정보 수정 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody ResponseVO update(@ModelAttribute AnlysPlcyVO vo, HttpServletRequest request) throws Exception {
		//user id, reg dt 세팅하기
		vo.setLstUpdusrId(((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
		vo.setLstUpdtDt(DateUtil.getDate());

		if(vo.getBizdtSecd() == null || "".equals(vo.getBizdtSecd()))
			vo.setBizdtSecd("1");
		
		ResponseVO responseVO = new ResponseVO();
		int result = 0;
		try {
			result = service.update(vo);
		} catch(ApplicationException e) {
			responseVO.setStatus(e.getStatus());
			return responseVO;
		}

		if(result == 0)
			throw new ApplicationException(HttpServletResponse.SC_BAD_REQUEST, Constants.ERROR_UPDATE);

		return responseVO;
	}

	/**
	 * 분석 정책 설정 정보 삭제
	 *
	 * @param vo 분석 정책 설정 정보 삭제 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ResponseVO delete(@ModelAttribute AnlysPlcyVO vo, HttpServletRequest request) throws Exception {
		//user id, reg dt 세팅하기
		vo.setLstUpdusrId(((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
		vo.setLstUpdtDt(DateUtil.getDate());

		if(service.delete(vo) == 0)
			throw new ApplicationException(HttpServletResponse.SC_BAD_REQUEST, Constants.ERROR_DELETE);
		return new ResponseVO();
	}

	/**
	 * 분석 정책 설정 정보 조회
	 *
	 * @param id 분석 정책 설정 정보 조회 키
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public @ResponseBody ResponseVO select(@RequestParam(value="id", required=true) int id) throws Exception {	
		AnlysPlcyVO result = service.select(id);
		if(log.isDebugEnabled())
			log.debug("select result vos [{}]", result != null ? result.toString() : "NULL");

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);

		if(result == null)
			responseVO.setStatus(HttpServletResponse.SC_NOT_FOUND);

		return responseVO;
	}
}
