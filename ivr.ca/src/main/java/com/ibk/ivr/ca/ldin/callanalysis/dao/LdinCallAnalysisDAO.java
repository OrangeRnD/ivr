package com.ibk.ivr.ca.ldin.callanalysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 대표번호이용현황  분석 DAO
 * 
 * table : TBCA_DLY_INPTH_LDIN_STAT (일별_인입경로_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Repository
public interface LdinCallAnalysisDAO {
	
	/**
     * 대표번호이용현황 분석
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 대표번호이용현황 분석
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}
