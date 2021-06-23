<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="tblBox">
			<table class="tblList">
				<colgroup>
					<col width="20%">
					<col width="20%">
					<col width="20%">
					<col width="20%">
					<col width="20%">
				</colgroup>
				<thead>
					<tr id="first">
						<th>구분</th>
						<th>총계</th>
						<th>일평균</th>
						<th>최대일</th>
						<th>최대일건수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="txt">거래</td>
						<td class="txt"><fmt:formatNumber value="${stat.TR_CNT}" pattern="#,###"></fmt:formatNumber></td>
						<td class="txt"><fmt:formatNumber value="${stat.AVG_TR_CNT}" pattern="#,###"></fmt:formatNumber></td>
						<td class="txt"><fmt:parseDate value="${stat.MAX_TR_DT}" pattern="yyyyMMdd" var="MAX_TR_DT"></fmt:parseDate><fmt:formatDate value="${MAX_TR_DT}" pattern="yyyy-MM-dd"/></td>
						<td class="txt"><fmt:formatNumber value="${stat.MAX_TR_CNT}" pattern="#,###"></fmt:formatNumber></td>
					</tr>
					<tr>
						<td class="txt">에러</td>
						<td class="txt"><fmt:formatNumber value="${stat.ERR_CNT}" pattern="#,###"></fmt:formatNumber></td>
						<td class="txt"><fmt:formatNumber value="${stat.AVG_ERR_CNT}" pattern="#,###"></fmt:formatNumber></td>
						<td class="txt"><fmt:parseDate value="${stat.MAX_ERR_DT}" pattern="yyyyMMdd" var="MAX_TR_DT"></fmt:parseDate><fmt:formatDate value="${MAX_TR_DT}" pattern="yyyy-MM-dd"/></td>
						<td class="txt"><fmt:formatNumber value="${stat.MAX_ERR_CNT}" pattern="#,###"></fmt:formatNumber></td>
					</tr>
					<tr>
						<td class="txt">거래 대비 에러 비율</td><c:choose><c:when test="${stat == null || stat.ERR_CNT <= 0 || stat.TR_CNT <= 0}">
						<td class="txt">-</td>
						<td class="txt">-</td>
						<td class="txt" colspan="2">-</td></c:when><c:otherwise>
						<fmt:formatNumber var="ERR_CNT" value="${stat.ERR_CNT}" pattern="#.##" />
						<fmt:formatNumber var="TR_CNT" value="${stat.TR_CNT}" pattern="#.##" />
						<td class="txt minus"><fmt:formatNumber value="${ERR_CNT/TR_CNT*100.0}" pattern="#,##0.0"></fmt:formatNumber> %</td>
						<td class="txt minus"><fmt:formatNumber value="${stat.AVG_ERR_CNT/stat.AVG_TR_CNT*100.0}" pattern="#,##0.0"></fmt:formatNumber> %</td>
						<td class="txt" colspan="2">-</td></c:otherwise></c:choose>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="tblBox">
			<div class="tblContainer1" style="height:288px">
				<div class="header-bg"></div>
				<div class="tblWrapper">
					<table  class="fixed-table">
						<thead>
							<tr>
								<th style="width:25%"><div class="th-text">조회일</div></th>
								<th style="width:25%"><div class="th-text">거래 총 건수</div></th>
								<th style="width:25%"><div class="th-text">에러 총 건수</div></th>
								<th style="width:25%"><div class="th-text">거래 대비 에러 비율</div></th>
							</tr>
						</thead>
						<tbody>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<fmt:formatNumber var="TR_CNT" value="${item.TR_CNT}" pattern="#.##"></fmt:formatNumber>
							<fmt:formatNumber var="ERR_CNT" value="${item.ERR_CNT}" pattern="#.##"></fmt:formatNumber>
							<tr>
								<td class="txt"><fmt:parseDate value="${item.DT}" pattern="yyyyMMdd" var="DT"></fmt:parseDate><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></td>
								<td class="txt"><fmt:formatNumber value="${item.TR_CNT}" pattern="#,###"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.ERR_CNT}" pattern="#,###"></fmt:formatNumber></td>
								<c:choose><c:when test="${item.ERR_CNT <= 0 || item.TR_CNT <= 0}"><td class="txt">0 %</td></c:when><c:otherwise>
								<td class="txt minus"><fmt:formatNumber value="${ERR_CNT/TR_CNT*100.0}" pattern="#,##0.0"></fmt:formatNumber> %</td>
								</c:otherwise></c:choose>
							</tr>
							</c:forEach></c:when><c:otherwise><tr><td colspan="4" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</div>