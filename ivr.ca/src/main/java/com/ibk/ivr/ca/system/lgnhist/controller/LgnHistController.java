package com.ibk.ivr.ca.system.lgnhist.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.lgnhist.service.LgnHistService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 로그인 이력 정보 controller
 * table : TBCA_LGN_HIST (사용자 로그인 이력 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/lgnhist", method=RequestMethod.POST)
public class LgnHistController {
	
    @Autowired
    private LgnHistService service;

    /**
     * 사용자 로그인 이력 정보 메인화면으로 사용자 로그인 이력 정보 list 화면
     *
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
    public String index(Model model) throws Exception {
        return "/index/system/lgnhist/lgnhist_index";
    }
    
	@RequestMapping(value = "/popup", method=RequestMethod.GET)
	public String popup(Model model) throws Exception {
		return "/popup/system/lgnhist/lgnhist_index";
	}
			     
    /**
     * 사용자 로그인 이력 정보 리스트 조회
     *
     * @param vo RequestVO 조회 조건 데이터
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {		
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("system_lgnhist", requestVO.getParam());
		
        List<Map<String, Object>> result = service.selectList(requestVO);
        if(log.isDebugEnabled())
            log.debug("selectList result vos count {}", requestVO.getSize());
							
        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
        return "/system/lgnhist/lgnhist_list";
    }
    
    /**
     * 사용자 로그인 이력 정보 엑셀 조회
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
            			"로그인_이력_아이디"
            			,"직원_번호"
            			,"로그인_상태_시스템코드
0 : 로그오프
200 : 로그인
401 : 인증실패'"
            			,"로그인_일시"
            			,"로그인_IP"
            			,"사용자_아이디"
						*/};
		String[] key = {
            			"LGN_HIST_ID"
            			,"EMP_NR"
            			,"LGN_STS_SECD"
            			,"LGN_DT"
            			,"LGN_IP"
            			,"USR_ID"
						};
		int[] type = {
            			java.sql.Types.INTEGER
            			,java.sql.Types.VARCHAR
            			,java.sql.Types.VARCHAR
            			,java.sql.Types.DATE
            			,java.sql.Types.VARCHAR
            			,java.sql.Types.INTEGER
						};
		
        model.addAttribute(Constants.RESULT, result);
		model.addAttribute("title", title);
		model.addAttribute("key", key);
		model.addAttribute("type", type);
		return null;//new ExcelView(java.net.URLEncoder.encode("lgnHist", "UTF-8"), "lgnHist");
    }
}