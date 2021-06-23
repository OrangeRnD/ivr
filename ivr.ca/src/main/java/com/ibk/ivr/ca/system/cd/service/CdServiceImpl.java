package com.ibk.ivr.ca.system.cd.service;

import java.util.List;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.cd.service.CdService;
import com.ibk.ivr.ca.system.cd.vo.CdVO;
import com.ibk.ivr.ca.system.cd.dao.CdDAO;

/**
 * 시스템 코드 정보 service implements
 * table : TBCA_CD (시스템 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
//@Slf4j
@Service
public class CdServiceImpl implements CdService {
	
    @Autowired
    private CdDAO dao;
    
    public CdServiceImpl() {
    }
  
    /**
     * 시스템 코드 정보 등록
     *
     * @param vo 시스템 코드 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(CdVO vo) throws Exception {
        return dao.insert(vo);
    }
  
    /**
     * 시스템 코드 정보 수정
     *
     * @param vo 시스템 코드 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(CdVO vo) throws Exception {
        return dao.update(vo);
    }
  
    /**
     * 시스템 코드 정보 삭제
     *
     * @param vo 시스템 코드 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(CdVO vo) throws Exception {
        return dao.delete(vo);
    }

    /**
     * 시스템 코드 정보 조회
     *
     * @param cdId 코드_아이디
     * @return CdVO 시스템 코드 정보 vo
     * @throws Exception
     */
    @Override
    public CdVO select(int cdId) throws Exception {
        return dao.select(cdId);
    }
    
    /**
     * 코드분류에 해당하는 코드 조회
     * 
     * @param cdClsfctn
     * @return List<CdVO> 코드리스트
     * @throws Exception
     */
    public List<CdVO> selectListAll(String cdClsfctn) throws Exception {
    	return dao.selectListAll(cdClsfctn);
    }
}