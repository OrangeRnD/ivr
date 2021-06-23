package com.ibk.ivr.ca.data.overtr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.data.overtr.dao.DataOverTrDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * 이상거래 고객 분석  service
 * table : TBCA_DLY_USER_DLNGPTRN_STAT (일별_이용자_거래패턴_현황)
 *         TBCA_DLY_LDIN_STAT (일별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/08/2019
 */
@Slf4j
@Service
public class DataOverTrServiceImpl implements DataOverTrService {

	/**
	 * 분석기간에 해당하는 총 이용자 수 
	 * 실 데이터 적재 후 selectTotalDlyLdinUserCnt 값으로 사용
	 * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자)
     * @return 총 이용자 수
	 * @throws Exception
	 */
	@Autowired
	private DataOverTrDAO dao;

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
     * 조회 조건에 따른 고객 목록
     * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자), 콜 건수, 콜당 오류 건수, 오류 발생 누계 건수, 상담원 연결 요청 건수
     * @return 고객
     * @throws Exception
     */
	@Override
	public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
		long size = dao.selectListCount(vo);
        if(size > 0) {
            vo.setSize(size);
            return dao.selectList(vo);
        }
        return null;
	}
}
