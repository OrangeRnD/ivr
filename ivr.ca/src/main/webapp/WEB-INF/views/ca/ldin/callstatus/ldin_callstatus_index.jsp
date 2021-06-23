<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">인입 총괄 현황</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회기간</th>
					<td>
						<select class="seltype01 wdt80px" name="param[FROM_Y]" id="f_year" data-value="${sessionScope.session.param.ldin_callstatus_FROM_Y}">
						</select>
						<select class="seltype01 wdt60px" name="param[FROM_M]" id="f_month" data-value="${sessionScope.session.param.ldin_callstatus_FROM_M}">
						</select>
						<span class="wave">~</span> 
						<select class="seltype01 wdt80px" name="param[TO_Y]" id="t_year" data-value="${sessionScope.session.param.ldin_callstatus_TO_Y}">
						</select>
						<select class="seltype01 wdt60px" name="param[TO_M]" id="t_month" data-value="${sessionScope.session.param.ldin_callstatus_TO_M}">
						</select>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
					<a class="btntype02 btn_primary" id="btn_search">조회</a>
					<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
					<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/ldin/callstatus/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="tblBox">
			<p class="sTitle">매체별 인입 현황</p>
			<div class="tblContainer1" style="height:252px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table" id="table_meda">
						<thead>
							<tr>
								<th style="width:10%"><div class="th-text div sorting sortingUp" data-type="date">년월</div></th>
								<th style="width:10%"><div class="th-text div sorting">총계</div></th>
								<th colspan="2" style="width:20%"><div class="th-text div sorting" data-idx="2">휴대폰</div></th>
								<th colspan="2" style="width:20%"><div class="th-text div sorting" data-idx="4">일반전화</div></th>
								<th colspan="2" style="width:20%"><div class="th-text div sorting" data-idx="6">인터넷전화(070)</div></th>
								<th colspan="2" style="width:20%"><div class="th-text sorting" data-idx="8">해외</div></th>
							</tr>
						</thead>
						<tbody id="meda_list">
							<tr><td colspan="10" class="txt">데이터 조회중입니다.</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
		</div>

		<div class="tblBox">
			<!-- <p class="sTitle">IVR 현황</p> -->
			<p class="chTit"><span class="mtit">IVR 현황</span><span class="command">(그리드 클릭 시 ARS유형별이용현황 화면으로 이동합니다.)</span></p>
			<div class="tblContainer2" style="height:276px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table" id="table_ivr">
						<thead>
							<tr>
								<th  rowspan="2" style="width:7%"><div class="th-text2 div sorting sortingUp" data-type="date">년월</div></th>
								<th  rowspan="2" style="width:9%"><div class="th-text2 div sorting">IVR 인입콜</div></th>
								<th  rowspan="2" colspan="2" style="width:14%"><div class="th-text2 div sorting" data-idx="2">대표번호</div></th>
								<th  rowspan="2" colspan="2" style="width:14%"><div class="th-text2 div sorting" data-idx="4">보이는 ARS</div></th>
								<th  rowspan="2" colspan="2" style="width:14%"><div class="th-text2 div sorting" data-idx="6">말로 하는 ARS</div></th>
								<th  colspan="6" style="width:42%"><div class="th-text">종료사유</div></th>
							</tr>
							<tr>
								<th style="width:14%" colspan="2"><div class="th-text3 div sorting" data-idx="8">IVR 종료</div></th>
								<th style="width:14%" colspan="2"><div class="th-text3 div sorting" data-idx="10">고객 종료</div></th>
								<th style="width:14%" colspan="2"><div class="th-text3 sorting" data-idx="12">상담원연결요청</div></th>
							</tr>
						</thead>
						<tbody id="ivr_list">
							<tr><td colspan="14" class="txt">데이터 조회중입니다.</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
		</div>

		<div class="tblBox">
			<div class="boxTwo">
				<p class="chTit"><span class="mtit">개인 / 사업자 콜 현황</span><span class="index">단위 : 명</span></p>
				<div class="chartFrame">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span class="sort">개인</span>
						<span class="blueDot"></span><span class="sort">사업자</span>
						<span class="greenDot"></span><span class="sort">미인증</span>
					</p> -->
					<div class="chart245" >
						<canvas id="userChart" ></canvas>
					</div>
				</div>
			</div>

			<div class="boxTwo">
				<p class="chTit"><span class="mtit">연령대별 콜 현황</span><span class="index">단위 : 명</span></p>
				<div class="chartFrame">
					<!-- <p class="chLegend2">
						<span class="redDot"></span><span class="sort">20대이하</span>
						<span class="blueDot"></span><span class="sort">30대</span>
						<span class="greenDot"></span><span class="sort">40대</span>
						<span class="puppleDot"></span><span class="sort">50대</span>
						<span class="skyDot"></span><span class="sort">60대</span>
						<span class="orangeDot"></span><span class="sort">70대이상</span>
					</p> -->
					<div class="chart245" >
						<canvas id="ageChart" ></canvas>
					</div>
				</div>
			</div>
		</div>