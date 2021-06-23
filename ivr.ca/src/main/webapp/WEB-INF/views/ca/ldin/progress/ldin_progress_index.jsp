<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">인입추이(일별, 월별)</p>
		</div>

		<div class="stateArea">
			<div class="dateLeft" id="prcs_dt">
			</div>
			<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/ldin/progress/excel">엑셀</a>
			</div>
		</div>
		<form name="search_frm">
			<input type="hidden" name="ym" value="${sessionScope.session.param.ldin_progress_daily}"/>
			<input type="hidden" name="year" value="${sessionScope.session.param.ldin_progress_monthly.YEAR}"/>
			<input type="hidden" name="restde" value=""/>
		</form>
		<div class="chartWrap">
			<div class="chTit">
				<span class="mtit">일별 추이</span>
				<span class="index">단위 : 건수 (일별)</span>
			</div>
			<div class="chartFrame2">
				<div class="chSelect">
						<select class="seltype01 wdt80px" id="dly_year">
						</select>
						<select class="seltype01 wdt60px" id="dly_month">
						</select>
				</div>
				<!-- <div class="chLegend">
					<span class="puppleDot"></span><span class="sort">거래</span>
					<span class="skyDot"></span><span class="sort">이용자</span>
					<span class="orangeDot"></span><span class="sort">콜</span>
				</div> -->
				<div class="chart280 selectLegend" >
					<canvas id="myChart"></canvas>
				</div>

			</div>
		</div>

		<div class="chartWrap">
			<div class="chTit">
				<span class="mtit">월별 추이</span>
				<span class="selectBox">
					<input id="restde_altv" type="radio" name="radio"<c:if test="${sessionScope.session.param.ldin_progress_monthly.RESTDE_ALTV == null}"> checked</c:if>><label for="restde_altv"><span></span>전체</label>
					<input id="restde_altv0" type="radio" name="radio"<c:if test="${sessionScope.session.param.ldin_progress_monthly.RESTDE_ALTV == '0'}"> checked</c:if>><label for="restde_altv0"><span></span>영업일만</label>
					<input id="restde_altv1" type="radio" name="radio"<c:if test="${sessionScope.session.param.ldin_progress_monthly.RESTDE_ALTV == '1'}"> checked</c:if>><label for="restde_altv1"><span></span>휴일만</label>
				</span>
				<span class="index">단위 : 건수 (월별)</span>
			</div>
			<div class="chartFrame2">
				<div class="chSelect">
					<select class="seltype01 wdt80px" id="mly_year">
					</select>
				</div>
				<!-- <div class="chLegend">
					<span class="puppleDot"></span><span class="sort">거래</span>
					<span class="skyDot"></span><span class="sort">이용자</span>
					<span class="orangeDot"></span><span class="sort">콜</span>
				</div> -->
				<div class="chart280 selectLegend">
					<canvas id="myChart2" ></canvas>
				</div>

			</div>
		</div>