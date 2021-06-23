<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<fmt:formatNumber var="sum" value="${item.CNT_1 + item.CNT_2 + item.CNT_3 + item.CNT_4}" pattern="#.0" />
							<fmt:formatNumber var="rCnt1" value="${item.CNT_1*100}" pattern="#.0" />
							<fmt:formatNumber var="rCnt2" value="${item.CNT_2*100}" pattern="#.0" />
							<fmt:formatNumber var="rCnt3" value="${item.CNT_3*100}" pattern="#.0" />
							<fmt:formatNumber var="rCnt4" value="${item.CNT_4*100}" pattern="#.0" />
							<tr>
								<td class="txt div"><fmt:parseDate value="${item.YM}" pattern="yyyyMM" var="YM"></fmt:parseDate><fmt:formatDate value="${YM}" pattern="yyyy-MM"/></td>
								<c:choose><c:when test="${(item.CNT_1 + item.CNT_2 + item.CNT_3 + item.CNT_4) <= 0}">
								<td class="div bold"><fmt:formatNumber value="${sum}" pattern="#,###"/> 건</td>
								<td><fmt:formatNumber value="${item.CNT_1}" pattern="#,###"/> 건</td>
								<td class="div rt">0 %</td>
								<td><fmt:formatNumber value="${item.CNT_2}" pattern="#,###"/> 건</td>
								<td class="div rt">0 %</td>
								<td><fmt:formatNumber value="${item.CNT_3}" pattern="#,###"/> 건</td>
								<td class="div rt">0 %</td>
								<td><fmt:formatNumber value="${item.CNT_4}" pattern="#,###"/> 건</td>
								<td>0 %</td>
								</c:when><c:otherwise>
								<td class="div bold"><fmt:formatNumber value="${sum}" pattern="#,###"/> 건</td>
								<td><fmt:formatNumber value="${item.CNT_1}" pattern="#,###"/> 건</td>
								<td class="div rt"><fmt:formatNumber value="${rCnt1/sum}" pattern="#,##0.0"/> %</td>
								<td><fmt:formatNumber value="${item.CNT_2}" pattern="#,###"/> 건</td>
								<td class="div rt"><fmt:formatNumber value="${rCnt2/sum}" pattern="#,##0.0"/> %</td>
								<td><fmt:formatNumber value="${item.CNT_3}" pattern="#,###"/> 건</td>
								<td class="div rt"><fmt:formatNumber value="${rCnt3/sum}" pattern="#,##0.0"/> %</td>
								<td><fmt:formatNumber value="${item.CNT_4}" pattern="#,###"/> 건</td>
								<td class="rt"><fmt:formatNumber value="${rCnt4/sum}" pattern="#,##0.0"/> %</td>
								</c:otherwise></c:choose>
							</tr>
							</c:forEach></c:when><c:otherwise><tr><td colspan="10" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
