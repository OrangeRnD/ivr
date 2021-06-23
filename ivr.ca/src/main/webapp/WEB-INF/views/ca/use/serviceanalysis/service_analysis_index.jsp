<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">전체 서비스 현황 조회</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-url="/use/serviceanalysis/list" data-target="tblList" data-search="true" data-append="false" data-session-type1="${sessionScope.session.param.ldin_serviceanalysis.STANDARD}"  data-session-type2="${sessionScope.session.param.ldin_serviceanalysis.COMPARE}">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT1]" class="inputCal" data-type="date" data-input="std_input" value="${sessionScope.session.param.ldin_serviceanalysis.FROM_DT1}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT1]" class="inputCal" data-type="date" data-input="std_input" value="${sessionScope.session.param.ldin_serviceanalysis.TO_DT1}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
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
					<th>비교기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT2]" class="inputCal" data-type="date" data-input="compare_input" value="${sessionScope.session.param.ldin_serviceanalysis.FROM_DT2}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT2]" class="inputCal" data-type="date" data-input="compare_input" value="${sessionScope.session.param.ldin_serviceanalysis.TO_DT2}"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
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
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" value="-1"<c:if test="${sessionScope.session.param.ldin_serviceanalysis.RESTDE_ALTV == null || sessionScope.session.param.ldin_serviceanalysis.RESTDE_ALTV == '-1'}"> checked</c:if>><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" value="0"<c:if test="${sessionScope.session.param.ldin_serviceanalysis.RESTDE_ALTV == '0'}"> checked</c:if>><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" value="1"<c:if test="${sessionScope.session.param.ldin_serviceanalysis.RESTDE_ALTV == '1'}"> checked</c:if>><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
				</tr>
				<tr>
					<th>서비스</th>
					<td>
						<ul class="svList" id="cdList">
							<c:forEach var="item" items="${SRVC_CD}" varStatus="status"><li><div class="svObj">${item.SRVC_NM}<input type="hidden" name="param[EX_CDS]" value="${item.SRVC_CD}"/><a class="svtDelete">삭제</a></div></li></c:forEach>
							<li><input type="text" class="input02" id="srvc_nm" placeholder="서비스명 검색">
								<div class="popupRecom" style="display:none;">
									<ul id="popup_list">
									</ul>
								</div>
							</li>
							<li><a id="btn_srvc"><img src="${webappRoot}/resources/images/icon_magnifier.png" style="margin:3px 0 0 5px;"></a></li>
						</ul>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/use/serviceanalysis/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="tblBox">
			<div class="tblContainer2" style="height:460px;" id="tblcontainer">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th class="div" rowspan="2" style="width:10%"><div class="th-text2">서비스</div></th>
								<th class="div" colspan="4" style="width:30%"><div class="th-text" id="std_period">조회 가간</div></th>
								<th colspan="8" style="width:60%"><div class="th-text" id="compare_period">비교 기간</div></th>
							</tr>
							<tr>
								<th style="width:7%"><div class="th-text3 sorting" data-idx="1">일평균</div></th>
								<th style="width:7%"><div class="th-text3 sorting" data-idx="2">총건수</div></th>
								<th style="width:8.5%"><div class="th-text3 sorting" data-idx="3" data-type="date">최대일</div></th>
								<th class="div" style="width:7.5%"><div class="th-text3 sorting" data-idx="4">최대일건수</div></th>
								<th style="width:7.5%"><div class="th-text3 sorting" data-idx="5">일평균</div></th>
								<th style="width:7.5%"><div class="th-text3 sorting" data-idx="6">변동건수</div></th>
								<th style="width:7.5%"><div class="th-text3 sorting" data-idx="7">변동률</div></th>
								<th style="width:7.5%"><div class="th-text3 sorting" data-idx="8">총건수</div></th>
								<th style="width:7%"><div class="th-text3 sorting" data-idx="9">변동건수</div></th>
								<th style="width:7%"><div class="th-text3 sorting" data-idx="10">변동률</div></th>
								<th style="width:8.5%"><div class="th-text3 sorting" data-idx="11" data-type="date">최대일</div></th>
								<th style="width:7.5%"><div class="th-text3 sorting" data-idx="12">최대일건수</div></th>
							</tr>
						</thead>
						<tbody id="tblList">
							<tr><td colspan="13" class="txt">데이터 조회중입니다.</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
		</div>
		
		<!--팝업 시작-->
		<div class="popupBox" style="display:none;" id="srvc_popup">
			<div class="layerPopup" style="width:600px; height:650px; top:calc(50% - 325px); left:calc(50% - 300px);  ">
				<p class="popTit">서비스코드<a class="closeBtn" id="ico_popup_close">닫기</a></p>
				<div id="popup_contents">
				</div>
			</div>
			<div class="popupDimed" id="popupDimed"></div>
		</div>
		<!--팝업 끝-->