package com.ibk.ivr.ca.system.authygrpfnct.service;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.system.authygrpfnct.vo.AuthyGrpFnctVO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 권한그룹 별 기능에 대한 권한 관리 service
 * table : TBCA_AUTHY_GRP_FNCT (권한그룹 별 기능에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
public interface AuthyGrpFnctService {
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 등록
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(AuthyGrpFnctVO vo) throws Exception;
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 수정
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(AuthyGrpFnctVO vo) throws Exception;
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 삭제
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(AuthyGrpFnctVO vo) throws Exception;
      
    /**
     * 권한그룹 별 기능에 대한 권한 관리 리스트 삭제
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @param ids 삭제데이터 ids
     * @return int 삭제건수
     * @throws Exception
     */
    public int deletes(AuthyGrpFnctVO vo, String[] ids) throws Exception;

    /**
     * 권한그룹 별 기능에 대한 권한 관리 조회
     *
     * @param authy_grp_cd 권한_그룹_코드
     * @return AuthyGrpFnctVO 권한그룹 별 기능에 대한 권한 관리 vo
     * @throws Exception
     */
    public AuthyGrpFnctVO select(String authy_grp_cd) throws Exception;
    
    /**
     * 권한그룹 별 기능에 대한 권한 관리 조회
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 조회 조건
     * @return AuthyGrpFnctVO 권한그룹 별 기능에 대한 권한 관리 vo
     * @throws Exception
     */
    public AuthyGrpFnctVO select(AuthyGrpFnctVO vo) throws Exception;
    
    /**
     * 권한그룹 별 기능에 대한 권한 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<AuthyGrpFnctVO> 권한그룹 별 기능에 대한 권한 관리 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 권한그룹 별 기능에 대한 권한 관리 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}