<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<p class="title">사용자 관리</p>
		<div class="tblBox">
			<form name="frm" data-type="edit">
			<input type="hidden" name="usrId" value="0"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>이름</th>
					<td>
						<input type="text" class="input01 wdt200px" name="empNm" title="이름" placeholder="이름" required/>
					</td>
					<th>직번</th>
					<td>
						<input type="text" class="input01 wdt200px" name="empNr" title="직번" placeholder="직번" required/>
					</td>
				</tr>
				<tr>
					<th>휴대전화</th>
					<td>
						<input type="text" class="input01 wdt200px" name="telno" title="휴대전화" placeholder="휴대전화" data-type="tel"/>
					</td>
					<th>이메일</th>
					<td>
						<input type="text" class="input01 wdt200px" name="email" title="이메일" placeholder="이메일"/>
					</td>
				</tr>
				<tr>
					<th>알림수신</th>
					<td>
						<input type="checkbox" name="smsNotiftnXmsnAltv" value="1" id="sms_snd" checked><label for="sms_snd"><span></span>SMS</label>
						<input type="checkbox" name="emailNotiftnXmsnAltv" value="1" id="email_snd" checked><label for="email_snd"><span></span>E-MAIL</label>
					</td>
					<th>보고서수신</th>
					<td>
						<input type="radio" name="dlyRptRcptnAltv" value="1" id="dlyRptRcptnAltv1" checked><label for="dlyRptRcptnAltv1"><span></span>수신</label>
						<input type="radio" name="dlyRptRcptnAltv" value="0" id="dlyRptRcptnAltv0"><label for="dlyRptRcptnAltv0"><span></span>미수신</label>
					</td>
				</tr>
				<tr>
					<th>권한등급</th>
					<td colspan="3">
						<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<input id="grp${item.cd}" type="radio" name="authyGrpCd" value="${item.cd}"<c:if test="${status.index == 0}"> checked</c:if>/><label for="grp${item.cd}"><span></span>${item.cdNm}</label>
						</c:forEach>
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
				</div>
			</div>
			</form>
		</div>
		
		<div class="tblBox">
			<p class="sTitle">사용자 목록</p>
			<div>
				<table class="tblList">
					<colgroup>
						<col width="5%">
						<col width="11%">
						<col width="8%">
						<col width="10%">
						<col width="14%">
						<col width="10%">
						<col width="8%">
						<col width="8%">
						<col width="10%">
						<col width="8%">
						<col width="8%">
					</colgroup>
					<thead>
						<tr id="first">
							<th>No.</th>
							<th>이름</th>
							<th>직번</th>
							<th>휴대전화</th>
							<th>이메일</th>
							<th>알림수신</th>
							<th>보고서수신</th>
							<th>권한등급</th>
							<th>최종로그인일시</th>
							<th>최종수정자</th>
							<th>최종수정일시</th>
						</tr>
					</thead>
					<tbody id="lists">
						<jsp:include page="./cd_list.jsp"></jsp:include>
					</table>
			</div>
		</div>