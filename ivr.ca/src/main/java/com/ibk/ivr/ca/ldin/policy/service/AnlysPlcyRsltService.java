package com.ibk.ivr.ca.ldin.policy.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 분석 정책 결과 service
 * table : TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/26/2019
 */
public interface AnlysPlcyRsltService {
  
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
}