<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="app" uri="/WEB-INF/tld/app.tld" %>

<c:choose>
	<c:when test="${result != null}">
		<c:forEach var="item" items="${result}" varStatus="status">
			<tr data-ft="${app:phone(item.TELNO)}" class="selectable">
				<td class="txt">${((paging.pageNo-1)*15) + status.index+1}</td>
				<c:choose>
					<c:when test="${item.USER_SECD == '1'}">
						<td class="txt">
							<a onclick="app.data.overtr.select('${item.TELNO}', '${item.RGSNO}', '1');">
								<span class="masking">${app:phoneMasking(item.TELNO)}</span>
								<span class="nonmasking">${app:phone(item.TELNO)}</span>
							</a>
						</td>
						<td class="txt">
							<a onclick="app.data.overtr.select('${item.TELNO}', '${item.RGSNO}', '1');">${item.RGSNO}</a>
						</td>
						<td class="txt">-</td>
					</c:when>
					<c:when test="${item.USER_SECD == '2'}">
						<td class="txt">
							<a onclick="app.data.overtr.select('${item.TELNO}', '${item.RGSNO}', '2');">
								<span class="masking">${app:phoneMasking(item.TELNO)}</span>
								<span class="nonmasking">${app:phone(item.TELNO)}</span>
							</a>
						</td>
						<td class="txt">-</td>
						<td class="txt">
							<a onclick="app.data.overtr.select('${item.TELNO}', '${item.RGSNO}', '2');">${app:bizno(item.RGSNO)}</a>
						</td>
					</c:when>
					<c:otherwise>
						<td class="txt">
							<a onclick="app.data.overtr.select('${item.TELNO}', '', '3');">
								<span class="masking">${app:phoneMasking(item.TELNO)}</span>
								<span class="nonmasking">${app:phone(item.TELNO)}</span>
							</a>
						</td>
						<td class="txt">-</td>
						<td class="txt">-</td>
					</c:otherwise>
				</c:choose>
				<fmt:formatNumber var="CALL_CNT" value="${item.CALL_CNT}" pattern="#.##" />
				<fmt:formatNumber var="ERR_CNT" value="${item.ERR_CNT}" pattern="#.##" />
				<td><fmt:formatNumber value="${item.CALL_CNT}" pattern="#,###"/> 건</td>
				<c:choose>
					<c:when test="${item.CALL_CNT <= 0 || item.ERR_CNT <= 0}">
						<td>0 %</td>
					</c:when>
					<c:otherwise>
						<td><fmt:formatNumber value="${ERR_CNT/CALL_CNT*100.0}" pattern="#,##0.0"/> %</td>
					</c:otherwise>
				</c:choose>
				<td><fmt:formatNumber value="${item.ERR_CNT}" pattern="#,###"/> 건</td>
				<td><fmt:formatNumber value="${item.CNSLR_CONN_CNT}" pattern="#,###"/> 건</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr data-type="none">
			<td class="txt" colspan="8">데이터가 존재하지 않습니다.</td>
		</tr>
	</c:otherwise>
</c:choose>
<tr style="display:none;">
	<td colspan="8">
		<div class="pagingData" id="paging" data-total="${paging.size}">
			<ul>
				<c:if test="${paging.pageNo-1 > 0}">
					<li><a class="before" data-no="${paging.pageNo-1}">before</a></li>
				</c:if>
				<c:forEach var="item" items="${paging.pages}" varStatus="status">
					<li<c:if test="${paging.pageNo == item}"> class="active"</c:if>><a data-no="${item}">${item}</a></li>
				</c:forEach>
				<c:if test="${paging.pageNo+1 <= paging.pageCount}">
					<li><a class="next" data-no="${paging.pageNo+1}">next</a></li>
				</c:if>
			</ul>
		</div>
	</td>
</tr>