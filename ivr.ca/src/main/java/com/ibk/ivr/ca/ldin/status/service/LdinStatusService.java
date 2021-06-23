package com.ibk.ivr.ca.ldin.status.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 실시간 텔레뱅킹 현황 service
 * 
 * table : TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 	       TBCA_DLY_INPTH_LDIN_STAT (일별_인입경로_인입_현황)
 *         TBCA_30MLY_TR_STAT (30분별_TR_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
public interface LdinStatusService {
    
    /**
     * 실시간 인입현황 및 인입경로 별 인입현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 당일 및 특정 일자 인입현황, 인입경로 별 인입현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectListLdinStat(RequestVO vo) throws Exception;
    
    /**
     * 특정일자 최종 인입현황
     *
     * @param vo 조회 조건
     * @return Map<String, Object> 특정 일자 인입현황
     * @throws Exception
     */
    public Map<String, Object> selectLstLdinStat(RequestVO vo) throws Exception;
  
    /**
     * 실시간 tr 건수
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 tr 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectListTrStat(RequestVO vo) throws Exception;
    
    /**
     * 실시간 인입현황 엑셀다운로드
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 당일 및 특정 일자 인입현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcelMlyLdinStat(RequestVO vo) throws Exception;
}
