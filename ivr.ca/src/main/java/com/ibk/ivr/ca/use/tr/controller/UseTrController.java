package com.ibk.ivr.ca.use.tr.controller;

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
import com.ibk.ivr.ca.use.tr.service.UseTrService;

import lombok.extern.slf4j.Slf4j;

/**
 * 거래 검색 controller
 * 
 * table : TBCA_IVR_LOG (IVR_로그)
 *          TBCA_IVR_TR_RCPTN_LOG (IVR_TR_수신_로그)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/use/tr", method=RequestMethod.POST)
public class UseTrController {

	@Autowired
	private UseTrService service;

	/**
	 * tr 검색 index
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/use/tr/use_tr_index";
	}

	/**
	 * tr 검색 index
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/popup", method=RequestMethod.GET)
	public String popup(Model model) throws Exception {
		return "/popup/use/tr/use_tr_index";
	}

	/**
	 * tr 검색
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
    	((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_tr", requestVO.getParam());
    	
		String fromDt = (String)requestVO.getParam().get("FROM_DT");
		String fromH = (String)requestVO.getParam().get("FROM_H");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		requestVO.getParam().put("FROM_TM", DateUtil.parseDate(fromDt.concat(fromH).concat(fromM).concat("00"), "yyyyMMddHHmmss"));
		
		String toDt = (String)requestVO.getParam().get("TO_DT");
		String toH = (String)requestVO.getParam().get("TO_H");
		String toM = (String)requestVO.getParam().get("TO_M");
		requestVO.getParam().put("TO_TM", DateUtil.parseDate(toDt.concat( toH).concat(toM).concat("59"), "yyyyMMddHHmmss"));

		requestVO.setPagingRow(15);
		
		//tr 검색
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", requestVO.getSize());

        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
		return "/use/tr/use_tr_list";
	}
	
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		String fromDt = (String)requestVO.getParam().get("FROM_DT");
		String fromH = (String)requestVO.getParam().get("FROM_H");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		requestVO.getParam().put("FROM_TM", DateUtil.parseDate(fromDt.concat(fromH).concat(fromM).concat("00"), "yyyyMMddHHmmss"));
		
		String toDt = (String)requestVO.getParam().get("TO_DT");
		String toH = (String)requestVO.getParam().get("TO_H");
		String toM = (String)requestVO.getParam().get("TO_M");
		requestVO.getParam().put("TO_TM", DateUtil.parseDate(toDt.concat( toH).concat(toM).concat("59"), "yyyyMMddHHmmss"));
		
		//tr 검색
		List<Map<String, Object>> list = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", list.size());
		
		if(requestVO.getParam().get("SRVC_CD") != null && !requestVO.getParam().get("SRVC_CD").equals("")) {
			model.addAttribute("nm", "거래");
		} else {
			model.addAttribute("nm", "서비스");
		}

		model.addAttribute(Constants.RESULT, list);
		model.addAttribute(Constants.FILE_NAME, "서비스현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				String nm = (String)model.get("nm");
				
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("서비스현황"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "서비스 현황(".concat(nm).concat(")"));

				String[] titles1 = {nm, nm.concat("명"), "발생건수", "에러건수"};
				int[] width1 = {30, 50, 30, 30};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					createStringCell(row, c++, (String)data.get("CD"));
					createStringCell(row, c++, (String)data.get("NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
					createNumberCell(row, c++, ((Number)data.get("ERR_CNT")).longValue());
				}
			}
		};
	}
}
