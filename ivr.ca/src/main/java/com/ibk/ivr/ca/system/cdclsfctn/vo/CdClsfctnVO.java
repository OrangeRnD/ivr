package com.ibk.ivr.ca.system.cdclsfctn.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 시스템 코드분류 정보 VO
 * table : TBCA_CD_CLSFCTN (시스템 코드분류 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class CdClsfctnVO extends DataVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6157878893541854532L;

	/**
     * column : CD_CLSFCTN
     * 코드_분류
     */
    private String cdClsfctn;

    /**
     * column : CD_CLSFCTN_NM
     * 코드_분류_이름
     */
    private String cdClsfctnNm;

    /**
     * column : RM
     * 비고
     */
    private String rm;

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
     * 등록자_이름
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
     * 최종_수정자_이름
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

    public CdClsfctnVO() {}
}