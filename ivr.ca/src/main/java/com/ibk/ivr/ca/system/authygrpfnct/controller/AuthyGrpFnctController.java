package com.ibk.ivr.ca.system.authygrpfnct.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.authygrpfnct.vo.AuthyGrpFnctVO;
import com.ibk.ivr.ca.system.authygrpfnct.service.AuthyGrpFnctService;

import lombok.extern.slf4j.Slf4j;

/**
 * 권한그룹 별 기능에 대한 권한 관리 controller
 * table : TBCA_AUTHY_GRP_FNCT (권한그룹 별 기능에 대한 권한 관리)
 * 
 * @author  yu kim
 * @version 1.0, 10/15/2019
 */
@Slf4j
@Controller
//@RequestMapping(value = "/system/authygrpfnct", method=RequestMethod.POST)
public class AuthyGrpFnctController {
	
    @Autowired
    private AuthyGrpFnctService service;

    /**
     * 권한그룹 별 기능에 대한 권한 관리 메인화면으로 권한그룹 별 기능에 대한 권한 관리 list 화면
     *
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = {"", "/"})
    public String index(Model model) throws Exception {
        RequestVO requestVO = new RequestVO();
        requestVO.setPageNo(1);
        List<Map<String, Object>> result = service.selectList(requestVO);
        if(log.isDebugEnabled())
            log.debug("selectList result vos count {}", requestVO.getSize());

        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
		
        return "/list/ca/system/system/authygrpfnct/authygrpfnct_list";
    }
			     
    /**
     * 권한그룹 별 기능에 대한 권한 관리 리스트 조회
     *
     * @param vo RequestVO 조회 조건 데이터
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String selectList(@ModelAttribute RequestVO requestVO, Model model) throws Exception {			
        List<Map<String, Object>> result = service.selectList(requestVO);
        if(log.isDebugEnabled())
            log.debug("selectList result vos count {}", requestVO.getSize());
							
        model.addAttribute(Constants.PAGING, requestVO);
        model.addAttribute(Constants.RESULT, result);
        return "/ca/system/system/authygrpfnct/authygrpfnct_list";
    }

    /**
     * 권한그룹 별 기능에 대한 권한 관리 등록화면
     *
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public String authyGrpFnct_edit() throws Exception {
        return "/edit/ca/system/system/authygrpfnct/authygrpfnct_edit";
    }
     
    /**
     * 권한그룹 별 기능에 대한 권한 관리 등록
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 등록 데이터
     * @return ResponseVO 처리결과정보
     * @throws Exception
     */
    @RequestMapping(value = "/edit/add")
    public @ResponseBody ResponseVO insert(@ModelAttribute AuthyGrpFnctVO vo, HttpServletRequest request) throws Exception {
        //user id, reg dt 세팅하기
		/*
		 * vo.setRegrId(ApplicationContext.getSession().getUser().getUserId());
		 * vo.setRegnDt(DateUtil.getDate());
		 * 
		 * if(service.insert(vo) == 0) throw new
		 * ApplicationException(Status.FAIL_INSERT);
		 */
        return new ResponseVO();
    }	
     
    /**
     * 권한그룹 별 기능에 대한 권한 관리 수정
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 수정 데이터
     * @return ResponseVO 처리결과정보
     * @throws Exception
     */
    @RequestMapping(value = "/view/update")
    public @ResponseBody ResponseVO update(@ModelAttribute AuthyGrpFnctVO vo, HttpServletRequest request) throws Exception {
        //user id, reg dt 세팅하기
		/*
		 * vo.setUpdusrId(ApplicationContext.getSession().getUser().getUserId());
		 * vo.setUpdtDt(DateUtil.getDate());
		 * 
		 * if(service.update(vo) == 0) throw new
		 * ApplicationException(Status.FAIL_UPDATE);
		 */
        return new ResponseVO();
    }
	     
    /**
     * 권한그룹 별 기능에 대한 권한 관리 삭제
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 삭제 데이터
     * @return ResponseVO 처리결과정보
     * @throws Exception
     */
    @RequestMapping(value = "/view/delete")
    public @ResponseBody ResponseVO delete(@ModelAttribute AuthyGrpFnctVO vo, HttpServletRequest request) throws Exception {
        //user id, reg dt 세팅하기
		/*
		 * vo.setUpdusrId(ApplicationContext.getSession().getUser().getUserId());
		 * vo.setUpdtDt(DateUtil.getDate());
		 * 
		 * if(service.delete(vo) == 0) throw new
		 * ApplicationException(Status.FAIL_DELETE);
		 */
        return new ResponseVO();
    }
    	     
    /**
     * 권한그룹 별 기능에 대한 권한 관리 리스트 삭제
     *
     * @param vo 권한그룹 별 기능에 대한 권한 관리 삭제 데이터
     * @param ids 삭제데이터 ids
     * @return ResponseVO 처리결과정보
     * @throws Exception
     */
    @RequestMapping(value = "/deletes")
    public @ResponseBody ResponseVO deletes(@ModelAttribute AuthyGrpFnctVO vo, @RequestParam(value="ids", required=true) String[] ids, HttpServletRequest request) throws Exception {
        //user id, reg dt 세팅하기
		/*
		 * vo.setUpdusrId(ApplicationContext.getSession().getUser().getUserId());
		 * vo.setUpdtDt(DateUtil.getDate());
		 * 
		 * if(service.deletes(vo, ids) != ids.length) throw new
		 * ApplicationException(Status.FAIL_DELETE, "fail.common.delete");
		 */
        return new ResponseVO();
    }    
		     
    /**
     * 권한그룹 별 기능에 대한 권한 관리 조회
     *
     * @param id 권한그룹 별 기능에 대한 권한 관리 조회 키
     * @return String jsp page
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public String select(@RequestParam(value="id", required=true) String id, Model model) throws Exception {	
        AuthyGrpFnctVO result = service.select(id);
        if(log.isDebugEnabled())
            log.debug("select result vos [{}]", result != null ? result.toString() : "NULL");
							
		/*
		 * if(result == null) throw new ApplicationException(Status.NOT_FOUND);
		 */
		
        model.addAttribute(Constants.RESULT, result);
		model.addAttribute(Constants.ID, id);
        return "/view/ca/system/system/authygrpfnct/authygrpfnct_view";
    }
}