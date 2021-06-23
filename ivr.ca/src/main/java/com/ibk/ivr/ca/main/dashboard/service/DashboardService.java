package com.ibk.ivr.ca.main.dashboard.service;

import java.util.List;
import java.util.Map;

/**
 * 대시보드 Service
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 *		   TBCA_DLY_INPTH_LDIN_STATT (일별_인입경로_인입_현황)
 *		   TBCA_MLY_INPTH_LDIN_STATT (분별_인입경로_인입_현황)
 * 		   TBCA_DLY_CALL_KND_STAT (일별_콜_종류_현황)
 *         TBCA_MLY_CALL_KND_STAT (분별_콜_종류_현황)
 *         TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
public interface DashboardService {

	/**
     * ARS 구분별 인입
     *
     * @param dt 현재 일자
     * @return List<Map<String, Object>> ARS 구분별 인입
     * @throws Exception
     */
    public List<Map<String, Object>> selectListArs(String dt) throws Exception;

	/**
     * 거래 대비 에러 비율
     *
     * @param dt 현재 일자
     * @return List<Map<String, Object>> 거래 대비 에러 비율
     * @throws Exception
     */
    public Map<String, Object> selectTrErr(String dt) throws Exception;
    
    /**
     * 최신 알림 발생 목록 3건 조회
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectListPlcyRslt() throws Exception;
}
