package com.ibk.ivr.ca.common.vo;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class DataVO implements java.io.Serializable {
	
	private static final long serialVersionUID = -6378554514422460841L;
	
	@JsonIgnore
	protected Locale locale;
}
