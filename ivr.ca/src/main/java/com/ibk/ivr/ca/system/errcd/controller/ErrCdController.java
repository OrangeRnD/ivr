package com.ibk.ivr.ca.system.errcd.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.errcd.service.ErrCdService;

import lombok.extern.slf4j.Slf4j;

/**
 * 에러 코드 정보 controller
 * table : TBCA_ERR_CD (에러 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/errcd", method=RequestMethod.POST)
public class ErrCdController {
	
    @Autowired
    private ErrCdService service;

    /**
     * 에러 코드 정보 메인화면으로 에러 코드 정보 list 화면
     *
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
    public String index(Model model) throws Exception {
        return "/index/system/errcd/errcd_index";
    }
			     
    /**
     * 에러 코드 정보 리스트 조회
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
        return "/system/errcd/errcd_list";
    }
    
    /**
     * 에러 코드 정보 엑셀 조회
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
            			"에러_코드"
            			,"에러_내용"
            			,"등록_일시"
						*/};
		String[] key = {
            			"ERR_CD"
            			,"ERR_CN"
            			,"REGN_DT"
						};
		int[] type = {
            			java.sql.Types.VARCHAR
            			,java.sql.Types.VARCHAR
            			,java.sql.Types.DATE
						};
		
        model.addAttribute(Constants.RESULT, result);
		model.addAttribute("title", title);
		model.addAttribute("key", key);
		model.addAttribute("type", type);
		return null;//new ExcelView(java.net.URLEncoder.encode("errCd", "UTF-8"), "errCd");
    }
}