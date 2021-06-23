package com.ibk.ivr.ca.ldin.analysis.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 실시간 인입추이 비교분석 service
 * 
 * table : TBCA_5MLY_LDIN_STAT (5분별_인입현황_현황)
 * 		   TBCA_DLY_LDIN_STAT (일별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/19/2019
 */
public interface LdinAnalysisService {

	/**
     * 실시간 5분단위 인입콜 및 비교일자 5분단위 또는 5분 평균 인입콜 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 5분단위 인입콜 및 비교일자 5분단위 또는 5분 평균 인입콜
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
	
	/**
	 * 평균 조회 시 해당 기간 최대 건수 일자 조회
	 * 
	 * @param vo
	 * @return 최대 건수 일자
	 * @throws Exception
	 */
    public String selectMaxDt(RequestVO vo) throws Exception;

	/**
     * 실시간 5분단위 인입콜 및 비교일자 5분단위 또는 5분 평균 인입콜 엑셀 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 5분단위 인입콜 및 비교일자 5분단위 또는 5분 평균 인입콜
     * @throws Exception
     */
    public List<Map<String, Object>> selectListExcel(RequestVO vo) throws Exception;
}
