package com.ibk.ivr.ca.system.errcd.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 에러 코드 정보 VO
 * table : TBCA_ERR_CD (에러 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class ErrCdVO extends DataVO {

	private static final long serialVersionUID = -1578848502035695868L;

	/**
     * column : ERR_CD
     * 에러_코드
     */
    private String errCd;

    /**
     * column : ERR_CN
     * 에러_내용
     */
    private String errCn;

    /**
     * column : REGN_DT
     * 등록_일시
     */
    private Date regnDt;

    public ErrCdVO() {}
}