<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">거래발생 일 변동량 분석</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-session-type="${sessionScope.session.param.data_tranalysis.STANDARD}">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.data_tranalysis.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.data_tranalysis.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="std_now" type="radio" name="param[STANDARD]" value="1" data-type="date" data-date-type="now" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="std_now"><span></span>당일</label>
						<input id="std_pre_date" type="radio" name="param[STANDARD]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="std_pre_date"><span></span>전일</label>
						<input id="std_this_week" type="radio" name="param[STANDARD]" value="3" data-type="date" data-date-type="this_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="std_this_week"><span></span>금주</label>
						<input id="std_pre_week" type="radio" name="param[STANDARD]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="std_pre_week"><span></span>전주</label>
						<input id="std_this_month" type="radio" name="param[STANDARD]" value="5" data-type="date" data-date-type="this_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="std_this_month"><span></span>당월</label>
						<input id="std_pre_month" type="radio" name="param[STANDARD]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="std_pre_month"><span></span>전월</label>
					</td>
				</tr>
				<tr>
					<th>기준일</th>
					<td>
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="-1"<c:if test="${sessionScope.session.param.data_tranalysis.RESTDE_ALTV == null || sessionScope.session.param.data_tranalysis.RESTDE_ALTV == '-1'}"> checked</c:if>><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="0"<c:if test="${sessionScope.session.param.data_tranalysis.RESTDE_ALTV == '0'}"> checked</c:if>><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="1"<c:if test="${sessionScope.session.param.data_tranalysis.RESTDE_ALTV == '1'}"> checked</c:if>><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/data/tranalysis/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>
		
		<div class="stateArea2">
			<div class="avHour">
				<span class="tit">평균 일 변동량 :</span>
				<span class="num" id="avg_sd">-</span>
			</div>
		</div>
<!--스크롤 테이블 시작 -->
		<div class="tblBox">
			<!-- <p class="sTitle">조회 기간 : <span id="period"></span></p> -->
			<div class="tblContainer1" style="height:288px;">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table narrow">
						<thead>
							<tr>
								<th style="width:8%"><div class="th-text">일자</div></th>
								<th style="width:5.6%"><div class="th-text">일변동량</div></th>
								<%
									for(int i = 0; i < 24; i++) {
								%>
								<th style="width:3.6%"><div class="th-text"><%=i%>시</div></th>
								<%		
									}
								%>
							</tr>
						</thead>
						<tbody id="lists">
							<tr><td colspan="26" class="txt">데이터 조회중입니다.</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
		</div>
<!--스크롤 테이블 끝 -->
		
		<div class="chartWrap">
			<div class="chTit">
				<span class="index">단위 : 표준편차</span>
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