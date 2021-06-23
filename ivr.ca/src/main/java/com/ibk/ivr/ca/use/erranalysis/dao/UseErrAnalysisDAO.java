package com.ibk.ivr.ca.use.erranalysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 실시간 에러발생 비교분석 DAO
 * 
 * table : TBCA_5MLY_LDIN_STAT (5분별_인입현황_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/08/2019
 */
@Repository
public interface UseErrAnalysisDAO {
	
	/**
	 * 상하위 제외를 위한 비교 기간에 대한 날짜 수
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    public Integer selectDtCnt(RequestVO vo) throws Exception;

	/**
     * 실시간 5분단위 에러건수 및 비교일자 5분단위 또는 5분 평균 에러건수 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 5분단위 에러건수 및 비교일자 5분단위 또는 5분 평균 에러건수
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
     * 실시간 5분단위 에러건수 및 비교일자 5분단위 또는 5분 평균 에러건수 엑셀 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 5분단위 에러건수 및 비교일자 5분단위 또는 5분 평균 에러건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectListExcel(RequestVO vo) throws Exception;
    
    /**
     * 5분 단위 포인트 클릭 시 해당 시간대 에러 리스트 조회 (선택 시분 - 5분 부터 선택 시간 미만 까지 데이타)
     * 
     * @param vo 검색 조건
     * @return 에러리스트
     * @throws Exception
     */
    public List<Map<String, Object>> selectErrList(RequestVO vo) throws Exception;
}
