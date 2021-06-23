package com.ibk.ivr.ca.system.cldr.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.exception.ApplicationException;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.cldr.dao.CldrDAO;
import com.ibk.ivr.ca.system.cldr.vo.CldrVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CldrServiceImpl implements CldrService {
	
	@Autowired
    private CldrDAO dao;
	
	public CldrServiceImpl() {
	}

	/**
	 * 특정 기간 일자 수
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public Integer selectPeriod(RequestVO vo) throws Exception {
		return dao.selectPeriod(vo);
	}

    /**
     * 특정 기간 일자 수 (비교 화면에서 사용)
     * @param vo
     * @return
     * @throws Exception
     */
	@Override
	public List<Integer> selectPeriod2(RequestVO vo) throws Exception {
		return dao.selectPeriod2(vo);
	}

    /**
     * 달력 정보 등록
     * @param year
     * @return
     * @throws Exception
     */
	@Override
	public int insert(int year) throws Exception {
		if(dao.selectExists(Integer.toString(year).concat("%")) != null)
    		return 365;
		
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		
    	c.set(year, 0, 0);
        c.set(Calendar.HOUR, 10);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        
        int result = 0;
        CldrVO vo = new CldrVO();
    	vo.setRestdeAltv(0);
    	vo.setRegnDt(date);
        for(int i = 0; i < 366; i++) {
        	c.add(Calendar.DATE, 1);
        	if(i == 365) {
        		if(c.get(Calendar.YEAR) != year)
        			break;
        	}
        	vo.setDt(DateUtil.yyyyMMdd.format(c.getTime()));
        	vo.setDtSecd(String.valueOf(c.get(Calendar.DAY_OF_WEEK)));
        	result += dao.insert(vo);
        }
        if(log.isDebugEnabled())
        	log.debug("calendar added {}", result);
        if(result < 365)
        	throw new ApplicationException(Constants.ERROR_INSERT);
		return 1;
	}
}
