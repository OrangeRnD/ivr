<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<fmt:formatNumber var="ravgCnt1" value="${(item.AVG_CNT1-item.AVG_CNT2)*100}" pattern="#.##" />
							<fmt:formatNumber var="ravgCnt2" value="${(item.AVG_CNT2-item.AVG_CNT1)*100}" pattern="#.##" />
							<fmt:formatNumber var="rtotCnt1" value="${(item.TOT_CNT1-item.TOT_CNT2)*100}" pattern="#.##" />
							<fmt:formatNumber var="rtotCnt2" value="${(item.TOT_CNT2-item.TOT_CNT1)*100}" pattern="#.##" />
							<fmt:formatNumber var="totCnt2" value="${item.TOT_CNT2}" pattern="#.##" />
							<c:choose><c:when test="${item.CD == 'C'}"><c:set var="NM" value="IVR 이용콜수"></c:set></c:when><c:when test="${item.CD == 'P'}"><c:set var="NM" value="대표번호"></c:set></c:when><c:when test="${item.CD == 'S'}"><c:set var="NM" value="말로하는 ARS 이용건수"></c:set></c:when><c:otherwise><c:set var="NM" value=" 이용콜수"></c:set></c:otherwise></c:choose>
							<tr data-cd="${item.CD}" data-nm="${item.NM}${NM}" data-avg1="<fmt:formatNumber value="${item.AVG_CNT1}" pattern="###"></fmt:formatNumber>" data-tot1="${item.TOT_CNT1}" data-avg2="<fmt:formatNumber value="${item.AVG_CNT2}" pattern="###"></fmt:formatNumber>" data-tot2="${item.TOT_CNT2}">
								<c:choose><c:when test="${item.CD == 'C'}"><td class="txt div" colspan="2">IVR 이용콜수</td></c:when>
								<c:when test="${item.CD == 'P'}"><td class="txt div" colspan="2">대표번호</td></c:when>
								<c:when test="${item.CD == 'S'}"><td class="txt div" colspan="2">말로하는 ARS 이용건수</td></c:when>
								<c:when test="${item.CD == '3'}"><td class="txt div">말로하는 ARS 이용콜수</td></c:when>
								<c:otherwise><td class="txt div">보이는 ARS 이용콜수</td></c:otherwise></c:choose>
								<td class="lineBold"><fmt:formatNumber value="${item.AVG_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold"><fmt:formatNumber value="${item.TOT_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td></td>
								<td class="txt"><fmt:parseDate value="${item.MAX_DT1}" pattern="yyyyMMdd" var="MAX_DT1"></fmt:parseDate><fmt:formatDate value="${MAX_DT1}" pattern="yyyy-MM-dd"/></td>
								<td class="lineBold div"><fmt:formatNumber value="${item.MAX_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold"><fmt:formatNumber value="${item.AVG_CNT2}" pattern="#,###"></fmt:formatNumber></td>
								<c:choose><c:when test="${item.AVG_CNT2 <= 0}">
								<td class="lineBold">-</td>
								<td class="lineBold">-</td>
								</c:when><c:when test="${item.AVG_CNT1 == item.AVG_CNT2}">
								<td class="lineBold">0</td>
								<td class="lineBold">0 %</td>
								</c:when><c:when test="${item.AVG_CNT1 > item.AVG_CNT2}">
								<td class="lineBold plus"><fmt:formatNumber value="${item.AVG_CNT1-item.AVG_CNT2}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold plus"><fmt:formatNumber value="${ravgCnt1/item.AVG_CNT2}" pattern="#,##0.0"></fmt:formatNumber> %</td>
								</c:when><c:otherwise>
								<td class="lineBold minus">- <fmt:formatNumber value="${item.AVG_CNT2-item.AVG_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold minus">- <fmt:formatNumber value="${ravgCnt2/item.AVG_CNT2}" pattern="#,##0.0"></fmt:formatNumber> %</td>
								</c:otherwise></c:choose>
								<td class="lineBold"><fmt:formatNumber value="${item.TOT_CNT2}" pattern="#,###"></fmt:formatNumber></td>
								<td></td>
								<c:choose><c:when test="${item.TOT_CNT2 <= 0}">
								<td class="lineBold">-</td>
								<td class="lineBold">-</td>
								</c:when><c:when test="${item.TOT_CNT1 == item.TOT_CNT2}">
								<td class="lineBold">0</td>
								<td class="lineBold">0 %</td>
								</c:when><c:when test="${item.TOT_CNT1 > item.TOT_CNT2}">
								<td class="lineBold plus"><fmt:formatNumber value="${item.TOT_CNT1-item.TOT_CNT2}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold plus"><fmt:formatNumber value="${rtotCnt1/totCnt2}" pattern="#,##0.0"></fmt:formatNumber> %</td>
								</c:when><c:otherwise>
								<td class="lineBold minus">- <fmt:formatNumber value="${item.TOT_CNT2-item.TOT_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold minus">- <fmt:formatNumber value="${rtotCnt2/totCnt2}" pattern="#,##0.0"></fmt:formatNumber> %</td>
								</c:otherwise></c:choose>
								<td class="txt"><fmt:parseDate value="${item.MAX_DT2}" pattern="yyyyMMdd" var="MAX_DT2"></fmt:parseDate><fmt:formatDate value="${MAX_DT2}" pattern="yyyy-MM-dd"/></td>
								<td class="lineBold"><fmt:formatNumber value="${item.MAX_CNT2}" pattern="#,###"></fmt:formatNumber></td>
							</tr>
							<c:if test="${item.CD == 'P'}"><tr data-cd="1" data-nm="ARS 이용콜수" data-period1="${item.NUM1}" data-period2="${item.NUM2}" data-avg1="0" data-tot1="0" data-avg2="0" data-tot2="0">
								<td class="txt" rowspan="3">대표번호</td>
								<td class="txt div">ARS 이용콜수</td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td></td>
								<td class="txt"><fmt:formatDate value="${MAX_DT1}" pattern="yyyy-MM-dd"/></td>
								<td class="lineBold div"><fmt:formatNumber value="${item.MAX_CNT1-MAX_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold"></td>
								<td class="lineBold">-</td>
								<td class="lineBold">-</td>
								<td class="lineBold"></td>
								<td></td>
								<td class="lineBold">-</td>
								<td class="lineBold">-</td>
								<td class="txt"><fmt:formatDate value="${MAX_DT2}" pattern="yyyy-MM-dd"/></td>
								<td class="lineBold"><fmt:formatNumber value="${item.MAX_CNT2-MAX_CNT2}" pattern="#,###"></fmt:formatNumber></td>
							</tr></c:if>
							</c:forEach></c:when><c:otherwise><tr><td colspan="16" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>