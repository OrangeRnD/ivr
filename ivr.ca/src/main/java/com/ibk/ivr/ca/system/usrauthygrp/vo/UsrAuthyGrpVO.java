package com.ibk.ivr.ca.system.usrauthygrp.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 사용자 별 권한 그룹 장보 VO
 * table : TBCA_USR_AUTHY_GRP (사용자 별 권한 그룹 장보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/15/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class UsrAuthyGrpVO extends DataVO {

    private static final long serialVersionUID = -1772596117729727038L;

    /**
     * column : USR_ID
     * 사용자_아이디
     */
    private int usrId;

    /**
     * column : SN
     * 일련번호
     */
    private int sn;

    /**
     * column : AUTHY_GRP_CD
     * 권한_그룹_코드
     */
    private String authyGrpCd;

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

    public UsrAuthyGrpVO() {}
}