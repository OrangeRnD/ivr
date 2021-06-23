<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<!-- <p class="title">알림 설정 관리</p> -->
		<div class="tblBox">
			<form name="frm" data-type="edit">
			<input type="hidden" name="anlysPlcyId" value="0"/>
			<input type="hidden" name="anlysPlcySecd" value="1"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>알림명</th>
					<td colspan="3">
						<input type="text" class="input01 wdt100ps" name="anlysPlcyNm" title="알림명" maxlength="50" placeholder="알림명" required/>
					</td>
				</tr>
				<tr>
					<th>SMS 발송 문구 <span id="sms_cn"></span></th>
					<td colspan="3">
						<input type="text" class="input01 wdt100ps" name="smsXmsnCn" title="SMS 발송 문구" maxlength="80" placeholder="최대 80byte(한글 40자 또는 영문 및 숫자 80자)까지 입력 가능합니다."/>
					</td>
				</tr>
				<tr>
					<th rowspan="2">비교기준</th>
					<td colspan="3">
						<input id="prdSecd1" type="radio" name="prdSecd" value="1" checked/><label for="prdSecd1"><span></span>전일 대비</label>
						<input id="prdSecd2" type="radio" name="prdSecd" value="2"/><label for="prdSecd2"><span></span>전주 평균 대비</label>
						<input id="prdSecd3" type="radio" name="prdSecd" value="3"/><label for="prdSecd3"><span></span>전월 평균 대비</label>
						<input id="prdSecd4" type="radio" name="prdSecd" value="4"/><label for="prdSecd4"><span></span>전분기 평균 대비</label>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<input id="prdSecd5" type="radio" name="prdSecd" value="5"/><label for="prdSecd5"><span></span>전주 동일요일 대비</label>
						<input id="prdSecd6" type="radio" name="prdSecd" value="6"/><label for="prdSecd6"><span></span>전월 동일일자 대비</label>
						<input id="prdSecd7" type="radio" name="prdSecd" value="7"/><label for="prdSecd7"><span></span>전분기 동일차월 동일일자 대비</label>
					</td>
				</tr>
				<tr>
					<th>기준일</th>
					<td>
						<input id="bizdtSecd1" type="radio" name="bizdtSecd" value="1" checked/><label for="bizdtSecd1"><span></span>전체</label>
						<input id="bizdtSecd2" type="radio" name="bizdtSecd" value="2"/><label for="bizdtSecd2"><span></span>영업일만</label>
						<input id="bizdtSecd3" type="radio" name="bizdtSecd" value="3"/><label for="bizdtSecd3"><span></span>휴일만</label>
					</td>
					<th>상하위 제외</th>
					<!-- <td class="subLine"> -->
					<td>
						<span class="label">상하위</span><input type="text" class="input01 int" name="exclsnStdval" title="상하위제외값" value="0" data-type="int" maxlength="2" data-format="true" required/> <span class="label">%</span>
					</td>
				</tr>
				<tr>
					<th>알림설정</th>
					<td>
						<input type="text" class="input01 wdt100px" name="notiftnStdval" value="0" title="알림설정값" data-type="int" maxlength="3" data-format="true" required/><span class="label">% 초과</span>
					</td>
					<th>전송설정</th>
					<td>
						<input type="checkbox" name="smsXmsnAltv" value="1" id="sms_snd" checked><label for="sms_snd"><span></span>SMS 전송</label>
						<input type="checkbox" name="emailXmsnAltv" value="1" id="email_snd" checked><label for="email_snd"><span></span>E-MAIL 전송</label>
					</td>
				</tr>
				<tr style="display:none;">
					<th>등록자/등록일시</th>
					<td id="regrNm"></td>
					<th>최종수정자/최종수정일시</th>
					<td id="lstUpdusrNm"></td>
				</tr>
			</table>
			<div class="btnArea">
				<div class="btnRight2">
					<a class="btntype02 btn_primary" id="btn_add">추가</a>
					<a class="btntype02 btn_primary view" id="btn_update" style="display:none;">수정</a>
					<a class="btntype02 btn_primary view" id="btn_delete" style="display:none;">삭제</a>
					<a class="btntype02 btn_primary" id="btn_new">초기화</a>
					<a class="btntype02 btn_primary" id="btn_policy_close">닫기</a>
				</div>
			</div>
			</form>
		</div>
		
		<div class="tblBox">
			<p class="sTitle">알림 설정 목록</p>
			<div id="policy_lists">
				<jsp:include page="./ldin_policy_list.jsp"></jsp:include>
			</div>
			<!-- <div class="page_wrap">
				<div class="pagingData" >
					<ul><li><a class="before">before</a></li><li><a>1</a></li><li class="active"><a>2</a></li><li><a class="next">next</a></li></ul>
				</div>
			</div> -->
		</div>