<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">알림 관리</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-target="lists" data-session-type="${sessionScope.session.param.system_policy.STANDARD}">
			<input type="hidden" name="param[ANLYS_PLCY_ID]" value=""/>
			<input type="hidden" name="pageNo" value="1"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.system_policy.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.system_policy.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="lately_week" type="radio" name="param[STANDARD]" value="3" data-search="true" data-type="date" data-date-type="lately_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_week"><span></span>최근1주</label>
						<input id="lately_month" type="radio" name="param[STANDARD]" value="4" data-search="true" data-type="date" data-date-type="lately_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_month"><span></span>최근 1개월</label>
						<input id="lately_3month" type="radio" name="param[STANDARD]" value="5" data-search="true" data-type="date" data-date-type="lately_3month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_3month"><span></span>최근 3개월</label>
					</td>
				</tr>
			</table>
			</form>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/system/policy/excel">엑셀</a>
				</div>
			</div>
		</div>

		<div class="tblBox">
			<div class="tblContainer1">
				<div class="header-bg"></div>
				<div class="tblWrapper" style="max-height:500px;height:auto;">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th style="width:5%"><div class="th-text">No.</div></th>
								<th style="width:20%"><div class="th-text">알림명</div></th>
								<th style="width:15%"><div class="th-text">알림발생일시</div></th>
								<th style="width:10%"><div class="th-text">인입건수</div></th>
								<th style="width:10%"><div class="th-text">비교건수</div></th>
								<th style="width:10%"><div class="th-text">알림설정</div></th>
								<th style="width:10%"><div class="th-text">초과</div></th>
								<th style="width:10%"><div class="th-text">SMS전송</div></th>
								<th style="width:10%"><div class="th-text">E-mail전송</div></th>
							</tr>
						</thead>
						<tbody id="lists">
							<tr><td colspan="9" class="txt">데이터 조회중입니다.</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
			<div class="page_wrap" id="page_wrap">
				<div class="pagingData"></div>
			</div>
		</div>		