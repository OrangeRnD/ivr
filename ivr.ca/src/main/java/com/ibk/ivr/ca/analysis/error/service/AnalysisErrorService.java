package com.ibk.ivr.ca.analysis.error.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 에러코드/거래 현황 service
 * 
 * table : TBCA_DLY_ERR_STAT (일별_에러_현황)
 *         TBCA_MLY_ERR_STAT (분별_에러_현황)
 *         TBCA_DLY_TR_ERR_STAT (일별_TR_에러_현황)
 *         TBCA_MLY_TR_ERR_STAT (분별_TR_에러_현황)
 *         TBCA_30MLY_ERR_STAT (30분별_에러_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/30/2019
 */
public interface AnalysisErrorService {
    
   /**
    * 에러코드 누계 조회
    * 
    * @param vo 기간
    * @return 에러코드 누계
    * @throws Exception
    */
    public Long selectTotalErrCnt(RequestVO vo) throws Exception;
    
    /**
     * 에러코드 TOP-5
     *
    * @param vo 기간
     * @return List<Map<String, Object>> 에러코드
     * @throws Exception
     */
    public List<Map<String, Object>> selectListErrTop5(RequestVO vo) throws Exception;
    
    /**
     * 에러연계거래 TOP-5
     *
    * @param vo 기간
     * @return List<Map<String, Object>> 거래코드
     * @throws Exception
     */
    public List<Map<String, Object>> selectListTrByErrTop5(RequestVO vo) throws Exception;
    
    /**
     * 거래연계 에러 리스트 조회
     *
    * @param vo 기간
     * @return List<Map<String, Object>> 거래 및 에러
     * @throws Exception
     */
    public List<Map<String, Object>> selectListErrBySrvc(RequestVO vo) throws Exception;
    
    /**
     * 당일 에러코드 top5 시간별 추이
     *
     * @param dt 당일
     * @return List<Map<String, Object>> 에러코드
     * @throws Exception
     */
    public List<Map<String, Object>> selectListThirtyMlyErrTop5(String dt) throws Exception;
}
