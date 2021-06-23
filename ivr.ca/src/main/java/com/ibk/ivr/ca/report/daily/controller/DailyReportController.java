package com.ibk.ivr.ca.report.daily.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.mail.MailSenderService;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.report.daily.DailyReport;
import com.ibk.ivr.ca.report.daily.service.DailyReportService;

/**
 * 일일보고서 controller
 * 
 * https://knowm.org/open-source/xchart/xchart-example-code/
 * 
 * table : TBCA_DLY_MEDA_CALL_STAT (일별_매체_콜_현황)
 * 		   TBCA_ANLYS_PLCY (분석_정책)
 * 		   TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 		   TBCA_DLY_SRVC_STAT (일별_인입_현황)
 * 		   TBCA_DLY_LAST_SRVC_STAT
 * 		   TBCA_DLY_SRVC_END_STAT
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/22/2019
 */
@Controller
@RequestMapping(value = "/system/report", method=RequestMethod.POST)
public class DailyReportController extends DailyReport {
	
	@Autowired
	private DailyReportService service;
	
	@Autowired
	private MailSenderService mailService;
	
    @Value("#{propertyConfigurer['constants.image.path']}")
    private String imagePath; 
	
    @Value("#{propertyConfigurer['constants.image.server']}")
    private String server; 
	
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/system/report/daily_report_index";
	}
	
	@RequestMapping(value = "/mail")
	public @ResponseBody ResponseVO mail(@ModelAttribute RequestVO requestVO) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String dt = (String)requestVO.getParam().get("DT");
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.parseDate(dt, "yyyyMMdd"));
		result.put("DT", df.format(c.getTime()));
		
		String subject = DateUtil.getDateString(c.getTime(), "yyyy년 MM월 dd일");
		
		Date now = c.getTime();
		
		//TO_TS
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		requestVO.getParam().put("TO_TS", c.getTime());
		
		//FROM_TS
		c.add(Calendar.DATE, -2);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		requestVO.getParam().put("FROM_TS", c.getTime());

		//전일
		requestVO.getParam().put("P_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		result.put("P_DT", df.format(c.getTime()));
		
		//전주 PW_FROM_DT
		c.add(Calendar.DATE, -6);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		requestVO.getParam().put("PW_FROM_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		result.put("PW_FROM_DT", df.format(c.getTime()));

		//전주 PW_TO_DT
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		requestVO.getParam().put("PW_TO_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		result.put("PW_TO_DT", df.format(c.getTime()));
		
		//전월 PM_TO_DT
		c.setTime(now);
		c.set(Calendar.DATE, 0);
		requestVO.getParam().put("PM_TO_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		result.put("PM_TO_DT", df.format(c.getTime()));

		//전월 PM_FROM_DT
		c.set(Calendar.DATE, 1);
		requestVO.getParam().put("PM_FROM_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		result.put("PM_FROM_DT", df.format(c.getTime()));
		
		setMeda(service.selectListMedaStat(requestVO), result, imagePath, dt);
		setPlcy(service.selectListPlcyRslt(requestVO), result);
		setSrvc(service.selectListSrvc(requestVO), result, imagePath, dt);

		result.put("server", server);
		
		mailService.send((String)requestVO.getParam().get("EMP_NR"), (String)requestVO.getParam().get("NAME"), subject.concat(" 일일보고서"), "/mail/report.html", result);
		
		return new ResponseVO();
	}
	
	@RequestMapping(value = "/daily")
	public String selectListDaily(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String dt = (String)requestVO.getParam().get("DT");
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtil.parseDate(dt, "yyyyMMdd"));
		
		model.addAttribute("DT", df.format(c.getTime()));
		
		Date now = c.getTime();
		
		//TO_TS
		c.add(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		requestVO.getParam().put("TO_TS", c.getTime());
		
		//FROM_TS
		c.add(Calendar.DATE, -2);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		requestVO.getParam().put("FROM_TS", c.getTime());

		//전일
		requestVO.getParam().put("P_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("P_DT", df.format(c.getTime()));
		
		//전주 PW_FROM_DT
		c.add(Calendar.DATE, -6);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		requestVO.getParam().put("PW_FROM_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("PW_FROM_DT", df.format(c.getTime()));

		//전주 PW_TO_DT
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		requestVO.getParam().put("PW_TO_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("PW_TO_DT", df.format(c.getTime()));
		
		//전월 PM_TO_DT
		c.setTime(now);
		c.set(Calendar.DATE, 0);
		requestVO.getParam().put("PM_TO_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("PM_TO_DT", df.format(c.getTime()));

		//전월 PM_FROM_DT
		c.set(Calendar.DATE, 1);
		requestVO.getParam().put("PM_FROM_DT", DateUtil.yyyyMMdd.format(c.getTime()));
		model.addAttribute("PM_FROM_DT", df.format(c.getTime()));
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		setMeda(service.selectListMedaStat(requestVO), result, imagePath, dt);
		setPlcy(service.selectListPlcyRslt(requestVO), result);
		setSrvc(service.selectListSrvc(requestVO), result, imagePath, dt);
		
		model.addAllAttributes(result);

		return "/system/report/daily_report_list";
	}
}
