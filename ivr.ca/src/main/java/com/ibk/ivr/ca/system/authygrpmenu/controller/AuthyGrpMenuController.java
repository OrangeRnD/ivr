package com.ibk.ivr.ca.system.authygrpmenu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.authygrpmenu.vo.AuthyGrpMenuVO;
import com.ibk.ivr.ca.system.cd.service.CdService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.system.authygrpmenu.service.AuthyGrpMenuService;

import lombok.extern.slf4j.Slf4j;

/**
 * 권한 그룹 별 메뉴에 대한 권한 관리 controller
 * table : TBCA_AUTHY_GRP_MENU (권한 그룹 별 메뉴에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/authygrpmenu", method=RequestMethod.POST)
public class AuthyGrpMenuController {

	@Autowired
	private AuthyGrpMenuService service;

	@Autowired
	private CdService cdService;

	/**
	 * 권한 그룹 별 메뉴에 대한 권한 관리 메인화면으로 권한 그룹 별 메뉴에 대한 권한 관리 list 화면
	 *
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model) throws Exception {
		//권한그룹 코드
		model.addAttribute("AUTHY_GRP_CD", cdService.selectListAll("AUTHY_GRP_CD"));
		return "/index/system/authygrpmenu/authygrpmenu_index";
	}

	/**
	 * 권한 그룹 별 메뉴에 대한 권한 관리 리스트 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public @ResponseBody ResponseVO selectList() throws Exception {
		List<AuthyGrpMenuVO> result = service.selectList();
		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}

	/**
	 * 권한 그룹 별 메뉴에 대한 권한 관리 등록
	 *
	 * @param vo 권한 그룹 별 메뉴에 대한 권한 관리 등록 데이터
	 * @return ResponseVO 처리결과정보
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody ResponseVO insert(@RequestBody List<AuthyGrpMenuVO> list, HttpServletRequest request) throws Exception {
		service.insert(list, ((UsrVO)request.getSession().getAttribute(Constants.SESSION)).getUsrId());
		return new ResponseVO();
	}
}