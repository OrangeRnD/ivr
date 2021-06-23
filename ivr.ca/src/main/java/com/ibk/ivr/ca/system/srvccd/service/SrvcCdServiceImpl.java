package com.ibk.ivr.ca.system.srvccd.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.srvccd.service.SrvcCdService;
import com.ibk.ivr.ca.system.srvccd.vo.SrvcCdVO;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.srvccd.dao.SrvcCdDAO;

/**
 * 서비스 코드 정보 service implements
 * table : TBCA_SRVC_CD (서비스 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
//@Slf4j
@Service
public class SrvcCdServiceImpl implements SrvcCdService {
	
    @Autowired
    private SrvcCdDAO dao;
    
    public SrvcCdServiceImpl() {
    }
  
    /**
     * 서비스 코드 정보 등록
     *
     * @param vo 서비스 코드 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(SrvcCdVO vo) throws Exception {
    	return dao.insert(vo);
    }
  
    /**
     * 서비스 코드 정보 수정
     *
     * @param vo 서비스 코드 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(SrvcCdVO vo) throws Exception {
    	return dao.update(vo);
    }
  
    /**
     * 서비스 코드 정보 삭제
     *
     * @param vo 서비스 코드 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(SrvcCdVO vo) throws Exception {
    	return dao.delete(vo);
    }

    /**
     * 서비스 코드 정보 조회
     *
     * @param srvcCd 서비스코드
     * @return CdVO 서비스 코드 정보 vo
     * @throws Exception
     */
    public SrvcCdVO select(String srvcCd) throws Exception {
    	return dao.select(srvcCd);
    }
    
    /**
     * 서비스 코드 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
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
     * 서비스 코드 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
  
    /**
     * 서비스 검색
     *
     * @param nm 서비스이름
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectFind(String nm) throws Exception {
        return dao.selectFind(nm);
    }
  
    /**
     * 세션에 있는 조회 조건 서비스 코드 조회
     *
     * @param vo 서비스 코드
     * @return List<Map<String, Object>> 서비스 코드 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectListForSession(Map<?, ?> vo) throws Exception {
    	return dao.selectListForSession(vo);
    }
}