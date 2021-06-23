<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<!-- <p class="ltit">메인 화면</p> -->
			<jsp:include page="../../../common/reload.jsp"></jsp:include>
		</div>
		<div class="stateArea">
			<div class="dateLeft" id="prcs_dt">
			</div>
			<div class="btnRight" style="display:none;">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
			</div>
			<form name="search_frm"><input type="hidden" name="dt" value="${DT}"/></form>
		</div>
		<div class="callState" id="ldin_status">
			<div class="callNum" data-class="callNum" data-type="call">
				<div class="boxTop bgSky" >
					<p class="statTit">콜 건수</p>
					<p class="num" id="CALL_CNT"><fmt:formatNumber value="${CUR.CALL_CNT}" pattern="#,###"></fmt:formatNumber><span>콜</span></p>
					<p class="disc">실시간 콜건수 누계</p>
				</div>
				<div class="calenBox bgSkyDk">
					<div class="arr">
						<a class="leftAr" id="btn_pre_dt1" data-value="-1">이전</a>
					</div>
					<div class="calData">
						<p class="date"><input type="text" id="s_dt1" value="${DT}"/><a id="btn_dt1"><fmt:parseDate value="${DT}" pattern="yyyyMMdd" var="DT"></fmt:parseDate><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></a></p>
						<p class="num" id="PRE_CALL_CNT"><fmt:formatNumber value="${PRE.CALL_CNT}" pattern="#,###"></fmt:formatNumber><span>콜</span></p>
					</div>
					<div class="arr">
						<a class="rightAr" id="btn_aft_dt1" data-value="1">이후</a>
					</div>
				</div>
				<div class="active"><img src="${webappRoot}/resources/images/selected_bgSky.png" alt="선택"></div>
			</div>

			<div class="callNum deselected" data-class="callNum" data-type="user">
				<div class="boxTop bgBlue">
					<p class="statTit">이용자수</p>
					<p class="num" id="USER_CNT"><fmt:formatNumber value="${CUR.USER_CNT}" pattern="#,###"></fmt:formatNumber><span>명</span></p>
					<p class="disc">실시간 이용자수 누계</p>
				</div>
				<div class="calenBox bgBlueDk">
					<div class="arr">
						<a class="leftAr" id="btn_pre_dt2" data-value="-1">이전</a>
					</div>
					<div class="calData">
						<p class="date"><input type="text" id="s_dt2" value="${DT}"/><a id="btn_dt2"><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></a></p>
						<p class="num" id="PRE_USER_CNT"><fmt:formatNumber value="${PRE.USER_CNT}" pattern="#,###"></fmt:formatNumber><span>명</span></p>
					</div>
					<div class="arr">
						<a class="rightAr" id="btn_aft_dt2" data-value="1">이후</a>
					</div>
				</div>
				<div class="active"><img src="${webappRoot}/resources/images/selected_bgBlue.png" alt="선택"></div>
			</div>

			<div class="callNum deselected" data-class="callNum" data-type="tr">
				<div class="boxTop bgPupple">
					<p class="statTit">거래 건수</p>
					<p class="num" id="TR_CNT"><fmt:formatNumber value="${CUR.TR_CNT}" pattern="#,###"></fmt:formatNumber><span>건</span></p>
					<p class="disc">실시간 거래건수 누계</p>
				</div>
				<div class="calenBox bgPuppleDk">
					<div class="arr">
						<a class="leftAr" id="btn_pre_dt3" data-value="-1">이전</a>
					</div>
					<div class="calData">
						<p class="date"><input type="text" id="s_dt3" value="${DT}"/><a id="btn_dt3"><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></a></p>
						<p class="num" id="PRE_TR_CNT"><fmt:formatNumber value="${PRE.TR_CNT}" pattern="#,###"></fmt:formatNumber><span>건</span></p>
					</div>
					<div class="arr">
						<a class="rightAr" id="btn_aft_dt3" data-value="1">이후</a>
					</div>
				</div>
				<div class="active"><img src="${webappRoot}/resources/images/selected_bgPupple.png" alt="선택"></div>
			</div>

			<div class="callCheck">
				<div class="topBox bgLpup">
					<p class="statTit">조회 서비스</p>
					<p class="num" id="RLTM_INQIRE_CNT"><fmt:formatNumber value="${CUR.RLTM_INQIRE_CNT}" pattern="#,###"></fmt:formatNumber><span>건</span></p>
				</div>
				<div class="botBox bgLpupDk">
					<p class="date" id="btn_dt4"><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></p>
					<p class="num2" id="PRE_RLTM_INQIRE_CNT"><fmt:formatNumber value="${PRE.RLTM_INQIRE_CNT}" pattern="#,###"></fmt:formatNumber><span>건</span></p>
				</div>
			</div>
			
			<div class="callTrans">
				<div class="topBox bgPink">
					<p class="statTit">이체 서비스</p>
					<p class="num" id="RLTM_TRANSFR_CNT"><fmt:formatNumber value="${CUR.RLTM_TRANSFR_CNT}" pattern="#,###"></fmt:formatNumber><span>건</span></p>
				</div>
				<div class="botBox bgPinkDk">
					<p class="date" id="btn_dt5"><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></p>
					<p class="num2" id="PRE_RLTM_TRANSFR_CNT"><fmt:formatNumber value="${PRE.RLTM_TRANSFR_CNT}" pattern="#,###"></fmt:formatNumber><span>건</span></p>
				</div>
			</div>
		</div>
		<table class="stat tbltype01 mgb20 call" data-class="stat tbltype01 mgb20" id="inpth_list">
			<tr id="first" class="inpth_call">
				<c:forEach var="item" items="${result}" varStatus="status"><c:if test="${item.INPTH_SECD != 'C' && item.INPTH_SECD != 'P' && item.NUM <= 6}">
				<th>${item.INPTH_SECD_NM}</th><td class="num" id="INPT_CALL_${item.INPTH_SECD}"><fmt:formatNumber value="${item.CALL_CNT}" pattern="#,###"></fmt:formatNumber>콜</td>
				</c:if></c:forEach>
			</tr>
			<tr id="first" class="inpth_call">
				<c:forEach var="item" items="${result}" varStatus="status"><c:if test="${item.INPTH_SECD != 'C' && item.INPTH_SECD != 'P' && item.NUM > 6 && item.NUM <= 12}">
				<th>${item.INPTH_SECD_NM}</th><td class="num" id="INPT_CALL_${item.INPTH_SECD}"><fmt:formatNumber value="${item.CALL_CNT}" pattern="#,###"></fmt:formatNumber>콜</td>
				</c:if></c:forEach>
			</tr>
			<tr id="first" class="inpth_user">
				<c:forEach var="item" items="${result}" varStatus="status"><c:if test="${item.INPTH_SECD != 'C' && item.INPTH_SECD != 'P' && item.NUM <= 6}">
				<th>${item.INPTH_SECD_NM}</th><td class="num" id="INPT_USER_${item.INPTH_SECD}"><fmt:formatNumber value="${item.USER_CNT}" pattern="#,###"></fmt:formatNumber>명</td>
				</c:if></c:forEach>
			</tr>
			<tr id="first" class="inpth_user">
				<c:forEach var="item" items="${result}" varStatus="status"><c:if test="${item.INPTH_SECD != 'C' && item.INPTH_SECD != 'P' && item.NUM > 6 && item.NUM <= 12}">
				<th>${item.INPTH_SECD_NM}</th><td class="num" id="INPT_USER_${item.INPTH_SECD}"><fmt:formatNumber value="${item.USER_CNT}" pattern="#,###"></fmt:formatNumber>명</td>
				</c:if></c:forEach>
			</tr>
			<tr id="first" class="inpth_tr">
				<c:forEach var="item" items="${result}" varStatus="status"><c:if test="${item.INPTH_SECD != 'C' && item.INPTH_SECD != 'P' && item.NUM <= 6}">
				<th>${item.INPTH_SECD_NM}</th><td class="num" id="INPT_TR_${item.INPTH_SECD}"><fmt:formatNumber value="${item.TR_CNT}" pattern="#,###"></fmt:formatNumber>건</td>
				</c:if></c:forEach>
			</tr>
			<tr id="first" class="inpth_tr">
				<c:forEach var="item" items="${result}" varStatus="status"><c:if test="${item.INPTH_SECD != 'C' && item.INPTH_SECD != 'P' && item.NUM > 6 && item.NUM <= 12}">
				<th>${item.INPTH_SECD_NM}</th><td class="num" id="INPT_TR_${item.INPTH_SECD}"><fmt:formatNumber value="${item.TR_CNT}" pattern="#,###"></fmt:formatNumber>건</td>
				</c:if></c:forEach>
			</tr>
		</table>
		<div class="tblBox" style="margin-bottom:10px;">
			<div class="boxTwo">
				<p class="chTit"><span class="mtit">ARS 유형별 인입 (대표번호)</span><span class="index">단위 : 콜</span></p>
				<div class="chartFrame" style="height:180px;padding:10px 20px;">
					<div class="chart245" style="height:158px;">
						<canvas id="arsChart" ></canvas>
					</div>
				</div>
			</div>

			<div class="boxTwo">
				<p class="chTit"><span class="mtit">거래 에러 비율</span><a class="quickLink" id="btn_url">바로가기</a><span class="index">단위 : 건</span></p>
				<div class="chartFrame" style="height:180px;padding:10px 20px;">
					<div class="chart245" style="height:158px;">
						<canvas id="errChart" ></canvas>
					</div>
				</div>
			</div>
		</div>
		<div class="tblBox" style="margin-bottom:0px;">
			<p class="sTitle" style="margin-bottom:5px;">알림 발생 목록</p>
			<div class="tblContainer1">
				<div class="header-bg"></div>
				<div class="tblWrapper">
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
						<tbody id="plcy_list">
							<tr><td colspan="9" class="txt">데이터 조회중입니다.</td></tr>
						</tbody>
				     </table>
				</div>
			</div>
		</div>		