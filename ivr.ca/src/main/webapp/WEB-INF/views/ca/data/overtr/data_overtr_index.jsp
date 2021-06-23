<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">이상 현상 분석</p>
		</div>
		<form data-type="search" name="search_frm" data-session-type="${sessionScope.session.param.data_overtr.PERIOD}">
			<input type="hidden" name="pageNo" value="1"/>
		<div class="tblBox">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>분석 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.data_overtr.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.data_overtr.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<input id="std_input" type="radio" name="param[PERIOD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="lately_week" type="radio" name="param[PERIOD]" value="3" data-type="date" data-date-type="lately_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_week"><span></span>최근1주</label>
						<input id="lately_month" type="radio" name="param[PERIOD]" value="4" data-type="date" data-date-type="lately_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_month"><span></span>최근 1개월</label>
						<input id="lately_3month" type="radio" name="param[PERIOD]" value="5" data-type="date" data-date-type="lately_3month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="lately_3month"><span></span>최근 3개월</label>
					</td>
				</tr>
				<tr>
					<th>기준 값</th>
					<td>
						<input id="STD_VALUE_5" type="radio" name="STD_VALUE" value="5"<c:if test="${sessionScope.session.param.data_overtr.STD_VALUE == null || sessionScope.session.param.data_overtr.STD_VALUE == '5'}"> checked</c:if>><label for="STD_VALUE_5"><span></span>상하위 5%</label>
						<input id="STD_VALUE_10" type="radio" name="STD_VALUE" value="10"<c:if test="${sessionScope.session.param.data_overtr.STD_VALUE == '10'}"> checked</c:if>><label for="STD_VALUE_10"><span></span>상하위 10%</label>
						<input id="STD_VALUE" type="radio" name="STD_VALUE" value=""<c:if test="${sessionScope.session.param.data_overtr.STD_VALUE != null && sessionScope.session.param.data_overtr.STD_VALUE != '5' && sessionScope.session.param.data_overtr.STD_VALUE != '10'}"> checked</c:if>><label for="STD_VALUE"><span></span>직접 입력</label>
						<c:choose><c:when test="${sessionScope.session.param.data_overtr.STD_VALUE == null}">
						<input type="text" class="input02" name="param[STD_VALUE]" placeholder="%" data-type="int" maxlength="2" style="float:none;display:none;" value="5"/><span style="display:none;">%</span>
						</c:when><c:otherwise>
						<input type="text" class="input02" name="param[STD_VALUE]" placeholder="%" data-type="int" maxlength="2" style="float:none;display:none;" value="${sessionScope.session.param.data_overtr.STD_VALUE}"/><span style="display:none;">%</span>
						</c:otherwise></c:choose>
						<a class="btntype05 btn_info" id="btn_std">기준값설정</a>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/data/overtr/excel">엑셀</a>
				</div>
			</div>
		</div>
		<c:choose><c:when test="${sessionScope.session.param.data_overtr == null}"><div class="tblBox">
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
					<th class="left"><input type="checkbox" id="call_err_chk" checked><label for="call_err_chk"><span></span>콜당 오류 발생 비율</label></th>
					<td class="subTh"><input id="CALL_ERR_L" type="radio" name="CALL_ERR_CNT" checked><label for="CALL_ERR_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_7" class="input01 wdt120px _checked_" name="param[CALL_ERR_L]" data-type="float" value="" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_ERR_M" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_7_1" class="input01 wdt120px" name="param[CALL_ERR_M_F]" data-type="float" value="" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_8_1" class="input01 wdt120px" name="param[CALL_ERR_M_T]" data-type="float" value="" data-target="CALL_ERR_M"/></td>
					
					<td class="subTh"><input id="CALL_ERR_U" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_8" class="input01 wdt120px" name="param[CALL_ERR_U]" data-type="float" value="" maxlength="9"/><span class="txt">이상</span></td>
				</tr>
				<tr>
					<th class="left"><input type="checkbox" id="err_chk" checked><label for="err_chk"><span></span>오류 발생 누계 건수</label></th>
					<td class="subTh"><input id="ERR_L" type="radio" name="ERR_CNT" checked><label for="ERR_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_5" class="input01 wdt120px _checked_" name="param[ERR_L]" data-type="int" value="" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="ERR_M" type="radio" name="ERR_CNT"><label for="ERR_M"><span></span>중</label></td>
					<td><input type="text" id="TYPE_5_1" class="input01 wdt120px" name="param[ERR_M_F]" data-type="int" value="" maxlength="9"/> <span class="txt">~</span> <input maxlength="9" type="text" id="TYPE_6_1" class="input01 wdt120px" name="param[ERR_M_T]" data-type="int" value="" data-target="ERR_M"/></td>
					
					<td class="subTh"><input id="ERR_U" type="radio" name="ERR_CNT"  ><label for="ERR_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_6" class="input01 wdt120px" name="param[ERR_U]" data-type="int" value="" maxlength="9"/><span class="txt">이상</span></td>
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
			</table>
		</div></c:when>
		<c:otherwise><div class="tblBox small">
		<table class="tbltype01" id="policy">
				<colgroup><col width="11%"><col width="60px"><col width="200px"><col width="60px"><col width="320px"><col width="60px"><col width="*"></colgroup>
				<c:choose><c:when test="${sessionScope.session.param.data_overtr.containsKey('CALL_L') || sessionScope.session.param.data_overtr.containsKey('CALL_M_F') || sessionScope.session.param.data_overtr.containsKey('CALL_M_T') || sessionScope.session.param.data_overtr.containsKey('CALL_U')}"><tr id="first">
					<th class="left"><input type="checkbox" id="call_chk" checked><label for="call_chk"><span></span>콜 이용 건수</label></th>
					<td class="subTh"><input id="CALL_L" type="radio" name="CALL_CNT"><label for="CALL_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_1" class="input01 wdt120px" name="param[CALL_L]" data-type="int" value="${sessionScope.session.param.data_overtr.CALL_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_M" type="radio" name="CALL_CNT"><label for="CALL_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_1_1" class="input01 wdt120px" name="param[CALL_M_F]" data-type="int" value="${sessionScope.session.param.data_overtr.CALL_M_F}" maxlength="9"/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_2_1" class="input01 wdt120px" name="param[CALL_M_T]" data-type="int" value="${sessionScope.session.param.data_overtr.CALL_M_T}" data-target="CALL_M"/>
					</td>
					
					<td class="subTh"><input id="CALL_U" type="radio" name="CALL_CNT"  ><label for="CALL_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_2" class="input01 wdt120px" name="param[CALL_U]" data-type="int" value="${sessionScope.session.param.data_overtr.CALL_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr id="first">
					<th class="left"><input type="checkbox" id="call_chk"><label for="call_chk"><span></span>콜 이용 건수</label></th>
					<td class="subTh"><input id="CALL_L" type="radio" name="CALL_CNT" checked disabled><label for="CALL_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_1" class="input01 wdt120px _checked_" name="param[CALL_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_M" type="radio" name="CALL_CNT"><label for="CALL_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_1_1" class="input01 wdt120px" name="param[CALL_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_2_1" class="input01 wdt120px" name="param[CALL_M_T]" data-type="int" value="" data-target="CALL_M" disabled/>
					</td>
					
					<td class="subTh"><input id="CALL_U" type="radio" name="CALL_CNT"  ><label for="CALL_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_2" class="input01 wdt120px" name="param[CALL_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
				<c:choose><c:when test="${sessionScope.session.param.data_overtr.containsKey('CALL_ERR_L') || sessionScope.session.param.data_overtr.containsKey('CALL_ERR_M_F') || sessionScope.session.param.data_overtr.containsKey('CALL_ERR_M_T') || sessionScope.session.param.data_overtr.containsKey('CALL_ERR_U')}"><tr>
					<th class="left"><input type="checkbox" id="call_err_chk" checked><label for="call_err_chk"><span></span>콜당 오류 발생 비율</label></th>
					<td class="subTh"><input id="CALL_ERR_L" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_7" class="input01 wdt120px" name="param[CALL_ERR_L]" data-type="float" value="${sessionScope.session.param.data_overtr.CALL_ERR_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_ERR_M" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_7_1" class="input01 wdt120px" name="param[CALL_ERR_M_F]" data-type="float" value="${sessionScope.session.param.data_overtr.CALL_ERR_M_F}" maxlength="9"/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_8_1" class="input01 wdt120px" name="param[CALL_ERR_M_T]" data-type="float" value="${sessionScope.session.param.data_overtr.CALL_ERR_M_T}" data-target="CALL_ERR_M"/>
					</td>
					
					<td class="subTh"><input id="CALL_ERR_U" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_8" class="input01 wdt120px" name="param[CALL_ERR_U]" data-type="float" value="${sessionScope.session.param.data_overtr.CALL_ERR_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr>
					<th class="left"><input type="checkbox" id="call_err_chk"><label for="call_err_chk"><span></span>콜당 오류 발생 비율</label></th>
					<td class="subTh"><input id="CALL_ERR_L" type="radio" name="CALL_ERR_CNT" checked disabled><label for="CALL_ERR_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_7" class="input01 wdt120px _checked_" name="param[CALL_ERR_L]" data-type="float" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CALL_ERR_M" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_7_1" class="input01 wdt120px" name="param[CALL_ERR_M_F]" data-type="float" value="" maxlength="9" disabled/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_8_1" class="input01 wdt120px" name="param[CALL_ERR_M_T]" data-type="float" value="" data-target="CALL_ERR_M" disabled/>
					</td>
					
					<td class="subTh"><input id="CALL_ERR_U" type="radio" name="CALL_ERR_CNT"><label for="CALL_ERR_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_8" class="input01 wdt120px" name="param[CALL_ERR_U]" data-type="float" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
				<c:choose><c:when test="${sessionScope.session.param.data_overtr.containsKey('ERR_L') || sessionScope.session.param.data_overtr.containsKey('ERR_M_F') || sessionScope.session.param.data_overtr.containsKey('ERR_M_T') || sessionScope.session.param.data_overtr.containsKey('ERR_U')}"><tr>
					<th class="left"><input type="checkbox" id="err_chk" checked><label for="err_chk"><span></span>오류 발생 누계 건수</label></th>
					<td class="subTh"><input id="ERR_L" type="radio" name="ERR_CNT"><label for="ERR_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_5" class="input01 wdt120px" name="param[ERR_L]" data-type="int" value="${sessionScope.session.param.data_overtr.ERR_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="ERR_M" type="radio" name="ERR_CNT"><label for="ERR_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_5_1" class="input01 wdt120px" name="param[ERR_M_F]" data-type="int" value="${sessionScope.session.param.data_overtr.ERR_M_F}" maxlength="9"/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_6_1" class="input01 wdt120px" name="param[ERR_M_T]" data-type="int" value="${sessionScope.session.param.data_overtr.ERR_M_T}" data-target="ERR_M"/>
					</td>
					
					<td class="subTh"><input id="ERR_U" type="radio" name="ERR_CNT"  ><label for="ERR_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_6" class="input01 wdt120px" name="param[ERR_U]" data-type="int" value="${sessionScope.session.param.data_overtr.ERR_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr>
					<th class="left"><input type="checkbox" id="err_chk"><label for="err_chk"><span></span>오류 발생 누계 건수</label></th>
					<td class="subTh"><input id="ERR_L" type="radio" name="ERR_CNT" checked disabled><label for="ERR_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_5" class="input01 wdt120px _checked_" name="param[ERR_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="ERR_M" type="radio" name="ERR_CNT"><label for="ERR_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_5_1" class="input01 wdt120px" name="param[ERR_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_6_1" class="input01 wdt120px" name="param[ERR_M_T]" data-type="int" value="" data-target="ERR_M" disabled/>
					</td>
					
					<td class="subTh"><input id="ERR_U" type="radio" name="ERR_CNT"  ><label for="ERR_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_6" class="input01 wdt120px" name="param[ERR_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
				<c:choose><c:when test="${sessionScope.session.param.data_overtr.containsKey('CNSLR_CONN_L') || sessionScope.session.param.data_overtr.containsKey('CNSLR_CONN_M_F') || sessionScope.session.param.data_overtr.containsKey('CNSLR_CONN_M_T') || sessionScope.session.param.data_overtr.containsKey('CNSLR_CONN_U')}"><tr>
					<th class="left"><input type="checkbox" id="cnslr_conn_chk" checked><label for="cnslr_conn_chk"><span></span>상담원 연결 건수</label></th>
					<td class="subTh"><input id="CNSLR_CONN_L" type="radio" name="CNSLR_CONN_CNT"><label for="CNSLR_CONN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_3" class="input01 wdt120px" name="param[CNSLR_CONN_L]" data-type="int" value="${sessionScope.session.param.data_overtr.CNSLR_CONN_L}" maxlength="9"/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CNSLR_CONN_M" type="radio" name="CNSLR_CONN_CNT"><label for="CNSLR_CONN_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_3_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_F]" data-type="int" value="${sessionScope.session.param.data_overtr.CNSLR_CONN_M_F}" maxlength="9"/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_4_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_T]" data-type="int" value="${sessionScope.session.param.data_overtr.CNSLR_CONN_M_T}" data-target="CNSLR_CONN_M"/>
					</td>
					
					<td class="subTh"><input id="CNSLR_CONN_U" type="radio" name="CNSLR_CONN_CNT"  ><label for="CNSLR_CONN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_4" class="input01 wdt120px" name="param[CNSLR_CONN_U]" data-type="int" value="${sessionScope.session.param.data_overtr.CNSLR_CONN_U}" maxlength="9"/><span class="txt">이상</span></td>
				</tr></c:when>
				<c:otherwise><tr>
					<th class="left"><input type="checkbox" id="cnslr_conn_chk"><label for="cnslr_conn_chk"><span></span>상담원 연결 건수</label></th>
					<td class="subTh"><input id="CNSLR_CONN_L" type="radio" name="CNSLR_CONN_CNT" checked disabled><label for="CNSLR_CONN_L"><span></span>하</label></td>
					<td><input type="text" id="TYPE_3" class="input01 wdt120px _checked_" name="param[CNSLR_CONN_L]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이하</span></td>
					
					<td class="subTh"><input id="CNSLR_CONN_M" type="radio" name="CNSLR_CONN_CNT"><label for="CNSLR_CONN_M"><span></span>중</label></td>
					<td>
						<input type="text" id="TYPE_3_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_F]" data-type="int" value="" maxlength="9" disabled/> <span class="txt">~</span> 
						<input maxlength="9" type="text" id="TYPE_4_1" class="input01 wdt120px" name="param[CNSLR_CONN_M_T]" data-type="int" value="" data-target="CNSLR_CONN_M" disabled/>
					</td>
					
					<td class="subTh"><input id="CNSLR_CONN_U" type="radio" name="CNSLR_CONN_CNT"  ><label for="CNSLR_CONN_U"><span></span>상</label></td>
					<td><input type="text" id="TYPE_4" class="input01 wdt120px" name="param[CNSLR_CONN_U]" data-type="int" value="" maxlength="9" disabled/><span class="txt">이상</span></td>
				</tr></c:otherwise></c:choose>
			</table>
		</div></c:otherwise></c:choose>
		<div class="tblBox"></div>
		</form>
		<div class="tblBox" style="margin-bottom:0px;">
			<div class="chTit">
				<span class="mtit">패턴 적용 고객 목록</span>
				<span class="index" id="total_cnt">패턴 적용 이용자 수 : 0 명</span>
			</div>
			<div class="tblContainer2" style="height:276px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th  rowspan="2" style="width:5%"><div class="th-text2">No.</div></th>
								<th  rowspan="2" style="width:15%"><div class="th-text2">전화번호</div></th>
								<th  rowspan="2" style="width:14%"><div class="th-text2">생년월일</div></th>
								<th  rowspan="2" style="width:14%"><div class="th-text2">사업자번호</div></th>
								<th  colspan="4" style="width:52%"><div class="th-text">패턴 적용</div></th>
							</tr>
							<tr>
								<th style="width:13%"><div class="th-text3">콜 이용</div></th>
								<th style="width:13%"><div class="th-text3">콜당 오류</div></th>
								<th style="width:13%"><div class="th-text3">오류 건수</div></th>
								<th style="width:13%"><div class="th-text3">상담원 연결</div></th>
							</tr>
						</thead>
						<tbody id="lists">
							<jsp:include page="./data_overtr_list.jsp"></jsp:include>
						</tbody>
				     </table>
				</div>
			</div>
			<div class="page_wrap" id="page_wrap">
				<div class="pagingData"></div>
			</div>
		</div>
		<form name="_temp_" method="get" target="blank:about">
			<input type="hidden" name="param[TELNO]" value=""/>
			<input type="hidden" name="param[RGSNO]" value=""/>
			<input type="hidden" name="param[USER_SECD]" value=""/>
			<input type="hidden" name="param[FROM_DT]" value=""/>
			<input type="hidden" name="param[TO_DT]" value=""/>
		</form>