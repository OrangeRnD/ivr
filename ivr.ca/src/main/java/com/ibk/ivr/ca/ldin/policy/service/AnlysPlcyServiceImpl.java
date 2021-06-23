package com.ibk.ivr.ca.ldin.policy.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.exception.ApplicationException;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.policy.dao.AnlysPlcyDAO;
import com.ibk.ivr.ca.ldin.policy.service.AnlysPlcyService;
import com.ibk.ivr.ca.ldin.policy.vo.AnlysPlcyVO;

/**
 * 분석 정책 설정 정보 service implements
 * table : TBCA_ANLYS_PLCY (분석 정책 설정 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/26/2019
 */
//@Slf4j
@Service
public class AnlysPlcyServiceImpl implements AnlysPlcyService {
	
    @Autowired
    private AnlysPlcyDAO dao;
    
    public AnlysPlcyServiceImpl() {
    }
  
    /**
     * 분석 정책 설정 정보 등록
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(AnlysPlcyVO vo) throws Exception {
    	vo.setAnlysPlcyId(0);
    	if(dao.selectExists(vo) != null)
    		throw new ApplicationException(HttpServletResponse.SC_CONFLICT, Constants.ERROR_INSERT);
        return dao.insert(vo);
    }
  
    /**
     * 분석 정책 설정 정보 수정
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(AnlysPlcyVO vo) throws Exception {
    	if(dao.selectExists(vo) != null)
    		throw new ApplicationException(HttpServletResponse.SC_CONFLICT, Constants.ERROR_INSERT);
    	return dao.update(vo);
    }
  
    /**
     * 분석 정책 설정 정보 삭제
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(AnlysPlcyVO vo) throws Exception {
        return dao.delete(vo);
    }
    
    /**
     * 분석 정책 설정 정보 리스트 삭제
     *
     * @param vo 분석 정책 설정 정보 데이타
     * @param ids 삭제데이터 ids
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int deletes(AnlysPlcyVO vo, int[] ids) throws Exception {
        int result = 0;
        for(int id : ids) {
            vo.setAnlysPlcyId(id);
            result += dao.delete(vo);
        }
        return result;
    }

    /**
     * 분석 정책 설정 정보 조회
     *
     * @param anlysPlcyId 분석_정책_아이디
     * @return AnlysPlcyVO 분석 정책 설정 정보 vo
     * @throws Exception
     */
    @Override
    public AnlysPlcyVO select(int anlysPlcyId) throws Exception {
        return dao.select(anlysPlcyId);
    }
    
    /**
     * 분석 정책 설정 정보 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 분석 정책 설정 정보 데이타
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
     * 분석 정책 설정 정보 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 분석 정책 설정 정보 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
}