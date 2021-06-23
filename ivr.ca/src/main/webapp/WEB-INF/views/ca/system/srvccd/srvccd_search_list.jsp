<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
			<table class="tblList">
				<colgroup>
						<col width="40%"><col width="60%">
				</colgroup>
				<thead>
					<tr id="first">
						<th>서비스코드</th><th>서비스명</th>
					</tr>
				</thead>
				<tbody>
				<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
					<tr data-cd="${item.SRVC_CD}" data-nm="${item.SRVC_NM}" class="selectable">
						<td class="txt">${item.SRVC_CD}</td>
						<td class="txt">${item.SRVC_NM}</td>
					</tr>
				</c:forEach></c:when><c:otherwise><tr><td colspan="2" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
				</tbody>
			</table>
			<div class="page_wrap">
				<p class="total" id="paging_popup_total">전체 : <span><fmt:formatNumber value="${paging.size}" pattern="#,###"></fmt:formatNumber></span>건</p>
				<div class="pagingData" id="paging_popup">
					<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
				</div>
			</div>