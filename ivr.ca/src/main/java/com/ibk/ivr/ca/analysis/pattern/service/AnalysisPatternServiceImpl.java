package com.ibk.ivr.ca.analysis.pattern.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.analysis.pattern.dao.AnalysisPatternDAO;
import com.ibk.ivr.ca.common.vo.RequestVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 서비스이용 고객패턴 분석  service
 * 
 * table : TBCA_DLY_USER_DLNGPTRN_STAT (일별_이용자_거래패턴_현황)
 *         TBCA_DLY_USER_SRVC_STAT (일별_이용자_서비스_현황)
 *         TBCA_MLY_USER_SRVC_STAT (분별_이용자_서비스_현황)
 *         TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/30/2019
 */
@Slf4j
@Service
public class AnalysisPatternServiceImpl implements AnalysisPatternService {

	/**
	 * 분석기간에 해당하는 총 이용자 수 
	 * 실 데이터 적재 후 selectTotalDlyLdinUserCnt 값으로 사용
	 * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자)
     * @return 총 이용자 수
	 * @throws Exception
	 */
	@Autowired
	private AnalysisPatternDAO dao;

    /**
     * 분석기간에 해당하는 총 이용자 수
     * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자)
     * @return 총 이용자 수
     * @throws Exception
     */
	@Override
	public Long selectTotalDlyLdinUserCnt(RequestVO vo) throws Exception {
		return dao.selectTotalDlyLdinUserCnt(vo);
	}

    /**
     * 분석기간에 해당하는 기준값에 해당하는 실제 값
     * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자), START(비율에 따른 시작 rownum)
     * @return 기준 값
     * @throws Exception
     */
	@Override
	public List<Map<String, Object>> getListStdVal(RequestVO vo) throws Exception {
		Long userCnt = dao.selectTotalCnt(vo);
		if(log.isDebugEnabled()) 
			log.debug("getListStdVal user total count : {}", userCnt);
		
		
		//상하 n % 계산 (무조건 올림으로 계산하기, 10일중에 상하 10% 는 2,9이나)
		double value = Double.parseDouble((String)vo.getParam().get("STD_VALUE"));
		if(log.isDebugEnabled()) 
			log.debug("getListStdVal value : {}", value);
		int limit = (int)Math.ceil(userCnt*value/100);
		if(log.isDebugEnabled()) 
			log.debug("getListStdVal limit : {}", limit);
		vo.getParam().put("START", limit);
		
		return dao.selectListStdVal(vo);
	}

    /**
     * 조회 조건에 따른 개인/사업자, 연령대, 서비스 분포 top5
     * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자), 콜건수, 상담원 연결 수, 콜당 tr 수, 이용시간(분)
     * @return 개인/사업자, 연령대, 서비스 분포 top5
     * @throws Exception
     */
	@Override
	public List<Map<String, Object>> getList(RequestVO vo) throws Exception {
		return dao.selectList(vo);
	}
}
