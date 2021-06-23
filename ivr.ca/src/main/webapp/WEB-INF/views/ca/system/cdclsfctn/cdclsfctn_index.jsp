<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<p class="title">코드 관리</p>
		<div class="tblBox" id="cls_edit" style="display:none;">
			<form name="frm1" data-type="edit">
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>코드분류</th>
					<td>
						<input type="text" class="input01 wdt200px" name="cdClsfctn" title="코드분류" placeholder="코드분류" required/>
					</td>
					<th>코드분류명</th>
					<td>
						<input type="text" class="input01 wdt200px" name="cdClsfctnNm" title="코드분류명" placeholder="코드분류명" required/>
					</td>
				</tr>
				<tr style="display:none;">
					<th>등록자/등록일시</th>
					<td id="regrNm1"></td>
					<th>최종수정자/최종수정일시</th>
					<td id="lstUpdusrNm1"></td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight2">
					<a class="btntype02 btn_primary" id="btn_add1">추가</a>
					<a class="btntype02 btn_primary view" id="btn_update1" style="display:none;">수정</a>
					<a class="btntype02 btn_primary view" id="btn_delete1" style="display:none;">삭제</a>
					<a class="btntype02 btn_primary" id="btn_new1">초기화</a>
					<a class="btntype02 btn_primary" id="btn_close1">닫기</a>
				</div>
			</div>
			</form>
		</div>
		<div class="tblBox" id="cd_edit" style="display:none;">
			<form name="frm2" data-type="edit">
			<input type="hidden" name="cdId" value="0"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>코드분류</th>
					<td colspan="3">
						<input type="text" class="input01 wdt200px" name="cdClsfctn" title="코드분류" placeholder="코드분류" required/>
					</td>
				</tr>
				<tr>
					<th>코드</th>
					<td>
						<input type="text" class="input01 wdt200px" name="cd" title="코드" placeholder="코드" required/>
					</td>
					<th>코드명</th>
					<td>
						<input type="text" class="input01 wdt200px" name="cdNm" title="코드명" placeholder="코드명" required/>
					</td>
				</tr>
				<tr>
					<th>정렬순서</th>
					<td>
						<input type="text" class="input01 wdt200px" name="sortOrdr" title="정렬순서" placeholder="정렬순서" data-type="int" required/>
					</td>
					<th>사용여부</th>
					<td>
						<input type="radio" name="useAltv" value="1" id="useAltv1" checked><label for="useAltv1"><span></span>사용</label>
						<input type="radio" name="useAltv" value="0" id="useAltv0"><label for="useAltv0"><span></span>사용안함</label>
					</td>
				</tr>
				<tr style="display:none;">
					<th>등록자/등록일시</th>
					<td id="regrNm2"></td>
					<th>최종수정자/최종수정일시</th>
					<td id="lstUpdusrNm2"></td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight2">
					<a class="btntype02 btn_primary" id="btn_add2">추가</a>
					<a class="btntype02 btn_primary view" id="btn_update2" style="display:none;">수정</a>
					<a class="btntype02 btn_primary view" id="btn_delete2" style="display:none;">삭제</a>
					<a class="btntype02 btn_primary" id="btn_new2">초기화</a>
					<a class="btntype02 btn_primary" id="btn_close2">닫기</a>
				</div>
			</div>
			</form>
		</div>
		
		<div class="tblBox">
			<form data-type="search" name="search_frm">
			<input type="hidden" name="pageNo" value="1"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>코드분류명</th>
					<td>
						<input type="text" class="input01 wdt200px" name="param[CD_CLSFCTN_NM]" title="코드분류명" placeholder="코드분류명" required/>
					</td>
					<th>코드명</th>
					<td>
						<input type="text" class="input01 wdt200px" name="param[CD_NM]" title="코드명" placeholder="코드명" required/>
					</td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight">
					<a class="btntype02 btn_primary" id="btn_search">조회</a>
					<a class="btntype02 btn_primary" id="btn_cls_add">코드분류추가</a>
					<a class="btntype02 btn_primary" id="btn_cd_add">코드추가</a>
				</div>
			</div>
			</form>
		</div>
		<div class="tblBox">
			<p class="sTitle">코드 목록</p>
			<div id="lists">
				<jsp:include page="./cdclsfctn_list.jsp"></jsp:include>
			</div>
		</div>