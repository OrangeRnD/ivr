package com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.dao;

import com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.vo.AnlysPlcyRsltNotiftnVO;

import org.springframework.stereotype.Repository;

/**
 * 정책 알림 사용자 알림 수신 관리 dao
 * table : TBCA_ANLYS_PLCY_RSLT_NOTIFTN (정책 알림 사용자 알림 수신 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Repository
public interface AnlysPlcyRsltNotiftnDAO {
  
    /**
     * 정책 알림 사용자 알림 수신 관리 등록
     *
     * @param vo 정책 알림 사용자 알림 수신 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(AnlysPlcyRsltNotiftnVO vo) throws Exception;
}