package com.ibk.ivr.ca.ldin.speakars.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 말로하는 ARS 유형별 이용현황 DAO
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 2/20/2020
 */
@Repository
public interface LdinSpeakArsDAO {
	
	/**
     * 말로하는 ARS 유형별 이용현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> ARS 유형별 이용현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}
