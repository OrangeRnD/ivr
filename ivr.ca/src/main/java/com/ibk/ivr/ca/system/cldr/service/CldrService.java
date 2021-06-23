package com.ibk.ivr.ca.system.cldr.service;

import java.util.List;

import com.ibk.ivr.ca.common.vo.RequestVO;

public interface CldrService {
	
	/**
	 * 특정 기간 일자 수
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    public Integer selectPeriod(RequestVO vo) throws Exception;
    
    /**
     * 특정 기간 일자 수 (비교 화면에서 사용)
     * @param vo
     * @return
     * @throws Exception
     */
    public List<Integer> selectPeriod2(RequestVO vo) throws Exception;
    
    /**
     * 달력 정보 등록
     * @param year
     * @return
     * @throws Exception
     */
    public int insert(int year) throws Exception;
}
