<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
				<table class="tblList">
					<colgroup>
						<col width="16%">
						<col width="12%"><col width="12%">
						<col width="10%"><col width="10%">
						<col width="10%"><col width="10%">
						<col width="10%"><col width="10%">
					</colgroup>
					<thead>
						<tr id="first">
							<th>알림명</th>
							<th>비교 기준</th>
							<th>기준일</th>
							<th>상하위 제외</th>
							<th>알림설정</th>
							<th>SMS전송</th>
							<th>E-mail전송</th>
							<th>최종수정자</th>
							<th>최종수정일시</th>
						</tr>
					</thead>
					<tbody id="tblList">
						<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
						<tr data-id="${item.anlysPlcyId}" style="cursor:pointer;">
							<td class="txt">${item.anlysPlcyNm}</td>
							<td class="txt">${item.prdSecdNm}</td>
							<td class="txt">${item.bizdtSecdNm}</td>
							<td class="txt">${item.exclsnStdval} %</td>
							<td class="txt">${item.notiftnStdval} % 초과</td>
							<td class="txt"><c:choose><c:when test="${item.smsXmsnAltv == 1}">작용</c:when><c:otherwise>미적용</c:otherwise></c:choose></td>
							<td class="txt"><c:choose><c:when test="${item.emailXmsnAltv == 1}">작용</c:when><c:otherwise>미적용</c:otherwise></c:choose></td>
							<td class="txt">${item.lstUpdusrNm}</td>
							<td class="txt"><fmt:formatDate value="${item.lstUpdtDt}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
						</c:forEach></c:when><c:otherwise><tr><td colspan="9" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
					</tbody>
				</table>
				<div class="page_wrap">
					<div class="pagingData" id="paging2">
						<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
					</div>
				</div>