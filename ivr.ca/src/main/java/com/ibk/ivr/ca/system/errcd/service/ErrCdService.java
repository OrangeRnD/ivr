package com.ibk.ivr.ca.system.errcd.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 에러 코드 정보 service
 * table : TBCA_ERR_CD (에러 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
public interface ErrCdService {
    
    /**
     * 에러 코드 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<ErrCdVO> 에러 코드 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 에러 코드 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 에러 코드 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}