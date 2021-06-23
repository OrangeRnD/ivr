package com.ibk.ivr.ca.system.authygrpmenu.dao;

import java.util.List;

import com.ibk.ivr.ca.system.authygrpmenu.vo.AuthyGrpMenuVO;

import org.springframework.stereotype.Repository;

/**
 * 권한 그룹 별 메뉴에 대한 권한 관리 dao
 * table : TBCA_AUTHY_GRP_MENU (권한 그룹 별 메뉴에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Repository
public interface AuthyGrpMenuDAO {
  
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 등록
     *
     * @param vo 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(AuthyGrpMenuVO vo) throws Exception;
  
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 수정
     *
     * @param vo 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int delete(AuthyGrpMenuVO vo) throws Exception;
  
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<AuthyGrpMenuVO> 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @throws Exception
     */
    public List<AuthyGrpMenuVO> selectList() throws Exception;
}