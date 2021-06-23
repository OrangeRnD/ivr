package com.ibk.ivr.ca.use.status.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;
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
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.use.status.service.UseStatusService;

import lombok.extern.slf4j.Slf4j;

/**
 * 거래/에러 현황 조회  Controller
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/24/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/use/status", method=RequestMethod.POST)
public class UseStatusController {
	
	@Autowired
	private UseStatusService service;
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/use/status/use_status_index";
	}

	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("use_status", requestVO.getParam());
		
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> list = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", requestVO.getSize());

		Map<String, Object> stat = service.selectStat(requestVO);
		
		model.addAttribute(Constants.RESULT, list.size() == 0 ? null : list);
		model.addAttribute("stat", stat);

		return "/use/status/use_status_list";
	}
	
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> list = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", requestVO.getSize());

		Map<String, Object> stat = service.selectStat(requestVO);
		
		model.addAttribute(Constants.RESULT, list);
		model.addAttribute("stat", stat);
		model.addAttribute(Constants.FILE_NAME, "거래에러현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("거래에러현황"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "거래.에러현황");

				String[] titles1 = {"구분", "총계", "일평균", "최대일", "최대일건수"};
				int[] width1 = {30, 30, 30, 30, 30};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;

				@SuppressWarnings("unchecked")
				Map<String, Object> stat = (Map<String, Object>)model.get("stat");
				Row row1 = sheet1.createRow(r++);
				c = 0;
				createStringCell(row1, c++, "거래");
				createNumberCell(row1, c++, ((Number)stat.get("TR_CNT")).longValue());
				createNumberCell(row1, c++, ((Number)stat.get("AVG_TR_CNT")).longValue());
				crateYMDCell(row1, c++, (String)stat.get("MAX_TR_DT"));
				createNumberCell(row1, c++, ((Number)stat.get("MAX_TR_CNT")).longValue());

				Row row2 = sheet1.createRow(r++);
				c = 0;
				createStringCell(row2, c++, "에러");
				createNumberCell(row2, c++, ((Number)stat.get("ERR_CNT")).longValue());
				createNumberCell(row2, c++, ((Number)stat.get("AVG_ERR_CNT")).longValue());
				crateYMDCell(row2, c++, (String)stat.get("MAX_ERR_DT"));
				createNumberCell(row2, c++, ((Number)stat.get("MAX_ERR_CNT")).longValue());

				Row row3 = sheet1.createRow(r++);
				c = 0;
				createStringCell(row3, c++, "거래 대비 에러 비율");
				
				Number ERR_CNT = (Number)stat.get("ERR_CNT");
				Number TR_CNT = (Number)stat.get("TR_CNT");
				Number AVG_TR_CNT = (Number)stat.get("AVG_TR_CNT");
				Number AVG_ERR_CNT = (Number)stat.get("AVG_ERR_CNT");
				if(ERR_CNT.doubleValue() == 0 || TR_CNT.doubleValue() == 0)
					createNumberCell(row3, c++, 0);
				else
					createNumberCell(row3, c++, ERR_CNT.doubleValue()/TR_CNT.doubleValue()*100);
				if(AVG_ERR_CNT.doubleValue() == 0 || AVG_TR_CNT.doubleValue() == 0)
					createNumberCell(row3, c++, 0);
				else
					createNumberCell(row3, c++, AVG_ERR_CNT.doubleValue()/AVG_TR_CNT.doubleValue()*100);
				createStringCell(row3, c++, "");
				createStringCell(row3, c++, "");
				
				String[] titles2 = {"조회일", "거래 총 건수", "에러 총 건수", "거래 대비 에러 비율"};
				int[] width2 = {30, 30, 30, 30};
				r++;r++;
		        createHeader(sheet1, r++, titles2, width2);
		        
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					Number trCnt = (Number)data.get("TR_CNT");
					Number errCnt = (Number)data.get("ERR_CNT");
					createNumberCell(row, c++, trCnt.longValue());
					createNumberCell(row, c++, errCnt.longValue());
					if(trCnt.doubleValue() == 0 || errCnt.doubleValue() == 0)
						createNumberCell(row, c++, 0);
					else
						createNumberCell(row, c++, errCnt.doubleValue()/trCnt.doubleValue()*100);
				}
			}
		};
	}
}
