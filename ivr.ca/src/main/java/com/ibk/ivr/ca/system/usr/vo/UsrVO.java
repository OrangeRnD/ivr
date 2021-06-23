package com.ibk.ivr.ca.system.usr.vo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 시스템 사용자 정보 VO
 * table : TBCA_USR (시스템 사용자 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class UsrVO extends DataVO {

	private static final long serialVersionUID = 6843102611538214203L;

	/**
     * column : USR_ID
     * 사용자_아이디
     */
    private int usrId;

    /**
     * column : EMP_NR
     * 직원_번호
     */
    private String empNr;

    /**
     * column : EMP_NM
     * 직원_이름
     */
    private String empNm;

    /**
     * OTP 번호
     */
    private String pswd;
    
    /**
     * column : SMS_NOTIFTN_XMSN_ALTV
     * SMS_알림_수신_여부
     */
    private int smsNotiftnXmsnAltv;

    /**
     * column : EMAIL_NOTIFTN_XMSN_ALTV
     * 이메일_알림_수신_여부
     */
    private int emailNotiftnXmsnAltv;
    
    /**
     * column : EMAIL
     * 이메일
     */
    private String email;

    /**
     * column : TELNO
     * 전화번호
     */
    private String telno;

    /**
     * column : DLY_RPT_RCPTN_ALTV
     * 일일_보고서_수신_여부
     */
    private int dlyRptRcptnAltv;

    /**
     * column : LST_LGN_DT
     * 최종_로그인_일시
     */
    private Date lstLgnDt;

    /**
     * column : LST_LOGOUT_DT
     * 최종_로그아웃_일시
     */
    private Date lstLogoutDt;

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
     * column : LST_LGN_IP
     * 최종_로그인_IP
     */
    private String lstLgnIp;

    /**
     * column : LGN_CNT
     * 로그인_횟수
     */
    private int lgnCnt;

    /**
     * column : USE_ALTV
     * 사용_여부
     */
    private int useAltv;

    /**
     * column : DEL_ALTV
     * 삭제_여부
     */
    private int delAltv;

    /**
     * column : REGR_ID
     * 등록자_아이디
     */
    private int regrId;

    /**
     * column : REGR_ID
     * 등록자_아이디
     */
    private String regrNm;

    /**
     * column : REGN_DT
     * 등록_일시
     */
    private Date regnDt;

    /**
     * column : LST_UPDUSR_ID
     * 최종_수정자_아이디
     */
    private int lstUpdusrId;

    /**
     * column : LST_UPDUSR_ID
     * 최종_수정자_아이디
     */
    private String lstUpdusrNm;

    /**
     * column : LST_UPDT_DT
     * 최종_수정_일시
     */
    private Date lstUpdtDt;

    /**
     * column : CHG_CNT
     * 변경_건수
     */
    private int chgCnt;

    /**
     * column : AUTHY_GRP_CD
     * 권한_그룹_코드
     */
    private String authyGrpCd;

    /**
     * column : SN
     * 일련번호
     */
    private int sn;
    
    @JsonIgnore
    private Map<String, Object> param;
    
    public Map<String, Object> getParam() {
    	if(param == null) 
    		param = new HashMap<String, Object>();
    	return param;
    }

    public UsrVO() {}
}