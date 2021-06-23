<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
							<c:choose><c:when test="${result != null}"><c:forEach var="item" items="${result}" varStatus="status">
							<tr>
								<td class="sort" title="${item.TRAN_NM}"><a onclick="app.use.call.select('${item.TRAN_CD}', '${item.TRAN_NM}');">${item.TRAN_CD}</a></td>
								<td><fmt:formatNumber value="${item.S_CNT}" pattern="#,###"></fmt:formatNumber></td>
								<td><fmt:formatNumber value="${item.F_CNT}" pattern="#,###"></fmt:formatNumber></td>
							</tr>
							</c:forEach></c:when><c:otherwise><tr><td colspan="3" class="txt">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>