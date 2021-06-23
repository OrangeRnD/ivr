package com.ibk.ivr.ca.system.authygrpmenu.service;

import java.util.Date;
import java.util.List;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.authygrpmenu.service.AuthyGrpMenuService;
import com.ibk.ivr.ca.system.authygrpmenu.vo.AuthyGrpMenuVO;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.system.authygrpmenu.dao.AuthyGrpMenuDAO;

/**
 * 권한 그룹 별 메뉴에 대한 권한 관리 service implements
 * table : TBCA_AUTHY_GRP_MENU (권한 그룹 별 메뉴에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
//@Slf4j
@Service
public class AuthyGrpMenuServiceImpl implements AuthyGrpMenuService {
	
    @Autowired
    private AuthyGrpMenuDAO dao;
    
    public AuthyGrpMenuServiceImpl() {
    }
  
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 등록
     *
     * @param vo 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @param regrId 등록자 id
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(List<AuthyGrpMenuVO> list, int regrId) throws Exception {
    	Date date = DateUtil.getDate();
    	int result = 0;
    	for(AuthyGrpMenuVO vo : list) {
    		vo.setRegrId(regrId);
    		vo.setRegnDt(date);
    		if(vo.getSn() != 0)
    			dao.delete(vo);
    		else
    			dao.insert(vo);
    	}
        return result;
    }
    
    /**
     * 권한 그룹 별 메뉴에 대한 권한 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<AuthyGrpMenuVO> 권한 그룹 별 메뉴에 대한 권한 관리 데이타
     * @throws Exception
     */
    @Override
    public List<AuthyGrpMenuVO> selectList() throws Exception {
        return dao.selectList();
    }
}