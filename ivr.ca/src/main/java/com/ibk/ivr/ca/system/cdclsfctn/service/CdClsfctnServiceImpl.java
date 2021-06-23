package com.ibk.ivr.ca.system.cdclsfctn.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.cdclsfctn.service.CdClsfctnService;
import com.ibk.ivr.ca.system.cdclsfctn.vo.CdClsfctnVO;
import com.ibk.ivr.ca.system.cdclsfctn.dao.CdClsfctnDAO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 시스템 코드분류 정보 service implements
 * table : TBCA_CD_CLSFCTN (시스템 코드분류 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
//@Slf4j
@Service
public class CdClsfctnServiceImpl implements CdClsfctnService {
	
    @Autowired
    private CdClsfctnDAO dao;
    
    public CdClsfctnServiceImpl() {
    }
  
    /**
     * 시스템 코드분류 정보 등록
     *
     * @param vo 시스템 코드분류 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(CdClsfctnVO vo) throws Exception {
    	if(dao.select(vo.getCdClsfctn()) != null) {
    		return -1;
    	}
        return dao.insert(vo);
    }
  
    /**
     * 시스템 코드분류 정보 수정
     *
     * @param vo 시스템 코드분류 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(CdClsfctnVO vo) throws Exception {
        return dao.update(vo);
    }
  
    /**
     * 시스템 코드분류 정보 삭제
     *
     * @param vo 시스템 코드분류 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(CdClsfctnVO vo) throws Exception {
        return dao.delete(vo);
    }

    /**
     * 시스템 코드분류 정보 조회
     *
     * @param cdClsfctn 코드_분류
     * @return CdClsfctnVO 시스템 코드분류 정보 vo
     * @throws Exception
     */
    @Override
    public CdClsfctnVO select(String cdClsfctn) throws Exception {
        return dao.select(cdClsfctn);
    }
    
    /**
     * 시스템 코드분류 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 코드분류 정보 데이타
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
}