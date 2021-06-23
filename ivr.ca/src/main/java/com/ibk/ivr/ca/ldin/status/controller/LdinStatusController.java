package com.ibk.ivr.ca.ldin.status.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.ibk.ivr.ca.ldin.status.service.LdinStatusService;

import lombok.extern.slf4j.Slf4j;

/**
 * 실시간 텔레뱅킹 현황 controller
 * 
 * table : TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 	       TBCA_DLY_INPTH_LDIN_STAT (일별_인입경로_인입_현황)
 *         TBCA_30MLY_TR_STAT (30분별_TR_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/status", method=RequestMethod.POST)
public class LdinStatusController {

	@Autowired
	private LdinStatusService service;

	/**
	 * 실시간 텔레뱅킹 현황 index 화면
	 * 
	 * 당일 인입콜 건수와 전일 인입콜 건수(현재 시간 기준) 조회
	 * 실시간 거래건수는 화면 로딩 후 후 처리
	 *
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		RequestVO requestVO = new RequestVO();
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("CURRENT_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		requestVO.getParam().put("HM", DateUtil.getDateString(c.getTime(), "HHmm"));
		c.add(Calendar.DATE, -1);
		requestVO.getParam().put("DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("DT", requestVO.getParam().get("DT"));

		//인입현황(당일, 전일, 인입경로별)
		List<Map<String, Object>> result = service.selectListLdinStat(requestVO);
		for(Map<String, Object> data : result) {
			if("C".equals(data.get("INPTH_SECD"))) {
				model.addAttribute("CUR", data);
			} else if("P".equals(data.get("INPTH_SECD"))) {
				model.addAttribute("PRE", data);
			}
		}
		if(log.isDebugEnabled())
			log.debug("selectListLdinStat result count {}", result.size());

		model.addAttribute(Constants.RESULT, result);

		return "/index/ldin/status/ldin_status_index";
	}

	/**
	 * 인입현황(당일, 전일, 인입경로별) 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody ResponseVO selectList(@RequestParam(name="dt") String dt) throws Exception {
		RequestVO requestVO = new RequestVO();
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("CURRENT_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		requestVO.getParam().put("HM", DateUtil.getDateString(c.getTime(), "HHmm"));
		if(dt == null) 
			c.add(Calendar.DATE, -1);
		else 
			c.setTime(DateUtil.parseDate(dt, "yyyyMMdd"));
		
		requestVO.getParam().put("DT", DateUtil.yyyyMMdd.format(c.getTime()));
		
		//인입현황(당일, 전일, 인입경로별)
		List<Map<String, Object>> result = service.selectListLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListLdinStat result count {}", requestVO.getSize());

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 특정일자 최종 인입현황
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/stat")
	public @ResponseBody ResponseVO selectLstLdinStat(@RequestParam(name="dt") String dt) throws Exception {
		RequestVO requestVO = new RequestVO();
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("HM", DateUtil.getDateString(c.getTime(), "HHmm"));
		if(dt == null) 
			c.add(Calendar.DATE, -1);
		else 
			c.setTime(DateUtil.parseDate(dt, "yyyyMMdd"));
		
		requestVO.getParam().put("DT", DateUtil.yyyyMMdd.format(c.getTime()));
		
		Map<String, Object> result = service.selectLstLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectLstLdinStat result count {}", result != null ? result.toString() : "0");
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 거래건수 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/tr")
	public @ResponseBody ResponseVO selectListTrStat() throws Exception {
		RequestVO requestVO = new RequestVO();
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("CURRENT_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		
		c.add(Calendar.DATE, -1);
		requestVO.getParam().put("PREVIOUS_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		
		List<Map<String, Object>> result = service.selectListTrStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListTrStat result count {}", result.size());
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 실시간 텔레뱅킹 현황 엑셀 다운로드
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return AbstractXlsxView excel page
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@RequestParam(name="dt") String dt, Model model) throws Exception {
		RequestVO requestVO = new RequestVO();
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("CURRENT_DT", DateUtil.yyyyMMdd.format(c.getTime()));

		c.add(Calendar.DATE, -1);
		requestVO.getParam().put("PREVIOUS_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		
		if(dt != null) 
			c.setTime(DateUtil.parseDate(dt, "yyyyMMdd"));
		
		requestVO.getParam().put("DT", DateUtil.yyyyMMdd.format(c.getTime()));
		
		List<Map<String, Object>> list = service.selectExcelMlyLdinStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectExcelMlyLdinStat result count {}", list.size());
		model.addAttribute("list", list);
		
		List<Map<String, Object>> trList = service.selectListTrStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListTrStat result count {}", trList.size());
		
		model.addAttribute("tr", trList);
		model.addAttribute(Constants.FILE_NAME, "텔레뱅킹현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("실시간 인입현황");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "실시간 인입현황");
				
				String[] titles = {"일자", "시간", "콜건수", "이용자수", "거래건수", "에러건수", "조회건수", "이체건수"};
				int[] width = {20, 15, 15, 15, 15, 15, 15, 15};
				this.createHeader(sheet, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get("list");
                StringBuilder sb = new StringBuilder("");
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					
					crateYMDCell(row, c++, (String)data.get("DT"));
					
	                String hm = (String)data.get("HM");
	                String hour = hm.substring(0, 2);
	                String min = hm.substring(2);
	                sb.setLength(0);
	                sb.append(hour);
	                sb.append("시 ");
                	sb.append(min);
                	sb.append("분");

					createStringCell(row, c++, sb.toString());
					createNumberCell(row, c++, ((Number)data.get("CALL_CNT")).longValue());
					createNumberCell(row, c++, ((Number)data.get("USER_CNT")).longValue());
					createNumberCell(row, c++, ((Number)data.get("TR_CNT")).longValue());
					createNumberCell(row, c++, ((Number)data.get("ERR_CNT")).longValue());
					createNumberCell(row, c++, ((Number)data.get("RLTM_INQIRE_CNT")).longValue());
					createNumberCell(row, c++, ((Number)data.get("RLTM_TRANSFR_CNT")).longValue());
				}
				
				Sheet sheet2 = workbook.createSheet("실시간거래건수");
				sheet2.setDefaultColumnWidth(15);
				
		        r = 1;
				this.createTitle(sheet2, r++, "실시간 거래건수");

				String[] titles2 = {"일자", "시간", "거래건수"};
				int[] width2 = {20, 15, 15};
				this.createHeader(sheet2, r++, titles2, width2);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> trList = (List<Map<String, Object>>)model.get("tr");
				for(Map<String, Object> data : trList) {
					Row row = sheet2.createRow(r++);
					c = 0;
					
					crateYMDCell(row, c++, (String)data.get("DT"));
					
	                String hm = (String)data.get("HM");
	                String hour = hm.substring(0, 2);
	                String min = hm.substring(2);
	                sb.setLength(0);
	                sb.append(hour);
	                sb.append("시 ");
                	sb.append(min);
                	sb.append("분");

					createStringCell(row, c++, sb.toString());
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
			}
		};
	}
}
