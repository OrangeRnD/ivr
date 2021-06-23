<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
						<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
						<tr data-id="${item.ANLYS_PLCY_ID}" style="cursor:pointer;">
							<td class="txt">${item.ANLYS_PLCY_NM}</td>
							<td class="txt">${item.PRD_SECD_NM}</td>
							<td class="txt">${item.BIZDT_SECD_NM}</td>
							<td class="txt">${item.EXCLSN_STDVAL} %</td>
							<td class="txt">${item.NOTIFTN_STDVAL} % 초과</td>
							<td class="txt"><c:choose><c:when test="${item.SMS_XMSN_ALTV == true}">적용</c:when><c:otherwise>미적용</c:otherwise></c:choose></td>
							<td class="txt"><c:choose><c:when test="${item.EMAIL_XMSN_ALTV == true}">적용</c:when><c:otherwise>미적용</c:otherwise></c:choose></td>
							<td class="txt"><fmt:formatNumber value="${item.CNT}" pattern="#,###"></fmt:formatNumber> 건</td>
							<td class="txt"><fmt:formatDate value="${item.DT}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
						</c:forEach></c:when><c:otherwise><tr><td colspan="9" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
						<tr style="display:none;">
							<td colspan="9">
								<p class="total" id="paging_total">전체 : <span><fmt:formatNumber value="${paging.size}" pattern="#,###"></fmt:formatNumber></span>건</p>
								<div class="pagingData" id="paging">
									<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
								</div>
							</td>
						</tr>