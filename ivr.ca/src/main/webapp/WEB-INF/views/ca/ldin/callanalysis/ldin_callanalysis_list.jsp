<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<fmt:formatNumber var="ravgCnt1" value="${(item.AVG_CNT1-item.AVG_CNT2)*100}" pattern="#.##" />
							<fmt:formatNumber var="ravgCnt2" value="${(item.AVG_CNT2-item.AVG_CNT1)*100}" pattern="#.##" />
							<fmt:formatNumber var="rtotCnt1" value="${(item.TOT_CNT1-item.TOT_CNT2)*100}" pattern="#.##" />
							<fmt:formatNumber var="rtotCnt2" value="${(item.TOT_CNT2-item.TOT_CNT1)*100}" pattern="#.##" />
							<fmt:formatNumber var="totCnt2" value="${item.TOT_CNT2}" pattern="#.##" />
							<tr data-cd="${item.INPTH_SECD}" data-nm="${item.INPTH_SECD_NM}" data-avg1="<fmt:formatNumber value="${item.AVG_CNT1}" pattern="###"></fmt:formatNumber>" data-tot1="${item.TOT_CNT1}" data-avg2="<fmt:formatNumber value="${item.AVG_CNT2}" pattern="###"></fmt:formatNumber>" data-tot2="${item.TOT_CNT2}">
								<td class="txt div">${item.INPTH_SECD_NM}</td>
								<td class="lineBold"><fmt:formatNumber value="${item.AVG_CNT1}" pattern="#,###"></fmt:formatNumber></td>
								<td class="lineBold"><fmt:formatNumber value="${item.TOT_CNT1}" pattern="#,###"></fmt:formatNumber></td>
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
							</tr></c:forEach><tr class="total">
								<td class="txt div">??? ???</td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td class="txt"></td>
								<td class="lineBold div"></td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td class="lineBold"></td>
								<td class="txt"></td>
								<td class="lineBold"></td>
							</tr></c:when><c:otherwise><tr><td colspan="13" class="txt">???????????? ???????????? ????????????.</td></tr></c:otherwise></c:choose>