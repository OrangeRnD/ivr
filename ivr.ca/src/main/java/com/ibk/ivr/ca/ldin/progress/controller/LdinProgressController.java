package com.ibk.ivr.ca.ldin.progress.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.ldin.progress.service.LdinProgressService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 인입추이 controller
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/progress", method=RequestMethod.POST)
public class LdinProgressController {

	@Autowired
	private LdinProgressService service;
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model, HttpSession session) throws Exception {
		model.addAttribute("DT", DateUtil.getDate());
		
		return "/index/ldin/progress/ldin_progress_index";
	}

	/**
	 * 일별인입추이
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/daily")
	public @ResponseBody ResponseVO selectListDlyLdinStat(@RequestParam(name="ym") String ym, HttpSession session) throws Exception {
		RequestVO requestVO = new RequestVO();
		requestVO.getParam().put("YM", ym);
		
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_progress_daily", ym);
		
		String currentDate = DateUtil.getDateString("yyyyMMdd");
		if(currentDate.startsWith(ym)) 
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> result = service.selectListDlyLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListDlyLdinStat result count {}", result.toString());
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 월별인입추이
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/monthly")
	public @ResponseBody ResponseVO selectListMlyLdinStat(@RequestParam(name="year") String year, @RequestParam(name="restde", required=false) String restdeAltv, HttpSession session) throws Exception {
		RequestVO requestVO = new RequestVO();
		requestVO.getParam().put("YEAR", year);
		if(restdeAltv != null && !restdeAltv.equals(""))
			requestVO.getParam().put("RESTDE_ALTV", restdeAltv);

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		if(currentDate.startsWith(year)) 
			requestVO.getParam().put("CURRENT_DATE", currentDate);

		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_progress_monthly", requestVO.getParam());
		
		List<Map<String, Object>> result = service.selectListMlyLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListMlyLdinStat result count {}", result.toString());
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 엑셀 다운로드
	 * 
	 * @param dt
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@RequestParam(name="ym") String ym, @RequestParam(name="year") String year, @RequestParam(name="restde", required=false) String restdeAltv, Model model) throws Exception {
		RequestVO requestVO = new RequestVO();
		requestVO.getParam().put("YM", ym);
		requestVO.getParam().put("YEAR", year);
		if(restdeAltv != null && !restdeAltv.equals(""))
			requestVO.getParam().put("RESTDE_ALTV", restdeAltv);

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		if(currentDate.startsWith(ym)) 
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> dlyList = service.selectListDlyLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListDlyLdinStat result count {}", dlyList.size());
		model.addAttribute("dly", dlyList);

		if(currentDate.startsWith(year)) 
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		else
			requestVO.getParam().remove("CURRENT_DATE");
		
		List<Map<String, Object>> mlyList = service.selectListMlyLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListMlyLdinStat result count {}", mlyList.size());
		
		model.addAttribute("mly", mlyList);
		model.addAttribute(Constants.FILE_NAME, "인입추이");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("일별 인입추이");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "일별 인입추이");
				
				String[] titles = {"일자", "콜건수", "이용자수", "거래건수"};
				int[] width = {30, 30, 30, 30};
				this.createHeader(sheet, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get("dly");
                StringBuilder sb = new StringBuilder("");
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					
					crateYMDCell(row, c++, (String)data.get("DT"));
					
					if(data.get("CALL_CNT") != null) {
						createNumberCell(row, c++, ((Number)data.get("CALL_CNT")).longValue());
					} else {
						createNumberCell(row, c++, 0);
					}
					if(data.get("USER_CNT") != null) {
						createNumberCell(row, c++, ((Number)data.get("USER_CNT")).longValue());
					} else {
						createNumberCell(row, c++, 0);
					}
					if(data.get("TR_CNT") != null) {
						createNumberCell(row, c++, ((Number)data.get("TR_CNT")).longValue());
					} else {
						createNumberCell(row, c++, 0);
					}
				}
				
				Sheet sheet2 = workbook.createSheet("월별 인입추이");
				sheet2.setDefaultColumnWidth(15);
				
		        r = 1;
				this.createTitle(sheet2, r++, "월별 인입추이");
				
				this.createHeader(sheet2, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> trList = (List<Map<String, Object>>)model.get("mly");
				for(Map<String, Object> data : trList) {
					Row row = sheet2.createRow(r++);
					c = 0;
					
	                String dt = (String)data.get("DT");
	                sb.setLength(0);
	                sb.append(dt.substring(0, 4));
	                sb.append("년 ");
                	sb.append(dt.substring(4));
                	sb.append("월");
                	
                	createStringCell(row, c++, sb.toString());

                	if(data.get("CALL_CNT") != null) {
						createNumberCell(row, c++, ((Number)data.get("CALL_CNT")).longValue());
					} else {
						createNumberCell(row, c++, 0);
					}
					if(data.get("USER_CNT") != null) {
						createNumberCell(row, c++, ((Number)data.get("USER_CNT")).longValue());
					} else {
						createNumberCell(row, c++, 0);
					}
					if(data.get("TR_CNT") != null) {
						createNumberCell(row, c++, ((Number)data.get("TR_CNT")).longValue());
					} else {
						createNumberCell(row, c++, 0);
					}
				}
			}
		};
	}
}
