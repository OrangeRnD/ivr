package com.ibk.ivr.ca.use.tr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.use.tr.dao.UseTrDAO;

/**
 * 거래 검색 service
 * 
 * table : TBCA_IVR_LOG (IVR_로그)
 *          TBCA_IVR_TR_RCPTN_LOG (IVR_TR_수신_로그)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/27/2019
 */
@Service
public class UseTrServiceImpl implements UseTrService {
	
	@Autowired
	private UseTrDAO dao;

    /**
     * TR 검색
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> tr 또는 서비스
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
