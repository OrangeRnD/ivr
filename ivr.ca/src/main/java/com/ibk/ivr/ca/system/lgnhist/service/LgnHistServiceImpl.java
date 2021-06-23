package com.ibk.ivr.ca.system.lgnhist.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.lgnhist.service.LgnHistService;
import com.ibk.ivr.ca.system.lgnhist.vo.LgnHistVO;
import com.ibk.ivr.ca.system.lgnhist.dao.LgnHistDAO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 사용자 로그인 이력 정보 service implements
 * table : TBCA_LGN_HIST (사용자 로그인 이력 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
//@Slf4j
@Service
public class LgnHistServiceImpl implements LgnHistService {
	
    @Autowired
    private LgnHistDAO dao;
    
    public LgnHistServiceImpl() {
    }
  
    /**
     * 사용자 로그인 이력 정보 등록
     *
     * @param vo 사용자 로그인 이력 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(LgnHistVO vo) throws Exception {
        return dao.insert(vo);
    }
    
    /**
     * 사용자 로그인 이력 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 사용자 로그인 이력 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
        long size = dao.selectListCount(vo);
        if(size > 0) {
            vo.setSize(size);
            return dao.selectList(vo);
        }
        return null;
    }
  
    /**
     * 사용자 로그인 이력 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 사용자 로그인 이력 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
}