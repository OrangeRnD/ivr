package com.ibk.ivr.ca.system.lgnhist.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 사용자 로그인 이력 정보 VO
 * table : TBCA_LGN_HIST (사용자 로그인 이력 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class LgnHistVO extends DataVO {

	private static final long serialVersionUID = -8213487954832539479L;

	/**
     * column : LGN_HIST_ID
     * 로그인_이력_아이디
     */
    private int lgnHistId;

    /**
     * column : EMP_NR
     * 직원_번호
     */
    private String empNr;

    /**
     * column : LGN_STS_SECD
     * 로그인_상태_시스템코드
0 : 로그오프
200 : 로그인
401 : 인증실패'
     */
    private String lgnStsSecd;

    /**
     * column : LGN_STS_SECD
     * 로그인_상태_시스템코드
0 : 로그오프
200 : 로그인
401 : 인증실패'
     */
    private String lgnStsSecdNm;
    
    /**
     * column : LGN_DT
     * 로그인_일시
     */
    private Date lgnDt;

    /**
     * column : LGN_IP
     * 로그인_IP
     */
    private String lgnIp;

    /**
     * column : USR_ID
     * 사용자_아이디
     */
    private int usrId;

    public LgnHistVO() {}
}