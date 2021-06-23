package com.ibk.ivr.ca.data.tranalysis.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 거래발생 일 변동량 분석 DAO
 * 
 * table : TBCA_HLY_TR_STAT (시간별_TR_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/07/2019
 */
@Repository
public interface DataTrAnalysisDAO {

	/**
     * 거래발생 일 변동량 분석 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 거래발생 일 변동량 분석 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
}
