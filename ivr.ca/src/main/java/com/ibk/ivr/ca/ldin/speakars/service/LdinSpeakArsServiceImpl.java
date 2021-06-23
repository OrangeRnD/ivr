package com.ibk.ivr.ca.ldin.speakars.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.speakars.dao.LdinSpeakArsDAO;

/**
 * ARS 유형별 이용현황 service impl
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Service
public class LdinSpeakArsServiceImpl implements LdinSpeakArsService {
	
    @Autowired
    private LdinSpeakArsDAO dao;
    
    public LdinSpeakArsServiceImpl() {
    }
	
	/**
     * ARS 유형별 이용현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> ARS 유형별 이용현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
    	return dao.selectList(vo);
    }

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
    	return dao.selectExcel(vo);
    }
}
