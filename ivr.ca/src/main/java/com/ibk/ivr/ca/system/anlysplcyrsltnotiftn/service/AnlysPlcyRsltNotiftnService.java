package com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.service;

import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.vo.AnlysPlcyRsltNotiftnVO;

/**
 * 정책 알림 사용자 알림 수신 관리 service
 * table : TBCA_ANLYS_PLCY_RSLT_NOTIFTN (정책 알림 사용자 알림 수신 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
public interface AnlysPlcyRsltNotiftnService {
  
    /**
     * 정책 알림 사용자 알림 수신 관리 등록
     *
     * @param vo 정책 알림 사용자 알림 수신 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(AnlysPlcyRsltNotiftnVO vo) throws Exception;
}