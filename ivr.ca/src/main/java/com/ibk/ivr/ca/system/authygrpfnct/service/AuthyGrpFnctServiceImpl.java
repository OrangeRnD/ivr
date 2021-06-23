package com.ibk.ivr.ca.system.authygrpfnct.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.authygrpfnct.service.AuthyGrpFnctService;
import com.ibk.ivr.ca.system.authygrpfnct.vo.AuthyGrpFnctVO;
import com.ibk.ivr.ca.system.authygrpfnct.dao.AuthyGrpFnctDAO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 권한그룹 별 기능에 대한 권한 관리 service implements
 * table : TBCA_AUTHY_GRP_FNCT (권한그룹 별 기능에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
//@Slf4j
@Service
public class AuthyGrpFnctServiceImpl implements AuthyGrpFnctService {
	
    @Autowired
    private AuthyGrpFnctDAO dao;
    
    public AuthyGrpFnctServiceImpl() {
    }
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 등록
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(AuthyGrpFnctVO vo) throws Exception {
        return dao.insert(vo);
    }
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 수정
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(AuthyGrpFnctVO vo) throws Exception {
        return dao.update(vo);
    }
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 삭제
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(AuthyGrpFnctVO vo) throws Exception {
        return dao.delete(vo);
    }
    
    /**
     * 권한그룹 별 기능에 대한 권한 관리 리스트 삭제
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 데이타
     * @param ids 삭제데이터 ids
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int deletes(AuthyGrpFnctVO vo, String[] ids) throws Exception {
        int result = 0;
        for(String id : ids) {
            vo.setAuthyGrpCd(id);
            result += dao.delete(vo);
        }
        return result;
    }

    /**
     * 권한그룹 별 기능에 대한 권한 관리 조회
     *
     * @param authy_grp_cd 권한_그룹_코드
     * @return AuthyGrpFnctVO 권한그룹 별 기능에 대한 권한 관리 vo
     * @throws Exception
     */
    @Override
    public AuthyGrpFnctVO select(String authy_grp_cd) throws Exception {
        return dao.selectById(authy_grp_cd);
    }
    
    /**
     * 권한그룹 별 기능에 대한 권한 관리 조회
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 조회 조건
     * @return AuthyGrpFnctVO 권한그룹 별 기능에 대한 권한 관리 vo
     * @throws Exception
     */
    @Override
    public AuthyGrpFnctVO select(AuthyGrpFnctVO vo) throws Exception {
        return dao.select(vo);
    }
    
    /**
     * 권한그룹 별 기능에 대한 권한 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 권한그룹 별 기능에 대한 권한 관리 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
        if(vo.getPageNo() == 0) {
            List<Map<String, Object>> list = dao.selectList(vo);
            vo.setSize(list.size());
            return list;
        }
            
        long size = dao.selectListCount(vo);
        if(size > 0) {
            vo.setSize(size);
            return dao.selectList(vo);
        }
        return null;
    }
  
    /**
     * 권한그룹 별 기능에 대한 권한 관리 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 권한그룹 별 기능에 대한 권한 관리 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
}