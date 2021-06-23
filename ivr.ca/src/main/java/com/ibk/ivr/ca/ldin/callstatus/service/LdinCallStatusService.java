package com.ibk.ivr.ca.ldin.callstatus.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 인입총괄현황 service
 * 
 * table : TBCA_MNBY_MEDA_CALL_STAT (월별_매체_콜_현황)
 *         TBCA_MLY_MEDA_CALL_STAT (분별_매체_콜_현황)
 * 		   TBCA_MNBY_CALL_KND_STAT (월별_콜_종류_현황)
 *         TBCA_MLY_CALL_KND_STAT (분별_콜_종류_현황)
 * 		   TBCA_MNBY_SRVC_END_STAT (월별_서비스_종료_현황)
 *         TBCA_MLY_SRVC_END_STAT (분별_서비스_종료_현황)
 * 		   TBCA_MNBY_LDIN_STAT (월별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 		   TBCA_MNBY_INPTH_LDIN_STAT (월별_인입경로_인입_현황)
 *         TBCA_MLY_INPTH_LDIN_STAT (분별_인입경로_인입_현황)
 * 		   TBCA_MNBY_USER_CALL_STAT (월별_이용자_콜_현황)
 *         TBCA_MLY_USER_CALL_STAT (분별_이용자_콜_현황)
 * 		   TBCA_MNBY_AGE_CALL_STAT (월별_연령_콜_현황)
 *         TBCA_MLY_AGE_CALL_STAT (분별_연령_콜_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
public interface LdinCallStatusService {
	
	/**
     * 매체별 인입 현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    public List<Map<String, Object>> selectListMedaCallStat(RequestVO vo) throws Exception;

	/**
     * IVR 현황 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    public List<Map<String, Object>> selectIvrList(RequestVO vo) throws Exception;
    
    /**
     * 사용자(개인/사업자)별 콜  현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    public List<Map<String, Object>> selectListUsrTypeInfo(RequestVO vo) throws Exception;
    
    /**
     * 연령대별 콜 현황
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>>
     * @throws Exception
     */
    public List<Map<String, Object>> selectListAgeRangeInfo(RequestVO vo) throws Exception;
    
}
