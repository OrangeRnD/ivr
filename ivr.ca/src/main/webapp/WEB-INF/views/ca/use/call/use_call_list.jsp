<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<tr data-icid="${item.ICID}" data-dt="${item.DT}" data-telno="${item.TELNO}" class="selectable">
								<td class="txt"><fmt:formatDate value="${item.LDIN_DT}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="txt"><fmt:formatDate value="${item.END_DT}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td class="txt"><span class="masking">${app:phoneMasking(item.TELNO)}</span><span class="nonmasking">${app:phone(item.TELNO)}</span></td>
								<c:choose><c:when test="${item.USER_SECD == '1'}"><td class="txt">${item.RGSNO}</td><td>-</td></c:when>
								<c:when test="${item.USER_SECD == '2'}"><td>-</td><td class="txt">${item.RGSNO}</td></c:when>
								<c:otherwise><td class="txt">-</td><td class="txt">-</td></c:otherwise></c:choose>
								<td class="txt">${item.INPTH_NM}</td>
								<td class="txt"><c:choose><c:when test="${item.END_RSN_SECD == '99'}">Y</c:when><c:otherwise>N</c:otherwise></c:choose></td>
								<td class="txt">${item.END_RSN_NM}</td>
								<td class="txt">${item.SRVC_NM}</td>
								<td class="txt"><c:choose><c:when test="${item.ERR_CNT == 1}">실패</c:when><c:otherwise>성공</c:otherwise></c:choose></td>
								<td class="txt" title="${item.USE_TM}초">
									<fmt:parseNumber value="${item.USE_TM/60}" pattern="#,###" var="USE_M" integerOnly="true"></fmt:parseNumber>
									<c:if test="${USE_M > 0}"><fmt:formatNumber value="${USE_M}" pattern="#,###"></fmt:formatNumber>분</c:if>
									<c:if test="${item.USE_TM%60 > 0}"> ${item.USE_TM%60}초</c:if>
									<c:if test="${item.USE_TM == null || item.USE_TM == 0}">-</c:if>
								</td>
							</tr>
							</c:forEach></c:when><c:otherwise><tr><td colspan="11" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
							<tr style="display:none;">
								<td colspan="11">
									<p class="total" id="paging_total">전체 : <span><fmt:formatNumber value="${paging.size}" pattern="#,###"></fmt:formatNumber></span>건</p>
									<div class="pagingData" id="paging">
										<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
									</div>
								</td>
							</tr>