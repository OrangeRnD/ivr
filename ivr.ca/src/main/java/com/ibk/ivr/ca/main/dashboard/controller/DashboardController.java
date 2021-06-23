package com.ibk.ivr.ca.main.dashboard.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.ldin.status.service.LdinStatusService;
import com.ibk.ivr.ca.main.dashboard.service.DashboardService;

import lombok.extern.slf4j.Slf4j;

/**
 * 메인 대시보드 controller
 * 
 * table : TBCA_MLY_INPTH_LDIN_STAT (분별_인입경로_인입_현황)
 *         TBCA_MLY_CALL_KND_STAT (분별_콜_종류_현황)
 *		   TBCA_MLY_LDIN_STAT (분별_인입_현황)
 *		   TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 		   TBCA_ANLYS_PLCY (분석_정책)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/25/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/main", method = RequestMethod.POST)
public class DashboardController {

	@Autowired
	private LdinStatusService ldinStatusService;

	@Autowired
	private DashboardService dashboardService;

	/**
	 * 대시보드 임시 추리
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		RequestVO requestVO = new RequestVO();
		Calendar c = Calendar.getInstance();
		requestVO.getParam().put("CURRENT_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		requestVO.getParam().put("HM", DateUtil.getDateString(c.getTime(), "HHmm"));
		c.add(Calendar.DATE, -1);
		requestVO.getParam().put("DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("DT", requestVO.getParam().get("DT"));

		//인입현황(당일, 전일, 인입경로별)
		List<Map<String, Object>> result = ldinStatusService.selectListLdinStat(requestVO);
		for(Map<String, Object> data : result) {
			if("C".equals(data.get("INPTH_SECD"))) {
				model.addAttribute("CUR", data);
			} else if("P".equals(data.get("INPTH_SECD"))) {
				model.addAttribute("PRE", data);
			}
		}
		if(log.isDebugEnabled())
			log.debug("selectListLdinStat result count {}", result.size());

		model.addAttribute(Constants.RESULT, result);

		return "/index/main/dashboard/dashboard_index";
	}
	
	/**
	 * ARS 구분별 인입 / 거래 대비 에러 비율
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stat")
	public @ResponseBody ResponseVO selectListArs() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>(2);
		
		String dt = DateUtil.getDateString("yyyyMMdd");
		List<Map<String, Object>> arsList = dashboardService.selectListArs(dt);
		result.put("ars", arsList);

		Map<String, Object> err = dashboardService.selectTrErr(dt);
		result.put("err", err);
		
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
	
	/**
	 * 최신 알림 발생 목록 3건 조회
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/plcyrslt")
	public String selectListPlcyRslt(Model model) throws Exception {
		List<Map<String, Object>> result = dashboardService.selectListPlcyRslt();
		if(log.isDebugEnabled())
			log.debug("selectListPlcyRslt result vos count {}", result.size());

		model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
		return "/main/dashboard/policy_result_list";
	}
}
