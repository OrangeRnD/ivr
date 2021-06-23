package com.ibk.ivr.ca.use.service.controller;

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
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.use.service.service.UseServiceStatusService;

import lombok.extern.slf4j.Slf4j;

/**
 * 서비스 이용현황  Controller
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 *         TBCA_DLY_LAST_SRVC_STAT (일별_마지막_서비스_현황)
 *         TBCA_MLY_LAST_SRVC_STAT (분별_마지막_서비스_현황)
 *         TBCA_DLY_TR_TIMEOUT_STAT (일별_TR_TIMEOUT_현황)
 *         TBCA_MLY_TR_TIMEOUT_STAT (분별_TR_TIMEOUT_현황)
 *         
 *         TBCA_30MLY_SRVC_STAT (30분별_서비스_현황)
 *         
 * 		   TBCA_DLY_SRVC_USER_STAT (일별_서비스_이용자_현황)
 * 		   TBCA_MLY_SRVC_USER_STAT (분별_서비스_이용자_현황)
 * 
 *         TBCA_DLY_SRVC_AGE_STAT (일별_서비스_연령_현황)
 *         TBCA_MLY_SRVC_AGE_STAT (분별_서비스_연령_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/23/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/use/service", method=RequestMethod.POST)
public class UseServiceStatusController {
	
	@Autowired
	private UseServiceStatusService service;
	
	/**
	 * 서비스 이용현황 index 로 서비스, 마지막 서비스, 상담원 연결, time-out top-10 조회
	 * default 로 금주 데이타 조회
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/use/service/service_status_index";
	}

	/**
	 * 서비스, 마지막 서비스, 상담원 연결, time-out top-10 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody ResponseVO selectList(@ModelAttribute RequestVO requestVO, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("use_service", requestVO.getParam());
		
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", requestVO.getSize());

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 당일 서비스 Top-5 시간별 추이
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/top5")
	public @ResponseBody ResponseVO selectListThirtyMlySrvcTop5() throws Exception {
		List<Map<String, Object>> result = service.selectListThirtyMlySrvcTop5(DateUtil.getDateString("yyyyMMdd"));
		if(log.isDebugEnabled())
			log.debug("selectListThirtyMlySrvcTop5 result count {}", result.toString());
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 이용자 구분 서비스 이용현황 TOP-10
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/user")
	public @ResponseBody ResponseVO selectListSrvcUserStat(@ModelAttribute RequestVO requestVO) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> result = service.getListSrvcUserStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListSrvcUserStat result count {}", requestVO.getSize());

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 연령별 서비스 이용현황 TOP-10
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/age")
	public @ResponseBody ResponseVO selectListSrvcAgeStat(@ModelAttribute RequestVO requestVO) throws Exception {
		int fromDt = Integer.parseInt((String)requestVO.getParam().get("FROM_DT"));
		int toDt = Integer.parseInt((String)requestVO.getParam().get("TO_DT"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt && curDt <= toDt)
			requestVO.getParam().put("CURRENT_DATE", currentDate);
		
		List<Map<String, Object>> result = service.getListSrvcAgeStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListSrvcAgeStat result count {}", requestVO.getSize());

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
		
        List<Map<String, Object>> srvcList = service.selectExcelForSrvc(requestVO);
		model.addAttribute("srvc", srvcList);

        List<Map<String, Object>> lstSrvcList = service.selectExcelForLstSrvc(requestVO);
		model.addAttribute("lst", lstSrvcList);

        List<Map<String, Object>> cnslrList = service.selectExcelForCnslrConn(requestVO);
		model.addAttribute("cnslr", cnslrList);

        List<Map<String, Object>> timeoutList = service.selectExcelForTimeout(requestVO);
		model.addAttribute("timeout", timeoutList);

        List<Map<String, Object>> userList = service.selectExcelSrvcUserStat(requestVO);
		model.addAttribute("user", userList);

        List<Map<String, Object>> ageList = service.selectExcelSrvcAgeStat(requestVO);
		model.addAttribute("age", ageList);
		
		/**
		 * 당일 서비스 Top5 시간별 추이
		 */
		List<Map<String, Object>> top5 = service.selectListThirtyMlySrvcTop5(DateUtil.getDateString("yyyyMMdd"));
		model.addAttribute("top5", top5);
		
		model.addAttribute(Constants.FILE_NAME, "서비스이용현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> srvc = (List<Map<String, Object>>)model.get("srvc");
				createSrvc(srvc, "서비스");

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> lst = (List<Map<String, Object>>)model.get("lst");
				createSrvc(lst, "마지막 서비스");

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> cnslr = (List<Map<String, Object>>)model.get("cnslr");
				createSrvc(cnslr, "상담원 연결");

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> timeout = (List<Map<String, Object>>)model.get("timeout");
				createTr(timeout, "Time-out Top");

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> user = (List<Map<String, Object>>)model.get("user");
				createUser(user);

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> age = (List<Map<String, Object>>)model.get("age");
				createAge(age);

				@SuppressWarnings("unchecked")
				List<Map<String, Object>> top5 = (List<Map<String, Object>>)model.get("top5");
				createTop5(top5);
			}
			
			private void createAge(List<Map<String, Object>> list) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("연령별 서비스"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "연령별 서비스");

				String[] titles1 = {"일자", "서비스코드", "서비스명", "20대 이하", "30대", "40대", "50대", "60대", "70대 이상" };
				int[] width1 = {20, 20, 30, 15, 15, 15, 15, 15, 15};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					createStringCell(row, c++, (String)data.get("SRVC_CD"));
					createStringCell(row, c++, (String)data.get("SRVC_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT2")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT3")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT4")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT5")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT6")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT7")).longValue());
				}
			}
			
			private void createUser(List<Map<String, Object>> list) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("개인,사업자별 서비스"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "개인/사업자별 서비스");

				String[] titles1 = {"일자", "서비스코드", "서비스명", "개인", "사업자", "미인증" };
				int[] width1 = {20, 20, 30, 15, 15, 15};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					createStringCell(row, c++, (String)data.get("SRVC_CD"));
					createStringCell(row, c++, (String)data.get("SRVC_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT1")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT2")).longValue());
					createNumberCell(row, c++, ((Number)data.get("CNT3")).longValue());
				}
			}
			
			private void createTop5(List<Map<String, Object>> list) throws Exception {
				Sheet sheet4 = workbook.createSheet("서비스 Top5 시간별 추이");
				sheet4.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet4, r++, DateUtil.getDateString("yyyy년 MM월 dd일").concat(" 서비스 Top5 시간별 추이"));
				
				String[] titles4 = {"시간", "", "", "", "", ""};
				int[] width4 = {30, 30, 30, 30, 30, 30};
				
				String stdHm = "";
				int titleIndex = 1;
				for(Map<String, Object> data : list) {
					if(!data.get("HM").equals(stdHm)) {
						if(titleIndex != 1) break;
						
						titles4[titleIndex++] = (String)data.get("SRVC_NM");
						stdHm = (String)data.get("HM");
					} else {
						if(titleIndex < 6)
							titles4[titleIndex++] = (String)data.get("SRVC_NM");
					}
				}
				
		        createHeader(sheet4, r++, titles4, width4);
		        
		        stdHm = "";
		        Row row = null;
				short c = 0;
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
			
			private void createSrvc(List<Map<String, Object>> list, String title) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName(title));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, title);

				String[] titles1 = {"일자", "서비스코드", "서비스명", "건수" };
				int[] width1 = {20, 20, 30, 15};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					createStringCell(row, c++, (String)data.get("SRVC_CD"));
					createStringCell(row, c++, (String)data.get("SRVC_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
			}
			
			private void createTr(List<Map<String, Object>> list, String title) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName(title));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, title);

				String[] titles1 = {"일자", "거래코드", "거래명", "건수" };
				int[] width1 = {20, 20, 30, 15};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					createStringCell(row, c++, (String)data.get("TRAN_CD"));
					createStringCell(row, c++, (String)data.get("TRAN_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
			}
		};
	}
}
