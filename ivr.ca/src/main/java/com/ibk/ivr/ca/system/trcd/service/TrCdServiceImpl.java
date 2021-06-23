package com.ibk.ivr.ca.system.trcd.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.trcd.service.TrCdService;
import com.ibk.ivr.ca.system.trcd.vo.TrCdVO;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.trcd.dao.TrCdDAO;

/**
 * TR 코드 service implements
 * table : TBCA_TR_CD (TR 코드)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
//@Slf4j
@Service
public class TrCdServiceImpl implements TrCdService {
	
    @Autowired
    private TrCdDAO dao;
    
    public TrCdServiceImpl() {
    }
  
    /**
     * TR 코드 정보 등록
     *
     * @param vo TR 코드 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(TrCdVO vo) throws Exception {
    	return dao.insert(vo);
    }
  
    /**
     * TR 코드 정보 수정
     *
     * @param vo TR 코드 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(TrCdVO vo) throws Exception {
    	return dao.update(vo);
    }
  
    /**
     * TR 코드 정보 삭제
     *
     * @param vo TR 코드 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(TrCdVO vo) throws Exception {
    	return dao.delete(vo);
    }

    /**
     * TR 코드 정보 조회
     *
     * @param tranCd TR 코드
     * @return CdVO TR 코드 정보 vo
     * @throws Exception
     */
    @Override
    public TrCdVO select(String tranCd) throws Exception {
    	return dao.select(tranCd);
    }
    
    /**
     * TR 코드 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> TR 코드 데이타
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
     * TR 코드 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> TR 코드 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
  
    /**
     * tr 검색
     *
     * @param nm 서비스이름
     * @return List<Map<String, Object>> tr 코드 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectFind(String nm) throws Exception {
    	return dao.selectFind(nm);
    }
}