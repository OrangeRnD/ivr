package com.ibk.ivr.ca.system.menu.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 시스템 메뉴 관리 VO
 * table : TBCA_MENU (시스템 메뉴 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class MenuVO extends DataVO {

    private static final long serialVersionUID = -1772596117729727038L;

    /**
     * column : MENU_ID
     * 메뉴_아이디
     */
    private int menuId;

    /**
     * column : MENU_NM
     * 메뉴_이름
     */
    private String menuNm;

    /**
     * column : MENU_URL
     * 메뉴_URL
     */
    private String menuUrl;

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

    public MenuVO() {}
}