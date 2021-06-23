<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<tr>
								<td class="txt">${((paging.pageNo-1)*15) + (status.index+1)}</td>
								<td class="txt">${item.CD}</td>
								<td class="txt">${item.NM}</td>
								<td class="txt"><fmt:formatNumber value="${item.CNT}" pattern="#,###"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.ERR_CNT}" pattern="#,###"></fmt:formatNumber></td>
							</tr>
							</c:forEach></c:when><c:otherwise><tr data-type="none"><td colspan="5" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
							<tr style="display:none;">
								<td colspan="5">
									<p class="total" id="paging_total">전체 : <span><fmt:formatNumber value="${paging.size}" pattern="#,###"></fmt:formatNumber></span>건</p>
									<div class="pagingData" id="paging">
										<ul><c:if test="${paging.pageNo-1 > 0}"><li><a class="before" data-no="${paging.pageNo-1}">before</a></li></c:if><c:forEach var="item" items="${paging.pages}" varStatus="status"><li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li></c:forEach><c:if test="${paging.pageNo+1 <= paging.pageCount}"><li><a class="next" data-no="${paging.pageNo+1}">next</a></li></c:if></ul>
									</div>
								</td>
							</tr>