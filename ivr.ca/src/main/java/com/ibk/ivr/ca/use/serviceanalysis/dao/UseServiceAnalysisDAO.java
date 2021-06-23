package com.ibk.ivr.ca.use.serviceanalysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 서비스 비교 분석 DAO
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/25/2019
 */
@Repository
public interface UseServiceAnalysisDAO {
	
	/**
     * 서비스 Top5 서비스 코드 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 코드
     * @throws Exception
     */
    public List<Map<String, Object>> selectListSrvcTop5(RequestVO vo) throws Exception;
    
    /**
     * 서비스 현황 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}
