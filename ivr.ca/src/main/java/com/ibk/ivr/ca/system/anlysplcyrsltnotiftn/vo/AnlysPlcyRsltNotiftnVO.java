package com.ibk.ivr.ca.system.anlysplcyrsltnotiftn.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 정책 알림 사용자 알림 수신 관리 VO
 * table : TBCA_ANLYS_PLCY_RSLT_NOTIFTN (정책 알림 사용자 알림 수신 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class AnlysPlcyRsltNotiftnVO extends DataVO {

    private static final long serialVersionUID = -1772596117729727038L;

    /**
     * column : ANLYS_PLCY_RSLT_SN
     * 분석_정책_결과_일련번호
     */
    private long anlysPlcyRsltSn;

    /**
     * column : USR_ID
     * 사용자_아이디
     */
    private int usrId;

    /**
     * column : XMSN_CN
     * 전송_내용
     */
    private String xmsnCn;

    /**
     * column : SMS_XMSN_ALTV
     * SMS_전송_여부
     */
    private int smsXmsnAltv;

    /**
     * column : EMAIL_XMSN_ALTV
     * 이메일_전송_여부
     */
    private int emailXmsnAltv;

    /**
     * column : TELNO
     * 전화번호
     */
    private String telno;

    /**
     * column : EMAIL
     * 이메일
     */
    private String email;

    /**
     * column : PRCS_DT
     * 처리_일시
     */
    private Date prcsDt;

    public AnlysPlcyRsltNotiftnVO() {}
}