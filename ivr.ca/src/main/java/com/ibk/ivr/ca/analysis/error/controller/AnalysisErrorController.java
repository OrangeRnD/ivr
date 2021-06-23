package com.ibk.ivr.ca.analysis.error.controller;

import java.util.HashMap;
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

import com.ibk.ivr.ca.analysis.error.service.AnalysisErrorService;
import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 에러코드/거래 현황 controller
 * 
 * table : TBCA_DLY_ERR_STAT (일별_에러_현황)
 *         TBCA_MLY_ERR_STAT (분별_에러_현황)
 *         TBCA_DLY_TR_ERR_STAT (일별_TR_에러_현황)
 *         TBCA_MLY_TR_ERR_STAT (분별_TR_에러_현황)
 *         TBCA_30MLY_ERR_STAT (30분별_에러_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/analysis/error", method=RequestMethod.POST)
public class AnalysisErrorController {
	
	@Autowired
	private AnalysisErrorService service;
	
	/**
	 * 에러코드 현황 index
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/analysis/error/analysis_error_index";
	}
	
	/**
	 * 에러코드 현황 조회
	 * 
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stat")
	public @ResponseBody ResponseVO selectListStat(@ModelAttribute RequestVO requestVO, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("analysis_error", requestVO.getParam());
		
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		Map<String, Object> result = new HashMap<String, Object>(3);
		
		//에러코드 누계
		Long errCnt = service.selectTotalErrCnt(requestVO);
		result.put("ERR_CNT", errCnt == null ? 0 : errCnt.longValue());
		
		//에러코드 top5
		List<Map<String, Object>> errTop5 = service.selectListErrTop5(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListErrTop5 result vos count {}", errTop5.size());
		result.put("ERR_LIST", errTop5);

		//에러연계 거래코드 top5
		List<Map<String, Object>> trTop5 = service.selectListTrByErrTop5(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListTrByErrTop5 result vos count {}", trTop5.size());
		result.put("TR_LIST", trTop5);
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 거래연계 에러 리스트 조회
	 * 
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/err")
	public String selectListErrBySrvc(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> list = service.selectListErrBySrvc(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListErrBySrvc result vos count {}", list.size());
		
		model.addAttribute(Constants.RESULT, list.size() == 0 ? null : list);
		return "/analysis/error/analysis_error_list";
	}
	
	/**
	 * 당일 에러코드 top5 시간별 추이
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tr")
	public @ResponseBody ResponseVO selectListThirtyMlyErrTop5() throws Exception {
		//현재일자
		List<Map<String, Object>> list = service.selectListThirtyMlyErrTop5(DateUtil.getDateString("yyyyMMdd"));
		if(log.isDebugEnabled())
			log.debug("selectListThirtyMlyErrTop5 result vos count {}", list.size());
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(list);
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

		//에러코드 top5
		List<Map<String, Object>> errTop5 = service.selectListErrTop5(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListErrTop5 result vos count {}", errTop5.size());

		//에러연계 거래코드 top5
		List<Map<String, Object>> trTop5 = service.selectListTrByErrTop5(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListTrByErrTop5 result vos count {}", trTop5.size());
		
		//거래연계 에러 리스트 조회
		List<Map<String, Object>> errList = service.selectListErrBySrvc(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListErrBySrvc result vos count {}", errList.size());
		
		//당일 에러코드 top5 시간별 추이
		List<Map<String, Object>> list = service.selectListThirtyMlyErrTop5(DateUtil.getDateString("yyyyMMdd"));
		if(log.isDebugEnabled())
			log.debug("selectListThirtyMlyErrTop5 result vos count {}", list.size());

		model.addAttribute("errTop5", errTop5);
		model.addAttribute("trTop5", trTop5);
		model.addAttribute("errList", errList);
		model.addAttribute(Constants.RESULT, list);
		
		model.addAttribute(Constants.FILE_NAME, "에러코드현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("에러코드"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "에러코드");

				String[] titles1 = {"에러코드", "에러건수", "에러메시지"};
				int[] width1 = {30, 30, 200};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> errTop5 = (List<Map<String, Object>>)model.get("errTop5");
				for(Map<String, Object> data : errTop5) {
					Row row = sheet1.createRow(r++);
					c = 0;
					createStringCell(row, c++, (String)data.get("ERR_CD"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
					createStringCell(row, c++, (String)data.get("ERR_CN"));
				}
				
				Sheet sheet2 = workbook.createSheet("에러코드연계거래");
				sheet2.setDefaultColumnWidth(15);

		        r = 1;
		        createTitle(sheet2, r++, "에러코드연계거래");

				String[] titles2 = {"서비스(거래)ID", "서비스(거래)명", "에러건수"};
				int[] width2 = {30, 50, 30};
		        createHeader(sheet2, r++, titles2, width2);

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> trTop5 = (List<Map<String, Object>>)model.get("trTop5");
				c = 0;
				for(Map<String, Object> data : trTop5) {
					Row row = sheet2.createRow(r++);
					c = 0;
					createStringCell(row, c++, (String)data.get("TRAN_CD"));
					createStringCell(row, c++, (String)data.get("TRAN_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
				
				Sheet sheet3 = workbook.createSheet("에러리스트");
				sheet3.setDefaultColumnWidth(15);

		        r = 1;
		        createTitle(sheet3, r++, "에러");

				String[] titles3 = {"에러코드", "서비스(거래)ID", "서비스(거래)명", "에러건수", "에러메시지"};
				int[] width3 = {30, 30, 50, 30, 100};
		        createHeader(sheet3, r++, titles3, width3);

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> errList = (List<Map<String, Object>>)model.get("errList");
				for(Map<String, Object> data : errList) {
					Row row = sheet3.createRow(r++);
					c = 0;
					createStringCell(row, c++, (String)data.get("ERR_CD"));
					createStringCell(row, c++, (String)data.get("TRAN_CD"));
					createStringCell(row, c++, (String)data.get("TRAN_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
					createStringCell(row, c++, (String)data.get("ERR_CN"));
				}
				
				///////////////////////////
				Sheet sheet4 = workbook.createSheet("에러코드 시간별 추이");
				sheet4.setDefaultColumnWidth(15);

		        r = 1;
		        createTitle(sheet4, r++, DateUtil.getDateString("yyyy년 MM월 dd일").concat(" 에러코드 시간별 추이"));
				
				String[] titles4 = {"시간", "", "", "", "", ""};
				int[] width4 = {30, 30, 30, 30, 30, 30};
				
				String stdHm = "";
				int titleIndex = 1;
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
				for(Map<String, Object> data : list) {
					if(!data.get("HM").equals(stdHm)) {
						if(titleIndex != 1) break;
						
						titles4[titleIndex++] = (String)data.get("ERR_CD");
						stdHm = (String)data.get("HM");
					} else {
						if(titleIndex < 6)
							titles4[titleIndex++] = (String)data.get("ERR_CD");
					}
				}
				
		        createHeader(sheet4, r++, titles4, width4);
		        
		        stdHm = "";
		        Row row = null;
		        StringBuilder sb = new StringBuilder("");
				for(Map<String, Object> data : list) {
					String hm = (String)data.get("HM");
					if(!stdHm.equals(hm)) {
						stdHm = hm;
						row = sheet4.createRow(r++);
						c = 0;
						sb.setLength(0);
						sb.append(hm.substring(0, 2));
						sb.append("시 ");
						sb.append(hm.substring(2));
						sb.append("분");
						createStringCell(row, c++, sb.toString());
					}
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
			}
		};
	}
}
