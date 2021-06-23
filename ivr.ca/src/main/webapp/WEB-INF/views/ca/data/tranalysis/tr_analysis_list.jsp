<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<tr id="result_sd" data-sd="${SD}" style="display:none;"><td colspan="26"></td></tr>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<tr>
								<td class="txt"><fmt:parseDate value="${item.DT}" pattern="yyyyMMdd" var="DT"></fmt:parseDate><fmt:formatDate value="${DT}" pattern="yyyy-MM-dd"/></td>
								<td class="txt"><fmt:formatNumber value="${item.SD}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF0}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF1}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF2}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF3}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF4}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF5}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF6}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF7}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF8}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF9}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF10}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF11}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF12}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF13}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF14}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF15}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF16}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF17}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF18}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF19}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF20}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF21}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF22}" pattern="#,##0.0"></fmt:formatNumber></td>
								<td class="txt"><fmt:formatNumber value="${item.DIFF23}" pattern="#,##0.0"></fmt:formatNumber></td>
							</tr>
							</c:forEach></c:when><c:otherwise><tr><td colspan="26" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>