package com.ibk.ivr.ca.system.policy.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.ibk.ivr.ca.system.policy.service.AnlysPlcyResultMngService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 알림 관리 controller
 * 
 * table : TBCA_ANLYS_PLCY (분석_정책)
 * 		   TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/policy", method=RequestMethod.POST)
public class AnlysPlcyResultMngController {

	@Autowired
	private AnlysPlcyResultMngService service;
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/system/policy/policy_result_index";
	}

	/**
	 * 분석 정책 설정 정보 리스트 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("system_policy", requestVO.getParam());
		
		String fromDt = (String)requestVO.getParam().get("FROM_DT");
		requestVO.getParam().put("FROM_TS",  DateUtil.parseDate(fromDt.concat("235959"), "yyyyMMddHHmmss", Calendar.DATE, -1));
		String toDt = (String)requestVO.getParam().get("TO_DT");
		requestVO.getParam().put("TO_TS", DateUtil.parseDate(toDt.concat("000000"), "yyyyMMddHHmmss", Calendar.DATE, 1));

		requestVO.setPagingRow(15);
		
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListForMng result vos count {}", requestVO.getSize());

        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
		return "/system/policy/policy_result_list";
	}
	
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		String fromDt = (String)requestVO.getParam().get("FROM_DT");
		requestVO.getParam().put("FROM_TS",  DateUtil.parseDate(fromDt.concat("235959"), "yyyyMMddHHmmss", Calendar.DATE, -1));
		String toDt = (String)requestVO.getParam().get("TO_DT");
		requestVO.getParam().put("TO_TS", DateUtil.parseDate(toDt.concat("000000"), "yyyyMMddHHmmss", Calendar.DATE, 1));
		
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", result.size());

		model.addAttribute(Constants.RESULT, result);
		model.addAttribute(Constants.FILE_NAME, "알림관리");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("알림발생현황"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "알림발생현황");

				String[] titles1 = {"알림명","알림발생일시","인입건수","비교건수","알림설정","초과","	SMS전송","E-mail전송"};
				int[] width1 = {50, 40, 20, 20, 20, 20, 20, 20};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					createStringCell(row, c++, (String)data.get("ANLYS_PLCY_NM"));
					createStringCell(row, c++, df.format((Date)data.get("OCCRRNC_DT")));
					Number occrrncCnt = (Number)data.get("OCCRRNC_CNT");
					createNumberCell(row, c++, occrrncCnt.longValue());
					Number cmprCnt = (Number)data.get("CMPR_CNT");
					createNumberCell(row, c++, cmprCnt.longValue());
					Number notiftnStdval = (Number)data.get("NOTIFTN_STDVAL");
					if(notiftnStdval != null)
						createStringCell(row, c++, notiftnStdval.toString().concat(" % 초과"));
					else
						createStringCell(row, c++, "-");

					createPercentCell(row, c++, ((occrrncCnt.doubleValue()-cmprCnt.doubleValue())/cmprCnt.doubleValue()));
					if(((Boolean)data.get("SMS_XMSN_ALTV")).booleanValue()) createStringCell(row, c++, "적용");
					else createStringCell(row, c++, "미적용");
					if(((Boolean)data.get("EMAIL_XMSN_ALTV")).booleanValue()) createStringCell(row, c++, "적용");
					else createStringCell(row, c++, "미적용");
				}
			}
		};
	}
}
