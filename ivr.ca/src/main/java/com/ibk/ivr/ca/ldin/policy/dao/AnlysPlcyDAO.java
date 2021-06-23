package com.ibk.ivr.ca.ldin.policy.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.policy.vo.AnlysPlcyVO;

import org.springframework.stereotype.Repository;

/**
 * 분석 정책 설정 정보 dao
 * table : TBCA_ANLYS_PLCY (분석 정책 설정 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/26/2019
 */
@Repository
public interface AnlysPlcyDAO {
  
    /**
     * 분석 정책 설정 정보 등록
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(AnlysPlcyVO vo) throws Exception;

    /**
     * 분석 정책 설정 정보 동일 조건 존재 여부 조회
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return Integer 값이 존재하는 경우 존재
     * @throws Exception
     */
    public Integer selectExists(AnlysPlcyVO vo) throws Exception;
  
    /**
     * 분석 정책 설정 정보 수정
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(AnlysPlcyVO vo) throws Exception;
  
    /**
     * 분석 정책 설정 정보 삭제
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(AnlysPlcyVO vo) throws Exception;

    /**
     * 분석 정책 설정 정보 조회
     *
     * @param anlysPlcyId 분석_정책_아이디
     * @return AnlysPlcyVO 분석 정책 설정 정보 vo
     * @throws Exception
     */
    public AnlysPlcyVO select(int anlysPlcyId) throws Exception;
    
    /**
     * 분석 정책 설정 정보 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 분석 정책 설정 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 분석 정책 설정 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 분석 정책 설정 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 분석 정책 설정 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}