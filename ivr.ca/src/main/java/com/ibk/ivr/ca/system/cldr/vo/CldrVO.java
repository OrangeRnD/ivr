package com.ibk.ivr.ca.system.cldr.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 시스템 사용자 정보 VO
 * table : TBCA_USR (시스템 사용자 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class CldrVO extends DataVO {

	private static final long serialVersionUID = 4272554263808669672L;

	/**
     * column : DT
     * 날짜
     */
    private String dt;

    /**
     * column : DT_SECD
     * java.util.Calendar.SUNDAY 참조
     * 날짜_구분코드
     * 1 : 일요일
     * 2 : 월요일
     * 3 : 화요일
     * 4 : 수요일
     * 5 : 목요일
     * 6 : 금요일
     * 7 : 토요일
     */
    private String dtSecd;

    /**
     * column : RESTDE_ALTV
     * 휴일_여부
     */
    private int restdeAltv;

    /**
     * column : REGN_DT
     * 등록_일시
     */
    private Date regnDt;

    public CldrVO() {}
}