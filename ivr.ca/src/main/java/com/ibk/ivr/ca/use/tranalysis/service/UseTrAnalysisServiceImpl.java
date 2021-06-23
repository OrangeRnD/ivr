package com.ibk.ivr.ca.use.tranalysis.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.use.tranalysis.dao.UseTrAnalysisDAO;

/**
 * 실시간 거래 발생 비교 service
 * 
 * table : TBCA_5MLY_LDIN_STAT (5분별_인입현황_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/07/2019
 */
@Service
public class UseTrAnalysisServiceImpl implements UseTrAnalysisService {
	
	@Autowired
	private UseTrAnalysisDAO dao;
	
	/**
     * 실시간 5분단위 거래 및 비교일자 5분단위 또는 5분 평균 거래 건수 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 5분단위 거래 및 비교일자 5분단위 또는 5분 평균 거래 건수
     * @throws Exception
     */
	@Override
	public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
		if(vo.getParam().get("AVG_ABOVE") != null && !vo.getParam().get("AVG_ABOVE").equals("")) {
			Integer dtCnt = dao.selectDtCnt(vo);
			if(dtCnt == null) {
				//FROM_DT1, TO_DT1
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parseDate((String)vo.getParam().get("TO_DT"), "yyyyMMdd"));
				long toDt = c.getTimeInMillis();

				c.setTime(DateUtil.parseDate((String)vo.getParam().get("FROM_DT"), "yyyyMMdd"));
				long fromDt = c.getTimeInMillis();
				
				dtCnt = (int)((toDt - fromDt)/1000.0/60.0/60.0/24.0);
			}
			
			//상하 n % 계산 (무조건 올림으로 계산하기, 10일중에 상하 10% 는 2,9이나)
			double avgAbove = Double.parseDouble((String)vo.getParam().get("AVG_ABOVE"));
			int limit = (int)Math.ceil(dtCnt*avgAbove/100);

			if(dtCnt-(limit*2) < 1) 
				return null;
			
			StringBuilder sb = new StringBuilder("");
			sb.append(limit);
			sb.append(", ");
			sb.append(dtCnt-(limit)*2);
			vo.setLimit(sb.toString());
		}
		return dao.selectList(vo);
	}
	
	/**
	 * 평균 조회 시 해당 기간 최대 건수 일자 조회
	 * 
	 * @param vo
	 * @return 최대 건수 일자
	 * @throws Exception
	 */
    public String selectMaxDt(RequestVO vo) throws Exception {
    	return dao.selectMaxDt(vo);
    }

	/**
     * 실시간 5분단위 거래 및 비교일자 5분단위 또는 5분 평균 거래 건수 엑셀 조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 실시간 5분단위 거래 건수 및 비교일자 5분단위 또는 5분 평균 거래 건수
     * @throws Exception
     */
    public List<Map<String, Object>> selectListExcel(RequestVO vo) throws Exception {
		if(vo.getParam().get("AVG_ABOVE") != null && !vo.getParam().get("AVG_ABOVE").equals("")) {
			Integer dtCnt = dao.selectDtCnt(vo);
			if(dtCnt == null) {
				//FROM_DT1, TO_DT1
				Calendar c = Calendar.getInstance();
				c.setTime(DateUtil.parseDate((String)vo.getParam().get("TO_DT"), "yyyyMMdd"));
				long toDt = c.getTimeInMillis();

				c.setTime(DateUtil.parseDate((String)vo.getParam().get("FROM_DT"), "yyyyMMdd"));
				long fromDt = c.getTimeInMillis();
				
				dtCnt = (int)((toDt - fromDt)/1000.0/60.0/60.0/24.0);
			}
			
			//상하 n % 계산 (무조건 올림으로 계산하기, 10일중에 상하 10% 는 2,9이나)
			double avgAbove = Double.parseDouble((String)vo.getParam().get("AVG_ABOVE"));
			int limit = (int)Math.ceil(dtCnt*avgAbove/100);

			if(dtCnt-(limit*2) < 1) 
				return null;
			
			StringBuilder sb = new StringBuilder("");
			sb.append(limit);
			sb.append(", ");
			sb.append(dtCnt-(limit)*2);
			vo.setLimit(sb.toString());
		}
		return dao.selectListExcel(vo);
    }
}
