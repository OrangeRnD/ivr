package com.ibk.ivr.ca.use.usertype.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 연령별,개인/사업자 서비스 이용현황  DAO
 * 
 * table : TBCA_DLY_SRVC_USER_STAT (일별_서비스_이용자_현황)
 *         TBCA_DLY_SRVC_AGE_STAT (일별_서비스_연령_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/24/2019
 * @deprecated UseService 와 통합
 */
//@Repository
public interface UserTypeDAO {
    
    /**
     * 이용자 구분 서비스 이용현황 TOP-10
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 이용자 구분 서비스 이용현황 TOP-10
     * @throws Exception
     */
    public List<Map<String, Object>> selectListSrvcUserStat(RequestVO vo) throws Exception;
    
    /**
     * 연령버 서비스 이용현황 TOP-10
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 연령버 서비스 이용현황 TOP-10
     * @throws Exception
     */
    public List<Map<String, Object>> selectListSrvcAgeStat(RequestVO vo) throws Exception;
}
