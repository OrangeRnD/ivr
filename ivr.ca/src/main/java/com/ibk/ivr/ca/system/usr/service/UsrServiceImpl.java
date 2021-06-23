package com.ibk.ivr.ca.system.usr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.usr.service.UsrService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.system.usrauthygrp.dao.UsrAuthyGrpDAO;

import com.ibk.ivr.ca.system.usr.dao.UsrDAO;
import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.exception.ApplicationException;
import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 시스템 사용자 정보 service implements
 * table : TBCA_USR (시스템 사용자 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Service
public class UsrServiceImpl implements UsrService {

    @Autowired
    private UsrDAO dao;
    
    @Autowired
    private UsrAuthyGrpDAO usrAuthyGrpDAO;
    
    public UsrServiceImpl() {
    }
  
    /**
     * 시스템 사용자 정보 등록
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(UsrVO vo) throws Exception {
    	if(dao.selectExists(vo) != null)
    		throw new ApplicationException(HttpServletResponse.SC_CONFLICT, Constants.ERROR_INSERT);
    	
		int result = dao.insert(vo);
        if(result == 1)
        	result = usrAuthyGrpDAO.insert(vo);

        return result;
    }
  
    /**
     * 시스템 사용자 정보 수정
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(UsrVO vo) throws Exception {
    	if(dao.selectExists(vo) != null)
    		throw new ApplicationException(HttpServletResponse.SC_CONFLICT, Constants.ERROR_INSERT);
    	
    	if(usrAuthyGrpDAO.delete(vo) != 0) {
    		vo.setRegrId(vo.getLstUpdusrId());
    		vo.setRegnDt(vo.getLstUpdtDt());
    		usrAuthyGrpDAO.insert(vo);
    	}

		int result = dao.update(vo);
		
		return result;
    }
  
    /**
     * SSO 최초 로그인 시 사용자 정보 수정
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int updateForSSO(UsrVO vo) throws Exception {
    	return dao.updateForSSO(vo);
    }
	  
    /**
     * 사용자 본인정보 수정
     *
     * @param vo 사용자관리 데이타
     * @return int 수정건수
     * @throws Exception
     */
	@Override
	public int updateMyUsr(UsrVO vo) throws Exception {
		return dao.update(vo);
	}
  
    /**
     * 시스템 사용자 정보 삭제
     *
     * @param vo 시스템 사용자 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(UsrVO vo) throws Exception {
    	vo.setAuthyGrpCd("-1");
    	usrAuthyGrpDAO.delete(vo);
        return dao.delete(vo);
    }

    /**
     * 시스템 사용자 정보 조회
     *
     * @param usr_id 사용자_아이디
     * @return UsrVO 시스템 사용자 정보 vo
     * @throws Exception
     */
    @Override
    public UsrVO select(int usrId) throws Exception {
        return dao.select(usrId);
    }
    
    /**
     * 시스템 사용자 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 사용자 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
    	List<Map<String, Object>> list = dao.selectList(vo);
        vo.setSize(list.size());
        return list;
    }
  
    /**
     * 정책 알림을 위한 시스템 사용자 정보 리스트조회
     *
     * @return List<UsrVO> 시스템 사용자 정보 데이타
     * @throws Exception
     */
    @Override
    public List<UsrVO> selectListForPlcy() throws Exception {
    	return dao.selectListForPlcy();
    }
  
    /**
     * 일일보고서 알림을 위한 시스템 사용자 정보 리스트조회
     *
     * @param currentDt 현재 일자
     * @return List<UsrVO> 시스템 사용자 정보 데이타
     * @throws Exception
     */
    @Override
    public List<UsrVO> selectListForReport(String currentDt) throws Exception {
    	return dao.selectListForReport(currentDt);
    }
}