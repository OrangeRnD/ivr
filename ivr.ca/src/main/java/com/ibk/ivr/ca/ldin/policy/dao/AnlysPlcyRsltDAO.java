package com.ibk.ivr.ca.ldin.policy.dao;

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
public interface AnlysPlcyRsltDAO {
    
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
  
    /**
     * 분석 정책 알림 발생 현황 상세 조회
     *
     * @param vo 분석정책아이디, 시작일자, 종료일자
     * @return List<Map<String, Object>> 분석 정책 알림 발생 현황 상세
     * @throws Exception
     */
    public List<Map<String, Object>> selectListDtl(RequestVO vo) throws Exception;
  
    /**
     * 분석 정책 결과 알림 대상 조회
     *
     * @return List<Map<String, Object>> 분석 정책 결과 알림 대상
     * @throws Exception
     */
    public List<Map<String, Object>> selectListForNotiftn() throws Exception;
}