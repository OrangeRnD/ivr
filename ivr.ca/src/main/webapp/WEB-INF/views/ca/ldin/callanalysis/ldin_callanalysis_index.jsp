<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">대표번호이용현황</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-url="/ldin/callanalysis/list" data-target="tblList" data-search="true" data-append="false" data-session-type1="${sessionScope.session.param.ldin_callanalysis.STANDARD}"  data-session-type2="${sessionScope.session.param.ldin_callanalysis.COMPARE}">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT1]" class="inputCal" data-type="date" data-input="std_input" value="${sessionScope.session.param.ldin_callanalysis.FROM_DT1}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT1]" class="inputCal" data-type="date" data-input="std_input" value="${sessionScope.session.param.ldin_callanalysis.TO_DT1}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="std_now" type="radio" name="param[STANDARD]" value="1" data-type="date" data-date-type="now" data-date1="param[FROM_DT1]" data-date2="param[TO_DT1]"><label for="std_now"><span></span>당일</label>
						<input id="std_pre_date" type="radio" name="param[STANDARD]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[FROM_DT1]" data-date2="param[TO_DT1]"><label for="std_pre_date"><span></span>전일</label>
						<input id="std_this_week" type="radio" name="param[STANDARD]" value="3" data-type="date" data-date-type="this_week" data-date1="param[FROM_DT1]" data-date2="param[TO_DT1]"><label for="std_this_week"><span></span>금주</label>
						<input id="std_pre_week" type="radio" name="param[STANDARD]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT1]" data-date2="param[TO_DT1]"><label for="std_pre_week"><span></span>전주</label>
						<input id="std_this_month" type="radio" name="param[STANDARD]" value="5" data-type="date" data-date-type="this_month" data-date1="param[FROM_DT1]" data-date2="param[TO_DT1]"><label for="std_this_month"><span></span>당월</label>
						<input id="std_pre_month" type="radio" name="param[STANDARD]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT1]" data-date2="param[TO_DT1]"><label for="std_pre_month"><span></span>전월</label>
					</td>
				</tr>
				<tr>
					<th>비교 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT2]" class="inputCal" data-type="date" data-input="compare_input" value="${sessionScope.session.param.ldin_callanalysis.FROM_DT2}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT2]" class="inputCal" data-type="date" data-input="compare_input" value="${sessionScope.session.param.ldin_callanalysis.TO_DT2}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="compare_input" type="radio" name="param[COMPARE]" value="0"><label for="compare_input"><span></span>직접입력</label>
						<input id="compare_pre_date" type="radio" name="param[COMPARE]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[FROM_DT2]" data-date2="param[TO_DT2]"><label for="compare_pre_date"><span></span>전일</label>
						<input id="compare_this_week" type="radio" name="param[COMPARE]" value="3" data-type="date" data-date-type="this_week" data-date1="param[FROM_DT2]" data-date2="param[TO_DT2]"><label for="compare_this_week"><span></span>금주</label>
						<input id="compare_pre_week" type="radio" name="param[COMPARE]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT2]" data-date2="param[TO_DT2]"><label for="compare_pre_week"><span></span>전주</label>
						<input id="compare_this_month" type="radio" name="param[COMPARE]" value="5" data-type="date" data-date-type="this_month" data-date1="param[FROM_DT2]" data-date2="param[TO_DT2]"><label for="compare_this_month"><span></span>당월</label>
						<input id="compare_pre_month" type="radio" name="param[COMPARE]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT2]" data-date2="param[TO_DT2]"><label for="compare_pre_month"><span></span>전월</label>
						<input id="compare_pre_month2" type="radio" name="param[COMPARE]" value="7" data-type="date" data-date-type="pre_month2" data-date1="param[FROM_DT2]" data-date2="param[TO_DT2]"><label for="compare_pre_month2"><span></span>전전월</label>
					</td>
				</tr>
				<tr>
					<th>기준일</th>
					<td>
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" value="-1"<c:if test="${sessionScope.session.param.ldin_callanalysis.RESTDE_ALTV == null || sessionScope.session.param.ldin_callanalysis.RESTDE_ALTV == '-1'}"> checked</c:if>><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" value="0"<c:if test="${sessionScope.session.param.ldin_callanalysis.RESTDE_ALTV == '0'}"> checked</c:if>><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" value="1"<c:if test="${sessionScope.session.param.ldin_callanalysis.RESTDE_ALTV == '1'}"> checked</c:if>><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/ldin/callanalysis/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="tblBox">
			<table class="tblList">
				<colgroup>
					<col width="10%">
					<col width="7.5%"><col width="7.5%"><col width="7.5%"><col width="7.5%">
					<col width="7.5%"><col width="7.5%">
					<col width="7.5%"><col width="7.5%"><col width="7.5%">
				</colgroup>
				<thead>
					<tr id="first">
						<th class="div" rowspan="2">인입구분</th>
						<th class="div" colspan="4" id="std_period">조회 가간</th>
						<th colspan="8" id="compare_period">비교 기간</th>
					</tr>
					<tr>
						<th class="sorting sorting2" data-idx="1">일평균</th>
						<th class="sorting sorting2" data-idx="2">총건수</th>
						<th class="sorting sorting2" data-idx="3" data-type="date">최대일</th>
						<th class="div sorting sorting2" data-idx="4">최대일건수</th>
						<th class="sorting sorting2" data-idx="5">일평균</th>
						<th class="sorting sorting2" data-idx="6">변동건수</th>
						<th class="sorting sorting2" data-idx="7">변동률</th>
						<th class="sorting sorting2" data-idx="8">총건수</th>
						<th class="sorting sorting2" data-idx="9">변동건수</th>
						<th class="sorting sorting2" data-idx="10">변동률</th>
						<th class="sorting sorting2" data-idx="11" data-type="date">최대일</th>
						<th class="sorting sorting2" data-idx="12">최대일건수</th>
					</tr>
				</thead>
				<tbody id="tblList">
					<tr><td colspan="13" class="txt">데이터 조회중입니다.</td></tr>
				</tbody>
			</table>
		</div>

		<div class="tblBox">
			<div class="boxThree half">
				<p class="chTit"><span class="mtit">일 평균 비교</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span>조회기간누적</span>
						<span class="blueDot"></span><span>비교기간누적</span>
					</p> -->
					<div class="chart245" >
						<canvas id="chart1" ></canvas>
					</div>
				</div>
			</div>

			<div class="boxThree half">
				<p class="chTit"><span class="mtit">총 건수 비교</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span>조회기간누적</span>
						<span class="blueDot"></span><span>전월평균</span>
					</p> -->
					<div class="chart245" >
						<canvas id="chart2" ></canvas>
					</div>
				</div>
			</div>

		</div>