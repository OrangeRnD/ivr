package com.ibk.ivr.ca.ldin.progress.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.ldin.progress.dao.LdinProgressDAO;

/**
 * 인입추이 - 일일, 월별 통계 service impl
 * 
 * table : TBCA_DLY_LDIN_STAT (일별_인입_현황)
 *         TBCA_MLY_LDIN_STAT (분별_인입_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Service
public class LdinProgressServiceImpl implements LdinProgressService {
	
    @Autowired
    private LdinProgressDAO dao;
    
    public LdinProgressServiceImpl() {
    }
    
    /**
     * 일별인입추이
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 일별인입추이
     * @throws Exception
     */
    public List<Map<String, Object>> selectListDlyLdinStat(RequestVO vo) throws Exception {
    	return dao.selectListDlyLdinStat(vo);
    }
    
    /**
     * 월별인입추이
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 월별인입추이
     * @throws Exception
     */
    public List<Map<String, Object>> selectListMlyLdinStat(RequestVO vo) throws Exception {
    	return dao.selectListMlyLdinStat(vo);
    }
}
