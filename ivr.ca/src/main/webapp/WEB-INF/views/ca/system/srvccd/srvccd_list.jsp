<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
			<table class="tblList">
				<colgroup>
						<col width="200px"><col width="300px"><col width="100px"><col width="*">
				</colgroup>
				<thead>
					<tr id="first">
						<th>서비스코드</th><th>서비스명</th><th>모니터링 여부</th><th>비고</th>
					</tr>
				</thead>
				<tbody>
				<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
					<tr data-id="${item.SRVC_CD}" class="selectable">
						<td class="txt">${item.SRVC_CD}</td>
						<td class="txt">${item.SRVC_NM}</td>
						<td class="txt"><c:choose><c:when test="${item.MONITORING_YN == '1'}">예</c:when><c:otherwise>아니오</c:otherwise></c:choose></td>
						<td></td>
					</tr>
				</c:forEach></c:when><c:otherwise><tr><td colspan="4" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
				</tbody>
			</table>
			<div class="page_wrap">
				<p class="total" id="paging_total">전체 : <span><fmt:formatNumber value="${paging.size}" pattern="#,###"></fmt:formatNumber></span>건</p>
				<div class="pagingData" id="paging">
					<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
				</div>
			</div>