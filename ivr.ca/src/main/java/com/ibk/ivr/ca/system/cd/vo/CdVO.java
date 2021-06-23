package com.ibk.ivr.ca.system.cd.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 시스템 코드 정보 VO
 * table : TBCA_CD (시스템 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class CdVO extends DataVO {

	private static final long serialVersionUID = -339825118295058574L;

	/**
     * column : CD_ID
     * 코드_아이디
     */
    private int cdId;

    /**
     * column : CD_CLSFCTN
     * 코드_분류
     */
    private String cdClsfctn;

    /**
     * column : CD
     * 코드
     */
    private String cd;

    /**
     * column : CD_NM
     * 코드_이름
     */
    private String cdNm;

    /**
     * column : SORT_ORDR
     * 정렬_순서
     */
    private int sortOrdr;

    /**
     * column : RM
     * 비고
     */
    private String rm;

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

    public CdVO() {}
}