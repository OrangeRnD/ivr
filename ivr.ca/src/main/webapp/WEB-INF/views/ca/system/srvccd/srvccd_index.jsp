<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">서비스코드</p>
		</div>
		<div class="tblBox">
			<form name="frm" data-type="edit">
			<input type="hidden" name="oldSrvcCd" value=""/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>서비스코드</th>
					<td>
						<input type="text" class="input01 wdt200px" name="srvcCd" title="서비스코드" placeholder="서비스코드" maxlength="10" required data-type="eng"/>
					</td>
				</tr>
				<tr>
					<th>서비스명</th>
					<td>
						<input type="text" class="input01 wdt200px" name="srvcNm" title="서비스명" placeholder="서비스명" maxlength="50" required/>
					</td>
				</tr>
				<tr>
					<th>모니터링 여부</th>
					<td>
						<input type="radio" name="monitoringYn" value="1" id="monitoringYn1" checked><label for="monitoringYn1"><span></span>예</label>
						<input type="radio" name="monitoringYn" value="0" id="monitoringYn0"><label for="monitoringYn0"><span></span>아니오</label>
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
				<colgroup><col width="11%"><col width="200px"><col width="11%"><col width="200px"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>서비스코드</th>
					<td>
						<input type="text" class="input01" maxlength="10" name="param[SRVC_CD]">
					</td>
					<th>서비스명</th>
					<td>
						<input type="text" class="input01" maxlength="50" name="param[SRVC_NM]">
					</td>
					<th>모니터링 여부</th>
					<td>
						<input type="radio" name="param[MONITORING_YN]" value="" id="myn_all" checked data-search="true"><label for="myn_all"><span></span>전체</label>
						<input type="radio" name="param[MONITORING_YN]" value="1" id="myn1" data-search="true"><label for="myn1"><span></span>예</label>
						<input type="radio" name="param[MONITORING_YN]" value="0" id="myn0" data-search="true"><label for="myn0"><span></span>아니오</label>
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
						<col width="200px"><col width="300px"><col width="100px"><col width="*">
					</colgroup>
					<thead>
						<tr id="first">
							<th>서비스코드</th><th>서비스명</th><th>모니터링 여부</th><th>비고</th>
						</tr>
					</thead>
					<tbody>
						<tr><td colspan="4" class="txt">데이터 조회중입니다.</td></tr>
					</tbody>
				</table>
			</div>
		</div>