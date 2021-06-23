<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">거래코드</p>
		</div>
		<div class="tblBox">
			<form name="frm" data-type="edit">
			<input type="hidden" name="oldTranCd" value=""/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>거래코드</th>
					<td>
						<input type="text" class="input01 wdt200px" name="tranCd" title="거래코드" placeholder="거래코드" maxlength="12" required data-type="eng"/>
					</td>
				</tr>
				<tr>
					<th>거래명</th>
					<td>
						<input type="text" class="input01 wdt200px" name="tranNm" title="거래명" placeholder="거래명" maxlength="50" required/>
					</td>
				</tr>
				<tr style="display:none;">
					<th>등록일시</th>
					<td id="regrNm"></td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight2">
					<a class="btntype02 btn_primary" id="btn_add">추가</a>
					<a class="btntype02 btn_primary view" id="btn_update" style="display:none;">수정</a>
					<a class="btntype02 btn_primary view" id="btn_delete" style="display:none;">삭제</a>
					<a class="btntype02 btn_primary" id="btn_new">초기화</a>
				</div>
			</div>
			</form>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm">
				<input type="hidden" name="pageNo" value=""/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="300px"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>거래코드</th>
					<td>
						<input type="text"  class="input01" maxlength="12" name="param[TRAN_CD]">
					</td>
					<th>거래명</th>
					<td>
						<input type="text"  class="input01" maxlength="50" name="param[TRAN_NM]">
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
					<col width="15%"><col width="15%"><col width="*">
				</colgroup>
				<thead>
					<tr id="first">
						<th>거래코드</th><th>거래명</th><th>비고</th>
					</tr>
				</thead>
				<tbody>
					<tr><td colspan="3" class="txt">데이터 조회중입니다.</td></tr>
				</tbody>
			</table>
			</div>
		</div>