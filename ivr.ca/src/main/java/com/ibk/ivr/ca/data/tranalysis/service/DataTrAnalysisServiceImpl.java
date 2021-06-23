package com.ibk.ivr.ca.data.tranalysis.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.data.tranalysis.dao.DataTrAnalysisDAO;

/**
 * 거래발생 일 변동량 분석 service
 * 
 * table : TBCA_HLY_TR_STAT (시간별_TR_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/07/2019
 */
@Service
public class DataTrAnalysisServiceImpl implements DataTrAnalysisService {

	@Autowired
	private DataTrAnalysisDAO dao;


	/**
	 * 거래발생 일 변동량 분석 조회
	 *
	 * @param vo 조회 조건
	 * @return List<Map<String, Object>> 거래발생 일 변동량 분석 건수
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
		return dao.selectList(vo);
	}
}
