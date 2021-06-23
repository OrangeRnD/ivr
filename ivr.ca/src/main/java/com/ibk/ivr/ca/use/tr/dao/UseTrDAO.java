package com.ibk.ivr.ca.use.tr.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 거래 검색 DAO
 * 
 * table : TBCA_IVR_LOG (IVR_로그)
 *          TBCA_IVR_TR_RCPTN_LOG (IVR_TR_수신_로그)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/27/2019
 */
@Repository
public interface UseTrDAO {
    
    /**
     * TR 검색 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
    
    /**
     * TR 검색
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> tr 또는 서비스
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
}
