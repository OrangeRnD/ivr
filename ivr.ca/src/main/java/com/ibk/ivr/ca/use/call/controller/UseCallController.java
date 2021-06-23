package com.ibk.ivr.ca.use.call.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.util.FormatUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.use.call.service.UseCallService;

import lombok.extern.slf4j.Slf4j;

/**
 * 고객별 콜 검색 controller
 * 
 * table : TBCA_IVR_LOG (IVR_로그)
 *          TBCA_IVR_TR_RCPTN_LOG (IVR_TR_수신_로그)
 *          TBCA_DLY_USER_SRVC_STAT (일별_이용자_서비스_현황)
 *          TBCA_MLY_USER_SRVC_STAT (분별_이용자_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/use/call", method=RequestMethod.POST)
public class UseCallController {

	@Autowired
	private UseCallService service;

	/**
	 * 고객콜 검색 index
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		return "/index/use/call/use_call_index";
	}

	/**
	 * 고객콜 검색 index
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/popup", method=RequestMethod.GET)
	public String popup(Model model) throws Exception {
		return "/popup/use/call/use_call_index";
	}

	/**
	 * 고객콜 검색
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {	
    	((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("use_call", requestVO.getParam());
    	
		String fromDt = (String)requestVO.getParam().get("FROM_DT");
		String fromH = (String)requestVO.getParam().get("FROM_H");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		requestVO.getParam().put("FROM_TM", DateUtil.parseDate(fromDt.concat(fromH).concat(fromM).concat("00"), "yyyyMMddHHmmss"));
		
		String toDt = (String)requestVO.getParam().get("TO_DT");
		String toH = (String)requestVO.getParam().get("TO_H");
		String toM = (String)requestVO.getParam().get("TO_M");
		requestVO.getParam().put("TO_TM", DateUtil.parseDate(toDt.concat( toH).concat(toM).concat("59"), "yyyyMMddHHmmss"));
		
		requestVO.setPagingRow(15);
		
		//고객콜
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", requestVO.getSize());

        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
		return "/use/call/use_call_list";
	}

	/**
	 * 평균이용시간
	 * @param requestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/time")
	public @ResponseBody ResponseVO selectListAvgUseTm(@ModelAttribute RequestVO requestVO) throws Exception {
		String fromDt = (String)requestVO.getParam().get("FROM_DT");
		String fromH = (String)requestVO.getParam().get("FROM_H");
		String fromM = (String)requestVO.getParam().get("FROM_M");
		requestVO.getParam().put("FROM_TM", DateUtil.parseDate(fromDt.concat(fromH).concat(fromM).concat("00"), "yyyyMMddHHmmss"));
		
		String toDt = (String)requestVO.getParam().get("TO_DT");
		String toH = (String)requestVO.getParam().get("TO_H");
		String toM = (String)requestVO.getParam().get("TO_M");
		requestVO.getParam().put("TO_TM", DateUtil.parseDate(toDt.concat( toH).concat(toM).concat("59"), "yyyyMMddHHmmss"));
		
		Integer avgUseTime = service.selectListAvgUseTm(requestVO);
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(avgUseTime == null ? 0 : avgUseTime.intValue());
		return responseVO;
	}

	/**
	 * 자주 쓰는 메뉴 조회
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/srvc")
	public String selectListFrequentlyUsedSrvc(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		//최근 3개월 검색
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("SRVC_TO_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		c.add(Calendar.MONTH, -3);
		requestVO.getParam().put("SRVC_FROM_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		List<Map<String, Object>> result = service.selectListFrequentlyUsedSrvc(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListFrequentlyUsedSrvc1 result vos count {}", requestVO.getSize());
		//데이터가 없는 경우 최근 6개월 검색
		if(result.size() == 0) {
			c.add(Calendar.MONTH, -3);
			requestVO.getParam().put("SRVC_FROM_DT", DateUtil.yyyyMMdd.format(c.getTime()));
			result = service.selectListFrequentlyUsedSrvc(requestVO);
			if(log.isDebugEnabled())
				log.debug("selectListFrequentlyUsedSrvc2 result vos count {}", requestVO.getSize());
		}
		model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
		return "/use/call/use_call_srvc_list";
	}

	/**
	 * 선택 콜 거래 조회
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tr")
	public String selectListTr(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		List<Map<String, Object>> result = service.selectListTr(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListTr result vos count {}", requestVO.getSize());

		model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
		return "/use/call/use_call_tr_list";
	}

	/**
	 * 선택 콜 에러 조회
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/err")
	public String selectListErr(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		List<Map<String, Object>> result = service.selectListErr(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectListErr result vos count {}", requestVO.getSize());

		model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
		return "/use/call/use_call_err_list";
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
		
		//고객콜
		List<Map<String, Object>> list = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result vos count {}", list.size());
		
		model.addAttribute(Constants.RESULT, list);
		model.addAttribute(Constants.FILE_NAME, "고객콜");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("고객콜"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "고객콜");

				String[] titles1 = {"인입일시", "종료일시", "전화번호", "생년월일", "사업자번호", "인입구분", "상담원연결횟수", "종료사유", "마지막서비스", "결과", "이용시간"};
				int[] width1 = {30, 30, 30, 30, 30, 30, 30, 50, 50, 30, 30};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					Date ldinDt = (Date)data.get("LDIN_DT");
					if(ldinDt != null)
						createStringCell(row, c++, DateUtil.getDateString(ldinDt, "yyyy-MM-dd HH:mm:ss"));
					else
						createStringCell(row, c++, "-");
					Date endDt = (Date)data.get("END_DT");
					if(endDt != null)
						createStringCell(row, c++, DateUtil.getDateString(endDt, "yyyy-MM-dd HH:mm:ss"));
					else
						createStringCell(row, c++, "-");
					createStringCell(row, c++, FormatUtil.formatPhoneMasking((String)data.get("TELNO")));
					if("1".equals(data.get("USER_SECD"))) {
						createStringCell(row, c++, (String)data.get("RGSNO"));
						createStringCell(row, c++, "-");
					} else if("2".equals(data.get("USER_SECD"))) {
						createStringCell(row, c++, "-");
						createStringCell(row, c++, FormatUtil.formatBizNo((String)data.get("RGSNO")));
					} else {
						createStringCell(row, c++, "-");
						createStringCell(row, c++, "-");
					}
					createStringCell(row, c++, (String)data.get("INPTH_NM"));
					if("100".equals(data.get("END_RSN_SECD"))) {
						createStringCell(row, c++, "Y");
					} else {
						createStringCell(row, c++, "N");
					}
					createStringCell(row, c++, (String)data.get("END_RSN_NM"));
					createStringCell(row, c++, (String)data.get("SRVC_NM"));
					if(data.get("ERR_CNT") != null)
						createStringCell(row, c++, "실패");
					else
						createStringCell(row, c++, "성공");
					Number useTm = (Number)data.get("USE_TM");
					if(useTm != null)
						createStringCell(row, c++, useTm.toString().concat("초"));
					else
						createStringCell(row, c++, "-");
				}
			}
		};
	}
}
