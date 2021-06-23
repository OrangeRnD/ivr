package com.ibk.ivr.ca.system.cldr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.cldr.vo.CldrVO;

@Repository
public interface CldrDAO {
	
    public String selectExists(String de) throws Exception;
    
    public int insert(CldrVO vo) throws Exception;
	
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
}
