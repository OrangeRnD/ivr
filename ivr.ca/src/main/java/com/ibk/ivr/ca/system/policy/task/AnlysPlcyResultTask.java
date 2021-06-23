package com.ibk.ivr.ca.system.policy.task;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibk.ivr.ca.common.mail.MailSenderService;
import com.ibk.ivr.ca.common.sms.SmsSenderService;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.service.AnlysPlcyRsltNotiftnService;
import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.vo.AnlysPlcyRsltNotiftnVO;
import com.ibk.ivr.ca.system.policy.service.AnlysPlcyResultMngService;
import com.ibk.ivr.ca.system.usr.service.UsrService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnlysPlcyResultTask {

	@Autowired
	private AnlysPlcyResultMngService service;
	
	@Autowired
	private UsrService usrService;
	
	@Autowired
	private AnlysPlcyRsltNotiftnService anlysPlcyRsltNotiftnService;
	
	@Autowired
	private MailSenderService mailService;
	
	@Autowired
	private SmsSenderService smsService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
	
	private DecimalFormat nf = new DecimalFormat("#,###");
	
	private DecimalFormat rtNf = new DecimalFormat("#,##0.0");
	
	public void task() {
		log.info("AnlysPlcyResultTask start -------------------------------");
		
		try {
			List<UsrVO> usrList = usrService.selectListForPlcy();
			
			StringBuilder emailContents = new StringBuilder("");
			StringBuilder smsContents = new StringBuilder("");
			//정책알림대상 가져오기 최근 1시간내에서만 가져옴(시스템 오류로 인한 미발송 건은 제외)
			List<Map<String, Object>> list = service.selectListForNotiftn(DateUtil.getDate(Calendar.HOUR, -1));
			for(Map<String, Object> data : list) {
				Number sn = (Number)data.get("ANLYS_PLCY_RSLT_SN");
				
				//이중 발송 방지를 위한 업데이트 체크
				if(service.updateNotifinSuccess(sn.longValue()) == 0)
					continue;

				String anlysPlcyNm = (String)data.get("ANLYS_PLCY_NM");
				String smsXmsnCn = (String)data.get("SMS_XMSN_CN");

				//해당 알림을 먼저 완료료 처리 후 sms 및 메일 발송
				emailContents.setLength(0);

				emailContents.append(anlysPlcyNm);
				emailContents.append("<br/>");
				emailContents.append("<br/>");
				Date occrrncDt = (Date)data.get("OCCRRNC_DT");
				emailContents.append(df.format(occrrncDt));
				emailContents.append(" 에 ");
				
				Number occrrncCnt = (Number)data.get("OCCRRNC_CNT");
				Number cmprCnt = (Number)data.get("CMPR_CNT");
				emailContents.append(rtNf.format((occrrncCnt.doubleValue()-cmprCnt.doubleValue())/cmprCnt.doubleValue()*100.0));
				emailContents.append(" % 초과하였습니다.");
				emailContents.append("<br/>");
				emailContents.append("<br/>");
				emailContents.append("발생건수 : ");
				emailContents.append(nf.format(occrrncCnt.longValue()));
				emailContents.append("건 ");
				emailContents.append("<br/>");
				emailContents.append("비교건수 : ");
				emailContents.append(nf.format(cmprCnt.longValue()));
				emailContents.append("건 ");

				smsContents.setLength(0);
				if(smsXmsnCn == null || "".equals(smsXmsnCn)) {
					smsContents.append("고객성향분석 알림 (");
					smsContents.append(anlysPlcyNm);
					smsContents.append(")");
				} else {
					smsContents.append(smsXmsnCn);
				}
				
				AnlysPlcyRsltNotiftnVO notiftnVO = new AnlysPlcyRsltNotiftnVO();
				notiftnVO.setAnlysPlcyRsltSn(sn.longValue());
				notiftnVO.setXmsnCn(smsContents.toString());

				boolean smsXmsnAltv = ((Boolean)data.get("SMS_XMSN_ALTV")).booleanValue();
				boolean emailXmsnAltv = ((Boolean)data.get("EMAIL_XMSN_ALTV")).booleanValue();
				
				for(UsrVO usr : usrList) {
					notiftnVO.setUsrId(usr.getUsrId());
					notiftnVO.setPrcsDt(DateUtil.getDate());
					
					if(smsXmsnAltv && usr.getTelno() != null && !usr.getTelno().equals("")) {
						try {
							notiftnVO.setTelno(usr.getTelno());
							smsService.send(usr.getTelno(), smsContents.toString());
							notiftnVO.setSmsXmsnAltv(1);
						} catch(Exception e) {
							notiftnVO.setSmsXmsnAltv(0);
							log.error(e.getMessage(), e);
							log.error("{} sms failed (user={}, nr={}, telno={})", anlysPlcyNm, usr.getEmpNm(), usr.getEmpNr(), usr.getTelno());
						}
					}
					
					//email value는 이메일수신여부가 1 인경우에 empNr(직번) 값이 들어온다. 없을 경우 null
					if(emailXmsnAltv && usr.getEmail() != null) {
						try {
							notiftnVO.setEmail(usr.getEmpNr());
							//sms 문구내용을 이메일 제목으로 함
							mailService.send(usr.getEmpNr(), usr.getEmpNm(), smsContents.toString(), emailContents.toString());
							notiftnVO.setEmailXmsnAltv(1);
						} catch(Exception e) {
							notiftnVO.setEmailXmsnAltv(0);
							log.error(e.getMessage(), e);
							log.error("{} email failed (user={}, nr={}, email={})", anlysPlcyNm, usr.getEmpNm(), usr.getEmpNr(), usr.getEmail());
						}
					}
					try {
						anlysPlcyRsltNotiftnService.insert(notiftnVO);
					} catch(Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		log.info("AnlysPlcyResultTask end -------------------------------");
	}
}
