package com.ibk.ivr.ca.use.usertype.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.use.usertype.dao.UserTypeDAO;

/**
 * 연령별,개인/사업자 서비스 이용현황  service
 * 
 * table : TBCA_DLY_SRVC_USER_STAT (일별_서비스_이용자_현황)
 *         TBCA_DLY_SRVC_AGE_STAT (일별_서비스_연령_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/24/2019
 * @deprecated UseService 와 통합
 */
//@Service
public class UserTypeServiceImpl implements UserTypeService {
	
	@Autowired
	private UserTypeDAO dao;
	
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
}
