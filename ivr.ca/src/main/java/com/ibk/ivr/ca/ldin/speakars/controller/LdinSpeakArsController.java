package com.ibk.ivr.ca.ldin.speakars.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.speakars.service.LdinSpeakArsService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * ARS 유형별 이용현황 controller
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/speakars", method=RequestMethod.POST)
public class LdinSpeakArsController {
	
	@Autowired
	private LdinSpeakArsService service;
	
	/**
	 * ARS 유형별 이용현황 index 화면
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/ldin/speakars/ldin_speakars_index";
	}

	/**
	 * ARS 유형별 이용현황 데이타 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
    @RequestMapping(value = "/list")
    public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
    	((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_speakars", requestVO.getParam());
    	
    	int fromDt1 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT1"));
    	int toDt1 = Integer.parseInt((String)requestVO.getParam().get("TO_DT1"));

    	int fromDt2 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT2"));
    	int toDt2 = Integer.parseInt((String)requestVO.getParam().get("TO_DT2"));
    	
    	String currentDate = DateUtil.getDateString("yyyyMMdd");
    	int curDt = Integer.parseInt(currentDate);
    	
    	if(curDt >= fromDt1 && curDt <= toDt1)
			requestVO.getParam().put("CURRENT_DATE1", currentDate);
    	
    	if(curDt >= fromDt2 && curDt <= toDt2)
			requestVO.getParam().put("CURRENT_DATE2", currentDate);
    	
        List<Map<String, Object>> result = service.selectList(requestVO);
        
        result.forEach(item -> {
        	String cd = (String)item.get("CD");
        	item.replace("CD", cd.trim());
        });
        
        if(log.isDebugEnabled())
            log.debug("selectList result vos count {}", requestVO.getSize());
        
        if(result.size() != 5) {
        	List<Map<String, Object>> result2 = new ArrayList<Map<String, Object>>(5);
        	int sort = 1;
        	String[] cd = {"ARS", "1910", "2114", "simple_Y", "simple_N"};
        	for(Map<String, Object> data : result) {
        		if(((Number)data.get("SORT")).intValue() == sort) {
        			result2.add(data);
        		} else {
        			Map<String, Object> tmp = new HashMap<String, Object>();
        			tmp.put("CD", cd[sort-1]);
        			tmp.put("SORT", sort);
        			tmp.put("AVG_CNT1", 0);
        			tmp.put("TOT_CNT1", 0);
        			tmp.put("MAX_CNT1", 0);
        			tmp.put("MAX_DT1", "");
        			tmp.put("NUM1", 0);
        			tmp.put("AVG_CNT2", 0);
        			tmp.put("TOT_CNT2", 0);
        			tmp.put("MAX_CNT2", 0);
        			tmp.put("MAX_DT2", "");
        			tmp.put("NUM2", 0);
        			result2.add(tmp);
        		}
        		sort++;
        	}
        	for(; sort <= 5; sort++) {
        		Map<String, Object> tmp = new HashMap<String, Object>();
    			tmp.put("CD", cd[sort-1]);
    			tmp.put("SORT", sort);
    			tmp.put("AVG_CNT1", 0);
    			tmp.put("TOT_CNT1", 0);
    			tmp.put("MAX_CNT1", 0);
    			tmp.put("MAX_DT1", "");
    			tmp.put("NUM1", 0);
    			tmp.put("AVG_CNT2", 0);
    			tmp.put("TOT_CNT2", 0);
    			tmp.put("MAX_CNT2", 0);
    			tmp.put("MAX_DT2", "");
    			tmp.put("NUM2", 0);
    			result2.add(tmp);
        	}
        	model.addAttribute(Constants.RESULT, result2);
        } else
        	model.addAttribute(Constants.RESULT, result);
        return "/ldin/speakars/ldin_speakars_list";
    }
    
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {	
    	int fromDt1 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT1"));
    	int toDt1 = Integer.parseInt((String)requestVO.getParam().get("TO_DT1"));

    	int fromDt2 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT2"));
    	int toDt2 = Integer.parseInt((String)requestVO.getParam().get("TO_DT2"));
    	
    	String currentDate = DateUtil.getDateString("yyyyMMdd");
    	int curDt = Integer.parseInt(currentDate);
    	
    	if(curDt >= fromDt1 && curDt <= toDt1)
			requestVO.getParam().put("CURRENT_DATE1", currentDate);
    	
    	if(curDt >= fromDt2 && curDt <= toDt2)
			requestVO.getParam().put("CURRENT_DATE2", currentDate);
    	
        List<Map<String, Object>> result = service.selectExcel(requestVO);
		model.addAttribute(Constants.RESULT, result);
		model.addAttribute(Constants.FILE_NAME, "말로하는ARS유형별이용현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("말로하는 ARS 유형별 이용현황");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "ARS 유형별 이용현황");

				String[] titles = {"일자", "말로하는 ARS 총 건수", "말로하는 ARS 메뉴이동", "음성 인식", "계좌 직접 선택", "간편송금 미사용"};
				int[] width = {20, 22, 22, 22, 22, 22};
				this.createHeader(sheet, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					
					crateYMDCell(row, c++, (String)data.get("DT"));
					
					Number CNT_1910 = (Number)data.get("CNT_1910");//말로하는 ARS 메뉴이동
					Number CNT_2114 = (Number)data.get("CNT_2114");//음성 인식
					Number CNT_simple_Y = (Number)data.get("CNT_simple_Y");//계좌 직접 선택
					Number CNT_simple_N = (Number)data.get("CNT_simple_N");//간편송금 미사용

					createNumberCell(row, c++, CNT_1910.longValue() + CNT_2114.longValue());
					createNumberCell(row, c++, CNT_1910.longValue());
					createNumberCell(row, c++, CNT_2114.longValue());
					createNumberCell(row, c++, CNT_simple_Y.longValue() + CNT_simple_N.longValue());
					createNumberCell(row, c++, CNT_simple_Y.longValue());
					createNumberCell(row, c++, CNT_simple_N.longValue());
				}
			}
		};
	}
}
