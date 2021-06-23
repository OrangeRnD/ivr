package com.ibk.ivr.ca.system.lgn.service;

import com.ibk.ivr.ca.system.usr.vo.UsrVO;

public interface LgnService {
    
    /**
     * 로그인
     *
     * @param empNr 직번
     * @param ip ip
     * @return UsrVO 사용자 vo
     * @throws Exception
     */
    public UsrVO sso(String empNr, String ip) throws Exception;
    
    /**
	 * 로그아웃 처리
	 *
	 * @param vo 로그아웃 정보
	 * @return int 수정건수
	 * @throws Exception
	 */
	public int logout(UsrVO vo) throws Exception;
}