package com.ibk.ivr.ca.system.menu.dao;

import java.util.List;
import java.util.Map;

import com.ibk.ivr.ca.system.menu.vo.MenuVO;

import com.ibk.ivr.ca.common.vo.RequestVO;

import org.springframework.stereotype.Repository;

/**
 * 시스템 메뉴 관리 dao
 * table : TBCA_MENU (시스템 메뉴 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Repository
public interface MenuDAO {
  
    /**
     * 시스템 메뉴 관리 등록
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    public int insert(MenuVO vo) throws Exception;
  
    /**
     * 시스템 메뉴 관리 수정
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @return int 수정건수
     * @throws Exception
     */
    public int update(MenuVO vo) throws Exception;
  
    /**
     * 시스템 메뉴 관리 삭제
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    public int delete(MenuVO vo) throws Exception;

    /**
     * 시스템 메뉴 관리 조회
     *
     * @param menu_id 메뉴_아이디
     * @return MenuVO 시스템 메뉴 관리 vo
     * @throws Exception
     */
    public MenuVO selectById(int menu_id) throws Exception;
    
    /**
     * 시스템 메뉴 관리 조회
     *
     * @param vo 시스템 메뉴 관리 조회 조건
     * @return MenuVO 시스템 메뉴 관리 vo
     * @throws Exception
     */
    public MenuVO select(MenuVO vo) throws Exception;
    
    /**
     * 시스템 메뉴 관리 전체 데이터수
     *
     * @param vo 조회 조건
     * @return long 전체 데이터수
     * @throws Exception
     */
    public long selectListCount(RequestVO vo) throws Exception;
  
    /**
     * 시스템 메뉴 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 메뉴 관리 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception;
  
    /**
     * 시스템 메뉴 관리 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 메뉴 관리 데이타
     * @throws Exception
     */
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception;
}