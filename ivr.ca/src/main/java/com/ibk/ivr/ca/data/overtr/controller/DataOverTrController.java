package com.ibk.ivr.ca.data.overtr.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.FormatUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.data.overtr.service.DataOverTrService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 이상거래 고객 분석 controller
 * 
 * table : TBCA_DLY_USER_DLNGPTRN_STAT (일별_이용자_거래패턴_현황)
 *         TBCA_DLY_LDIN_STAT (일별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/08/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/data/overtr", method=RequestMethod.POST)
public class DataOverTrController {
	
	@Autowired
	private DataOverTrService service;
	
	/**
	 * 서비쇼이용 고객패턴 분석 index
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/data/overtr/data_overtr_index";
	}
	
	/**
	 * 기준 값 조회
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/value")
	public @ResponseBody ResponseVO selectListStdVal(@ModelAttribute RequestVO requestVO) throws Exception {
		List<Map<String, Object>> result = service.getListStdVal(requestVO);
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 패턴 적용 고객 리스트 조회
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("data_overtr", requestVO.getParam());
		
		requestVO.setPagingRow(15);
		
		List<Map<String, Object>> result = service.selectList(requestVO);
		
        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
		return "/data/overtr/data_overtr_list";
	}
	
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("getList result count {}", result.size());

		model.addAttribute(Constants.RESULT, result);
		model.addAttribute(Constants.FILE_NAME, "이상거래_고객_분석");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)model.get(Constants.RESULT);
				
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("패턴 적용 고객 목록"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "패턴 적용 고객 목록");

				String[] titles1 = {"전화번호", "생년월일", "사업자번호", "콜 이용 건수", "콜당 오류 발생 비율", "오류 발생 누계 건수", "상담원 연결 패턴"};
				int[] width1 = {50, 50, 50, 30, 30, 30, 30};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				for(Map<String, Object> data : dataList) {
					Row row = sheet1.createRow(r++);
					c = 0;
					
					createStringCell(row, c++, FormatUtil.formatPhoneMasking((String)data.get("TELNO")));
					
					Object userSecd = data.get("USER_SECD");
					if("1".equals(userSecd)) {
						createStringCell(row, c++, (String)data.get("RGSNO"));
						createStringCell(row, c++, "");
					} else if("2".equals(userSecd)) {
						createStringCell(row, c++, "");
						createStringCell(row, c++, FormatUtil.formatBizNo((String)data.get("RGSNO")));
					} else {
						createStringCell(row, c++, "");
						createStringCell(row, c++, "");
					}
					
					Number CALL_CNT = (Number)data.get("CALL_CNT");
					Number ERR_CNT = (Number)data.get("ERR_CNT");
					createNumberCell(row, c++, CALL_CNT.longValue());
					if(ERR_CNT.longValue() <= 0 || CALL_CNT.longValue() <= 0) {
						createNumberCell(row, c++, 0);
					} else {
						createPercentCell(row, c++, ERR_CNT.doubleValue()/CALL_CNT.doubleValue());
					}
					createNumberCell(row, c++, ERR_CNT.longValue());
					createNumberCell(row, c++, ((Number)data.get("CNSLR_CONN_CNT")).longValue());
				}
			}
		};
	}
}
