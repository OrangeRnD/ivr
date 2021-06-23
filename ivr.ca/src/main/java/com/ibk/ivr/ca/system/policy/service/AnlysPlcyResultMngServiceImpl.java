package com.ibk.ivr.ca.system.policy.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.policy.dao.AnlysPlcyResultMngDAO;

/**
 * 분석 정책 결과 service
 * table : TBCA_ANLYS_PLCY_RSLT (분석_정책_결과)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/26/2019
 */
@Service
public class AnlysPlcyResultMngServiceImpl implements AnlysPlcyResultMngService {
	
    @Autowired
    private AnlysPlcyResultMngDAO dao;
    
    public AnlysPlcyResultMngServiceImpl() {
    }
    
    /**
     * 분석 정책 결과 알림 완료
     *
     * @param anlysPlcyRsltSn 분석_정책_결과_일련번호
     * @return int 수정건수
     * @throws Exception
     */
    public int updateNotifinSuccess(long anlysPlcyRsltSn) throws Exception {
    	return dao.updateNotifinSuccess(anlysPlcyRsltSn);
    }
    
    /**
     * 분석 정책 결과 알림 완료
     *
     * @param anlysPlcyRsltSn 분석_정책_결과_일련번호
     * @return int 수정건수
     * @throws Exception
     */
    public int updateNotifinFail(long anlysPlcyRsltSn) throws Exception {
    	return dao.updateNotifinFail(anlysPlcyRsltSn);
    }
  
    /**
     * 분석 정책 결과 알림 대상 조회
     *
     * @param occrrncDt 발생_일시
     * @return List<Map<String, Object>> 분석 정책 결과 알림 대상
     * @throws Exception
     */
    public List<Map<String, Object>> selectListForNotiftn(Date occrrncDt) throws Exception {
    	return dao.selectListForNotiftn(occrrncDt);
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
}