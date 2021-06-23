package com.ibk.ivr.ca.system.srvccd.controller;

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
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.exception.ApplicationException;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.srvccd.service.SrvcCdService;
import com.ibk.ivr.ca.system.srvccd.vo.SrvcCdVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 서비스 코드 정보 controller
 * table : TBCA_SRVC_CD (서비스 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/srvccd", method=RequestMethod.POST)
public class SrvcCdController {

	@Autowired
	private SrvcCdService service;

	/**
	 * 서비스 코드 정보 메인화면으로 서비스 코드 정보 list 화면
	 *
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/system/srvccd/srvccd_index";
	}

	/**
	 * 서비스 코드 정보 리스트 조회
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
		return "/system/srvccd/srvccd_list";
	}

	/**
	 * 서비스 코드 정보 등록
	 *
	 * @param vo 서비스 코드 정보 등록 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody ResponseVO insert(@ModelAttribute SrvcCdVO vo, HttpServletRequest request) throws Exception {
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

		return responseVO;
	}	

	/**
	 * 서비스 코드 정보 수정
	 *
	 * @param vo 서비스 코드 정보 수정 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody ResponseVO update(@ModelAttribute SrvcCdVO vo, HttpServletRequest request) throws Exception {
		vo.setRegnDt(DateUtil.getDate());

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
	 * 서비스 코드 정보 삭제
	 *
	 * @param vo 서비스 코드 정보 삭제 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody ResponseVO delete(@ModelAttribute SrvcCdVO vo, HttpServletRequest request) throws Exception {
		if(service.delete(vo) == 0)
			throw new ApplicationException(HttpServletResponse.SC_BAD_REQUEST, Constants.ERROR_DELETE);
		return new ResponseVO();
	}

	/**
	 * 서비스 코드 정보 조회
	 *
	 * @param id 서비스 코드 정보 조회 키
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/view")
	public @ResponseBody ResponseVO select(@RequestParam(value="id", required=true) String id, Model model) throws Exception {	
		SrvcCdVO result = service.select(id);
		if(log.isDebugEnabled())
			log.debug("select result vos [{}]", result != null ? result.toString() : "NULL");

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);

		if(result == null)
			responseVO.setStatus(HttpServletResponse.SC_NOT_FOUND);

		return responseVO;
	}

	/**
	 * 서비스 코드 정보 엑셀 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return AbstractXlsxView excel page
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		List<Map<String, Object>> result = service.selectExcel(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result datas count {}", requestVO.getSize());

		String[] title = {/*
            			"서비스_코드_일련번호"
            			,"상위_서비스_코드_일련번호"
            			,"서비스_코드"
            			,"서비스_이름"
            			,"서비스_1단계_코드"
            			,"서비스_2단계_코드"
            			,"서비스_3단계_코드"
            			,"서비스_4단계_코드"
            			,"서비스_5단계_코드"
            			,"서비스_6단계_코드"
            			,"등록_일시"
		 */};
		String[] key = {
				"SRVC_CD_SN"
				,"UPR_SRVC_CD_SN"
				,"SRVC_CD"
				,"SRVC_NM"
				,"SRVC_1STEP_CD"
				,"SRVC_2STEP_CD"
				,"SRVC_3STEP_CD"
				,"SRVC_4STEP_CD"
				,"SRVC_5STEP_CD"
				,"SRVC_6STEP_CD"
				,"REGN_DT"
		};
		int[] type = {
				java.sql.Types.INTEGER
				,java.sql.Types.INTEGER
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.VARCHAR
				,java.sql.Types.DATE
		};

		model.addAttribute(Constants.RESULT, result);
		model.addAttribute("title", title);
		model.addAttribute("key", key);
		model.addAttribute("type", type);
		return null;// new ExcelView(java.net.URLEncoder.encode("srvcCd", "UTF-8"), "srvcCd");
	}
}