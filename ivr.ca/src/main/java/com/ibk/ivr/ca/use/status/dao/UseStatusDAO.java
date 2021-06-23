package com.ibk.ivr.ca.use.status.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 거래/에러 현황 조회  DAO
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/24/2019
 */
@Repository
public interface UseStatusDAO {
    
    /**
     * 거래/에러 현황 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 거래/에러 현황
     * @throws Exception
     */
    public Map<String, Object> selectStat(RequestVO vo) throws Exception;
    
    /**
     * 거래/에러 현황 일별 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 거래/에러 현황 일별 조회
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
}
