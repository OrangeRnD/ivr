package com.ibk.ivr.ca.analysis.error.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.analysis.error.dao.AnalysisErrorDAO;
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
@Service
public class AnalysisErrorServiceImpl implements AnalysisErrorService {
	
	@Autowired
	private AnalysisErrorDAO dao;
    
   /**
    * 에러코드 누계 조회
    * 
    * @param vo 기간
    * @return 에러코드 누계
    * @throws Exception
    */
	@Override
    public Long selectTotalErrCnt(RequestVO vo) throws Exception {
		return dao.selectTotalErrCnt(vo);
    }
    
    /**
     * 에러코드 TOP-5
     *
    * @param vo 기간
     * @return List<Map<String, Object>> 에러코드
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> selectListErrTop5(RequestVO vo) throws Exception {
		return dao.selectListErrTop5(vo);
    }
    
    /**
     * 에러연계거래 TOP-5
     *
    * @param vo 기간
     * @return List<Map<String, Object>> 거래코드
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> selectListTrByErrTop5(RequestVO vo) throws Exception {
		return dao.selectListTrByErrTop5(vo);
    }
    
    /**
     * 거래연계 에러 리스트 조회
     *
    * @param vo 기간
     * @return List<Map<String, Object>> 거래 및 에러
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> selectListErrBySrvc(RequestVO vo) throws Exception {
		return dao.selectListErrBySrvc(vo);
    }
    
    /**
     * 당일 에러코드 top5 시간별 추이
     *
     * @param dt 당일
     * @return List<Map<String, Object>> 에러코드
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> selectListThirtyMlyErrTop5(String dt) throws Exception {
		List<Map<String, Object>> list = dao.selectListThirtyMlyErrTop5 (dt) ;
		if(list.size() == 0) return list;
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<String> hmList = dao.selectListThirtyMlyErrTop5HM(dt);
		List<Object> srvcList = new ArrayList<Object>() ;
		
		for(Map<String, Object> data: list) {
			if(hmList.indexOf(data.get("HM")) == -1)
				hmList.add((String) data.get("HM"));
			if(srvcList.indexOf(data.get("ERR_CD")) == -1)
				srvcList.add(data.get("ERR_CD"));
		}
		
		boolean b = false;
		Object srvcNm = "";
		for(Object hm : hmList) {
			for(Object srvcCd : srvcList) {
				b = false;
				for(Map<String, Object> data : list) {
					if(srvcCd.equals(data.get("ERR_CD"))) {
						srvcNm = data.get("ERR_CN");
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
					clone.put("ERR_CD", srvcCd);
					clone.put("ERR_CN", srvcNm);
					result.add (clone);

				}
			}
		}
		
		return result;
    }
}
