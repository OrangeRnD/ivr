package com.ibk.ivr.ca.ldin.callstatus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
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
import com.ibk.ivr.ca.ldin.callstatus.service.LdinCallStatusService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 인입총괄현황 controller
 * 
 * table : TBCA_MNBY_MEDA_CALL_STAT (월별_매체_콜_현황)
 *         TBCA_MLY_MEDA_CALL_STAT (분별_매체_콜_현황)
 * 		   TBCA_MNBY_CALL_KND_STAT (월별_콜_종류_현황)
 *         TBCA_MLY_CALL_KND_STAT (분별_콜_종류_현황)
 * 		   TBCA_MNBY_SRVC_END_STAT (월별_서비스_종료_현황)
 *         TBCA_MLY_SRVC_END_STAT (분별_서비스_종료_현황)
 * 		   TBCA_MNBY_LDIN_STAT (월별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 		   TBCA_MNBY_INPTH_LDIN_STAT (월별_인입경로_인입_현황)
 *         TBCA_MLY_INPTH_LDIN_STAT (분별_인입경로_인입_현황)
 * 		   TBCA_MNBY_USER_CALL_STAT (월별_이용자_콜_현황)
 *         TBCA_MLY_USER_CALL_STAT (분별_이용자_콜_현황)
 * 		   TBCA_MNBY_AGE_CALL_STAT (월별_연령_콜_현황)
 *         TBCA_MLY_AGE_CALL_STAT (분
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/ldin/callstatus", method=RequestMethod.POST)
public class LdinCallStatusController {
	
	@Autowired
	private LdinCallStatusService service;
	
	/**
	 * 인입 총괄 현황 index
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/ldin/callstatus/ldin_callstatus_index";
	}
	
	/**
	 * 매체별 인입현황
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/meda")
	public String selectListMedaCallStat(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
		String fromY = (String)requestVO.getParam().get("FROM_Y");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		String toY = (String)requestVO.getParam().get("TO_Y");
		String toM = (String)requestVO.getParam().get("TO_M");
		
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_callstatus_FROM_Y", fromY);
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_callstatus_FROM_M", fromM);
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_callstatus_TO_Y", toY);
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_callstatus_TO_M", toM);
		
		int fromYm = Integer.parseInt(fromY.concat(fromM));
		int toYm = Integer.parseInt(toY.concat(toM));
		int ym = Integer.parseInt(DateUtil.getDateString("yyyyMM"));
		if(ym >= fromYm && ym <= toYm)
			requestVO.getParam().put("CURRENT_DATE", DateUtil.getDateString("yyyyMMdd"));
		
		List<Map<String, Object>> list = this.service.selectListMedaCallStat(requestVO);
		if(log.isDebugEnabled())
			log.debug("ldinCallStatusService.selectList result count {}", list.size());

		model.addAttribute(Constants.RESULT, list.size() > 0 ? list : null);
		return "/ldin/callstatus/ldin_callstatus_meda_list";
	}
	
	/**
	 * ivr 이용현황
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/ivr")
	public String selectIvrList(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		String fromY = (String)requestVO.getParam().get("FROM_Y");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		String toY = (String)requestVO.getParam().get("TO_Y");
		String toM = (String)requestVO.getParam().get("TO_M");
		
		int fromYm = Integer.parseInt(fromY.concat(fromM));
		int toYm = Integer.parseInt(toY.concat(toM));
		int ym = Integer.parseInt(DateUtil.getDateString("yyyyMM"));
		if(ym >= fromYm && ym <= toYm)
			requestVO.getParam().put("CURRENT_DATE", DateUtil.getDateString("yyyyMMdd"));
		
		List<Map<String, Object>> list = this.service.selectIvrList(requestVO);
		if(log.isDebugEnabled()) {
			log.debug("ldinCallStatusService.selectIvrList result count {}", list.size());
		}
		model.addAttribute(Constants.RESULT, list.size() > 0 ? list : null);
		return "/ldin/callstatus/ldin_callstatus_ivr_list";
	}
	
	/**
	 * 이용자별, 연령별 콜 현황
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/stat")
	public @ResponseBody ResponseVO selectListStat(@ModelAttribute RequestVO requestVO) throws Exception {
		String fromY = (String)requestVO.getParam().get("FROM_Y");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		String toY = (String)requestVO.getParam().get("TO_Y");
		String toM = (String)requestVO.getParam().get("TO_M");
		
		int fromYm = Integer.parseInt(fromY.concat(fromM));
		int toYm = Integer.parseInt(toY.concat(toM));
		int ym = Integer.parseInt(DateUtil.getDateString("yyyyMM"));
		if(ym >= fromYm && ym <= toYm)
			requestVO.getParam().put("CURRENT_DATE", DateUtil.getDateString("yyyyMMdd"));
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> userTypeInfoMap = this.service.selectListUsrTypeInfo(requestVO);
		if(log.isDebugEnabled()) {
			log.debug("ldinCallStatusService.selectListUsrTypeInfo result count {}", userTypeInfoMap.size());
		}
		
		dataMap.put("user", userTypeInfoMap);
		
		List<Map<String, Object>> ageRangeInfoMap = this.service.selectListAgeRangeInfo(requestVO);
		if(log.isDebugEnabled()) {
			log.debug("ldinCallStatusService.selectListAgeRangeInfo result count {}", ageRangeInfoMap.size());
		}
		
		dataMap.put("age", ageRangeInfoMap);
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(dataMap);
		return responseVO;
	}
	
	/**
	 * 실시간 인입추이 엑셀다운로드
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		String fromY = (String)requestVO.getParam().get("FROM_Y");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		String toY = (String)requestVO.getParam().get("TO_Y");
		String toM = (String)requestVO.getParam().get("TO_M");
		
		int fromYm = Integer.parseInt(fromY.concat(fromM));
		int toYm = Integer.parseInt(toY.concat(toM));
		int ym = Integer.parseInt(DateUtil.getDateString("yyyyMM"));
		if(ym >= fromYm && ym <= toYm)
			requestVO.getParam().put("CURRENT_DATE", DateUtil.getDateString("yyyyMMdd"));
		
		List<Map<String, Object>> meda = this.service.selectListMedaCallStat(requestVO);
		model.addAttribute("meda", meda);
		
		List<Map<String, Object>> ivr = this.service.selectIvrList(requestVO);
		model.addAttribute("ivr", ivr);
		
		List<Map<String, Object>> userTypeInfoMap = this.service.selectListUsrTypeInfo(requestVO);
		model.addAttribute("user", userTypeInfoMap);
		
		List<Map<String, Object>> ageRangeInfoMap = this.service.selectListAgeRangeInfo(requestVO);
		model.addAttribute("age", ageRangeInfoMap);
		
		model.addAttribute(Constants.FILE_NAME, "인입총괄현황");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("매체별 인입 현황");
				sheet.setDefaultColumnWidth(15);
				
		        short r = 1;
				this.createTitle(sheet, r++, "매체별 인입 현황");

                StringBuilder sb = new StringBuilder("");
				String[] titles = {"년월", "총계", "휴대폰", "", "일반전화", "", "인터넷전화(070)", "", "해외", ""};
				int[] width = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
				this.createHeader(sheet, r++, titles, width);
				sheet.addMergedRegion(new CellRangeAddress(r-1, r-1, 2, 3));
				sheet.addMergedRegion(new CellRangeAddress(r-1, r-1, 4, 5));
				sheet.addMergedRegion(new CellRangeAddress(r-1, r-1, 6, 7));
				sheet.addMergedRegion(new CellRangeAddress(r-1, r-1, 8, 9));
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> medaList = (List<Map<String, Object>>)model.get("meda");
                short c = 0;
				for(Map<String, Object> data : medaList) {
					Row row = sheet.createRow(r++);
					c = 0;
					
	                String ym = (String)data.get("YM");
	                String year = ym.substring(0, 4);
	                String month = ym.substring(4);
	                sb.setLength(0);
	                sb.append(year);
	                sb.append("년 ");
                	sb.append(month);
                	sb.append("월");
					createStringCell(row, c++, sb.toString());
					
					long cnt1 = data.get("CNT_1") != null ? ((Number)data.get("CNT_1")).longValue() : 0;
					long cnt2 = data.get("CNT_2") != null ? ((Number)data.get("CNT_2")).longValue() : 0;
					long cnt3 = data.get("CNT_3") != null ? ((Number)data.get("CNT_3")).longValue() : 0;
					long cnt4 = data.get("CNT_4") != null ? ((Number)data.get("CNT_4")).longValue() : 0;
					
					long sum = cnt1 + cnt2 + cnt3 + cnt4;
					
					createNumberCell(row, c++, sum);
					createNumberCell(row, c++, cnt1);
					if(cnt1 == 0 || sum == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, cnt1*1.0/sum);
					
					createNumberCell(row, c++, cnt2);
					if(cnt2 == 0 || sum == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, cnt2*1.0/sum);
					
					createNumberCell(row, c++, cnt3);
					if(cnt3 == 0 || sum == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, cnt3*1.0/sum);
					
					createNumberCell(row, c++, cnt4);
					if(cnt4 == 0 || sum == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, cnt4*1.0/sum);
				}
				
				Sheet sheet2 = workbook.createSheet("IVR 현황");
				sheet2.setDefaultColumnWidth(15);
				
		        r = 1;
				this.createTitle(sheet2, r++, "IVR 현황");

				String[] titles2 = {"년월", "IVR 인입콜", "대표번호", "", "보이는 ARS", "", "말로 하는 ARS", "", "종료사유", "", "", "", "", ""};
				int[] width2 = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
				this.createHeader(sheet2, r++, titles2, width2);

				String[] titles2_1 = {"", "", "", "", "", "", "", "", "IVR 종료", "", "고객 종료", "", "상담원연결요청", ""};
				int[] width2_1 = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
				this.createHeader(sheet2, r++, titles2_1, width2_1);

				sheet2.addMergedRegion(new CellRangeAddress(r-2, r-1, 0, 0));
				sheet2.addMergedRegion(new CellRangeAddress(r-2, r-1, 1, 1));
				sheet2.addMergedRegion(new CellRangeAddress(r-2, r-1, 2, 3));
				sheet2.addMergedRegion(new CellRangeAddress(r-2, r-1, 4, 5));
				sheet2.addMergedRegion(new CellRangeAddress(r-2, r-1, 6, 7));
				sheet2.addMergedRegion(new CellRangeAddress(r-2, r-2, 8, 13));
				sheet2.addMergedRegion(new CellRangeAddress(r-1, r-1, 8, 9));
				sheet2.addMergedRegion(new CellRangeAddress(r-1, r-1, 10, 11));
				sheet2.addMergedRegion(new CellRangeAddress(r-1, r-1, 12, 13));
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> ivrList = (List<Map<String, Object>>)model.get("ivr");
				for(Map<String, Object> data : ivrList) {
					Row row = sheet2.createRow(r++);
					c = 0;

	                String ym = (String)data.get("YM");
	                String year = ym.substring(0, 4);
	                String month = ym.substring(4);
	                sb.setLength(0);
	                sb.append(year);
	                sb.append("년 ");
                	sb.append(month);
                	sb.append("월");
					createStringCell(row, c++, sb.toString());

					long callCnt = data.get("CALL_CNT") != null ? ((Number)data.get("CALL_CNT")).longValue() : 0;
					long pCallCnt = data.get("P_CALL_CNT") != null ? ((Number)data.get("P_CALL_CNT")).longValue() : 0;

					long ivr1 = data.get("IVR_1") != null ? ((Number)data.get("IVR_1")).longValue() : 0;
					long ivr2 = data.get("IVR_2") != null ? ((Number)data.get("IVR_2")).longValue() : 0;

					createNumberCell(row, c++, callCnt);
					createNumberCell(row, c++, pCallCnt);
					if(pCallCnt == 0 || callCnt == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, pCallCnt*1.0/callCnt);
					createNumberCell(row, c++, ivr1);
					if(ivr1 == 0 || callCnt == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, ivr1*1.0/callCnt);
					createNumberCell(row, c++, ivr2);
					if(ivr2 == 0 || callCnt == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, ivr2*1.0/callCnt);

					long end1 = data.get("END_1") != null ? ((Number)data.get("END_1")).longValue() : 0;
					long end2 = data.get("END_2") != null ? ((Number)data.get("END_2")).longValue() : 0;
					long end3 = data.get("END_3") != null ? ((Number)data.get("END_3")).longValue() : 0;
					
					long endSum = end1 + end2 + end3;

					createNumberCell(row, c++, end2);
					if(end2 == 0 || callCnt == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, end2*1.0/endSum);
					createNumberCell(row, c++, end1);
					if(end1 == 0 || callCnt == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, end1*1.0/endSum);
					createNumberCell(row, c++, end3);
					if(end3 == 0 || callCnt == 0)
						createPercentCell(row, c++, 0);
					else
						createPercentCell(row, c++, end3*1.0/endSum);
				}
				
				Sheet sheet3 = workbook.createSheet("개인,사업자 콜 현황");
				sheet3.setDefaultColumnWidth(15);
				
		        r = 1;
				this.createTitle(sheet3, r++, "개인,사업자 콜 현황");

				String[] titles3 = {"구분", "건수"};
				int[] width3 = {20, 20};
				this.createHeader(sheet3, r++, titles3, width3);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> userList = (List<Map<String, Object>>)model.get("user");
				for(Map<String, Object> data : userList) {
					Row row = sheet3.createRow(r++);
					c = 0;

					createStringCell(row, c++, (String)data.get("CD_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
				
				Sheet sheet4 = workbook.createSheet("연령대별 콜 현황");
				sheet4.setDefaultColumnWidth(15);
				
		        r = 1;
				this.createTitle(sheet4, r++, "연령대별 콜 현황");

				String[] titles4 = {"구분", "건수"};
				int[] width4 = {20, 20};
				this.createHeader(sheet4, r++, titles4, width4);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> ageList = (List<Map<String, Object>>)model.get("age");
				for(Map<String, Object> data : ageList) {
					Row row = sheet4.createRow(r++);
					c = 0;

					createStringCell(row, c++, (String)data.get("CD_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
			}
		};
	}
}
