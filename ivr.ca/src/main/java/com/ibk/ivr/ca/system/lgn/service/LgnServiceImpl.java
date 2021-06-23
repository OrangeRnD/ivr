package com.ibk.ivr.ca.system.lgn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.system.lgn.dao.LgnDAO;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LgnServiceImpl implements LgnService {

	@Autowired
	private LgnDAO dao;

	public LgnServiceImpl() {
	}

	/**
	 * 로그인 처리
	 *
     * @param empNr 직번
     * @param ip ip
     * @return UsrVO 사용자 vo
     * @throws Exception
     */
    public UsrVO sso(String empNr, String ip) throws Exception {
    	UsrVO result = dao.select(empNr);
		//아이디 존재여부
		if(result == null) {
			log.error("login id fail : empNr={}", empNr);
			return null;
		}
		
		result.setLgnDt(DateUtil.getDate());
		result.setLgnIp(ip);
		dao.login(result);
		
		return result;
	}

	/**
	 * 로그아웃 처리
	 *
	 * @param vo 로그아웃 정보
	 * @return int 수정건수
	 * @throws Exception
	 */
	@Override
	public int logout(UsrVO vo) throws Exception {
		return dao.logout(vo);
	}
}