package com.ibk.ivr.ca.report.daily.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.report.daily.DailyReport;
import com.ibk.ivr.ca.report.daily.service.DailyReportService;

import lombok.extern.slf4j.Slf4j;

/**
 * 리포트 이미지 생성 처리
 * 
 * 리포트 메일 발송이 '서버1'에서만 발송 되어 '서버1'에서만 리포트 이미지가 생성됨
 * 발송 후 해당 이미지가 해당 '서버1' 에만 존재하여 '서버2'로 접속되는 경우 해당 이미지가 보이지 않음으로 메일 발송 시 동일 시간에 이미지 생성 
 */
@Slf4j
public class DailyReportImageTask extends DailyReport {
	
	@Autowired
	private DailyReportService service;
	
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
			setSrvc(service.selectListSrvc(requestVO), result, imagePath, currentDt);
		} catch (Exception e) {
		}
		log.info("DailyReportTask end -------------------------------");
	}
}
