<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">서비스이용 고객 패턴 분석</p>
		</div>
		<form data-type="search" name="search_frm" data-session-type="${sessionScope.session.param.analysis_pattern.STANDARD}">
		<div class="tblBox small">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>분석 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.analysis_pattern.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.analysis_pattern.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="lately_week" type="radio" name="param[STANDARD]" value="3" data-type="date" data-date-type="lately_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_week"><span></span>최근1주</label>
						<input id="lately_month" type="radio" name="param[STANDARD]" value="4" data-type="date" data-date-type="lately_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_month"><span></span>최근 1개월</label>
						<input id="lately_3month" type="radio" name="param[STANDARD]" value="5" data-type="date" data-date-type="lately_3month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_3month"><span></span>최근 3개월</label>
					</td>
				</tr>
				<tr>
					<th>기준 값</th>
					<td>
						<input id="STD_VALUE_5" type="radio" name="STD_VALUE" value="5"<c:if test="${sessionScope.session.param.analysis_pattern.STD_VALUE == null || sessionScope.session.param.analysis_pattern.STD_VALUE == '5'}"> checked</c:if>><label for="STD_VALUE_5"><span></span>상하위 5%</label>
						<input id="STD_VALUE_10" type="radio" name="STD_VALUE" value="10"<c:if test="${sessionScope.session.param.analysis_pattern.STD_VALUE == '10'}"> checked</c:if>><label for="STD_VALUE_10"><span></span>상하위 10%</label>
						<input id="STD_VALUE" type="radio" name="STD_VALUE" value=""<c:if test="${sessionScope.session.param.analysis_pattern.STD_VALUE != null && sessionScope.session.param.analysis_pattern.STD_VALUE != '5' && sessionScope.session.param.analysis_pattern.STD_VALUE != '10'}"> checked</c:if>><label for="STD_VALUE"><span></span>직접 입력</label>
						<c:choose><c:when test="${sessionScope.session.param.analysis_pattern.STD_VALUE == null}">
						<input type="text" class="input02" name="param[STD_VALUE]" placeholder="%" data-type="int" maxlength="2" style="float:none;display:none;" value="5"/><span style="display:none;">%</span>
						</c:when><c:otherwise>
						<input type="text" class="input02" name="param[STD_VALUE]" placeholder="%" data-type="int" maxlength="2" style="float:none;display:none;" value="${sessionScope.session.param.analysis_pattern.STD_VALUE}"/><span style="display:none;">%</span>
						</c:otherwise></c:choose>
						<a class="btntype05 btn_info" id="btn_std">기준값설정</a>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/analysis/pattern/excel">엑셀</a>
				</div>
			</div>
		</div>
		<c:choose><c:when test="${sessionScope.session.param.analysis_pattern == null}"><div class="tblBox small">
			<table class="tbltype01" id="policy">
				<colgroup><col width="11%"><col width="60px"><col width="200px"><col width="60px"><col width="320px"><col width="60px"><col width="*"></colgroup>
				<tr id="first">
					<th class="left"><input type="checkbox" id="call_chk" checked><label for="call_chk"><span></span>콜 이용 건수</label></th>
					<td class="subTh"><input id="CALL_L" type="radio" name="CALL_CNT" checked><label for="CALL_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_1" class="input01 wdt120px _checked_" name="param[CALL_L]" data-type="int" value="" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_M" type="radio" name="CALL_CNT"><label for="CALL_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_1_1" class="input01 wdt120px" name="param[CALL_M_F]" data-type="int" value="" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_2_1" class="input01 wdt120px" name="param[CALL_M_T]" data-type="int" value="" data-target="CALL_M"/></td>
					
					<td class="subTh"><input id="CALL_U" type="radio" name="CALL_CNT"  ><label for="CALL_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_2" class="input01 wdt120px" name="param[CALL_U]" data-type="int" value="" maxlength="9"/><span class="txt">이상</span></td>
				</tr>
				<tr>
					<th class="left"><input type="checkbox" id="cnslr_conn_chk" checked><label for="cnslr_conn_chk"><span></span>상담원 연결 건수</label></th>
					<td class="subTh"><input id="CNSLR_CONN_L" type="radio" name="CNSLR_CONN_CNT" checked><label for="CNSLR_CONN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_3" class="input01 wdt120px _checked_" name="param[CNSLR_CONN_L]" data-type="int" value="" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CNSLR_CONN_M" type="radio" name="CNSLR_CONN_CNT"><label for="CNSLR_CONN_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_3_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_F]" data-type="int" value="" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_4_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_T]" data-type="int" value="" data-target="CNSLR_CONN_M"/></td>
					
					<td class="subTh"><input id="CNSLR_CONN_U" type="radio" name="CNSLR_CONN_CNT"  ><label for="CNSLR_CONN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_4" class="input01 wdt120px" name="param[CNSLR_CONN_U]" data-type="int" value="" maxlength="9"/><span class="txt">이상</span></td>
				</tr>
				<tr>
					<th class="left"><input type="checkbox" id="call_tran_chk" checked><label for="call_tran_chk"><span></span>콜당 거래 건수</label></th>
					<td class="subTh"><input id="CALL_TRAN_L" type="radio" name="CALL_TRAN_CNT" checked><label for="CALL_TRAN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_5" class="input01 wdt120px _checked_" name="param[CALL_TRAN_L]" data-type="int" value="" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_TRAN_M" type="radio" name="CALL_TRAN_CNT"><label for="CALL_TRAN_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_5_1" class="input01 wdt120px" name="param[CALL_TRAN_M_F]" data-type="int" value="" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_6_1" class="input01 wdt120px" name="param[CALL_TRAN_M_T]" data-type="int" value="" data-target="CALL_TRAN_M"/></td>
					
					<td class="subTh"><input id="CALL_TRAN_U" type="radio" name="CALL_TRAN_CNT"  ><label for="CALL_TRAN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_6" class="input01 wdt120px" name="param[CALL_TRAN_U]" data-type="int" value="" maxlength="9"/><span class="txt">이상</span></td>
				</tr>
				<tr>
					<th class="left"><input type="checkbox" id="avg_use_chk" checked><label for="avg_use_chk"><span></span>이용시간 (초)</label></th>
					<td class="subTh"><input id="AVG_USE_TM_L" type="radio" name="AVG_USE_TM_CNT" checked><label for="AVG_USE_TM_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_7" class="input01 wdt120px _checked_" name="param[AVG_USE_TM_L]" data-type="int" value="" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="AVG_USE_TM_M" type="radio" name="AVG_USE_TM_CNT"><label for="AVG_USE_TM_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_7_1" class="input01 wdt120px" name="param[AVG_USE_TM_M_F]" data-type="int" value="" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_8_1" class="input01 wdt120px" name="param[AVG_USE_TM_M_T]" data-type="int" value="" data-target="AVG_USE_TM_M"/></td>
					
					<td class="subTh"><input id="AVG_USE_TM_U" type="radio" name="AVG_USE_TM_CNT"><label for="AVG_USE_TM_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_8" class="input01 wdt120px" name="param[AVG_USE_TM_U]" data-type="int" value="" maxlength="9"/><span class="txt">이상</span></td>
				</tr>
			</table>
		</div></c:when>
		<c:otherwise><div class="tblBox small">
			<table class="tbltype01" id="policy">
				<colgroup><col width="11%"><col width="60px"><col width="200px"><col width="60px"><col width="320px"><col width="60px"><col width="*"></colgroup>
				<c:choose><c:when test="${sessionScope.session.param.analysis_pattern.containsKey('CALL_L') || sessionScope.session.param.analysis_pattern.containsKey('CALL_M_F') || sessionScope.session.param.analysis_pattern.containsKey('CALL_M_T') || sessionScope.session.param.analysis_pattern.containsKey('CALL_U')}"><tr id="first">
					<th class="left"><input type="checkbox" id="call_chk" checked><label for="call_chk"><span></span>콜 이용 건수</label></th>
					<td class="subTh"><input id="CALL_L" type="radio" name="CALL_CNT"><label for="CALL_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_1" class="input01 wdt120px" name="param[CALL_L]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_M" type="radio" name="CALL_CNT"><label for="CALL_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_1_1" class="input01 wdt120px" name="param[CALL_M_F]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_M_F}" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_2_1" class="input01 wdt120px" name="param[CALL_M_T]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_M_T}" data-target="CALL_M"/></td>
					
					<td class="subTh"><input id="CALL_U" type="radio" name="CALL_CNT"  ><label for="CALL_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_2" class="input01 wdt120px" name="param[CALL_U]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr id="first">
					<th class="left"><input type="checkbox" id="call_chk"><label for="call_chk"><span></span>콜 이용 건수</label></th>
					<td class="subTh"><input id="CALL_L" type="radio" name="CALL_CNT" checked disabled><label for="CALL_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_1" class="input01 wdt120px _checked_" name="param[CALL_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_M" type="radio" name="CALL_CNT" disabled><label for="CALL_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_1_1" class="input01 wdt120px" name="param[CALL_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_2_1" class="input01 wdt120px" name="param[CALL_M_T]" data-type="int" value="" data-target="CALL_M" disabled/></td>
					
					<td class="subTh"><input id="CALL_U" type="radio" name="CALL_CNT" disabled><label for="CALL_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_2" class="input01 wdt120px" name="param[CALL_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
				<c:choose><c:when test="${sessionScope.session.param.analysis_pattern.containsKey('CNSLR_CONN_L') || sessionScope.session.param.analysis_pattern.containsKey('CNSLR_CONN_M_F') || sessionScope.session.param.analysis_pattern.containsKey('CNSLR_CONN_M_T') || sessionScope.session.param.analysis_pattern.containsKey('CNSLR_CONN_U')}"><tr>
					<th class="left"><input type="checkbox" id="cnslr_conn_chk" checked><label for="cnslr_conn_chk"><span></span>상담원 연결 건수</label></th>
					<td class="subTh"><input id="CNSLR_CONN_L" type="radio" name="CNSLR_CONN_CNT"><label for="CNSLR_CONN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_3" class="input01 wdt120px" name="param[CNSLR_CONN_L]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CNSLR_CONN_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CNSLR_CONN_M" type="radio" name="CNSLR_CONN_CNT"><label for="CNSLR_CONN_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_3_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_F]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CNSLR_CONN_M_F}" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_4_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_T]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CNSLR_CONN_M_T}" data-target="CNSLR_CONN_M"/></td>
					
					<td class="subTh"><input id="CNSLR_CONN_U" type="radio" name="CNSLR_CONN_CNT"  ><label for="CNSLR_CONN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_4" class="input01 wdt120px" name="param[CNSLR_CONN_U]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CNSLR_CONN_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr>
					<th class="left"><input type="checkbox" id="cnslr_conn_chk"><label for="cnslr_conn_chk"><span></span>상담원 연결 건수</label></th>
					<td class="subTh"><input id="CNSLR_CONN_L" type="radio" name="CNSLR_CONN_CNT" checked disabled><label for="CNSLR_CONN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_3" class="input01 wdt120px _checked_" name="param[CNSLR_CONN_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CNSLR_CONN_M" type="radio" name="CNSLR_CONN_CNT" disabled><label for="CNSLR_CONN_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_3_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_4_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_T]" data-type="int" value="" data-target="CNSLR_CONN_M" disabled/></td>
					
					<td class="subTh"><input id="CNSLR_CONN_U" type="radio" name="CNSLR_CONN_CNT" disabled><label for="CNSLR_CONN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_4" class="input01 wdt120px" name="param[CNSLR_CONN_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
				<c:choose><c:when test="${sessionScope.session.param.analysis_pattern.containsKey('CALL_TRAN_L') || sessionScope.session.param.analysis_pattern.containsKey('CALL_TRAN_M_F') || sessionScope.session.param.analysis_pattern.containsKey('CALL_TRAN_M_T') || sessionScope.session.param.analysis_pattern.containsKey('CALL_TRAN_U')}"><tr>
					<th class="left"><input type="checkbox" id="call_tran_chk" checked><label for="call_tran_chk"><span></span>콜당 거래 건수</label></th>
					<td class="subTh"><input id="CALL_TRAN_L" type="radio" name="CALL_TRAN_CNT"><label for="CALL_TRAN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_5" class="input01 wdt120px" name="param[CALL_TRAN_L]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_TRAN_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_TRAN_M" type="radio" name="CALL_TRAN_CNT"><label for="CALL_TRAN_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_5_1" class="input01 wdt120px" name="param[CALL_TRAN_M_F]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_TRAN_M_F}" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_6_1" class="input01 wdt120px" name="param[CALL_TRAN_M_T]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_TRAN_M_T}" data-target="CALL_TRAN_M"/></td>
					
					<td class="subTh"><input id="CALL_TRAN_U" type="radio" name="CALL_TRAN_CNT"  ><label for="CALL_TRAN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_6" class="input01 wdt120px" name="param[CALL_TRAN_U]" data-type="int" value="${sessionScope.session.param.analysis_pattern.CALL_TRAN_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr>
					<th class="left"><input type="checkbox" id="call_tran_chk"><label for="call_tran_chk"><span></span>콜당 거래 건수</label></th>
					<td class="subTh"><input id="CALL_TRAN_L" type="radio" name="CALL_TRAN_CNT" checked disabled><label for="CALL_TRAN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_5" class="input01 wdt120px _checked_" name="param[CALL_TRAN_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_TRAN_M" type="radio" name="CALL_TRAN_CNT" disabled><label for="CALL_TRAN_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_5_1" class="input01 wdt120px" name="param[CALL_TRAN_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_6_1" class="input01 wdt120px" name="param[CALL_TRAN_M_T]" data-type="int" value="" data-target="CALL_TRAN_M" disabled/></td>
					
					<td class="subTh"><input id="CALL_TRAN_U" type="radio" name="CALL_TRAN_CNT" disabled><label for="CALL_TRAN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_6" class="input01 wdt120px" name="param[CALL_TRAN_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
				<c:choose><c:when test="${sessionScope.session.param.analysis_pattern.containsKey('AVG_USE_TM_L') || sessionScope.session.param.analysis_pattern.containsKey('AVG_USE_TM_M_F') || sessionScope.session.param.analysis_pattern.containsKey('AVG_USE_TM_M_T') || sessionScope.session.param.analysis_pattern.containsKey('AVG_USE_TM_U')}"><tr>
					<th class="left"><input type="checkbox" id="avg_use_chk" checked><label for="avg_use_chk"><span></span>이용시간 (초)</label></th>
					<td class="subTh"><input id="AVG_USE_TM_L" type="radio" name="AVG_USE_TM_CNT"><label for="AVG_USE_TM_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_7" class="input01 wdt120px" name="param[AVG_USE_TM_L]" data-type="int" value="${sessionScope.session.param.analysis_pattern.AVG_USE_TM_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="AVG_USE_TM_M" type="radio" name="AVG_USE_TM_CNT"><label for="AVG_USE_TM_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_7_1" class="input01 wdt120px" name="param[AVG_USE_TM_M_F]" data-type="int" value="${sessionScope.session.param.analysis_pattern.AVG_USE_TM_M_F}" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_8_1" class="input01 wdt120px" name="param[AVG_USE_TM_M_T]" data-type="int" value="${sessionScope.session.param.analysis_pattern.AVG_USE_TM_M_T}" data-target="AVG_USE_TM_M"/></td>
					
					<td class="subTh"><input id="AVG_USE_TM_U" type="radio" name="AVG_USE_TM_CNT"><label for="AVG_USE_TM_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_8" class="input01 wdt120px" name="param[AVG_USE_TM_U]" data-type="int" value="${sessionScope.session.param.analysis_pattern.AVG_USE_TM_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr>
					<th class="left"><input type="checkbox" id="avg_use_chk"><label for="avg_use_chk"><span></span>이용시간 (초)</label></th>
					<td class="subTh"><input id="AVG_USE_TM_L" type="radio" name="AVG_USE_TM_CNT" checked disabled><label for="AVG_USE_TM_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_7" class="input01 wdt120px _checked_" name="param[AVG_USE_TM_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="AVG_USE_TM_M" type="radio" name="AVG_USE_TM_CNT" disabled><label for="AVG_USE_TM_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_7_1" class="input01 wdt120px" name="param[AVG_USE_TM_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_8_1" class="input01 wdt120px" name="param[AVG_USE_TM_M_T]" data-type="int" value="" data-target="AVG_USE_TM_M" disabled/></td>
					
					<td class="subTh"><input id="AVG_USE_TM_U" type="radio" name="AVG_USE_TM_CNT" disabled><label for="AVG_USE_TM_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_8" class="input01 wdt120px" name="param[AVG_USE_TM_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
			</table>
		</div></c:otherwise></c:choose>
		<div class="tblBox"></div>
		</form>
		<div class="tblBox small">
			<div class="cusBox">
				<p class="sTitle">총 이용자수</p>
				<div class="static bgSky"  style="height:188px;height:154px; ">
					<p class="num" id="user_cnt">-</p>
					<p class="disc">분석기간<br>중복 이용자수 병합</p>
				</div>
			</div>
			<div class="cusBox">
				<p class="sTitle">패턴 이용자수</p>
				<div class=" static bgBlue"  style="height:188px;height:154px; " >
					<p class="num" id="ptn_cnt">-</p>
					<p class="num2" id="ptn_rt">-</p>
				</div>
			</div>
			<div class="cusBox">
				<p class="sTitle">이용서비스 분포 TOP5</p>
				<table class="tblList small">
					<colgroup>
						<col width="70%"><col width="*">
					</colgroup>
					<tbody id="srvc_list">
						<tr><td class="txt">-</td><td>-</td></tr>
						<tr><td class="txt">-</td><td>-</td></tr>
						<tr><td class="txt">-</td><td>-</td></tr>
						<tr><td class="txt">-</td><td>-</td></tr>
						<tr><td class="txt">-</td><td>-</td></tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="tblBox">
			<div class="cusBox half">
				<p class="chTit"><span class="mtit">개인/사업자 분포</span><span class="index">단위 : 명</span></p>
				<div class="chartFrame" style="height:200px;">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span>개인</span>
						<span class="blueDot"></span><span>사업자</span>
					</p> -->
					<div class="chart245" style="height:165px;">
						<canvas id="userChart" ></canvas>
					</div>
				</div>			
			</div>

			<div class="cusBox half">
				<p class="chTit"><span class="mtit">연령대별 분포</span><span class="index">단위 : 명</span></p>
				<div class="chartFrame" style="height:200px;">
					<!-- <p class="chLegend">
						<span class="redDot"></span><span>20대이하</span>
						<span class="blueDot"></span><span>30대</span>
					</p> -->
					<div class="chart245" style="height:165px;">
						<canvas id="ageChart" ></canvas>
					</div>
				</div>	
			</div>

		</div>