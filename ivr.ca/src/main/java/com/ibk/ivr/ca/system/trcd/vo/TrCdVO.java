package com.ibk.ivr.ca.system.trcd.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * TR 코드 VO
 * table : TBCA_TR_CD (TR 코드)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class TrCdVO extends DataVO {

	private static final long serialVersionUID = -1544891625623070603L;

	/**
     * column : TRAN_CD
     * TR_코드
     */
    private String tranCd;

    /**
     * column : TRAN_CD
     * TR_코드
     */
    private String oldTranCd;

    /**
     * column : TRAN_NM
     * TR_이름
     */
    private String tranNm;

    /**
     * column : REGN_DT
     * 등록_일시
     */
    private Date regnDt;

    public TrCdVO() {}
}