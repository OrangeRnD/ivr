<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">거래, 에러 현황 조회</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-url="/use/status/list" data-target="lists" data-search="true" data-session-type="${sessionScope.session.param.use_status.STANDARD}">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.use_status.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.use_status.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
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
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="-1"<c:if test="${sessionScope.session.param.use_status.RESTDE_ALTV == null || sessionScope.session.param.use_status.RESTDE_ALTV == '-1'}"> checked</c:if>><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="0"<c:if test="${sessionScope.session.param.use_status.RESTDE_ALTV == '0'}"> checked</c:if>><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="1"<c:if test="${sessionScope.session.param.use_status.RESTDE_ALTV == '1'}"> checked</c:if>><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/use/status/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>
		<div class="stateArea2">
			<div class="avHour">
				<span class="tit" style="width:auto;">조회 기간 &nbsp;&nbsp;: </span>
				<span class="num" style="width:auto;padding-left:10px;" id="std_period"></span>
			</div>
		</div>
		<div id="lists">
			<jsp:include page="./use_status_list.jsp"></jsp:include>
		</div>