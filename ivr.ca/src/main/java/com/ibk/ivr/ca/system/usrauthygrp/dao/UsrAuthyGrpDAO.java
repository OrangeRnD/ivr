package com.ibk.ivr.ca.system.usrauthygrp.dao;

import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import org.springframework.stereotype.Repository;

/**
 * 사용자 별 권한 그룹 장보 dao
 * table : TBCA_USR_AUTHY_GRP (사용자 별 권한 그룹 장보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/15/2019
 */
@Repository
public interface UsrAuthyGrpDAO {
  
    /**
     * 사용자 별 권한 그룹 장보 등록
     *
     * @param vo 사용자 별 권한 그룹 장보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(UsrVO vo) throws Exception;
  
    /**
     * 사용자 별 권한 그룹 장보 삭제
     *
     * @param vo 사용자 별 권한 그룹 장보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(UsrVO vo) throws Exception;
}