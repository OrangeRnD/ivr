package com.ibk.ivr.ca.system.cdclsfctn.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.system.cdclsfctn.vo.CdClsfctnVO;

import com.ibk.ivr.ca.common.vo.RequestVO;

import org.springframework.stereotype.Repository;

/**
 * 시스템 코드분류 정보 dao
 * table : TBCA_CD_CLSFCTN (시스템 코드분류 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Repository
public interface CdClsfctnDAO {
  
    /**
     * 시스템 코드분류 정보 등록
     *
     * @param vo 시스템 코드분류 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(CdClsfctnVO vo) throws Exception;
  
    /**
     * 시스템 코드분류 정보 수정
     *
     * @param vo 시스템 코드분류 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(CdClsfctnVO vo) throws Exception;
  
    /**
     * 시스템 코드분류 정보 삭제
     *
     * @param vo 시스템 코드분류 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(CdClsfctnVO vo) throws Exception;

    /**
     * 시스템 코드분류 정보 조회
     *
     * @param cdClsfctn 코드_분류
     * @return CdClsfctnVO 시스템 코드분류 정보 vo
     * @throws Exception
     */
    public CdClsfctnVO select(String cdClsfctn) throws Exception;
    
    /**
     * 시스템 코드분류 정보 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 시스템 코드분류 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 코드분류 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
}