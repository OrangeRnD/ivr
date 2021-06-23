package com.ibk.ivr.ca.system.usr.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 시스템 사용자 정보 service
 * table : TBCA_USR (시스템 사용자 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
public interface UsrService {
  
    /**
     * 시스템 사용자 정보 등록
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(UsrVO vo) throws Exception;
  
    /**
     * 시스템 사용자 정보 수정
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(UsrVO vo) throws Exception;
  
    /**
     * SSO 최초 로그인 시 사용자 정보 수정
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int updateForSSO(UsrVO vo) throws Exception;
    
    /**
     * 본인 정보 수정
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public int updateMyUsr(UsrVO vo) throws Exception;
  
    /**
     * 시스템 사용자 정보 삭제
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(UsrVO vo) throws Exception;

    /**
     * 시스템 사용자 정보 조회
     *
     * @param usrId 사용자_아이디
     * @return UsrVO 시스템 사용자 정보 vo
     * @throws Exception
     */
    public UsrVO select(int usrId) throws Exception;
  
    /**
     * 시스템 사용자 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 사용자 정보 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 정책 알림을 위한 시스템 사용자 정보 리스트조회
     *
     * @return List<UsrVO> 시스템 사용자 정보 데이타
     * @throws Exception
     */
    public List<UsrVO> selectListForPlcy() throws Exception;
  
    /**
     * 일일보고서 알림을 위한 시스템 사용자 정보 리스트조회
     * @param currentDt 현재 일자
     * @return List<UsrVO> 시스템 사용자 정보 데이타
     * @throws Exception
     */
    public List<UsrVO> selectListForReport(String currentDt) throws Exception;
}