package com.ibk.ivr.ca.report.daily;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.Styler.TextAlignment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DailyReport {

	private DecimalFormat nf = new DecimalFormat("#,###");
	
	private DecimalFormat rtNf = new DecimalFormat("#,##0.0");
	
	//private DecimalFormat heightNf = new DecimalFormat("0.0");
	
	public DailyReport() {}
	
	/**
	 * 서비스이용현황, 서비스 비교현황
	 * 당일 서비스현황 분포도
	 * @param requestVO
	 * @param model
	 * @param nf
	 * @param rtNf
	 * @throws Exception
	 */
	protected void setSrvc(List<Map<String, Object>> list, Map<String, Object> model, String imagePath, String yyyyMMdd) throws Exception {
		//List<Map<String, Object>> list = service.selectListSrvc(requestVO);
		
		//당일/전일 top5 서비스 용
		long srvcChart1y = 0, srvcChart2y = 0, srcvTotCnt = 0;
		
		//당일 이용서비스 현황
		List<Map<String, Object>> srvcList = new ArrayList<Map<String, Object>>(10);
		for(Map<String, Object> srvc : list) {
			int idx = ((Number)srvc.get("NUM")).intValue();
			String type = (String)srvc.get("TYPE");
			
			if(srvcList.size() < idx)
				srvcList.add(new HashMap<String, Object>());
			
			Map<String, Object> data = srvcList.get(idx-1);
			//SRVC_CD, SRVC_NM, CNT, P_CNT, TYPE
			data.put("SRVC_CD_".concat(type), srvc.get("SRVC_CD"));
			data.put("SRVC_NM_".concat(type), srvc.get("SRVC_NM"));
			Number cnt = (Number)srvc.get("CNT");
			data.put("CNT_".concat(type), nf.format(cnt.longValue()));
			Number pCnt = (Number)srvc.get("P_CNT");
			if(cnt.longValue() == 0 || pCnt.longValue() == 0)
				data.put("RT_".concat(type), "0");
			else
				data.put("RT_".concat(type), rtNf.format((cnt.doubleValue()-pCnt.doubleValue())/pCnt.doubleValue()*100.0));
			
			if(cnt.longValue() < pCnt.longValue())
				data.put("COLOR_".concat(type), "#ff6384");
			else
				data.put("COLOR_".concat(type), "#666");
			
			if(srvc.get("TYPE").equals("1")) {
				srvcChart1y = Math.max(srvcChart1y, cnt.longValue());
				srvcChart1y = Math.max(srvcChart1y, pCnt.longValue());

				Number pwCnt = (Number)srvc.get("PW_CNT");
				srvcChart2y = Math.max(srvcChart2y, cnt.longValue());
				srvcChart2y = Math.max(srvcChart2y, pwCnt.longValue());
				
				srcvTotCnt += cnt.longValue();
			}
		}
		model.put("srvc", srvcList);
		
		//당일/전일 top5 서비스
		int std = 1;
		for(int i = 1; i < String.valueOf(srvcChart1y).length(); i++) {
			std *= 10;
		}
		srvcChart1y = srvcChart1y - (srvcChart1y%std) + std;

		/*long unit = srvcChart1y / 10;
		
		List<String> lines = new ArrayList<String>(10);
		lines.add(nf.format(srvcChart1y));
		for(int i = 1; i < 10; i++) {
			lines.add(nf.format(srvcChart1y-(unit*i)));
		}
		model.put("srvcLine1", lines);*/
		
		std = 1;
		for(int i = 1; i < String.valueOf(srvcChart2y).length(); i++) {
			std *= 10;
		}
		srvcChart2y = srvcChart2y - (srvcChart2y%std) + std;

		/*long unit2 = srvcChart2y / 10;
		
		List<String> lines2 = new ArrayList<String>(10);
		lines2.add(nf.format(srvcChart2y));
		for(int i = 1; i < 10; i++) {
			lines2.add(nf.format(srvcChart2y-(unit2*i)));
		}
		model.put("srvcLine2", lines2);*/
		
		//pie chart
		PieChart pieChart = new PieChartBuilder().width(400).height(400).build();
		Color[] sliceColors = new Color[] { new Color(255, 98, 132), new Color(232, 103, 191), new Color(169, 108, 246), new Color(93, 126, 247), new Color(52, 164, 235)
											, new Color(45, 191, 239), new Color(60, 186, 157), new Color(111, 189, 48), new Color(219, 211, 15), new Color(247, 163, 63)};
		pieChart.getStyler().setSeriesColors(sliceColors);
		pieChart.getStyler().setChartBackgroundColor(Color.WHITE);
		pieChart.getStyler().setChartFontColor(Color.WHITE);
		pieChart.getStyler().setChartTitleVisible(false);
		pieChart.getStyler().setLegendVisible(false);
		pieChart.getStyler().setPlotBorderVisible(false);

		String[] pieColor = {"#ff6384", "#e867bf", "#ac6cf6", "#5d7bf7", "#34a2eb", "#2dbfef", "#3cba9f", "#6cbd30", "#dbd40f", "#f7a33f"};
		List<Map<String, Object>> srvcPieList = new ArrayList<Map<String, Object>>(10);
		int i = 0;
		/*double pieFrom = 0;*/
		
		List<String> srvcNmList = new ArrayList<String>();
		List<Number> cntList = new ArrayList<Number>();
		List<Number> pCntList = new ArrayList<Number>();
		List<Number> pwCntList = new ArrayList<Number>();
		
		//Map<String, Object> srvcChart1 = new HashMap<String, Object>();
		//Map<String, Object> srvcChart2 = new HashMap<String, Object>();
		for(Map<String, Object> srvc : list) {
			if(srvc.get("TYPE").equals("1")) {
				int idx = ((Number)srvc.get("NUM")).intValue();
				//String idxStr = String.valueOf(idx);
				Number cnt = (Number)srvc.get("CNT");
				String cntStr = nf.format(cnt.longValue());
				if(idx <= 5) {
					/*Number pCnt = (Number)srvc.get("P_CNT");
					srvcChart1.put("CNT_".concat(idxStr), cntStr);
					srvcChart1.put("P_CNT_".concat(idxStr), nf.format(pCnt.longValue()));
					srvcChart1.put("RT_".concat(idxStr), heightNf.format(cnt.doubleValue()/srvcChart1y*100.0));
					srvcChart1.put("P_RT_".concat(idxStr), heightNf.format(pCnt.doubleValue()/srvcChart1y*100.0));
					srvcChart1.put("SRVC_NM_".concat(idxStr), srvc.get("SRVC_NM"));

					Number pwCnt = (Number)srvc.get("PW_CNT");
					srvcChart2.put("CNT_".concat(idxStr), cntStr);
					srvcChart2.put("P_CNT_".concat(idxStr), nf.format(pwCnt.longValue()));
					srvcChart2.put("RT_".concat(idxStr), heightNf.format(cnt.doubleValue()/srvcChart2y*100.0));
					srvcChart2.put("P_RT_".concat(idxStr), heightNf.format(pwCnt.doubleValue()/srvcChart2y*100.0));
					srvcChart2.put("SRVC_NM_".concat(idxStr), srvc.get("SRVC_NM"));*/
					
					String srvcNm = (String)srvc.get("SRVC_NM");
					if(srvcNm.length() > 5)
						srvcNm = srvcNm.substring(0, 5).concat("...");
					srvcNmList.add(srvcNm);
					cntList.add(cnt);
					pCntList.add((Number)srvc.get("P_CNT"));
					pwCntList.add((Number)srvc.get("PW_CNT"));
				}
				Map<String, Object> srvcPieChart = new HashMap<String, Object>();
				srvcPieChart.put("SRVC_NM", srvc.get("SRVC_NM"));
				srvcPieChart.put("CNT", cntStr);
				srvcPieChart.put("COLOR", pieColor[i++]);
				
				double pieRt = cnt.doubleValue()/srcvTotCnt*100.0;
				
				pieChart.addSeries((String)srvc.get("SRVC_NM"), pieRt);
			    
				/*long piePecent = Math.round(pieRt*3.6);
				srvcPieChart.put("PIE_PECENT", piePecent);
				srvcPieChart.put("PIE_PATH", getPieChartPath(200, 200, 80, 200, pieFrom, pieFrom + piePecent));
				srvcPieChart.put("PIE_RT", heightNf.format(pieRt));
				
				Point txtPoint = getPoint(200, 200, 80 + ((200-80)/2), pieFrom + (((pieFrom + piePecent)-pieFrom)/2));
				srvcPieChart.put("PIE_TXT_X", txtPoint.x - ((cntStr.length()+2)/2*7));
				srvcPieChart.put("PIE_TXT_Y", txtPoint.y);
				
				pieFrom = pieFrom + piePecent;*/
				
				srvcPieList.add(srvcPieChart);
			}
		}
		/*if(pieFrom != 360 && srvcPieList.size() > 0) {
			Map<String, Object> srvcPieChart = srvcPieList.get(srvcPieList.size() - 1);
			long piePecent = (Long)srvcPieChart.get("PIE_PECENT");
			srvcPieChart.put("PIE_PATH", getPieChartPath(200, 200, 80, 200, pieFrom-piePecent, 360));
		}*/
		
		//model.put("srvcChart1", srvcChart1);
		//model.put("srvcChart2", srvcChart2);
		model.put("srvcPieChart", srvcPieList);
		
		//폴더 구조 /년/월/일
		StringBuilder sb = new StringBuilder("");
		sb.append(yyyyMMdd.substring(0, 4));
		sb.append("/");
		sb.append(yyyyMMdd.substring(4, 6));
		sb.append("/");
		sb.append(yyyyMMdd.substring(6));
		sb.append("/");
		
		String folder = sb.toString();
		model.put("imageFolder", folder);
		
		File file = new File(imagePath, folder);
		if(!file.exists())
			file.mkdirs();
		
		String pieImageName = yyyyMMdd.concat("_4.png");
		sb.setLength(0);
		sb.append(imagePath);
		sb.append(folder);
		sb.append(pieImageName);
		
		if(log.isDebugEnabled())
				log.debug("report pie image : {}", sb.toString());
		
	    BitmapEncoder.saveBitmapWithDPI(pieChart, sb.toString(), BitmapFormat.PNG, 300);
		model.put("pieImage", pieImageName);

		if(srvcNmList.size() == 0) {
			srvcNmList.add("-");
			cntList.add(0);
			pCntList.add(0);
			pwCntList.add(0);
		}
		
		CategoryChart chart1 = getCategoryChart(435, 340, (double)srvcChart1y, .9, new Color[] {new Color(255, 127, 154), new Color(89, 185, 246)});
		chart1.getStyler().setXAxisLabelRotation(45);
		chart1.addSeries("당일", srvcNmList, cntList);
		chart1.addSeries("전일", srvcNmList, pCntList);
		
		String srvcImageName1 = yyyyMMdd.concat("_2.png");
		sb.setLength(0);
		sb.append(imagePath);
		sb.append(folder);
		sb.append(srvcImageName1);
		
		if(log.isDebugEnabled())
				log.debug("report srvc image 1 : {}", sb.toString());
		
	    BitmapEncoder.saveBitmapWithDPI(chart1, sb.toString(), BitmapFormat.PNG, 300);
		model.put("srvcImage1", srvcImageName1);
		
		CategoryChart chart2 = getCategoryChart(435, 340, (double)srvcChart2y, .9, new Color[] {new Color(255, 127, 154), new Color(89, 185, 246)});
		chart2.getStyler().setXAxisLabelRotation(45);
		chart2.addSeries("당일", srvcNmList, cntList);
		chart2.addSeries("전주평균", srvcNmList, pwCntList);
		
		String srvcImageName2 = yyyyMMdd.concat("_3.png");
		sb.setLength(0);
		sb.append(imagePath);
		sb.append(folder);
		sb.append(srvcImageName2);
		
		if(log.isDebugEnabled())
				log.debug("report srvc image 2 : {}", sb.toString());
		
	    BitmapEncoder.saveBitmapWithDPI(chart2, sb.toString(), BitmapFormat.PNG, 300);
		model.put("srvcImage2", srvcImageName2);
	}
	
	protected Point getPoint(double cX, double cY, double r, double degrees) {
		double rad = (degrees) * Math.PI / 180.0;
		
		Point point = new Point();
		point.setLocation(cX + (r * Math.cos(rad)), cY + (r * Math.sin(rad)));
		return point;
	}
	
	protected String getPieChartPath(double x, double y, double radiusIn, double radiusOut, double startAngle, double endAngle) {
		Point startIn = getPoint(x, y, radiusIn, endAngle);
		Point endIn = getPoint(x, y, radiusIn, startAngle);
	 
		Point startOut = getPoint(x, y, radiusOut, endAngle);
		Point endOut = getPoint(x, y, radiusOut, startAngle);
	 
		String arcSweep = (endAngle - startAngle) <= 180 ? "0" : "1";
		
		StringBuilder sb = new StringBuilder("");
	 
		sb.append("M");
		sb.append(" ");
		sb.append(startIn.x); 
		sb.append(" ");
		sb.append(startIn.y);
		sb.append(" ");
		sb.append("L"); 
		sb.append(" ");
		sb.append(startOut.x); 
		sb.append(" ");
		sb.append(startOut.y);
		sb.append(" ");
		sb.append("A"); 
		sb.append(" ");
		sb.append(radiusOut); 
		sb.append(" ");
		sb.append(radiusOut); 
		sb.append(" ");
		sb.append(0); 
		sb.append(" ");
		sb.append(arcSweep); 
		sb.append(" ");
		sb.append(0); 
		sb.append(" ");
		sb.append(endOut.x); 
		sb.append(" ");
		sb.append(endOut.y);
		sb.append(" ");
		sb.append("L"); 
		sb.append(" ");
		sb.append(endIn.x); 
		sb.append(" ");
		sb.append(endIn.y);
		sb.append(" ");
		sb.append("A"); 
		sb.append(" ");
		sb.append(radiusIn); 
		sb.append(" ");
		sb.append(radiusIn); 
		sb.append(" ");
		sb.append(0); 
		sb.append(" ");
		sb.append(arcSweep); 
		sb.append(" ");
		sb.append(1); 
		sb.append(" ");
		sb.append(startIn.x); 
		sb.append(" ");
		sb.append(startIn.y);
		sb.append(" ");
		sb.append("z");
		return sb.toString();
	}
	
	/**
	 * 인입 추이분석 발생내역
	 * @param requestVO
	 * @param model
	 * @param nf
	 * @param rtNf
	 * @throws Exception
	 */
	protected void setPlcy(List<Map<String, Object>> plcyRsltList, Map<String, Object> model) throws Exception {
		SimpleDateFormat tsDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//List<Map<String, Object>> plcyRsltList = service.selectListPlcyRslt(requestVO);
		
		int no = 1;
		for(Map<String, Object> data : plcyRsltList) {
			data.put("NO", no++);
			Date OCCRRNC_DT = (Date)data.get("OCCRRNC_DT");
			data.put("S_OCCRRNC_DT", tsDf.format(OCCRRNC_DT));
			Number OCCRRNC_CNT = (Number)data.get("OCCRRNC_CNT");
			data.put("S_OCCRRNC_CNT", nf.format(OCCRRNC_CNT));
			Number CMPR_CNT = (Number)data.get("CMPR_CNT");
			data.put("S_CMPR_CNT", nf.format(CMPR_CNT));
			data.put("S_NOTIFTN_STDVAL", nf.format((Number)data.get("NOTIFTN_STDVAL")));
			
			if(CMPR_CNT.intValue() != 0)
				data.put("S_EXCESS", rtNf.format((OCCRRNC_CNT.doubleValue()-CMPR_CNT.doubleValue())/CMPR_CNT.doubleValue()*100.0).concat(" %"));
			else
				data.put("S_EXCESS", "-");
			
			if(((Boolean)data.get("SMS_XMSN_ALTV")).booleanValue()) 
				data.put("S_SMS_XMSN_ALTV", "적용");
			else
				data.put("S_SMS_XMSN_ALTV", "미적용");
			if(((Boolean)data.get("EMAIL_XMSN_ALTV")).booleanValue()) 
				data.put("S_EMAIL_XMSN_ALTV", "적용");
			else
				data.put("S_EMAIL_XMSN_ALTV", "미적용");
		}
		
		model.put("plcy", plcyRsltList.size() == 0 ? null : plcyRsltList);
	}
	
	/**
	 * 콜 인입현황, 콜 인입 비교현황
	 * @param requestVO
	 * @param model
	 * @param nf
	 * @throws Exception
	 */
	protected void setMeda(List<Map<String, Object>> medaList, Map<String, Object> model, String imagePath, String yyyyMMdd) throws Exception {
		//List<Map<String, Object>> medaList = service.selectListMedaStat(requestVO);
		long totCnt1 = 0, totCnt2 = 0, totCnt3 = 0, totCnt4 = 0;
		for(Map<String, Object> meda : medaList) {
			Number cnt1 = (Number)meda.get("TOT_CNT1");
			Number cnt2 = (Number)meda.get("TOT_CNT2");
			Number cnt3 = (Number)meda.get("TOT_CNT3");
			Number cnt4 = (Number)meda.get("TOT_CNT4");
			
			totCnt1 += cnt1.longValue();
			totCnt2 += cnt2.longValue();
			totCnt3 += cnt3.longValue();
			totCnt4 += cnt4.longValue();
			
			meda.put("S_TOT_CNT1", nf.format(cnt1.longValue()));
			meda.put("S_TOT_CNT2", nf.format(cnt2.longValue()));
			meda.put("S_TOT_CNT3", nf.format(cnt3.longValue()));
			meda.put("S_TOT_CNT4", nf.format(cnt4.longValue()));
			
			if(cnt1.longValue() == 0) {
				meda.put("S_TOT_RT2", "0");
				meda.put("S_TOT_RT3", "0");
				meda.put("S_TOT_RT4", "0");
			} else {
				if(cnt2.longValue() == 0)
					meda.put("S_TOT_RT2", "0");
				else
					meda.put("S_TOT_RT2", rtNf.format((cnt1.doubleValue()-cnt2.doubleValue())/cnt2.doubleValue()*100.0));
				
				if(cnt1.longValue() < cnt2.longValue())
					meda.put("COLOR2", "#ff6384");
				else
					meda.put("COLOR2", "#666");
				
				if(cnt3.longValue() == 0)
					meda.put("S_TOT_RT3", "0");
				else
					meda.put("S_TOT_RT3", rtNf.format((cnt1.doubleValue()-cnt3.doubleValue())/cnt3.doubleValue()*100.0));
				
				if(cnt1.longValue() < cnt3.longValue())
					meda.put("COLOR3", "#ff6384");
				else
					meda.put("COLOR3", "#666");
				
				if(cnt4.longValue() == 0)
					meda.put("S_TOT_RT4", "0");
				else
					meda.put("S_TOT_RT4", rtNf.format((cnt1.doubleValue()-cnt4.doubleValue())/cnt4.doubleValue()*100.0));

				if(cnt1.longValue() < cnt4.longValue())
					meda.put("COLOR4", "#ff6384");
				else
					meda.put("COLOR4", "#666");
			}
		}
		/*
		Map<String, Object> medaTot = new HashMap<String, Object>();
		medaTot.put("TOT_CNT1", totCnt1);
		medaTot.put("TOT_CNT2", totCnt2);
		medaTot.put("TOT_CNT3", totCnt3);
		medaTot.put("TOT_CNT4", totCnt4);

		medaTot.put("S_TOT_CNT1", nf.format(totCnt1));
		medaTot.put("S_TOT_CNT2", nf.format(totCnt2));
		medaTot.put("S_TOT_CNT3", nf.format(totCnt3));
		medaTot.put("S_TOT_CNT4", nf.format(totCnt4));
		
		medaTot.put("MEDA_SECD_NM", "총계");
		medaTot.put("MEDA_SECD", "-1");
		
		if(totCnt1 == 0) {
			medaTot.put("S_TOT_RT2", "0");
			medaTot.put("S_TOT_RT3", "0");
			medaTot.put("S_TOT_RT4", "0");
		} else {
			if(totCnt2 == 0)
				medaTot.put("S_TOT_RT2", "0");
			else
				medaTot.put("S_TOT_RT2", rtNf.format((double)(totCnt1-totCnt2)/totCnt2*100.0));
			if(totCnt3 == 0)
				medaTot.put("S_TOT_RT3", "0");
			else
				medaTot.put("S_TOT_RT3", rtNf.format((double)(totCnt1-totCnt3)/totCnt3*100.0));
			if(totCnt4 == 0)
				medaTot.put("S_TOT_RT4", "0");
			else
				medaTot.put("S_TOT_RT4", rtNf.format((double)(totCnt1-totCnt4)/totCnt4*100.0));
		}
		medaList.add(medaTot);*/
		
		long y = Math.max(totCnt1, totCnt2);
		y = Math.max(y, totCnt3);
		y = Math.max(y, totCnt4);
		
		int std = 1;
		for(int i = 1; i < String.valueOf(y).length(); i++) {
			std *= 10;
		}
		y = y - (y%std) + std;

		/*long unit = y / 10;
		
		List<String> lines = new ArrayList<String>(10);
		lines.add(nf.format(y));
		for(int i = 1; i < 10; i++) {
			lines.add(nf.format(y-(unit*i)));
		}
		model.put("medaLine", lines);
		
		medaTot.put("HEIGHT1", heightNf.format((double)totCnt1/y*100.0));
		medaTot.put("HEIGHT2", heightNf.format((double)totCnt2/y*100.0));
		medaTot.put("HEIGHT3", heightNf.format((double)totCnt3/y*100.0));
		medaTot.put("HEIGHT4", heightNf.format((double)totCnt4/y*100.0));
		model.put("medaTot", medaTot);*/
		
		CategoryChart chart = getCategoryChart(934, 260, (double)y, .6, new Color[] { new Color(89, 185, 246)});
	    
		chart.addSeries("콜인입", Arrays.asList(new String[] { "당일", "전일", "전주평균", "전월평균" }), Arrays.asList(new Number[] { totCnt1, totCnt2, totCnt3, totCnt4 }));

		//폴더 구조 /년/월/일
		StringBuilder sb = new StringBuilder("");
		sb.append(yyyyMMdd.substring(0, 4));
		sb.append("/");
		sb.append(yyyyMMdd.substring(4, 6));
		sb.append("/");
		sb.append(yyyyMMdd.substring(6));
		sb.append("/");
		
		String folder = sb.toString();
		model.put("imageFolder", folder);
		
		File file = new File(imagePath, folder);
		if(!file.exists())
			file.mkdirs();
		
	    String medaIageName = yyyyMMdd.concat("_1.png");
		
		sb.setLength(0);
		sb.append(imagePath);
		sb.append(folder);
		sb.append(medaIageName);
		
		if(log.isDebugEnabled())
			log.debug("report meda image : {}", sb.toString() );
		
	    BitmapEncoder.saveBitmapWithDPI(chart, sb.toString(), BitmapFormat.PNG, 300);
		model.put("medaImage", medaIageName);
		
		model.put("meda", medaList);
	}
	
	private CategoryChart getCategoryChart(int width, int height, double yAxisMax, double spaceFill, Color[] sliceColors) {
		CategoryChart chart = new CategoryChartBuilder().width(width).height(height).build();
	    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
	    chart.getStyler().setHasAnnotations(true);
	    chart.getStyler().setSeriesColors(sliceColors);
	    
	    chart.getStyler().setPlotGridLinesColor(new Color(195, 195, 195));
	    chart.getStyler().setPlotMargin(0);
	    chart.getStyler().setAvailableSpaceFill(spaceFill);
	    chart.getStyler().setPlotContentSize(0.8);
	    
	    chart.getStyler().setYAxisLabelAlignment(TextAlignment.Right);
	    chart.getStyler().setYAxisMax(yAxisMax);
	    chart.getStyler().setYAxisMin(.0);

	    chart.getStyler().setDecimalPattern("#,###");
		chart.getStyler().setChartBackgroundColor(Color.WHITE);
	    chart.getStyler().setChartFontColor(Color.WHITE);
	    chart.getStyler().setChartTitleVisible(false);
	    chart.getStyler().setLegendVisible(false);
	    chart.getStyler().setPlotBorderVisible(false);
	    return chart;
	}
}
