<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">자주쓰는 서비스 이용현황</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-session-type="${sessionScope.session.param.use_service.STANDARD}">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>조회 기간</th>
					<td>
						<span class="calBox2"><input type="text" name="param[FROM_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.use_service.FROM_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
						<span class="wave">~</span>
						<span class="calBox2"><input type="text" name="param[TO_DT]" class="inputCal" data-type="date" value="${sessionScope.session.param.use_service.TO_DT}" data-input="std_input"><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
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
						<input id="RESTDE_ALTV" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="-1"<c:if test="${sessionScope.session.param.use_service.RESTDE_ALTV == null || sessionScope.session.param.use_service.RESTDE_ALTV == '-1'}"> checked</c:if>><label for="RESTDE_ALTV"><span></span>전체</label>
						<input id="RESTDE_ALTV0" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="0"<c:if test="${sessionScope.session.param.use_service.RESTDE_ALTV == '0'}"> checked</c:if>><label for="RESTDE_ALTV0"><span></span>영업일만</label>
						<input id="RESTDE_ALTV1" type="radio" name="param[RESTDE_ALTV]" data-search="true" value="1"<c:if test="${sessionScope.session.param.use_service.RESTDE_ALTV == '1'}"> checked</c:if>><label for="RESTDE_ALTV1"><span></span>휴일만</label>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_excel hide" data-url="/use/service/excel">엑셀</a>
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
<!--스크롤 테이블 시작 -->
		<div class="tblBox">
			<!-- <p class="sTitle">조회 기간 : <span id="period"></span></p> -->
			<div class="tblContainer2" style="height:280px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th colspan="2" style="width:25%"><div class="th-text">서비스 Top-10</div></th>
								<th colspan="2" style="width:25%"><div class="th-text">마지막 서비스 Top-10</div></th>
								<th colspan="2" style="width:25%"><div class="th-text">상담원 연결 Top-10</div></th>
								<th colspan="2" style="width:25%"><div class="th-text">Time-out Top-10</div></th>
							</tr>
							<tr>
								<th style="width:18%"><div class="th-text3">서비스명</div></th>
								<th style="width:7%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:18%"><div class="th-text3">서비스명</div></th>
								<th style="width:7%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:18%"><div class="th-text3">서비스명</div></th>
								<th style="width:7%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:18%"><div class="th-text3">거래명</div></th>
								<th style="width:7%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
							</tr>
						</thead>
						<tbody id="lists">
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
							<tr>
								<td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td><td class="txt">-</td><td class="lineBold">-</td>
							</tr>
						</tbody>
				     </table>
				</div>
			</div>
		</div>
<!--스크롤 테이블 끝 -->
		<div class="tblBox">
			<p class="sTitle">개인 / 사업자별 서비스 Top-10</p>
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
								<th style="width:35%"><div class="th-text3">서비스명</div></th>
								<th style="width:15%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:35%"><div class="th-text3">서비스명</div></th>
								<th style="width:15%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
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
								<th colspan="2" style="width:16%"><div class="th-text">60대</div></th>
								<th colspan="2" style="width:16%"><div class="th-text">70대 이상</div></th>
							</tr>
							<tr>
								<th style="width:12%"><div class="th-text3">서비스명</div></th><th style="width:5%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:12%"><div class="th-text3">서비스명</div></th><th style="width:5%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:12%"><div class="th-text3">서비스명</div></th><th style="width:5%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:12%"><div class="th-text3">서비스명</div></th><th style="width:5%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:11%"><div class="th-text3">서비스명</div></th><th style="width:5%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
								<th style="width:11%"><div class="th-text3">서비스명</div></th><th style="width:5%"><div class="th-text3 sorting sortingDown" data-sort="sortingDown">건수</div></th>
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
			
		<%-- <div class="tblBox">
			<div class="boxThree">
				<p class="chTit"><span class="mtit">서비스 Top-5</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame">
					<div class="chart245" >
						<canvas id="chart1" ></canvas>
					</div>
				</div>
			</div>

			<div class="boxThree">
				<p class="chTit"><span class="mtit">마지막 서비스 Top-5</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame">
					<div class="chart245" >
						<canvas id="chart2" ></canvas>
					</div>
				</div>
			</div>

			<div class="boxThree">
				<p class="chTit"><span class="mtit">상담원 연결 Top-5</span><span class="index">단위 : 건수</span></p>
				<div class="chartFrame">
					<div class="chart245" >
						<canvas id="chart3" ></canvas>
					</div>
				</div>
			</div>
		</div> --%>
		
		<div class="chartWrap">
			<div class="chTit">
				<span class="mtit">당일 서비스 Top5 시간별 추이</span>
				<span class="index">단위 : 건수</span>
			</div>
			<div class="chartFrame2">
				<!-- <div class="chLegend">
					<span class="redDot"></span><span class="sort" id="top5_1">-</span>
					<span class="blueDot"></span><span class="sort" id="top5_2">-</span>
					<span class="greenDot"></span><span class="sort" id="top5_3">-</span>
					<span class="puppleDot"></span><span class="sort" id="top5_4">-</span>
					<span class="orangeDot"></span><span class="sort" id="top5_5">-</span>
				</div> -->
				<div class="chart280" >
					<canvas id="myChart"></canvas>
				</div>
			</div>
		</div>