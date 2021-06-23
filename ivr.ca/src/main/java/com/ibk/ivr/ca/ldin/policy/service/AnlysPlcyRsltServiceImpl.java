package com.ibk.ivr.ca.ldin.policy.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.policy.dao.AnlysPlcyRsltDAO;

@Service
public class AnlysPlcyRsltServiceImpl implements AnlysPlcyRsltService {
	
    @Autowired
    private AnlysPlcyRsltDAO dao;
    
    public AnlysPlcyRsltServiceImpl() {
    }
  
    /**
     * 분석 정책 알림 발생 현황 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 분석 정책 알림 발생 현황
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
    	long size = dao.selectListCount(vo);
        if(size > 0) {
            vo.setSize(size);
            return dao.selectList(vo);
        }
        return null;
    }
  
    /**
     * 분석 정책 알림 발생 현황 상세 조회
     *
     * @param vo 분석정책아이디, 시작일자, 종료일자
     * @return List<Map<String, Object>> 분석 정책 알림 발생 현황 상세
     * @throws Exception
     */
    public List<Map<String, Object>> selectListDtl(RequestVO vo) throws Exception {
    	return dao.selectListDtl(vo);
    }
}
