package com.ibk.ivr.ca.ldin.callanalysis.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.callanalysis.dao.LdinCallAnalysisDAO;

/**
 * 대표번호이용현황 분석 service impl
 * 
 * table : TBCA_DLY_INPTH_LDIN_STAT (일별_인입경로_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Service
public class LdinCallAnalysisServiceImpl implements LdinCallAnalysisService {
	
    @Autowired
    private LdinCallAnalysisDAO dao;
    
    public LdinCallAnalysisServiceImpl() {
    }
	
	/**
     * 대표번호이용현황 분석
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 대표번호이용현황 분석
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
    	return dao.selectList(vo);
    }

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
    	return dao.selectExcel(vo);
    }
}
