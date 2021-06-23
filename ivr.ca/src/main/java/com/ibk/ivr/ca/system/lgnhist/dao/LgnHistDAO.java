package com.ibk.ivr.ca.system.lgnhist.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.system.lgnhist.vo.LgnHistVO;

import com.ibk.ivr.ca.common.vo.RequestVO;

import org.springframework.stereotype.Repository;

/**
 * 사용자 로그인 이력 정보 dao
 * table : TBCA_LGN_HIST (사용자 로그인 이력 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Repository
public interface LgnHistDAO {
  
    /**
     * 사용자 로그인 이력 정보 등록
     *
     * @param vo 사용자 로그인 이력 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(LgnHistVO vo) throws Exception;
    
    /**
     * 사용자 로그인 이력 정보 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 사용자 로그인 이력 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 사용자 로그인 이력 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 사용자 로그인 이력 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 사용자 로그인 이력 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}