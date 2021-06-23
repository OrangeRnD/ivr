package com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.service;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.service.AnlysPlcyRsltNotiftnService;
import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.vo.AnlysPlcyRsltNotiftnVO;
import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.dao.AnlysPlcyRsltNotiftnDAO;

/**
 * 정책 알림 사용자 알림 수신 관리 service implements
 * table : TBCA_ANLYS_PLCY_RSLT_NOTIFTN (정책 알림 사용자 알림 수신 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
//@Slf4j
@Service
public class AnlysPlcyRsltNotiftnServiceImpl implements AnlysPlcyRsltNotiftnService {
	
    @Autowired
    private AnlysPlcyRsltNotiftnDAO dao;
    
    public AnlysPlcyRsltNotiftnServiceImpl() {
    }
  
    /**
     * 정책 알림 사용자 알림 수신 관리 등록
     *
     * @param vo 정책 알림 사용자 알림 수신 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(AnlysPlcyRsltNotiftnVO vo) throws Exception {
        return dao.insert(vo);
    }
}