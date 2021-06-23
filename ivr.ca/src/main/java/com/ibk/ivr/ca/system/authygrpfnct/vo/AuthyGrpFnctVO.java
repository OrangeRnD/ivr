package com.ibk.ivr.ca.system.authygrpfnct.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 권한그룹 별 기능에 대한 권한 관리 VO
 * table : TBCA_AUTHY_GRP_FNCT (권한그룹 별 기능에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class AuthyGrpFnctVO extends DataVO {

    private static final long serialVersionUID = -1772596117729727038L;

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

    /**
     * column : FNCT_SECD
     * 기능_구분코드
1 : 이용자등록
2 : 시스템설정
3 : 정책설정
4 : 보고서생성
5 : 상세보기
6 : PDF 다운
7 : excel다운
8 : 보고서출력
9 : 화면출력'
     */
    private String fnctSecd;

    /**
     * column : FNCT_SECD
     * 기능_구분코드
1 : 이용자등록
2 : 시스템설정
3 : 정책설정
4 : 보고서생성
5 : 상세보기
6 : PDF 다운
7 : excel다운
8 : 보고서출력
9 : 화면출력'
     */
    private String fnctSecdNm;
    
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
     * column : LST_UPDT_DT
     * 최종_수정_일시
     */
    private Date lstUpdtDt;

    /**
     * column : CHG_CNT
     * 변경_건수
     */
    private int chgCnt;

    public AuthyGrpFnctVO() {}
}