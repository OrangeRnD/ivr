package com.ibk.ivr.ca.use.service.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.use.service.dao.UseServiceStatusDAO;

/**
 * 서비스 이용현황  service
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 *         TBCA_DLY_LAST_SRVC_STAT (일별_마지막_서비스_현황)
 *         TBCA_MLY_LAST_SRVC_STAT (분별_마지막_서비스_현황)
 *         TBCA_DLY_TR_TIMEOUT_STAT (일별_TR_TIMEOUT_현황)
 *         TBCA_MLY_TR_TIMEOUT_STAT (분별_TR_TIMEOUT_현황)
 *         
 *         TBCA_30MLY_SRVC_STAT (30분별_서비스_현황)
 *         
 * 		   TBCA_DLY_SRVC_USER_STAT (일별_서비스_이용자_현황)
 * 		   TBCA_MLY_SRVC_USER_STAT (분별_서비스_이용자_현황)
 * 
 *         TBCA_DLY_SRVC_AGE_STAT (일별_서비스_연령_현황)
 *         TBCA_MLY_SRVC_AGE_STAT (분별_서비스_연령_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/23/2019
 */
@Service
public class UseServiceStatusServiceImpl implements UseServiceStatusService {
	
	@Autowired
	private UseServiceStatusDAO dao;

    /**
     * 서비스, 마지막 서비스, 상담원 연결, Time-out Top 10 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시간별 서비스 건수
     * @throws Exception
     */
	@Override
	public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
		return dao.selectList(vo);
	}

    
    /**
     * 당일 서비스 Top-5 시간별 추이
     *
     * @param dt 현재 일자
     * @return List<Map<String, Object>> 시간별 서비스 건수
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> selectListThirtyMlySrvcTop5(String dt) throws Exception {
		List<Map<String, Object>> list = dao.selectListThirtyMlySrvcTop5 (dt) ;
		if(list.size() == 0) return list;
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<String> hmList = dao.selectListThirtyMlySrvcTop5HM(dt);
		List<Object> srvcList = new ArrayList<Object>() ;
		
		for(Map<String, Object> data: list) {
			if(hmList.indexOf(data.get("HM")) == -1)
				hmList.add((String) data.get("HM"));
			if(srvcList.indexOf(data.get("SRVC_CD")) == -1)
				srvcList.add(data.get("SRVC_CD"));
		}
		
		boolean b = false;
		Object srvcNm = "";
		for(Object hm : hmList) {
			for(Object srvcCd : srvcList) {
				b = false;
				for(Map<String, Object> data : list) {
					if(srvcCd.equals(data.get("SRVC_CD"))) {
						srvcNm = data.get("SRVC_NM");
						if (hm.equals(data.get("HM"))) {
							result.add(data);
							b = true;
							break;
						}
					}
				}
				if(!b) {
					Map<String, Object> clone = new HashMap<String, Object> ();
					clone.put("CNT", 0);
					clone.put("HM", hm);
					clone.put("SRVC_CD", srvcCd);
					clone.put("SRVC_NM", srvcNm);
					result.add (clone);

				}
			}
		}
		
		return result;
	}
	
    /**
     * 이용자 구분 서비스 이용현황 TOP-10
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 이용자 구분 서비스 이용현황 TOP-10
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> getListSrvcUserStat(RequestVO vo) throws Exception {
		return dao.selectListSrvcUserStat(vo);
	}
    
    /**
     * 연령버 서비스 이용현황 TOP-10
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 연령버 서비스 이용현황 TOP-10
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> getListSrvcAgeStat(RequestVO vo) throws Exception {
		return dao.selectListSrvcAgeStat(vo);
	}

    public List<Map<String, Object>> selectExcelForSrvc(RequestVO vo) throws Exception {
		return dao.selectExcelForSrvc(vo);
	}

    public List<Map<String, Object>> selectExcelForLstSrvc(RequestVO vo) throws Exception {
		return dao.selectExcelForLstSrvc(vo);
	}

    public List<Map<String, Object>> selectExcelForCnslrConn(RequestVO vo) throws Exception {
		return dao.selectExcelForCnslrConn(vo);
	}

    public List<Map<String, Object>> selectExcelForTimeout(RequestVO vo) throws Exception {
		return dao.selectExcelForTimeout(vo);
	}

    public List<Map<String, Object>> selectExcelSrvcUserStat(RequestVO vo) throws Exception {
		return dao.selectExcelSrvcUserStat(vo);
	}

    public List<Map<String, Object>> selectExcelSrvcAgeStat(RequestVO vo) throws Exception {
		return dao.selectExcelSrvcAgeStat(vo);
	}
}
