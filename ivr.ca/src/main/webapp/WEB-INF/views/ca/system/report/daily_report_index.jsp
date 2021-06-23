<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">일일보고서</p>
		</div>
		<div class="tblBox">
			<form data-type="search" name="search_frm">
			<input type="text" name="param[NAME]" value="" style="width:0px;height:0px;border:none;"/>
			<input type="text" name="param[EMP_NR]" value="" style="width:0px;height:0px;border:none;"/>
			<table class="tbltype01">
				<colgroup><col width="11%"><col width="*"></colgroup>
				<tr id="first">
					<th>일자</th>
					<td>
						<span class="calBox2"><input type="text" name="param[DT]" class="inputCal" data-type="date" value=""><a data-type="calendar"><img src="${webappRoot}/resources/images/icon_calendar2.gif"></a></span> 
					</td>
				</tr>
			</table>
			</form>
			<div class="btnArea">
				<div class="btnRight">
				<a class="btntype02 btn_primary" id="btn_search">조회</a>
				<a class="btntype02 btn_primary" id="btn_print hide">프린트</a>
				<a class="btntype02 btn_primary" id="btn_mail">메일발송</a>
				</div>
			</div>
		</div>
		
		<div id="lists">
		</div>
		<div class="popupBox" id="popupBox" style="display:none;">
			<div class="layerPopup" style="width:500px; height:200px; top:calc(50% - 100px); left:calc(50% - 250px);  ">
				<p class="popTit">메일 주소<a class="closeBtn" id="btn_close">닫기</a></p>
				<table class="tbltype01">
					<colgroup><col width="11%"><col width="25%"><col width="11%"><col width="*"></colgroup>
					<tr id="first">
						<th>직번</th>
						<td>
							<input type="text" class="input01 wdt200px" id="empNr" title="직번" placeholder="직번" maxlength="15" data-type="eng" value="${session.empNr}"/>
						</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>
							<input type="text" class="input01 wdt200px" id="personal" title="이름" placeholder="이름" maxlength="15" data-type="kor" value="${session.empNm}"/>
						</td>
					</tr>
				</table>
				<div class="btnArea">
					<div class="btnRight2">
						<a class="btntype02 btn_primary" id="btn_send">발송</a>
						<a class="btntype02 btn_primary" id="btn_cancel">취소</a>
					</div>
				</div>
			</div>
			<div class="popupDimed" id="popupDimed"></div>
		</div>