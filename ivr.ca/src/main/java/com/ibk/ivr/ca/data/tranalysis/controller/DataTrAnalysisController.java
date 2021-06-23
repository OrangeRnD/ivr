package com.ibk.ivr.ca.data.tranalysis.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import com.ibk.ivr.ca.data.tranalysis.service.DataTrAnalysisService;
import com.ibk.ivr.ca.system.usr.vo.UsrVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 거래발생 일 변동량 분석 controller
 * 
 * table : TBCA_HLY_TR_STAT (시간별_TR_현황)
 * 
 * @author 데이터인사이트(주)
 * @version 1.0, 10/07/2019
 */
@Slf4j
@Controller
@RequestMapping(value = "/data/tranalysis", method=RequestMethod.POST)
public class DataTrAnalysisController {

	@Autowired
	private DataTrAnalysisService service;
	
	/**
	 * 실시간 거래 발생 비교 index 화면
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"", "/"}, method=RequestMethod.GET)
	public String index() throws Exception {
		return "/index/data/tranalysis/tr_analysis_index";
	}

	/**
	 * 거래발생 일 변동량 리스트 조회
	 *
	 * @param vo RequestVO 조회 조건 데이터
	 * @return ResponseVO
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String selectList(@ModelAttribute RequestVO requestVO, Model model, HttpSession session) throws Exception {
		((UsrVO)session.getAttribute(Constants.SESSION)).getParam().put("data_tranalysis", requestVO.getParam());
		
		calculate(requestVO, model, false);
		return "/data/tranalysis/tr_analysis_list";
	}

	private void calculate(RequestVO requestVO, Model model, boolean isExcel) throws Exception {
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", result.toString());
		
		String td = DateUtil.getDateString("yyyyMMdd");
		
		Map<String, Object> tmAvg = result.remove(0);
		double avgs = 0;
		for(Map<String, Object> data : result) {
			int tmCnt = ((Number)data.get("CNT")).intValue();
			double values = 0;
			for(int i = 0; i < 24; i++) {
				double cnt = ((Number)data.get("CNT".concat(String.valueOf(i)))).doubleValue();
				double avgCnt = ((Number)tmAvg.get("CNT".concat(String.valueOf(i)))).doubleValue();
				
				//오늘 중 0 건인 데이터(미래 데이터)에 대해서는 계산 안함.
				if(td.equals(data.get("DT")) && cnt == 0) {
					data.put("DIFF".concat(String.valueOf(i)), 0);
				} else {
					double value = Math.abs(avgCnt - cnt);
					values += value;
					data.put("DIFF".concat(String.valueOf(i)), value);
				}
			}
			double avg = values / tmCnt;
			data.put("SD", avg);
			avgs += avg;
		}
		
		double tSd = avgs / result.size();

		model.addAttribute("SD", tSd);
		if(isExcel)
			model.addAttribute(Constants.RESULT, result);
		else
			model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
	}

	private void calculate_StandardDeviation(RequestVO requestVO, Model model, boolean isExcel) throws Exception {
		List<Map<String, Object>> result = service.selectList(requestVO);
		if(log.isDebugEnabled())
			log.debug("selectList result count {}", result.toString());

		List<Double> totalValues = new ArrayList<Double>();
		List<Double> totalMeans = new ArrayList<Double>();
		StandardDeviation sd = new StandardDeviation();
		for(Map<String, Object> data : result) {
			double mean = ((Number)data.get("AVG_CNT")).doubleValue();
			double[] values = new double[24];
			for(int i = 0; i < 24; i++) {
				double cnt = ((Number)data.get("CNT".concat(String.valueOf(i)))).doubleValue();
				values[i] = Math.abs(mean - cnt);
				data.put("DIFF".concat(String.valueOf(i)), values[i]);
				totalValues.add(values[i]);
			}
			data.put("SD", sd.evaluate(values, mean));
			totalMeans.add(mean);
		}
		
		double[] tValues = new double[totalValues.size()];
		int i = 0;
		for(Double d : totalValues) {
			tValues[i++] = d.doubleValue();
		}
		double[] tMeans = new double[totalMeans.size()];
		i = 0;
		for(Double d : totalMeans) {
			tMeans[i++] = d.doubleValue();
		}
		Mean mean = new Mean();
		double tSd = sd.evaluate(tValues, mean.evaluate(tMeans));

		model.addAttribute("SD", tSd);
		if(isExcel)
			model.addAttribute(Constants.RESULT, result);
		else
			model.addAttribute(Constants.RESULT, result.size() == 0 ? null : result);
	}
	
	/**
	 * 거래발생 일 변동량 엑셀다운로드
	 * @param requestVO
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	public AbstractXlsxView selectListExcel(@ModelAttribute RequestVO requestVO, Model model) throws Exception {
		calculate(requestVO, model, true);
		model.addAttribute(Constants.FILE_NAME, "거래발생일변동량분석");
		
		return new DefaultXlsxView() {

			@Override
			protected void create(Map<String, Object> model, HttpServletRequest request) throws Exception {
				Sheet sheet = workbook.createSheet("거래발생 일 변동량 분석");
				sheet.setDefaultColumnWidth(15);
				
				Number tSd = (Number)model.get("SD");
				
				java.text.DecimalFormat df = new java.text.DecimalFormat("#,###.00");
				
		        short r = 1;
				this.createTitle(sheet, r++, "평균 일 변동량 : ".concat(df.format(tSd)));

				String[] titles = new String[26];
				int[] width = new int[26];
				titles[0] = "일자";
				width[0] = 30;
				titles[1] = "일 변동량";
				width[1] = 15;
				for(int i = 2; i < 26; i++) {
					titles[i] = String.valueOf(i-2).concat(" 시");
					width[i] = 10;
				}
				
				this.createHeader(sheet, r++, titles, width);
				
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = (List<Map<String, Object>>)model.get(Constants.RESULT);
                short c = 0;
				for(Map<String, Object> data : list) {
					Row row = sheet.createRow(r++);
					c = 0;
					crateYMDCell(row, c++, (String)data.get("DT"));
					createNumberCell(row, c++, ((Number)data.get("SD")).doubleValue());
					for(int i = 0; i < 24; i++) {
						createNumberCell(row, c++, ((Number)data.get("DIFF".concat(String.valueOf(i)))).doubleValue());
					}
				}
			}
		};
	}
}
