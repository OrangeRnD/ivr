package com.ibk.ivr.ca.system.cldr.task;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibk.ivr.ca.system.cldr.service.CldrService;

import lombok.extern.slf4j.Slf4j;

/**
 * 기본 달력 정보 생성 배치
 */
@Slf4j
public class CldrTask {

	@Autowired
	private CldrService service;
	
	
	public void task() {
		log.info("CldrTask start -------------------------------");
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR) + 1;
		log.info("CldrTask year insert {} -------------------------------", year);
		try {
			service.insert(year);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		log.info("CldrTask end -------------------------------");
	}
}
