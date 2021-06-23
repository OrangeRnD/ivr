package com.ibk.ivr.ca.ldin.callanalysis.controller;

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
import com.ibk.ivr.ca.ldin.callanalysis.service.LdinCallAnalysisService;
//import com.ibk.ivr.ca.system.cd.service.CdService;
//import com.ibk.ivr.ca.system.cd.vo.CdVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 대표번호이용현황 분석 controller
 * 
 * table : TBCA_DLY_INPTH_LDIN_STAT (일별_인입경로_인입_현황)
 *         TBCA_MLY_INPTH_LDIN_STAT (분별_인입경로_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/callanalysis", method=RequestMethod.POST)
public class LdinCallAnalysisController {

	@Autowired
	private LdinCallAnalysisService service;

	/**
	 * 대표번호이용현황 분석 index 화면
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/ldin/callanalysis/ldin_callanalysis_index";
	}

	/**
	 * 대표번호이용현황 분석 데이타 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
    	((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_callanalysis", requestVO.getParam());
    	
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

		model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
		return "/ldin/callanalysis/ldin_callanalysis_list";
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
		model.addAttribute(Constants.FILE_NAME, "대표번호이용현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("대표번호이용현황");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "대표번호이용현황");

				String[] titles = {"일자", "15882588(KT)", "15662566(SKB)", "카드신청(15660088)", "고객채번(8331000)", "VIP(0807764000)", "카드연체(2XXX)", "IT상담(2292114)", "정보동의철회(7764400)", "비밀번호받기(10000)", "콜백(20000)", "3자통화(30000)", "소액콜백(3224)"};
				int[] width = {20, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
				this.createHeader(sheet, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					
					crateYMDCell(row, c++, (String)data.get("DT"));

					createNumberCell(row, c++, ((Number)data.get("CNT1")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT2")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT3")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT4")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT5")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT6")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT7")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT8")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT9")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT10")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT11")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT12")).longValue());
				}
			}
		};
	}
}
