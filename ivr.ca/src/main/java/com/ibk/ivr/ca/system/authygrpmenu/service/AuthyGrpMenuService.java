package com.ibk.ivr.ca.system.authygrpmenu.service;

import java.util.List;
import com.ibk.ivr.ca.system.authygrpmenu.vo.AuthyGrpMenuVO;

/**
 * 권한 그룹 별 메뉴에 대한 권한 관리 service
 * table : TBCA_AUTHY_GRP_MENU (권한 그룹 별 메뉴에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
public interface AuthyGrpMenuService {
  
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 등록
     *
     * @param list 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @param regrId 등록자 id
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(List<AuthyGrpMenuVO> list, int regrId) throws Exception;
    
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<AuthyGrpMenuVO> 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @throws Exception
     */
    public List<AuthyGrpMenuVO> selectList() throws Exception;
}