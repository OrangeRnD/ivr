package com.ibk.ivr.ca.ldin.ars.controller;

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
//import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.ars.service.LdinArsService;
//import com.ibk.ivr.ca.system.cd.service.CdService;
//import com.ibk.ivr.ca.system.cd.vo.CdVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * ARS 유형별 이용현황 controller
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 *		   TBCA_DLY_INPTH_LDIN_STATT (일별_인입경로_인입_현황)
 *		   TBCA_MLY_INPTH_LDIN_STATT (분별_인입경로_인입_현황)
 * 		   TBCA_DLY_CALL_KND_STAT (일별_콜_종류_현황)
 *         TBCA_MLY_CALL_KND_STAT (분별_콜_종류_현황)
 *         TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/ars", method=RequestMethod.POST)
public class LdinArsController {
	
	@Autowired
	private LdinArsService service;
	
	/**
	 * ARS 유형별 이용현황 index 화면
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/ldin/ars/ldin_ars_index";
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
    	((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_ars", requestVO.getParam());
    	
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
        if(log.isDebugEnabled())
            log.debug("selectList result vos count {}", requestVO.getSize());
        
        if(result.size() > 0) {
        	//ARS 이용콜수 구하기 위해 보이는 ARS 이용콜수+말로하는 ARS 이용콜수 조회
        	for(Map<String, Object> data : result) {
        		if(data.get("CD").equals("P")) {
        			requestVO.getParam().put("MAX_DT1", data.get("MAX_DT1"));
        			requestVO.getParam().put("MAX_DT2", data.get("MAX_DT2"));
        			
        			Map<String, Object> maxData = service.selectMaxDtCallKnd(requestVO);
        			model.addAttribute("MAX_CNT1", maxData.get("CNT1"));
        			model.addAttribute("MAX_CNT2", maxData.get("CNT2"));
        			break;
        		}
        	}
        }
							
        model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
        return "/ldin/ars/ldin_ars_list";
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
		model.addAttribute(Constants.FILE_NAME, "ARS유형별이용현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("ARS 유형별 이용현황");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "ARS 유형별 이용현황");

				String[] titles = {"일자", "IVR 이용 콜수", "대표번호", "ARS 이용 콜수", "보이는 ARS 이용 콜수", "말로하는 ARS 이용콜수", "말로하는 ARS 이용건수"};
				int[] width = {20, 15, 15, 15, 15, 15, 15};
				this.createHeader(sheet, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					
					crateYMDCell(row, c++, (String)data.get("DT"));
					
					Number IVR_CALL_CNT1 = (Number)data.get("IVR_CALL_CNT1");
					Number P_CALL_CNT1 = (Number)data.get("P_CALL_CNT1");
					Number V_CALL_CNT1 = (Number)data.get("V_CALL_CNT1");
					Number T_CALL_CNT1 = (Number)data.get("T_CALL_CNT1");
					Number T_USER_CNT1 = (Number)data.get("T_USER_CNT1");

					createNumberCell(row, c++, IVR_CALL_CNT1.longValue() == 0 ? ((Number)data.get("IVR_CALL_CNT2")).longValue() : IVR_CALL_CNT1.longValue());
					createNumberCell(row, c++, P_CALL_CNT1.longValue() == 0 ? ((Number)data.get("P_CALL_CNT2")).longValue() : P_CALL_CNT1.longValue());
					
					long arsCnt = P_CALL_CNT1.longValue() == 0 ? ((Number)data.get("P_CALL_CNT2")).longValue() : P_CALL_CNT1.longValue();
					arsCnt -= V_CALL_CNT1.longValue() == 0 ? ((Number)data.get("V_CALL_CNT2")).longValue() : V_CALL_CNT1.longValue();
					arsCnt -= T_CALL_CNT1.longValue() == 0 ? ((Number)data.get("T_CALL_CNT2")).longValue() : T_CALL_CNT1.longValue();
					
					createNumberCell(row, c++, arsCnt);
					
					createNumberCell(row, c++, V_CALL_CNT1.longValue() == 0 ? ((Number)data.get("V_CALL_CNT2")).longValue() : V_CALL_CNT1.longValue());
					createNumberCell(row, c++, T_CALL_CNT1.longValue() == 0 ? ((Number)data.get("T_CALL_CNT2")).longValue() : T_CALL_CNT1.longValue());
					createNumberCell(row, c++, T_USER_CNT1.longValue() == 0 ? ((Number)data.get("T_USER_CNT2")).longValue() : T_USER_CNT1.longValue());
				}
			}
		};
	}
}
