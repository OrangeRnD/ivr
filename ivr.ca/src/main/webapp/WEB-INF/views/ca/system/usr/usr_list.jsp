<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld" %>
						<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
						<tr data-id="${item.USR_ID}" class="selectable">
							<td class="txt">${status.index+1}</td>
							<td class="txt">${item.EMP_NM}</td>
							<td class="txt">${item.EMP_NR}</td>
							<td class="txt">${app:phoneMasking(item.TELNO)}</td>
							<td class="txt">${item.EMAIL}</td>
							<td class="txt">
							<c:choose><c:when test="${item.SMS_NOTIFTN_XMSN_ALTV == true && item.EMAIL_NOTIFTN_XMSN_ALTV == true}">SMS / E-Mail</c:when>
							<c:otherwise><c:if test="${item.SMS_NOTIFTN_XMSN_ALTV == true}">SMS</c:if> <c:if test="${item.EMAIL_NOTIFTN_XMSN_ALTV == true}">E-Mail</c:if></c:otherwise></c:choose>
							</td>
							<td class="txt"><c:choose><c:when test="${item.DLY_RPT_RCPTN_ALTV == true}">수신</c:when><c:otherwise>미수신</c:otherwise></c:choose></td>
							<td class="txt">${item.AUTHY_GRP_NM}</td>
							<td class="txt lgn" data-nr="${item.EMP_NR}" data-nm="${item.EMP_NM}"><fmt:formatDate value="${item.LST_LGN_DT}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td class="txt">${item.LST_UPDUSR_NM}</td>
							<td class="txt"><fmt:formatDate value="${item.LST_UPDT_DT}" pattern="yyyy-MM-dd"/></td>
						</tr>
						</c:forEach></c:when><c:otherwise><tr><td colspan="11" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
					</tbody>