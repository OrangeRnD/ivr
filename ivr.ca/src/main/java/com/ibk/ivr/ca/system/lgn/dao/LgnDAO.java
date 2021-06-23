package com.ibk.ivr.ca.system.lgn.dao;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.system.usr.vo.UsrVO;

@Repository
public interface LgnDAO {
    
    /**
     * 로그인 조회
     *
     * @param empNr 직번
     * @return UsrVO 사용자 vo
     * @throws Exception
     */
    public UsrVO select(String empNr) throws Exception;
  
    /**
     * 로그인 정보 등록
     *
     * @param vo 로그인 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int login(UsrVO vo) throws Exception;
  
    /**
     * 로그아웃 정보 등록
     *
     * @param vo 로그인 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int logout(UsrVO vo) throws Exception;
}