<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">에러코드</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm" data-url="/system/errcd/list" data-target="lists" data-search="true">
				<input type="hidden" name="pageNo" value=""/>
				<input type="text" value="" style="display:none;"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>에러코드</th>
					<td>
						<input type="text"  class="input01" maxlength="12" name="param[ERR_CD]">
					</td>
				</tr>
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
					<col width="15%"><col width="*">
				</colgroup>
				<thead>
					<tr id="first">
						<th>에러코드</th><th>에러메시지</th>
					</tr>
				</thead>
				<tbody>
					<tr><td colspan="2" class="txt">데이터 조회중입니다.</td></tr>
				</tbody>
			</table>
			</div>
		</div>