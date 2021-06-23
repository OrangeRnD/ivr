<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">로그인이력</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-url="/system/lgnhist/list" data-target="lists" data-search="true">
				<input type="hidden" name="pageNo" value=""/>
				<input type="text" value="" style="display:none;"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
				<c:choose><c:when test="${param.nr == null}"><tr id="first">
					<th>직번</th>
					<td>
						<input type="text"  class="input01" name="param[EMP_NR]" maxlength="10" value="${sessionScope.session.param.system_lgnhist.EMP_NR}">
					</td>
					<th>이름</th>
					<td>
						<input type="text"  class="input01" name="param[EMP_NM]" maxlength="25" value="${sessionScope.session.param.system_lgnhist.EMP_NM}">
					</td>
				</tr>
				</c:when><c:otherwise><tr id="first">
					<th>직번</th>
					<td>
						<input type="text"  class="input01" name="param[EMP_NR]" maxlength="10" value="${param.nr}">
					</td>
					<th>이름</th>
					<td>
						<input type="text"  class="input01" name="param[EMP_NM]" maxlength="25" value="${param.nm}">
					</td>
				</tr></c:otherwise></c:choose>
			</table>
			<div class="btnArea">
				<div class="btnRight">
					<a class="btntype02 btn_primary" id="btn_search">조회</a>
				</div>
			</div>
			</form>
		</div>
		<div class="tblBox">
			<div id="lists">
				<table class="tblList">
				<colgroup>
					<col width="25%"><col width="25%"><col width="25%"><col width="25%">
				</colgroup>
				<thead>
					<tr id="first">
						<th>직번</th><th>이름</th><th>로그인일시</th><th>상태</th>
				</thead>
				<tbody>
					<tr><td colspan="4" class="txt">데이터 조회중입니다.</td></tr>
				</tbody>
			</table>
			</div>
		</div>