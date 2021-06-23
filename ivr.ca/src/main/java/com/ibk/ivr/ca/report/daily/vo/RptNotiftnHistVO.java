package com.ibk.ivr.ca.report.daily.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 일일 보고서 발송 이력
 * table : TBCA_RPT_NOTIFTN_HIST
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 11/26/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class RptNotiftnHistVO extends DataVO {

	private static final long serialVersionUID = -1103557224965133056L;

	/**
     * column : SN
     * 일련번호
     */
    private int sn;
    
    /**
     * column : DT
     * 년월일
     */
    private String dt;

    /**
     * column : EMP_NR
     * 직원_번호
     */
    private String empNr;

    /**
     * column : SNDNG_ALTV
     * 발송_여부
     */
    private int sndngAltv;

    /**
     * column : OPRTR_ID
     * 처리자_아이디
     */
    private int oprtrId;

    /**
     * column : PRCS_DT
     * 처리_일시
     */
    private Date prcsDt;
    
    public RptNotiftnHistVO() {}
}
