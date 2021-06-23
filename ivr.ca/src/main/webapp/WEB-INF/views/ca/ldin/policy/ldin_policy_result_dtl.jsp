<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
						<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
						<tr>
							<td class="txt">${status.index+1}</td>
							<td class="txt"><fmt:formatDate value="${item.OCCRRNC_DT}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class="txt"><fmt:formatNumber value="${item.OCCRRNC_CNT}" pattern="#,###"></fmt:formatNumber> 건</td>
							<td class="txt"><fmt:formatNumber value="${item.CMPR_CNT}" pattern="#,###"></fmt:formatNumber> 건</td>
							<c:choose><c:when test="${item.CMPR_CNT <= 0}">
							<td class="txt"> - </td>
							</c:when><c:otherwise>
							<td class="txt"><fmt:formatNumber value="${(item.OCCRRNC_CNT-item.CMPR_CNT)/item.CMPR_CNT*100}" pattern="#,##0.0"></fmt:formatNumber> % 초과</td>
							</c:otherwise></c:choose>
						</tr>
						</c:forEach></c:when><c:otherwise><tr><td colspan="5" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>