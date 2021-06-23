package com.ibk.ivr.ca.use.call.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 고객별 콜 검색 DAO
 * 
 * table : TBCA_IVR_LOG (IVR_로그)
 *          TBCA_IVR_TR_RCPTN_LOG (IVR_TR_수신_로그)
 *          TBCA_DLY_USER_SRVC_STAT (일별_이용자_서비스_현황)
 *          TBCA_MLY_USER_SRVC_STAT (분별_이용자_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/27/2019
 */
@Repository
public interface UseCallDAO {
    
    /**
     * 고객 콜 검색 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
    
    /**
     * 고객 콜 검색
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 고객 콜
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
	
	/**
     * 평균이용시간 조회
     *
     * @param vo 조회 조건
     * @return Integer 평균이용시간
     * @throws Exception
     */
    public Integer selectListAvgUseTm(RequestVO vo) throws Exception;
    
    /**
     * 자주 쓰는 메뉴
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 top5
     * @throws Exception
     */
    public List<Map<String, Object>> selectListFrequentlyUsedSrvc(RequestVO vo) throws Exception;
    
    /**
     * 선택 콜 거래 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 거래 성공, 실패 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectListTr(RequestVO vo) throws Exception;
    
    /**
     * 선택 콜 에러 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 에러
     * @throws Exception
     */
    public List<Map<String, Object>> selectListErr(RequestVO vo) throws Exception;
}
