<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">에러코드 유형분석</p>
		</div>
		<div class="tblBox small">
			<form data-type="search" name="search_frm" data-session-type="${sessionScope.session.param.analysis_error.STANDARD}">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>분석 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.analysis_error.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.analysis_error.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="lately_week" type="radio" name="param[STANDARD]" value="3" data-search="true" data-type="date" data-date-type="lately_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_week"><span></span>최근1주</label>
						<input id="lately_month" type="radio" name="param[STANDARD]" value="4" data-search="true" data-type="date" data-date-type="lately_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_month"><span></span>최근 1개월</label>
						<input id="lately_3month" type="radio" name="param[STANDARD]" value="5" data-search="true" data-type="date" data-date-type="lately_3month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_3month"><span></span>최근 3개월</label>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/analysis/error/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="tblBox small">
			<div class="errBox">
				<p class="sTitle">에러코드 누계</p>
				<div class="static2 bgSky"  style="height:210px; height:170px; padding-top:60px;" >
					<p class="num" id="err_cnt">0<span>건</span></p>
				</div>
			</div>

			<div class="errBox">
				<p class="chTit"><span class="mtit">에러코드 TOP5</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame4" style="height:170px;">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span>남</span>
						<span class="blueDot"></span><span>여</span>
					</p> -->
					<div class="chart180" style="height:140px;">
						<canvas id="errChart" ></canvas>
					</div>
				</div>	
			</div>

			<div class="errBox">
				<p class="chTit"><span class="mtit">에러코드 연계거래 TOP5</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame4" style="height:170px;">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span>20대이하</span>
						<span class="blueDot"></span><span>30대</span>
					</p> -->
					<div class="chart180" style="height:140px;">
						<canvas id="trChart" ></canvas>
					</div>
				</div>	
			</div>
		</div>

		<div class="tblBox small">
			<p class="sTitle">에러 리스트</p>
			<div class="tblContainer1 small"  style="height:129px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th style="width:15%"><div class="th-text">에러코드</div></th>
								<th style="width:15%"><div class="th-text">거래코드</div></th>
								<th style="width:12%"><div class="th-text">시스템에러여부</div></th>
								<th style="width:48%"><div class="th-text">오류메시지</div></th>
								<th style="width:10%"><div class="th-text">건수</div></th>
							</tr>
						</thead>
						<tbody id="err_list">
							<jsp:include page="./analysis_error_list.jsp"></jsp:include>
						</tbody>
				     </table>
				</div>
			</div>
		</div>


		<div class="chartWrap">
			<div class="chTit">
				<span class="mtit">당일 에러코드 TOP 5 시간별 추이</span>
				<span class="index">단위 : 건수</span>
			</div>
			<div class="chartFrame2">
				<!-- <div class="chLegend">
					<span class="redDot"></span><span class="sort" id="top5_1">-</span>
					<span class="blueDot"></span><span class="sort" id="top5_2">-</span>
					<span class="greenDot"></span><span class="sort" id="top5_3">-</span>
					<span class="puppleDot"></span><span class="sort" id="top5_4">-</span>
					<span class="orangeDot"></span><span class="sort" id="top5_5">-</span>
				</div> -->
				<div class="chart280" >
					<canvas id="myChart"></canvas>
				</div>
			</div>
		</div>