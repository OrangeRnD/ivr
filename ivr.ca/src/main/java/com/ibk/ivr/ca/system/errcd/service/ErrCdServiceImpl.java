package com.ibk.ivr.ca.system.errcd.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.errcd.service.ErrCdService;
import com.ibk.ivr.ca.system.errcd.dao.ErrCdDAO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 에러 코드 정보 service implements
 * table : TBCA_ERR_CD (에러 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
//@Slf4j
@Service
public class ErrCdServiceImpl implements ErrCdService {
	
    @Autowired
    private ErrCdDAO dao;
    
    public ErrCdServiceImpl() {
    }
    
    /**
     * 에러 코드 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 에러 코드 정보 데이타
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
     * 에러 코드 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 에러 코드 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
}