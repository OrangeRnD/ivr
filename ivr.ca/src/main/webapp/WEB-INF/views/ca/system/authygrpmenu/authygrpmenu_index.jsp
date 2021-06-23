<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<div class="titleBox">
			<p class="ltit">메뉴별 권한</p>
		</div>
		<div class="tblBox">
			<div class="btnArea">
				<div class="btnRight">
					<a class="btntype02 btn_primary" id="btn_save">수정</a>
				</div>
			</div>
		</div>
		<div class="tblBox">
			<form name="frm">
			<div id="lists">
				<table class="tblList">
					<colgroup>
						<col width="10%">
						<col width="15%">
						<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
						<col width="*">
						</c:forEach>
					</colgroup>
					<thead>
						<tr id="first">
							<th>대 메뉴명</th>
							<th>소 메뉴명</th>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<th>${item.cdNm}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="txt" rowspan="7">인입현황</td>
							<td class="txt">실시간텔레뱅킹현황</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="1" data-cd="${item.cd}" id="agm_1_${item.cd}"><label for="agm_1_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">인입추이(일별,월별)</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="2" data-cd="${item.cd}" id="agm_2_${item.cd}"><label for="agm_2_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">실시간인입추이비교</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="3" data-cd="${item.cd}" id="agm_3_${item.cd}"><label for="agm_3_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">알림발생현황조회</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="4" data-cd="${item.cd}" id="agm_4_${item.cd}"><label for="agm_4_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">인입총괄현황</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="5" data-cd="${item.cd}" id="agm_5_${item.cd}"><label for="agm_5_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">ARS유형별이용현황</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="6" data-cd="${item.cd}" id="agm_6_${item.cd}"><label for="agm_6_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">대표번호이용현황</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="7" data-cd="${item.cd}" id="agm_7_${item.cd}"><label for="agm_7_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt" rowspan="7">이용현황</td>
							<td class="txt">실시간거래발생비교</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="8" data-cd="${item.cd}" id="agm_8_${item.cd}"><label for="agm_8_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">실시간에러발생비교</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="9" data-cd="${item.cd}" id="agm_9_${item.cd}"><label for="agm_9_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">거래,에러현황조회</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="10" data-cd="${item.cd}" id="agm_10_${item.cd}"><label for="agm_10_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">서비스이용현황</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="11" data-cd="${item.cd}" id="agm_11_${item.cd}"><label for="agm_11_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">서비스현황조회</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="12" data-cd="${item.cd}" id="agm_12_${item.cd}"><label for="agm_12_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">고객콜검색</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="13" data-cd="${item.cd}" id="agm_13_${item.cd}"><label for="agm_13_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">거래검색</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="14" data-cd="${item.cd}" id="agm_14_${item.cd}"><label for="agm_14_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt" rowspan="2">유형분석</td>
							<td class="txt">서비스이용고객패턴분석</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="15" data-cd="${item.cd}" id="agm_15_${item.cd}"><label for="agm_15_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">에러코드유형분석</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="16" data-cd="${item.cd}" id="agm_16_${item.cd}"><label for="agm_16_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt" rowspan="2">데이터분석</td>
							<td class="txt">이상거래고객분석</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="17" data-cd="${item.cd}" id="agm_17_${item.cd}"><label for="agm_17_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">거래발생일변동량분석</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="18" data-cd="${item.cd}" id="agm_18_${item.cd}"><label for="agm_18_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt" rowspan="9">시스템관리</td>
							<td class="txt">알림관리</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="19" data-cd="${item.cd}" id="agm_19_${item.cd}"><label for="agm_19_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">일일보고서</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="20" data-cd="${item.cd}" id="agm_20_${item.cd}"><label for="agm_20_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">사용자관리</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="21" data-cd="${item.cd}" id="agm_21_${item.cd}"><label for="agm_21_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">메뉴별권한</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="22" data-cd="${item.cd}" id="agm_22_${item.cd}"><label for="agm_22_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">코드관리</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="23" data-cd="${item.cd}" id="agm_23_${item.cd}"><label for="agm_23_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">서비스코드</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="24" data-cd="${item.cd}" id="agm_24_${item.cd}"><label for="agm_24_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">거래코드</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="25" data-cd="${item.cd}" id="agm_25_${item.cd}"><label for="agm_25_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">에러코드</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="26" data-cd="${item.cd}" id="agm_26_${item.cd}"><label for="agm_26_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
						<tr>
							<td class="txt">로그인이력</td>
							<c:forEach var="item" items="${AUTHY_GRP_CD}" varStatus="status">
							<td class="txt"><input type="checkbox" data-menu-id="27" data-cd="${item.cd}" id="agm_27_${item.cd}"><label for="agm_27_${item.cd}"><span></span></label></td>
							</c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
		</div>