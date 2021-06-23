<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<fmt:formatNumber var="endSum" value="${item.END_1 + item.END_2 + item.END_3}" pattern="#.##" />
							<fmt:formatNumber var="callCnt" value="${item.CALL_CNT}" pattern="#.##" />
							<fmt:formatNumber var="pCallCnt" value="${item.P_CALL_CNT}" pattern="#.##" />
							<fmt:formatNumber var="ivr1" value="${item.IVR_1}" pattern="#.##" />
							<fmt:formatNumber var="ivr2" value="${item.IVR_2}" pattern="#.##" />
							<fmt:formatNumber var="end1" value="${item.END_1}" pattern="#.##" />
							<fmt:formatNumber var="end2" value="${item.END_2}" pattern="#.##" />
							<fmt:formatNumber var="end3" value="${item.END_3}" pattern="#.##" />
							<tr data-ym="${item.YM}" class="selectable">
								<td class="txt div"><fmt:parseDate value="${item.YM}" pattern="yyyyMM" var="YM"></fmt:parseDate><fmt:formatDate value="${YM}" pattern="yyyy-MM"/></td>
								<td class="div bold"><fmt:formatNumber value="${item.CALL_CNT}" pattern="#,###"/> 건</td>
								
								<td><fmt:formatNumber value="${item.P_CALL_CNT}" pattern="#,###"/> 건</td>
								<c:choose><c:when test="${item.CALL_CNT <= 0}"><td class="div rt">0 %</td></c:when><c:otherwise>
								<td class="div rt"><fmt:formatNumber value="${pCallCnt/callCnt*100.0}" pattern="#,##0.0"/> %</td></c:otherwise></c:choose>
								
								<c:choose><c:when test="${item.P_CALL_CNT <= 0}">
								<td><fmt:formatNumber value="${item.IVR_1}" pattern="#,###"/> 건 </td>
								<td class="div rt">0 %</td>
								<td><fmt:formatNumber value="${item.IVR_2}" pattern="#,###"/> 건</td>
								<td class="div rt">0 %</td>
								</c:when><c:otherwise>
								<td><fmt:formatNumber value="${item.IVR_1}" pattern="#,###"/> 건 </td>
								<td class="div rt"><fmt:formatNumber value="${ivr1/pCallCnt*100.0}" pattern="#,##0.0"/> %</td>
								<td><fmt:formatNumber value="${item.IVR_2}" pattern="#,###"/> 건</td>
								<td class="div rt"><fmt:formatNumber value="${ivr2/pCallCnt*100.0}" pattern="#,##0.0"/> %</td>
								</c:otherwise></c:choose>
								
								<c:choose><c:when test="${(item.END_1 + item.END_2 + item.END_3) <= 0}">
								<td><fmt:formatNumber value="${item.END_2}" pattern="#,###"/> 건</td>
								<td class="div rt">0 %</td>
								<td><fmt:formatNumber value="${item.END_1}" pattern="#,###"/> 건</td>
								<td class="div rt">0 %</td>
								<td><fmt:formatNumber value="${item.END_3}" pattern="#,###"/> 건</td>
								<td class="rt">0 %</td>
								</c:when><c:otherwise>
								<td><fmt:formatNumber value="${item.END_2}" pattern="#,###"/> 건</td>
								<td class="div rt"><fmt:formatNumber value="${end2/endSum*100.0}" pattern="#,##0.0"/> %</td>
								<td><fmt:formatNumber value="${item.END_1}" pattern="#,###"/> 건</td>
								<td class="div rt"><fmt:formatNumber value="${end1/endSum*100.0}" pattern="#,##0.0"/> %</td>
								<td><fmt:formatNumber value="${item.END_3}" pattern="#,###"/> 건</td>
								<td class="rt"><fmt:formatNumber value="${end3/endSum*100.0}" pattern="#,##0.0"/> %</td>
								</c:otherwise></c:choose>
							</tr>
							</c:forEach></c:when><c:otherwise><tr><td colspan="14" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>