<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">실시간 거래 발생 비교</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-session-type="${sessionScope.session.param.use_tranalysis.TYPE}">
			<input type="hidden" name="param[DT]" value="${sessionScope.session.param.use_tranalysis.DT}"/>
			<input type="hidden" name="param[FROM_DT]" value="${sessionScope.session.param.use_tranalysis.FROM_DT}" data-type="formated"/>
			<input type="hidden" name="param[TO_DT]" value="${sessionScope.session.param.use_tranalysis.TO_DT}" data-type="formated"/>
			<input type="hidden" name="param[PRE_DT]" value="${sessionScope.session.param.use_tranalysis.PRE_DT}" data-type="formated"/>
			<input type="text" style="display:none;"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"><col width="11%"><col width="30%"></colgroup>
				<tr id="first">
					<th rowspan="2">비교기준</th>
					<td colspan="3">
						<input id="pre_date" type="radio" name="param[TYPE]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[PRE_DT]" data-date2="param[PRE_DT]" checked data-search="true"><label for="pre_date"><span></span>전일 대비</label>
						<input id="pre_week" type="radio" name="param[TYPE]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="pre_week"><span></span>전주 평균 대비</label>
						<input id="pre_month" type="radio" name="param[TYPE]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="pre_month"><span></span>전월 평균 대비</label>
						<input id="pre_quarter" type="radio" name="param[TYPE]" value="7" data-type="date" data-date-type="pre_quarter" data-date1="param[FROM_DT]" data-date2="param[TO_DT]" data-search="true"><label for="pre_quarter"><span></span>전분기 평균 대비</label>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<input id="pre_same_day" type="radio" name="param[TYPE]" value="101" data-search="true"><label for="pre_same_day"><span></span>전주 동일요일 대비</label>
						<input id="pre_same_date" type="radio" name="param[TYPE]" value="102" data-search="true"><label for="pre_same_date"><span></span>전월 동일일자 대비</label>
						<input id="pre_quarter_date" type="radio" name="param[TYPE]" value="103" data-search="true"><label for="pre_quarter_date"><span></span>전분기 동일차월 동일일자 대비</label>
					</td>
				</tr>
				<tr>
					<th>기준일</th>
					<td>
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" value="-1" data-search="true"<c:if test="${sessionScope.session.param.use_tranalysis.RESTDE_ALTV == null || sessionScope.session.param.use_tranalysis.RESTDE_ALTV == '-1'}"> checked</c:if>><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" value="0" data-search="true"<c:if test="${sessionScope.session.param.use_tranalysis.RESTDE_ALTV == '0'}"> checked</c:if>><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" value="1" data-search="true"<c:if test="${sessionScope.session.param.use_tranalysis.RESTDE_ALTV == '1'}"> checked</c:if>><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
					<th>상하위 제외</th>
					<td>
						<span class="label">상하위</span><input type="text" class="input01 int" name="param[AVG_ABOVE]" value="" data-type="int" data-format="true" maxlength="2"/> <span class="label">%</span>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/use/tranalysis/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="stateArea2">
			<div class="avHour">
				<span class="tit" style="width:auto;">비교일자 &nbsp;&nbsp;: </span>
				<span class="num" style="width:auto;padding-left:10px;" id="compare_period"></span>
			</div>
			<div class="avHour" style="float:right;">
				<span class="tit" style="width:auto;">현재일자 &nbsp;&nbsp;: </span>
				<span class="num" style="width:auto;padding-left:10px;" id="std_period"></span>
			</div>
		</div>
		<div class="chartWrap">
			<div class="chTit">
				<span class="mtit">실시간 거래 발생 현황</span>
				<span class="index">단위 : 건수</span>
			</div>
			<div class="chartFrame5">
				<!-- <div class="chLegend">
					<span class="redDot"></span><span class="sort" id="compare_label">전일</span>
					<span class="skyDot"></span><span class="sort">실시간인입콜</span>
				</div> -->
				<div class="chart440">
					<canvas id="myChart"  ></canvas>
				</div>
			</div>
		</div>