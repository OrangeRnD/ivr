<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">콜검색</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm">
			<input type="hidden" name="pageNo" value="1"/>
			<input type="hidden" name="param[P_TRAN_NM]" value=""/>
			<input type="hidden" name="param[P_TRAN_CD]" value=""/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="300px"><col width="11%"><col width="*"></colgroup>
				<c:choose><c:when test="${param['param[FROM_DT]'] != null}"><tr id="first">
					<th>조회기간</th>
					<td colspan="3">
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${param['param[FROM_DT]']}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<select class="seltype01 time" name="param[FROM_H]">
						</select>
						<select class="seltype01 time" name="param[FROM_M]">
						</select>
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${param['param[TO_DT]']}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<select class="seltype01 time" name="param[TO_H]">
						</select>
						<select class="seltype01 time" name="param[TO_M]">
						</select>
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="std_now" type="radio" name="param[STANDARD]" value="1" data-type="date" data-date-type="now" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_now"><span></span>당일</label>
						<input id="std_pre_date" type="radio" name="param[STANDARD]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_date"><span></span>전일</label>
						<input id="std_this_week" type="radio" name="param[STANDARD]" value="3" data-type="date" data-date-type="this_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_this_week"><span></span>금주</label>
						<input id="std_pre_week" type="radio" name="param[STANDARD]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_week"><span></span>전주</label>
						<input id="std_this_month" type="radio" name="param[STANDARD]" value="5" data-type="date" data-date-type="this_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_this_month"><span></span>당월</label>
						<input id="std_pre_month" type="radio" name="param[STANDARD]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_month"><span></span>전월</label>
					</td>
				</tr>
				<tr>
					<th>서비스</th>
					<td>
						<ul class="svList">
							<li><input type="text" class="input01 wdt140px" id="srvc_nm" placeholder="서비스명 검색" name="param[SRVC_NM]" value="${param['param[SRVC_NM]']}">
								<div class="popupRecom" style="display:none;">
									<ul id="srvc_list">
									</ul>
								</div>
							</li>
							<li><a id="btn_srvc"><img src="${webappRoot}/resources/images/icon_magnifier.png" style="margin:3px 0 0 5px;"></a></li>
						</ul>
						<input type="hidden" name="param[SRVC_CD]" value="${param['param[SRVC_CD]']}">
					</td>
					<th>거래</th>
					<td>
						<ul class="svList">
							<li><input type="text" class="input01 wdt140px" id="tr_nm" placeholder="거래 검색" name="param[TRAN_NM]" value="${param['param[TRAN_NM]']}">
								<div class="popupRecom" style="display:none;">
									<ul id="tr_list">
									</ul>
								</div>
							</li>
							<li><a id="btn_tr"><img src="${webappRoot}/resources/images/icon_magnifier.png" style="margin:3px 0 0 5px;"></a></li>
						</ul>
						<input type="hidden" name="param[TRAN_CD]" value="${param['param[TRAN_CD]']}"/>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<input type="text"  class="input01 wdt140px" name="param[TELNO]" data-type="tel" placeholder="전화번호" maxlength="15" value="${param['param[TELNO]']}"/>
					</td>
					<th>생년월일/사업자번호</th>
					<td>
						<c:choose><c:when test="${param['param[USER_SECD]'] == '2'}">
						<input id="regno1" type="radio" name="param[USER_SECD]" value="1"><label for="regno1"><span></span>개인</label>
						<input id="regno2" type="radio" name="param[USER_SECD]" value="2" checked><label for="regno2"><span></span>사업자</label>
						</c:when><c:otherwise>
						<input id="regno1" type="radio" name="param[USER_SECD]" value="1" checked><label for="regno1"><span></span>개인</label>
						<input id="regno2" type="radio" name="param[USER_SECD]" value="2"><label for="regno2"><span></span>사업자</label>
						</c:otherwise></c:choose>
						<input type="text" class="input01 wdt140px" name="param[RGSNO]" value="${param['param[RGSNO]']}" placeholder="생년월일" maxlength="13" data-type="bizno"/>
					</td>
				</tr></c:when><c:otherwise><tr id="first">
					<th>조회기간</th>
					<td colspan="3">
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.use_call.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<select class="seltype01 time" name="param[FROM_H]" data-value="${sessionScope.session.param.use_call.FROM_H}">
						</select>
						<select class="seltype01 time" name="param[FROM_M]" data-value="${sessionScope.session.param.use_call.FROM_M}">
						</select>
						<span class="wave">~</span> 
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.use_call.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<select class="seltype01 time" name="param[TO_H]" data-value="${sessionScope.session.param.use_call.TO_H}">
						</select>
						<select class="seltype01 time" name="param[TO_M]" data-value="${sessionScope.session.param.use_call.TO_M}">
						</select>
						<input id="std_input" type="radio" name="param[STANDARD]" value="0"><label for="std_input"><span></span>직접입력</label>
						<input id="std_now" type="radio" name="param[STANDARD]" value="1" data-type="date" data-date-type="now" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_now"><span></span>당일</label>
						<input id="std_pre_date" type="radio" name="param[STANDARD]" value="2" data-type="date" data-date-type="pre_date" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_date"><span></span>전일</label>
						<input id="std_this_week" type="radio" name="param[STANDARD]" value="3" data-type="date" data-date-type="this_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_this_week"><span></span>금주</label>
						<input id="std_pre_week" type="radio" name="param[STANDARD]" value="4" data-type="date" data-date-type="pre_week" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_week"><span></span>전주</label>
						<input id="std_this_month" type="radio" name="param[STANDARD]" value="5" data-type="date" data-date-type="this_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_this_month"><span></span>당월</label>
						<input id="std_pre_month" type="radio" name="param[STANDARD]" value="6" data-type="date" data-date-type="pre_month" data-date1="param[FROM_DT]" data-date2="param[TO_DT]"><label for="std_pre_month"><span></span>전월</label>
					</td>
				</tr>
				<tr>
					<th>서비스</th>
					<td>
						<ul class="svList">
							<li><input type="text" class="input01 wdt140px" id="srvc_nm" placeholder="서비스명 검색" name="param[SRVC_NM]" value="${sessionScope.session.param.use_call.SRVC_NM}">
								<div class="popupRecom" style="display:none;">
									<ul id="srvc_list">
									</ul>
								</div>
							</li>
							<li><a id="btn_srvc"><img src="${webappRoot}/resources/images/icon_magnifier.png" style="margin:3px 0 0 5px;"></a></li>
						</ul>
						<input type="hidden" name="param[SRVC_CD]" value="${sessionScope.session.param.use_call.SRVC_CD}">
					</td>
					<th>거래</th>
					<td>
						<ul class="svList">
							<li><input type="text" class="input01 wdt140px" id="tr_nm" placeholder="거래 검색" name="param[TRAN_NM]" value="${sessionScope.session.param.use_call.TRAN_NM}">
								<div class="popupRecom" style="display:none;">
									<ul id="tr_list">
									</ul>
								</div>
							</li>
							<li><a id="btn_tr"><img src="${webappRoot}/resources/images/icon_magnifier.png" style="margin:3px 0 0 5px;"></a></li>
						</ul>
						<input type="hidden" name="param[TRAN_CD]" value="${sessionScope.session.param.use_call.TRAN_CD}"/>
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<input data-session-type="${sessionScope.session.param.use_call.STANDARD}" type="text" class="input01 wdt140px" name="param[TELNO]" data-type="tel" placeholder="전화번호" maxlength="15" value="${sessionScope.session.param.use_call.TELNO}"/>
					</td>
					<th>생년월일/사업자번호</th>
					<td>
						<c:choose><c:when test="${sessionScope.session.param.use_call.USER_SECD == '2'}">
						<input id="regno1" type="radio" name="param[USER_SECD]" value="1"><label for="regno1"><span></span>개인</label>
						<input id="regno2" type="radio" name="param[USER_SECD]" value="2" checked><label for="regno2"><span></span>사업자</label>
						</c:when><c:otherwise>
						<input id="regno1" type="radio" name="param[USER_SECD]" value="1" checked><label for="regno1"><span></span>개인</label>
						<input id="regno2" type="radio" name="param[USER_SECD]" value="2"><label for="regno2"><span></span>사업자</label>
						</c:otherwise></c:choose>
						<input type="text" class="input01 wdt140px" name="param[RGSNO]" value="${sessionScope.session.param.use_call.RGSNO}" placeholder="생년월일" maxlength="13" data-type="bizno"/>
					</td>
				</tr></c:otherwise></c:choose>
			</table>
			<div class="btnArea">
				<div class="btnRight">
					<a class="btntype02 btn_primary" id="btn_search">조회</a>
					<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
					<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/use/call/excel">엑셀</a>
				</div>
			</div>
			</form>
		</div>

		<div class="stateArea2">
			<div class="avHour">
				<span class="tit" style="width:auto;">조회기간 &nbsp;&nbsp;: </span>
				<span class="num" style="width:auto;padding-left:10px;" id="std_period">-</span>
			</div>
			<div class="avHour" style="float:right;">
				<span class="tit">평균통화시간 &nbsp;&nbsp;: </span>
				<span class="num" style="width:auto;" id="avg_time">-</span>
			</div>
		</div>
<!--스크롤 테이블 시작 -->
		<div class="tblBox">
			<div class="tblContainer1" style="height:280px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th style="width:10%"><div class="th-text">인입일시</div></th>
								<th style="width:10%"><div class="th-text">종료일시</div></th>
								<th style="width:9%"><div class="th-text">전화번호</div></th>
								<th style="width:8%"><div class="th-text">생년월일</div></th>
								<th style="width:8%"><div class="th-text">사업자번호</div></th>
								<th style="width:8%"><div class="th-text">인입구분</div></th>
								<th style="width:8%"><div class="th-text">상담원연결</div></th>
								<th style="width:10%"><div class="th-text">종료사유</div></th>
								<th style="width:15%"><div class="th-text">마지막 서비스</div></th>
								<th style="width:7%"><div class="th-text">결과</div></th>
								<th style="width:7%"><div class="th-text">이용시간</div></th>
							</tr>
						</thead>
						<tbody id="call_list">
							<tr><td colspan="11" class="txt">-</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
			<div class="page_wrap" id="page_wrap">
				<div class="pagingData"></div>
			</div>
			<c:if test="${session.authyGrpCd == '1'}">
				<div class="btnArea" style="margin-top:-31px;width: 140px;float: right;">
					<div class="btnRight">
						<a class="btntype03 btn_danger" href="http://172.18.118.31" target="_blank">와이즈포탈 연결</a>
					</div>
				</div>
			</c:if>
		</div>
<!--스크롤 테이블 끝 -->
		<div class="tblBox">
			<div class="csBox">
				<p class="sTitle">자주 쓰는 메뉴 (최근 3개월)</p>
				<div class="tblContainer1" style="height:217px">
					<div class="header-bg"></div>
					<div class="tblWrapper">
						<table class="fixed-table">
							<thead>
								<tr>
									<th style="width:50%"><div class="th-text">서비스명 TOP5</div></th>
									<th style="width:30%"><div class="th-text">코드</div></th>
									<th style="width:20%"><div class="th-text">건수</div></th>
								</tr>
							</thead>
							<tbody id="call_srvc_list">
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
							</tbody>
					     </table>
					</div>
				</div>			
			</div>
			<div class="csBox">
				<p class="sTitle">선택 콜 발생 거래</p>
				<div class="tblContainer1" style="height:217px">
					<div class="header-bg"></div>
					<div class="tblWrapper">
						<table class="fixed-table">
							<thead>
								<tr>
									<th style="width:60%"><div class="th-text">발생거래</div></th>
									<th style="width:20%"><div class="th-text">성공건수</div></th>
									<th style="width:20%"><div class="th-text">실패건수</div></th>
								</tr>
							</thead>
							<tbody id="call_tr_list">
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td><td>-</td></tr>
							</tbody>
					     </table>
					</div>
				</div>
				<%-- <table class="tblList">
					<colgroup>
						<col width="45%"><col width="25%"><col width="15%"><col width="15%">
					</colgroup>
					<thead>
						<tr>
							<th>거래 TOP5</th>
							<th>소계</th>
							<th>성공</th>
							<th>실패</th>
						</tr>
					</thead>
					<tbody id="tr_list">
						<tr><td class="sort">입출금거래내역조회 입출금거래내역조회 입출금거래내역조회</td><td>811,111</td><td>8</td><td>8</td></tr>
					</tbody>
				</table>	 --%>
			</div>
			<div class="csBox">
				<p class="sTitle">선택 콜 발생 에러코드</p>
				<div class="tblContainer1" style="height:217px">
					<div class="header-bg"></div>
					<div class="tblWrapper">
						<table class="fixed-table">
							<thead>
								<tr>
									<th style="width:30%"><div class="th-text">발생에러코드</div></th>
									<th style="width:70%"><div class="th-text">에러메시지</div></th>
								</tr>
							</thead>
							<tbody id="call_err_list">
								<tr><td class="sort">-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td></tr>
								<tr><td class="sort">-</td><td>-</td></tr>
							</tbody>
					     </table>
					</div>
				</div>
				<%-- <table class="tblList">
					<colgroup>
						<col width="70%"><col width="*">
					</colgroup>
					<thead>
						<tr>
							<th>에러코드 TOP5</th>
							<th>건수</th>
						</tr>
					</thead>
					<tbody id="err_list">
						<tr><td class="sort">CDSK324024238CDSK324024238CDSK324024238</td><td>113,864</td></tr>
					</tbody>
				</table>	 --%>
			</div>
		</div>
		<!--팝업 시작-->
		<div class="popupBox" style="display:none;" id="search_popup">
			<div class="layerPopup" style="width:600px; height:650px; top:calc(50% - 325px); left:calc(50% - 300px);  ">
				<p class="popTit"><span id="popupTtl">서비스코드</span><a class="closeBtn" id="ico_popup_close">닫기</a></p>
				<div id="popup_contents">
				</div>
			</div>
			<div class="popupDimed" id="popupDimed"></div>
		</div>
		<!--팝업 끝-->