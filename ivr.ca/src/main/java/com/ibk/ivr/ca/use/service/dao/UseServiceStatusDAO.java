package com.ibk.ivr.ca.use.service.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 서비스 이용현황  DAO
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 *         TBCA_DLY_LAST_SRVC_STAT (일별_마지막_서비스_현황)
 *         TBCA_MLY_LAST_SRVC_STAT (분별_마지막_서비스_현황)
 *         TBCA_DLY_TR_TIMEOUT_STAT (일별_TR_TIMEOUT_현황)
 *         TBCA_MLY_TR_TIMEOUT_STAT (분별_TR_TIMEOUT_현황)
 *         
 *         TBCA_30MLY_SRVC_STAT (30분별_서비스_현황)
 *         
 * 		   TBCA_DLY_SRVC_USER_STAT (일별_서비스_이용자_현황)
 * 		   TBCA_MLY_SRVC_USER_STAT (분별_서비스_이용자_현황)
 * 
 *         TBCA_DLY_SRVC_AGE_STAT (일별_서비스_연령_현황)
 *         TBCA_MLY_SRVC_AGE_STAT (분별_서비스_연령_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/23/2019
 */
@Repository
public interface UseServiceStatusDAO {
    
    /**
     * 서비스, 마지막 서비스, 상담원 연결, Time-out Top 10 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시간별 서비스 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
    
    /**
     * 당일 서비스 Top-5 시간별 추이 시간
     * 
     * @param dt
     * @return
     * @throws Exception
     */
    public List<String> selectListThirtyMlySrvcTop5HM(String dt) throws Exception;
    
    /**
     * 당일 서비스 Top-5 시간별 추이
     *
     * @param dt 현재 일자
     * @return List<Map<String, Object>> 시간별 서비스 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectListThirtyMlySrvcTop5(String dt) throws Exception;
    
    /**
     * 이용자 구분 서비스 이용현황 TOP-10
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 이용자 구분 서비스 이용현황 TOP-10
     * @throws Exception
     */
    public List<Map<String, Object>> selectListSrvcUserStat(RequestVO vo) throws Exception;
    
    /**
     * 연령버 서비스 이용현황 TOP-10
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 연령버 서비스 이용현황 TOP-10
     * @throws Exception
     */
    public List<Map<String, Object>> selectListSrvcAgeStat(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcelForSrvc(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcelForLstSrvc(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcelForCnslrConn(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcelForTimeout(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcelSrvcUserStat(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcelSrvcAgeStat(RequestVO vo) throws Exception;
}
