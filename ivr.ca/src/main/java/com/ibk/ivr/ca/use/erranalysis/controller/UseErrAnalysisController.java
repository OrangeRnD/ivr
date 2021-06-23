package com.ibk.ivr.ca.use.erranalysis.controller;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.util.FormatUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.use.erranalysis.service.UseErrAnalysisService;

import lombok.extern.slf4j.Slf4j;

/**
 * 실시간 에러발생 비교분석 controller
 * 
 * table : TBCA_5MLY_LDIN_STAT (5분별_인입현황_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/08/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/use/erranalysis", method=RequestMethod.POST)
public class UseErrAnalysisController {

	@Autowired
	private UseErrAnalysisService service;
	
	/**
	 * 실시간 에러발생 비교분석 index 화면
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/use/erranalysis/err_analysis_index";
	}

	/**
	 * 실시간 에러발생 비교분석 데이타 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody ResponseVO selectList(@ModelAttribute RequestVO requestVO, HttpSession session) throws Exception {
		requestVO.setPageNo(0);
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("use_erranalysis", requestVO.getParam());
		
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", result != null ? result.toString() : "null");
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 5분 단위 포인트 클릭 시 해당 시간대 에러 리스트 조회 (선택 시분 - 5분 부터 선택 시간 미만 까지 데이타)
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO 에러리스트
	 * @throws Exception
	 */
	@RequestMapping(value = "/err")
	public @ResponseBody ResponseVO selectErrList(@ModelAttribute RequestVO requestVO) throws Exception {
		//오늘 날짜에 대해서만 조회 가능
		Calendar c = Calendar.getInstance();
		String dt = (String)requestVO.getParam().get("DT");
		if(dt == null) {
			dt = DateUtil.yyyyMMdd.format(c.getTime());
		} else {
			c.setTime(DateUtil.parseDate(dt, "yyyyMMdd"));
		}
		requestVO.getParam().put("DT", dt);
		
		String hh = (String)requestVO.getParam().get("HH");
		String mm = (String)requestVO.getParam().get("MM");
		c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hh));
		c.set(Calendar.MINUTE, Integer.parseInt(mm));
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		requestVO.getParam().put("TO_TS", c.getTime());
		
		c.add(Calendar.MINUTE, -5);
		requestVO.getParam().put("FROM_TS", c.getTime());
		
		List<Map<String, Object>> result = service.selectErrList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", result != null ? result.toString() : "null");
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 실시간 에러 발생 비교 엑셀다운로드
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		requestVO.setPageNo(0);
		
		if(requestVO.getParam().get("PRE_DT") != null && !requestVO.getParam().get("PRE_DT").equals("")) {
			model.addAttribute("DT2", (String)requestVO.getParam().get("PRE_DT"));
		} else {
			model.addAttribute("DT2", (String)requestVO.getParam().get("FROM_DT"));
			model.addAttribute("DT3", (String)requestVO.getParam().get("TO_DT"));
			
			String maxDt = service.selectMaxDt(requestVO);
			model.addAttribute("MAX_DT", maxDt);
			requestVO.getParam().put("MAX_DT", maxDt);
		}
		model.addAttribute("DT", (String)requestVO.getParam().get("DT"));

		List<Map<String, Object>> list = service.selectListExcel(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", list != null ? list.toString() : "null");
		
		model.addAttribute("list", list);
		model.addAttribute(Constants.FILE_NAME, "실시간에러발생비교");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("실시간 에러 발생 비교");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "실시간 에러 발생 비교");

                StringBuilder sb = new StringBuilder("");
				String[] titles = {"시간", "에러 건수", "에러 건수"};
				String[] titles2 = {"시간", "에러 건수", "에러 건수", ""};
				
				sb.append("에러 건수 ( ");
				sb.append(FormatUtil.formatDate((String)model.get("DT"), "$1-$2-$3"));
				sb.append(" )");
				titles[1] = sb.toString();
				titles2[1] = sb.toString();
				
				if(model.get("DT3") != null) {
					sb.setLength(0);
					sb.append("에러 건수 ( ");
					sb.append(FormatUtil.formatDate((String)model.get("MAX_DT"), "$1-$2-$3"));
					sb.append(" )");
					titles2[2] = sb.toString();
					
					sb.setLength(0);
					sb.append("에러 평균  ( ");
					sb.append(FormatUtil.formatDate((String)model.get("DT2"), "$1-$2-$3"));
					sb.append(" ~ ");
					sb.append(FormatUtil.formatDate((String)model.get("DT3"), "$1-$2-$3"));
					sb.append(" )");
					titles2[3] = sb.toString();
					
					int[] width = {30, 60, 60, 60};
					this.createHeader(sheet, r++, titles2, width);
				} else {
					sb.setLength(0);
					sb.append("에러 건수 ( ");
					sb.append(FormatUtil.formatDate((String)model.get("DT2"), "$1-$2-$3"));
					sb.append(" )");
					titles[2] = sb.toString();
				}
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get("list");
				if(list == null) {
					Row row = sheet.createRow(r++);
					short c = 0;
					
					createStringCell(row, c++, "상하위 제외 범위를 초과하였습니다.");
					createNumberCell(row, c++, 0);
					createNumberCell(row, c++, 0);
					if(model.get("DT3") != null)
						createNumberCell(row, c++, 0);
					return;
				}
				
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					
	                String hm = (String)data.get("HM");
	                String hour = hm.substring(0, 2);
	                String min = hm.substring(2);
	                sb.setLength(0);
	                sb.append(hour);
	                sb.append("시 ");
                	sb.append(min);
                	sb.append("분");

					createStringCell(row, c++, sb.toString());
					createNumberCell(row, c++, ((Number)data.get("CNT1")).longValue());
					if(model.get("DT3") != null)
						createNumberCell(row, c++, ((Number)data.get("CNT3")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT2")).longValue());
				}
			}
		};
	}
}
