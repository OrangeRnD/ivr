<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">이용자별 서비스 이용현황</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value=""><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value=""><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_now" type="radio" name="param[STANDARD]" value="1" data-type="date" data-date-type="now" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_now"><span></span>당일</label>
						<input id="std_pre_date" type="radio" name="param[STANDARD]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_date"><span></span>전일</label>
						<input id="std_this_week" type="radio" name="param[STANDARD]" value="3" data-type="date" data-date-type="this_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_this_week"><span></span>금주</label>
						<input id="std_pre_week" type="radio" name="param[STANDARD]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_week"><span></span>전주</label>
						<input id="std_this_month" type="radio" name="param[STANDARD]" value="5" data-type="date" data-date-type="this_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_this_month"><span></span>당월</label>
						<input id="std_pre_month" type="radio" name="param[STANDARD]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_month"><span></span>전월</label>
					</td>
				</tr>
				<tr>
					<th>기준일</th>
					<td>
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" value="-1" checked><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" value="0"><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" value="1" ><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-excel="/use/service/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="tblBox">
			<p class="sTitle">이용자별서비스 Top-10</p>
			<div class="tblContainer2" style="height:280px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th colspan="2" style="width:50%"><div class="th-text">개인</div></th>
								<th colspan="2" style="width:50%"><div class="th-text">사업자</div></th>
							</tr>
							<tr>
								<th style="width:25%"><div class="th-text3">서비스명</div></th>
								<th style="width:25%"><div class="th-text3">건수</div></th>
								<th style="width:25%"><div class="th-text3">서비스명</div></th>
								<th style="width:25%"><div class="th-text3">건수</div></th>
							</tr>
						</thead>
						<tbody id="lists1">
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="tblBox">
			<p class="sTitle">연령별 서비스 Top-10</p>
			<div class="tblContainer2" style="height:280px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table class="fixed-table">
						<thead>
							<tr>
								<th colspan="2" style="width:17%"><div class="th-text">20대 이하</div></th>
								<th colspan="2" style="width:17%"><div class="th-text">30대</div></th>
								<th colspan="2" style="width:17%"><div class="th-text">40대</div></th>
								<th colspan="2" style="width:17%"><div class="th-text">50대</div></th>
								<th colspan="2" style="width:16%"><div class="th-text">70대</div></th>
								<th colspan="2" style="width:16%"><div class="th-text">70대 이상</div></th>
							</tr>
							<tr>
								<th style="width:9%"><div class="th-text3">서비스명</div></th><th style="width:8%"><div class="th-text3">건수</div></th>
								<th style="width:9%"><div class="th-text3">서비스명</div></th><th style="width:8%"><div class="th-text3">건수</div></th>
								<th style="width:9%"><div class="th-text3">서비스명</div></th><th style="width:8%"><div class="th-text3">건수</div></th>
								<th style="width:9%"><div class="th-text3">서비스명</div></th><th style="width:8%"><div class="th-text3">건수</div></th>
								<th style="width:8%"><div class="th-text3">서비스명</div></th><th style="width:8%"><div class="th-text3">건수</div></th>
								<th style="width:8%"><div class="th-text3">서비스명</div></th><th style="width:8%"><div class="th-text3">건수</div></th>
							</tr>
						</thead>
						<tbody id="lists2">
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
						</tbody>
					</table>
				</div>
				</div>
			</div>