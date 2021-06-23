package com.ibk.ivr.ca.use.serviceanalysis.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.use.serviceanalysis.dao.UseServiceAnalysisDAO;

/**
 * 서비스 비교 분석 service
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/25/2019
 */
@Service
public class UseServiceAnalysisServiceImpl implements UseServiceAnalysisService {
	
	@Autowired
	private UseServiceAnalysisDAO dao;
	
	/**
     * 서비스 Top5 서비스 코드 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 코드
     * @throws Exception
     */
	@Override
    public List<Map<String, Object>> selectListSrvcTop5(RequestVO vo) throws Exception {
		return dao.selectListSrvcTop5(vo);
	}

	/**
     * 서비스 현황 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 건수
     * @throws Exception
     */
	@Override
	public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
		if(vo.getParam().get("EX_CDS") != null && !"".equals(vo.getParam().get("EX_CDS"))) {
			Object obj = vo.getParam().get("EX_CDS");
			if(obj instanceof String) {
				vo.getParam().put("EX_CD", obj);
				vo.getParam().remove("EX_CDS");
			}
		}
		return dao.selectList(vo);
	}

    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
    	return dao.selectExcel(vo);
    }
}
