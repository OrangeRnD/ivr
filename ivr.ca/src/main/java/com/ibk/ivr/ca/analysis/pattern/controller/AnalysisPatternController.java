package com.ibk.ivr.ca.analysis.pattern.controller;

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

import com.ibk.ivr.ca.analysis.pattern.service.AnalysisPatternService;
import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 서비스이용 고객패턴 분석 controller
 * 
 * table : TBCA_DLY_USER_DLNGPTRN_STAT (일별_이용자_거래패턴_현황)
 *         TBCA_DLY_USER_SRVC_STAT (일별_이용자_서비스_현황)
 *         TBCA_MLY_USER_SRVC_STAT (분별_이용자_서비스_현황)
 *         TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/analysis/pattern", method=RequestMethod.POST)
public class AnalysisPatternController {
	
	@Autowired
	private AnalysisPatternService service;
	
	/**
	 * 서비쇼이용 고객패턴 분석 index
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/analysis/pattern/analysis_pattern_index";
	}
	
	/**
	 * 기준 값 조회
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/value")
	public @ResponseBody ResponseVO selectListStdVal(@ModelAttribute RequestVO requestVO) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> result = service.getListStdVal(requestVO);
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 서비쇼이용 고객패턴 정보 조회
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody ResponseVO selectList(@ModelAttribute RequestVO requestVO, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("analysis_pattern", requestVO.getParam());
		
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> result = service.getList(requestVO);
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 서비쇼이용 총 이용자수
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user")
	public @ResponseBody ResponseVO selectTotalDlyLdinUserCnt(@ModelAttribute RequestVO requestVO) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		Long result = service.selectTotalDlyLdinUserCnt(requestVO);
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		//이용서비스를 최대 50개까지 가져오기
		requestVO.setLimit("50");
		
		List<Map<String, Object>> result = service.getList(requestVO);
		if(log.isDebugEnabled())
			log.debug("getList result count {}", result.size());

		model.addAttribute(Constants.RESULT, result);
		model.addAttribute(Constants.FILE_NAME, "서비스이용_고객패턴_분석");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> dataList = (List<Map<String, Object>>)model.get(Constants.RESULT);
				
				//패턴 이용자 수
				long ptnCnt = 0;
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("개인/사업자분포"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "개인/사업자 분포");

				String[] titles1 = {"구분", "이용자수"};
				int[] width1 = {50, 30};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				int i = 0;
				for(; i < dataList.size(); i++) {
					Map<String, Object> data = dataList.get(i);
					if(((Number)data.get("TYPE")).intValue() == 1) {
						Row row = sheet1.createRow(r++);
						c = 0;
						createStringCell(row, c++, (String)data.get("NM"));
						Number cnt = (Number)data.get("CNT");
						ptnCnt += cnt.longValue();
						createNumberCell(row, c++, cnt.longValue());
					} else
						break;
				}
				
				Sheet sheet2 = workbook.createSheet("연령대별 분포");
				sheet2.setDefaultColumnWidth(15);

		        r = 1;
		        createTitle(sheet2, r++, "연령대별 분포");

				String[] titles2 = {"구분", "이용자수"};
				int[] width2 = {50, 30};
		        createHeader(sheet2, r++, titles2, width2);
				
				c = 0;
				for(; i < dataList.size(); i++) {
					Map<String, Object> data = dataList.get(i);
					if(((Number)data.get("TYPE")).intValue() == 2) {
						Row row = sheet2.createRow(r++);
						c = 0;
						createStringCell(row, c++, (String)data.get("NM"));
						Number cnt = (Number)data.get("CNT");
						createNumberCell(row, c++, cnt.longValue());
					} else
						break;
				}
				
				Sheet sheet3 = workbook.createSheet("이용서비스");
				sheet3.setDefaultColumnWidth(15);

		        r = 1;
		        createTitle(sheet3, r++, "이용서비스");

				String[] titles3 = {"서비스명", "서비스건수", "서비스비율(서비스건수/패턴이용자수)"};
				int[] width3 = {100, 30, 50};
		        createHeader(sheet3, r++, titles3, width3);
				
                for(; i < dataList.size(); i++) {
					Map<String, Object> data = dataList.get(i);
					
					if(((Number)data.get("TYPE")).intValue() == 3) {
						Row row = sheet3.createRow(r++);
						c = 0;
						createStringCell(row, c++, (String)data.get("NM"));
						Number cnt = (Number)data.get("CNT");
						createNumberCell(row, c++, cnt.longValue());
						
						double rt = cnt.doubleValue()/ptnCnt;
						createPercentCell(row, c++, rt);
					} else
						break;
				}
			}
		};
	}
}
