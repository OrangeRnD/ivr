package com.ibk.ivr.ca.system.cdclsfctn.controller;

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
import com.ibk.ivr.ca.system.cdclsfctn.vo.CdClsfctnVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.system.cdclsfctn.service.CdClsfctnService;

import lombok.extern.slf4j.Slf4j;

/**
 * 시스템 코드분류 정보 controller
 * table : TBCA_CD_CLSFCTN (시스템 코드분류 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/cdclsfctn", method=RequestMethod.POST)
public class CdClsfctnController {

	@Autowired
	private CdClsfctnService service;
	
	/**
	 * 시스템 코드분류 정보 메인화면으로 시스템 코드분류 정보 list 화면
	 *
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		RequestVO requestVO = new RequestVO();
		requestVO.setPageNo(1);
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", requestVO.getSize());

		model.addAttribute(Constants.PAGING, requestVO);
		model.addAttribute(Constants.RESULT, result);

		return "/index/system/cdclsfctn/cdclsfctn_index";
	}

	/**
	 * 시스템 코드분류 정보 리스트 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model) throws Exception {			
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", requestVO.getSize());

		model.addAttribute(Constants.PAGING, requestVO);
		model.addAttribute(Constants.RESULT, result);
		return "/system/cdclsfctn/cdclsfctn_list";
	}

	/**
	 * 시스템 코드분류 정보 등록
	 *
	 * @param vo 시스템 코드분류 정보 등록 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody ResponseVO insert(@ModelAttribute CdClsfctnVO vo, HttpServletRequest request) throws Exception {
		//user id, reg dt 세팅하기
        vo.setRegrId(((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
        vo.setRegnDt(DateUtil.getDate());
        					
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
		else if(result == -1)
			responseVO.setStatus(409);
		

		return responseVO;
	}	

	/**
	 * 시스템 코드분류 정보 수정
	 *
	 * @param vo 시스템 코드분류 정보 수정 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody ResponseVO update(@ModelAttribute CdClsfctnVO vo, HttpServletRequest request) throws Exception {
		//user id, reg dt 세팅하기
		vo.setLstUpdusrId(((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
        vo.setLstUpdtDt(DateUtil.getDate());
    				
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
	 * 시스템 코드분류 정보 삭제
	 *
	 * @param vo 시스템 코드분류 정보 삭제 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ResponseVO delete(@ModelAttribute CdClsfctnVO vo, HttpServletRequest request) throws Exception {
		//user id, reg dt 세팅하기
		vo.setLstUpdusrId(((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
        vo.setLstUpdtDt(DateUtil.getDate());
        
        if(service.delete(vo) == 0)
			throw new ApplicationException(HttpServletResponse.SC_BAD_REQUEST, Constants.ERROR_DELETE);
		return new ResponseVO();
	}

	/**
	 * 시스템 코드분류 정보 조회
	 *
	 * @param id 시스템 코드분류 정보 조회 키
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public @ResponseBody ResponseVO select(@RequestParam(value="id", required=true) String id, Model model) throws Exception {	
		CdClsfctnVO result = service.select(id);
		if(log.isDebugEnabled())
			log.debug("select result vos [{}]", result != null ? result.toString() : "NULL");

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);

		if(result == null)
			responseVO.setStatus(HttpServletResponse.SC_NOT_FOUND);

		return responseVO;
	}
}