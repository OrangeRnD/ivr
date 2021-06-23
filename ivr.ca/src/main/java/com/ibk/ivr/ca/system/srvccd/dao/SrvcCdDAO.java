package com.ibk.ivr.ca.system.srvccd.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.srvccd.vo.SrvcCdVO;

import org.springframework.stereotype.Repository;

/**
 * 서비스 코드 정보 dao
 * table : TBCA_SRVC_CD (서비스 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Repository
public interface SrvcCdDAO {
  
    /**
     * 서비스 코드 정보 등록
     *
     * @param vo 서비스 코드 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(SrvcCdVO vo) throws Exception;
  
    /**
     * 서비스 코드 정보 수정
     *
     * @param vo 서비스 코드 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(SrvcCdVO vo) throws Exception;
  
    /**
     * 서비스 코드 정보 삭제
     *
     * @param vo 서비스 코드 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(SrvcCdVO vo) throws Exception;

    /**
     * 서비스 코드 정보 조회
     *
     * @param srvcCd 서비스코드
     * @return CdVO 서비스 코드 정보 vo
     * @throws Exception
     */
    public SrvcCdVO select(String srvcCd) throws Exception;
    
    /**
     * 서비스 코드 정보 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 서비스 코드 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 서비스 코드 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
  
    /**
     * 서비스 검색
     *
     * @param nm 서비스이름
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectFind(String nm) throws Exception;
  
    /**
     * 세션에 있는 조회 조건 서비스 코드 조회
     *
     * @param vo 서비스 코드
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectListForSession(Map<?, ?> vo) throws Exception;
}