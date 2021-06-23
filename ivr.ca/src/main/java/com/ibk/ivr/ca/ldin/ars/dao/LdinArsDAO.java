package com.ibk.ivr.ca.ldin.ars.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * ARS 유형별 이용현황 DAO
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 *		   TBCA_DLY_INPTH_LDIN_STATT (일별_인입경로_인입_현황)
 *		   TBCA_MLY_INPTH_LDIN_STATT (분별_인입경로_인입_현황)
 * 		   TBCA_DLY_CALL_KND_STAT (일별_콜_종류_현황)
 *         TBCA_MLY_CALL_KND_STAT (분별_콜_종류_현황)
 *         TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Repository
public interface LdinArsDAO {
	
	/**
     * ARS 유형별 이용현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> ARS 유형별 이용현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
    
    /**
     * 대표번호 ARS 이용콜수 구하기 위해 대표번호의 최대일자의 '보이는 ARS 이용콜수 + 말로하는 ARS 이용콜수' 값 조회
     * ARS 이용콜수 = 대표번호 콜수 - 보이는 ARS 이용콜수 - 말로하는 ARS 이용콜수
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectMaxDtCallKnd(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}
