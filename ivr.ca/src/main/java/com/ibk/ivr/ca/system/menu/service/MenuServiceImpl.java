package com.ibk.ivr.ca.system.menu.service;

import java.util.List;
import java.util.Map;

//import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibk.ivr.ca.system.menu.service.MenuService;
import com.ibk.ivr.ca.system.menu.vo.MenuVO;
import com.ibk.ivr.ca.system.menu.dao.MenuDAO;

import com.ibk.ivr.ca.common.vo.RequestVO;

/**
 * 시스템 메뉴 관리 service implements
 * table : TBCA_MENU (시스템 메뉴 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
//@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
	
    @Autowired
    private MenuDAO dao;
    
    public MenuServiceImpl() {
    }
  
    /**
     * 시스템 메뉴 관리 등록
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @return int 등록건수
     * @throws Exception
     */
    @Override
    public int insert(MenuVO vo) throws Exception {
        return dao.insert(vo);
    }
  
    /**
     * 시스템 메뉴 관리 수정
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @return int 수정건수
     * @throws Exception
     */
    @Override
    public int update(MenuVO vo) throws Exception {
        return dao.update(vo);
    }
  
    /**
     * 시스템 메뉴 관리 삭제
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int delete(MenuVO vo) throws Exception {
        return dao.delete(vo);
    }
    
    /**
     * 시스템 메뉴 관리 리스트 삭제
     *
     * @param vo 시스템 메뉴 관리 데이타
     * @param ids 삭제데이터 ids
     * @return int 삭제건수
     * @throws Exception
     */
    @Override
    public int deletes(MenuVO vo, int[] ids) throws Exception {
        int result = 0;
        for(int id : ids) {
            vo.setMenuId(id);
            result += dao.delete(vo);
        }
        return result;
    }

    /**
     * 시스템 메뉴 관리 조회
     *
     * @param menu_id 메뉴_아이디
     * @return MenuVO 시스템 메뉴 관리 vo
     * @throws Exception
     */
    @Override
    public MenuVO select(int menu_id) throws Exception {
        return dao.selectById(menu_id);
    }
    
    /**
     * 시스템 메뉴 관리 조회
     *
     * @param vo 시스템 메뉴 관리 조회 조건
     * @return MenuVO 시스템 메뉴 관리 vo
     * @throws Exception
     */
    @Override
    public MenuVO select(MenuVO vo) throws Exception {
        return dao.select(vo);
    }
    
    /**
     * 시스템 메뉴 관리 리스트조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 메뉴 관리 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectList(RequestVO vo) throws Exception {
        if(vo.getPageNo() == 0) {
            List<Map<String, Object>> list = dao.selectList(vo);
            vo.setSize(list.size());
            return list;
        }
            
        long size = dao.selectListCount(vo);
        if(size > 0) {
            vo.setSize(size);
            return dao.selectList(vo);
        }
        return null;
    }
  
    /**
     * 시스템 메뉴 관리 엑셀조회
     *
     * @param vo 조회 조건
     * @return List<Map<String, Object>> 시스템 메뉴 관리 데이타
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> selectExcel(RequestVO vo) throws Exception {
        return dao.selectExcel(vo);
    }
}