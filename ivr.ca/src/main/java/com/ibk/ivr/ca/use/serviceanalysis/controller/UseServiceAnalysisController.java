package com.ibk.ivr.ca.use.serviceanalysis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;
import com.ibk.ivr.ca.common.vo.DefaultXlsxView;
import com.ibk.ivr.ca.common.vo.RequestVO;
import com.ibk.ivr.ca.system.srvccd.service.SrvcCdService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;
import com.ibk.ivr.ca.use.serviceanalysis.service.UseServiceAnalysisService;

import lombok.extern.slf4j.Slf4j;

/**
 * 서비스 비교 분석 controller
 * 
 * table : TBCA_DLY_SRVC_STAT (일별_서비스_현황)
 *         TBCA_MLY_SRVC_STAT (분별_서비스_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 9/18/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/use/serviceanalysis", method=RequestMethod.POST)
public class UseServiceAnalysisController {
	
	@Autowired
	private UseServiceAnalysisService service;
	
	@Autowired
	private SrvcCdService srvcCdService;
	
	/**
	 * 서비스 비교분석 index
	 * 기존 금주와 전주 비교
	 * @param model
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index(Model model, HttpSession session) throws Exception {
        RequestVO requestVO = new RequestVO();
        /*Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        
        //금주
        requestVO.getParam().put("FROM_DT1", DateUtil.yyyyMMdd.format(c.getTime()));
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        if(c.get(Calendar.DATE) > date)
        	c.set(Calendar.DATE, date);
        requestVO.getParam().put("TO_DT1", DateUtil.yyyyMMdd.format(c.getTime()));*/
        
        if(((UsrVO)session.getAttribute(Constants.SESSION)).getParam().get("ldin_serviceanalysis") != null) {
        	Map<?, ?> param = (Map<?, ?>)((UsrVO)session.getAttribute(Constants.SESSION)).getParam().get("ldin_serviceanalysis");
        	if(param.get("EX_CDS") != null || param.get("EX_CD") != null) {
            	List<Map<String, Object>> cds = srvcCdService.selectListForSession(param);
            	model.addAttribute("SRVC_CD", cds);
            	
            	return "/index/use/serviceanalysis/service_analysis_index";
        	}
        }
        
        requestVO.getParam().put("DT", DateUtil.getDateString("yyyyMMdd"));
        
        List<Map<String, Object>> cds = service.selectListSrvcTop5(requestVO);
        model.addAttribute("SRVC_CD", cds);
        
		return "/index/use/serviceanalysis/service_analysis_index";
	}
    
    /**
     * 서비스 비교분석 검색 조회
     * @param requestVO
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {		
    	((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("ldin_serviceanalysis", requestVO.getParam());
    	
		int fromDt1 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT1"));
		int toDt1 = Integer.parseInt((String)requestVO.getParam().get("TO_DT1"));

		int fromDt2 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT2"));
		int toDt2 = Integer.parseInt((String)requestVO.getParam().get("TO_DT2"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt1 && curDt <= toDt1)
			requestVO.getParam().put("CURRENT_DATE1", currentDate);

		if(curDt >= fromDt2 && curDt <= toDt2)
			requestVO.getParam().put("CURRENT_DATE2", currentDate);
		
        List<Map<String, Object>> result = service.selectList(requestVO);
        if(log.isDebugEnabled())
            log.debug("selectList result vos count {}", requestVO.getSize());
							
        model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
        return "/use/serviceanalysis/service_analysis_list";
    }
	
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {	
		int fromDt1 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT1"));
		int toDt1 = Integer.parseInt((String)requestVO.getParam().get("TO_DT1"));

		int fromDt2 = Integer.parseInt((String)requestVO.getParam().get("FROM_DT2"));
		int toDt2 = Integer.parseInt((String)requestVO.getParam().get("TO_DT2"));

		String currentDate = DateUtil.getDateString("yyyyMMdd");
		int curDt = Integer.parseInt(currentDate);

		if(curDt >= fromDt1 && curDt <= toDt1)
			requestVO.getParam().put("CURRENT_DATE1", currentDate);

		if(curDt >= fromDt2 && curDt <= toDt2)
			requestVO.getParam().put("CURRENT_DATE2", currentDate);
		
        List<Map<String, Object>> list = service.selectExcel(requestVO);

		model.addAttribute(Constants.RESULT, list);
		model.addAttribute(Constants.FILE_NAME, "서비스현황조회");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet1 = workbook.createSheet(WorkbookUtil.createSafeSheetName("서비스현황조회"));
				sheet1.setDefaultColumnWidth(15);

		        short r = 1;
		        createTitle(sheet1, r++, "서비스현황조회");

				String[] titles1 = {"일자", "서비스코드", "서비스명", "건수" };
				int[] width1 = {20, 15, 50, 15};
		        createHeader(sheet1, r++, titles1, width1);
				
				short c = 0;
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
				for(Map<String, Object> data : list) {
					Row row = sheet1.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					createStringCell(row, c++, (String)data.get("SRVC_CD"));
					createStringCell(row, c++, (String)data.get("SRVC_NM"));
					createNumberCell(row, c++, ((Number)data.get("CNT")).longValue());
				}
			}
		};
	}
}
