package com.ibk.ivr.ca.ldin.policy.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 분석 정책 설정 정보 VO
 * 
 * table : TBCA_ANLYS_PLCY (분석_정책)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/26/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class AnlysPlcyVO extends DataVO {

    private static final long serialVersionUID = -1772596117729727038L;

    /**
     * column : ANLYS_PLCY_ID
     * 분석_정책_아이디
     */
    private int anlysPlcyId;

    /**
     * column : ANLYS_PLCY_SECD
     * 알림_정책_구분코드
1 : 인입 실시간 추이
     */
    private String anlysPlcySecd;

    /**
     * column : ANLYS_PLCY_SECD
     * 알림_정책_구분코드
1 : 인입 실시간 추이
     */
    private String anlysPlcySecdNm;
    
    /**
     * column : ANLYS_PLCY_NM
     * 분석_정책_이름
     */
    private String anlysPlcyNm;

    /**
     * column : PRD_SECD
     * 기간_구부코드
1 : 전일대
2 : 전주평균
3 : 전월평균
4 : 전분기평균
5 : 전주 동일요일
6 :전월 동일일자
7 : 전분기 동일차월
     */
    private String prdSecd;

    /**
     * column : PRD_SECD
     * 기간_구부코드
1 : 전일대
2 : 전주평균
3 : 전월평균
4 : 전분기평균
5 : 전주 동일요일
6 :전월 동일일자
7 : 전분기 동일차월
     */
    private String prdSecdNm;

    /**
     * column : BIZDT_SECD
     * 영업일_구분_코드
1 : 전체
2 : 영입일만
3 : 휴일만'
     */
    private String bizdtSecd;

    /**
     * column : BIZDT_SECD
     * 영업일_구분_코드
1 : 전체
2 : 영입일만
3 : 휴일만'
     */
    private String bizdtSecdNm;

    /**
     * column : EXCLSN_STDVAL
     * 제외_기준값
     */
    private int exclsnStdval;

    /**
     * column : NOTIFTN_STDVAL
     * 알림_기준값
     */
    private int notiftnStdval;
    
    /**
     * column : SMS_XMSN_ALTV
     * sms_전송_여부
     */
    private int smsXmsnAltv;
    
    /**
     * column : EMAIL_XMSN_ALTV
     * 이메일_전송_여부
     */
    private int emailXmsnAltv;

    /**
     * column : SMS_XMSN_CN
     * SMS_전송_내용
     */
    private String smsXmsnCn;

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

    public AnlysPlcyVO() {}
}