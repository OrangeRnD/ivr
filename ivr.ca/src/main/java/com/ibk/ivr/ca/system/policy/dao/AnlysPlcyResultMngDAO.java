package com.ibk.ivr.ca.system.policy.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

import org.springframework.stereotype.Repository;

/**
 * 분석 정책 결과 dao
 * table : TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/26/2019
 */
@Repository
public interface AnlysPlcyResultMngDAO {
  
    /**
     * 분석 정책 결과 알림 완료
     *
     * @param anlysPlcyRsltSn 분석_정책_결과_일련번호
     * @return int 수정건수
     * @throws Exception
     */
    public int updateNotifinSuccess(long anlysPlcyRsltSn) throws Exception;
  
    /**
     * 분석 정책 결과 알림 완료 실패로 다시 0으로 업데이트
     *
     * @param anlysPlcyRsltSn 분석_정책_결과_일련번호
     * @return int 수정건수
     * @throws Exception
     */
    public int updateNotifinFail(long anlysPlcyRsltSn) throws Exception;
  
    /**
     * 분석 정책 결과 알림 대상 조회
     *
     * @param occrrncDt 발생_일시
     * @return List<Map<String, Object>> 분석 정책 결과 알림 대상
     * @throws Exception
     */
    public List<Map<String, Object>> selectListForNotiftn(Date occrrncDt) throws Exception;
    
    /**
     * 분석 정책 알림 발생 현황 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 분석 정책 알림 발생 현황 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 분석 정책 알림 발생 현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
}