package com.ibk.ivr.ca.data.overtr.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 이상거래 고객 분석  service
 * table : TBCA_DLY_USER_DLNGPTRN_STAT (일별_이용자_거래패턴_현황)
 *         TBCA_DLY_LDIN_STAT (일별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/08/2019
 */
public interface DataOverTrService {
    
    /**
     * 분석기간에 해당하는 기준값에 해당하는 실제 값
     * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자), START(비율에 따른 시작 rownum)
     * @return 기준 값
     * @throws Exception
     */
    public List<Map<String, Object>> getListStdVal(RequestVO vo) throws Exception;
    
    /**
     * 조회 조건에 따른 고객 목록
     * 
     * @param vo FROM_DT(분석기간 시작일자), TO_DT(분석기간 종료일자), 콜 건수, 콜당 오류 건수, 오류 발생 누계 건수, 상담원 연결 요청 건수
     * @return 고객
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
}
