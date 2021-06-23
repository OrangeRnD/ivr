package com.ibk.ivr.ca.system.authygrpmenu.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 권한 그룹 별 메뉴에 대한 권한 관리 VO
 * table : TBCA_AUTHY_GRP_MENU (권한 그룹 별 메뉴에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class AuthyGrpMenuVO extends DataVO {

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
     * column : MENU_ID
     * 메뉴_아이디
     */
    private int menuId;

    /**
     * column : DEL_ALTV
     * 삭제_여부
     */
    @JsonIgnore
    private int delAltv;

    /**
     * column : REGR_ID
     * 등록자_아이디
     */
    @JsonIgnore
    private int regrId;

    /**
     * column : REGN_DT
     * 등록_일시
     */
    @JsonIgnore
    private Date regnDt;

    /**
     * column : LST_UPDUSR_ID
     * 최종_수정자_아이디
     */
    @JsonIgnore
    private int lstUpdusrId;

    /**
     * column : LST_UPDT_DT
     * 최종_수정_일시
     */
    @JsonIgnore
    private Date lstUpdtDt;

    /**
     * column : CHG_CNT
     * 변경_건수
     */
    @JsonIgnore
    private int chgCnt;

    public AuthyGrpMenuVO() {}
}