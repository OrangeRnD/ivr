package com.ibk.ivr.ca.report.daily.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ibk.ivr.ca.common.mail.MailSenderService;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.report.daily.DailyReport;
import com.ibk.ivr.ca.report.daily.service.DailyReportService;
import com.ibk.ivr.ca.report.daily.vo.RptNotiftnHistVO;
import com.ibk.ivr.ca.system.usr.service.UsrService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DailyReportTask extends DailyReport {
	
	@Autowired
	private DailyReportService service;
	
	@Autowired
	private UsrService usrService;
	
	@Autowired
	private MailSenderService mailService;
	
    @Value("#{propertyConfigurer['constants.image.path']}")
    private String imagePath; 
	
    @Value("#{propertyConfigurer['constants.image.server']}")
    private String server; 
	
	public void task() {
		log.info("DailyReportTask start -------------------------------");
		
		RequestVO requestVO = new RequestVO();
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		result.put("DT", df.format(c.getTime()));
		
		String currentDt = DateUtil.yyyyMMdd.format(c.getTime());
		requestVO.getParam().put("DT", currentDt);
		
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
		
		try {
			setMeda(service.selectListMedaStat(requestVO), result, imagePath, currentDt);
			setPlcy(service.selectListPlcyRslt(requestVO), result);
			setSrvc(service.selectListSrvc(requestVO), result, imagePath, currentDt);
			
			result.put("server", server);
			
			List<UsrVO> usrList = usrService.selectListForReport(currentDt);
			for(UsrVO usr : usrList) {
				try {
					//insert history
					RptNotiftnHistVO rptNotiftnHistVO = new RptNotiftnHistVO();
					rptNotiftnHistVO.setDt(currentDt);
					rptNotiftnHistVO.setEmpNr(usr.getEmpNr());
					rptNotiftnHistVO.setOprtrId(0);
					rptNotiftnHistVO.setPrcsDt(now);
					rptNotiftnHistVO.setSndngAltv(0);
					service.insertRptNotiftnHist(rptNotiftnHistVO);
					
					mailService.send(usr.getEmpNr(), usr.getEmpNm(), subject.concat(" 일일보고서"), "/mail/report.html", result);
					
					rptNotiftnHistVO.setSndngAltv(1);
					service.updateRptNotiftnHistForSuccess(rptNotiftnHistVO);
				} catch(Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("DailyReportTask end -------------------------------");
	}
}
