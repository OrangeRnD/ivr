package com.ibk.ivr.ca.system.srvccd.vo;

import java.util.Date;

import com.ibk.ivr.ca.common.vo.DataVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 서비스 코드 정보 VO
 * table : TBCA_SRVC_CD (서비스 코드 정보)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class SrvcCdVO extends DataVO {

	private static final long serialVersionUID = -3193610908798740822L;

    /**
     * column : SRVC_CD
     * 서비스_코드
     */
    private String srvcCd;

    /**
     * column : SRVC_CD
     * 서비스_코드
     */
    private String oldSrvcCd;

    /**
     * column : SRVC_NM
     * 서비스_이름
     */
    private String srvcNm;
    
    /**
     * column : MONITORING_YN
     * 모니터링_여부
     * 값이 '1' 인 경우 주요 서비스 화면(top 조회)에서 제외 처리 됨
     */
    private String monitoringYn;

    /**
     * column : REGN_DT
     * 등록_일시
     */
    private Date regnDt;

    public SrvcCdVO() {}
}