package com.ibk.ivr.ca.system.errcd.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;

import org.springframework.stereotype.Repository;

/**
 * 에러 코드 정보 dao
 * table : TBCA_ERR_CD (에러 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Repository
public interface ErrCdDAO {
    
    /**
     * 에러 코드 정보 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 에러 코드 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 에러 코드 정보 데이타
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