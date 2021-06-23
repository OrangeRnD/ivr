<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">알림 발생 현황 관리</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-search="true" data-session-type="${sessionScope.session.param.ldin_policy_result.STANDARD}">
			<input type="hidden" name="param[ANLYS_PLCY_ID]" value=""/>
			<input type="hidden" name="pageNo" value="1"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.ldin_policy_result.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.ldin_policy_result.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
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
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/ldin/policy/result/excel">엑셀</a>
				</div>
			</div>
		</div>

		<div class="tblBox">
			<div class="chTit" style="width:50%;padding-top:10px;">
				<span class="mtit">알림 발생 목록</span>
			</div>
			<div style="width:50%;position:relative;padding-bottom:10px;float:right;"><a class="btntype03 btn_info" style="float:right;" id="btn_policy">알림 설정 관리</a></div>
			<div class="tblContainer1" style="float:left;">
				<div class="header-bg"></div>
				<div class="tblWrapper"  style="max-height:400px;height:auto;">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th style="width:20%"><div class="th-text">알림명</div></th>
								<th style="width:10%"><div class="th-text">비교 기준</div></th>
								<th style="width:10%"><div class="th-text">기준일</div></th>
								<th style="width:10%"><div class="th-text">상하위 제외</div></th>
								<th style="width:10%"><div class="th-text">알림설정</div></th>
								<th style="width:10%"><div class="th-text">SMS전송</div></th>
								<th style="width:9%"><div class="th-text">E-mail전송</div></th>
								<th style="width:9%"><div class="th-text">알림발생건수</div></th>
								<th style="width:12%"><div class="th-text">최종알림일시</div></th>
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
		
		<div class="popupBox" id="popupBox" style="display:none;">
			<div class="layerPopup" style="width:600px; height:360px; top:calc(50% - 180px); left:calc(50% - 300px);  ">
				<p class="popTit">알림 발생 상세 내역<a class="closeBtn" id="btn_close">닫기</a></p>
				<div class="tblContainer1"  style="height:252px;">
					<div class="header-bg"></div>
					<div class="tblWrapper">
						<table  class="fixed-table">
							<thead>
								<tr>
									<th style="width:10%"><div class="th-text">No.</div></th>
									<th style="width:30%"><div class="th-text">발생일시</div></th>
									<th style="width:20%"><div class="th-text">인입건수</div></th>
									<th style="width:20%"><div class="th-text">비교건수</div></th>
									<th style="width:20%"><div class="th-text">초과</div></th>
								</tr>
							</thead>
							<tbody id="detail_list">
								<jsp:include page="./ldin_policy_result_dtl.jsp"></jsp:include>
							</tbody>
					     </table>
					</div>
				</div>
				
				<%-- <table class="tblList">
					<colgroup>
						<col width="10%"><col width="30%"><col width="20%"><col width="20%"><col width="20%">
					</colgroup>
					<thead>
					<tr id="first">
						<th>No.</th>
						<th>발생일시</th>
						<th>인입건수</th>
						<th>비교건수</th>
						<th>비율</th>
					</tr>
					</thead>
					<tbody id="detail_list">
						<jsp:include page="./ldin_policy_result_dtl.jsp"></jsp:include>
					</tbody>
				</table> --%>
			</div>
			<div class="popupDimed" id="popupDimed"></div>
		</div>
		<div class="popupBox" id="policy_box" style="width:100%; height:100%; position:absolute;display:none;">
			<div class="layerPopup" style="width:100%; height:100%;">
				<p class="popTit">알림 설정 관리<a class="closeBtn" id="ico_policy_close">닫기</a></p>
				<jsp:include page="./ldin_policy_index.jsp"></jsp:include>
			</div>
			<div class="popupDimed" id="popupDimed2"></div>
		</div>