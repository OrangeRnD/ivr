package com.ibk.ivr.ca.system.trcd.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.common.vo.ResponseVO;
import com.ibk.ivr.ca.system.trcd.service.TrCdService;

import lombok.extern.slf4j.Slf4j;

/**
 * TR 코드 controller
 * table : TBCA_TR_CD (TR 코드)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/16/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/system/trcd/search", method=RequestMethod.POST)
public class TrCdSearchController {
	
    @Autowired
    private TrCdService service;

    /**
     * TR 코드 메인화면으로 TR 코드 list 화면
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
		
        return "/system/trcd/trcd_search_index";
    }
			     
    /**
     * TR 코드 리스트 조회
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
        return "/system/trcd/trcd_search_list";
    }

	/**
	 * tr 코드 정보 리스트 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return String jsp page
	 * @throws Exception
	 */
	@RequestMapping(value = "/nm")
	public @ResponseBody ResponseVO selectFind(@RequestParam(name="nm") String nm) throws Exception {			
		List<Map<String, Object>> result = service.selectFind(nm);
		if(log.isDebugEnabled())
			log.debug("selectFind result vos count {}", result.size());

		ResponseVO responseVO = new ResponseVO();
		responseVO.setData(result);
		return responseVO;
	}
}