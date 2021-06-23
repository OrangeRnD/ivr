package com.ibk.ivr.ca.report.daily.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.report.daily.vo.RptNotiftnHistVO;

/**
 * 일일보고서 DAO
 * 
 * table : TBCA_DLY_MEDA_CALL_STAT (일별_매체_콜_현황)
 * 		   TBCA_ANLYS_PLCY (분석_정책)
 * 		   TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 		   TBCA_DLY_SRVC_STAT (일별_인입_현황)
 * 		   TBCA_DLY_LAST_SRVC_STAT
 * 		   TBCA_DLY_SRVC_END_STAT
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/22/2019
 */
@Repository
public interface DailyReportDAO {
	
	/**
	 * 매체별 콜 인입 현황
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    public List<Map<String, Object>> selectListMedaStat(RequestVO vo) throws Exception;
    
    /**
     * 인입추이 분석 발생 내역
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectListPlcyRslt(RequestVO vo) throws Exception;
    
    /**
     * 서비스 이용현황
     * @param vo
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectListSrvc(RequestVO vo) throws Exception;
    
    /**
     * 메일 발송 이력 등록
     * @param vo
     * @return
     * @throws Exception
     */
    public int insertRptNotiftnHist(RptNotiftnHistVO vo) throws Exception;
    
    /**
     * 메일 발송 성공 등록
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateRptNotiftnHistForSuccess(RptNotiftnHistVO vo) throws Exception;
}
