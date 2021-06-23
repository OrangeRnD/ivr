<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld" %>
				<table class="tblList">
					<colgroup>
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
					</colgroup>
					<thead>
						<tr id="first">
							<th>코드분류</th>
							<th>코드분류명</th>
							<th>코드</th>
							<th>코드명</th>
							<th>정렬순서</th>
							<th>사용여부</th>
							<th>최종수정자</th>
							<th>최종수정일시</th>
						</tr>
					</thead>
					<tbody id="body_list">
						<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
						<tr>
							<td class="txt"><a data-id="${item.CD_CLSFCTN}">${item.CD_CLSFCTN}</a></td>
							<td class="txt"><a data-id="${item.CD_CLSFCTN}">${item.CD_CLSFCTN_NM}</a></td>
							<td class="txt"><a data-cd="${item.CD_ID}">${item.CD}</a></td>
							<td class="txt"><a data-cd="${item.CD_ID}">${item.CD_NM}</a></td>
							<td class="txt">${item.SORT_ORDR}</td>
							<td class="txt"><c:choose><c:when test="${item.USE_ALTV == true}">사용</c:when><c:otherwise>사용안함</c:otherwise></c:choose></td>
							<td class="txt">${item.LST_UPDUSR_NM}</td>
							<td class="txt"><fmt:formatDate value="${item.LST_UPDT_DT}" pattern="yyyy-MM-dd"/></td>
						</tr>
						</c:forEach></c:when><c:otherwise><tr><td colspan="8" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
					</tbody>
				</table>
				<div class="page_wrap">
					<p class="total" id="paging_total">전체 : <span><fmt:formatNumber value="${paging.size}" pattern="#,###"></fmt:formatNumber></span>건</p>
					<div class="pagingData" id="paging">
						<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
					</div>
				</div>