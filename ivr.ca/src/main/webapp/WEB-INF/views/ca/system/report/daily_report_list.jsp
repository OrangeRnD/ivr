<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div style="font-family: '나눔고딕', dotum;padding: 0;border: 0px;outline: 0;vertical-align: baseline;background: transparent;box-sizing: border-box;width: 1024px;margin: 0 auto;position: relative; ">
	<div style="margin: 0;border: 0px;outline: 0;vertical-align: baseline;background: transparent;box-sizing: border-box;float: left;width: 1024px;background-color: #fff;padding: 30px;">
		<h1 style="width:964px;font-size: 18px;font-weight: 600;border-bottom: 1px solid #ccc;padding-bottom: 15px;margin-bottom: 20px;">콜 인입현황</h1>
		<p style="float: left;width: 964px;height: 20px;font-size: 14px;color: #222222;font-weight: 700;margin-bottom: 5px;padding-left: 3px;line-height: 16px;">콜 인입 현황</p>
		<div style="box-sizing: border-box;float: left;width: 100%;margin-bottom: 40px;">
			<table style="border-spacing: 0;border: 0 none;width: 964px;float: left;font-size: 12px;line-height: 12px;font-family: 'engMed', 'korMed';letter-spacing: 0;border-collapse: collapse;">
				<colgroup>
					<col width="13%"><col width="12%">
					<col width="12%"><col width="13%">
					<col width="12%"><col width="13%">
					<col width="12%"><col width="13%">
				</colgroup>
				<thead style="background-color: #847c7c;border-bottom: 1px solid #111;border-top: 1px solid #111;color: #fff;">
					<tr>
						<th rowspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">인입구분</th>
						<th rowspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">${DT}</th>
						<th colspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">전일</th>
						<th colspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">전주평균 대비</th>
						<th colspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: none;text-align: center;border-top: none;">전월평균 대비</th>
					</tr>
					<tr>
						<th colspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">${P_DT}</th>
						<th colspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">${PW_FROM_DT} ~ ${PW_TO_DT}</th>
						<th colspan="2" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: none;border-top: 1px solid #928b8b;text-align: center;">${PM_FROM_DT} ~ ${PM_TO_DT}</th>
					</tr>
				</thead>
				<tbody>
					<c:choose><c:when test="${meda != null}"><c:forEach var="item" items="${meda}" varStatus="status"><c:choose><c:when test="${item.MEDA_SECD != '-1'}">
					<tr <c:if test="${status.index == 0}"> style="border-top: 1px solid #111;"</c:if>>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: center;">${item.MEDA_SECD_NM}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT1}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT2}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: ${item.COLOR2};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_RT2} %</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT3}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: ${item.COLOR3};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_RT3} %</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT4}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-bottom: 1px solid #dddddd;color: ${item.COLOR4};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;border-right: none;">${item.S_TOT_RT4} %</td>
					</tr></c:when><c:otherwise>
					<tr style="background-color:#9d9797;">
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: center;">${item.MEDA_SECD_NM}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT1}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT2}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_RT2} %</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT3}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_RT3} %</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;">${item.S_TOT_CNT4}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-bottom: 1px solid #dddddd;color: #fff;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;max-width: 150px;text-align: right;border-right: none;">${item.S_TOT_RT4} %</td>
					</tr></c:otherwise></c:choose>
					</c:forEach></c:when><c:otherwise><tr style="border-top: 1px solid #111;"><td colspan="8" style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: none;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;">데이터가 존재하지 않습니다.</td></tr></c:otherwise></c:choose>
				</tbody>
			</table>
		</div>
		
		<p style="float: left;width: 964px;height: 20px;font-size: 14px;color: #222222;font-weight: 700;margin-bottom: 5px;padding-left: 3px;line-height: 16px;">콜 인입 비교현황</p>
		<div style="float: left;width: 964px;margin-bottom: 40px;border: 1px solid #ccc;padding: 15px;">
			<p style="font-size: 12px;font-weight: 500;width: 934px;margin-bottom: 10px;">콜인입</p>
			<div style="position:relative;width: 934px;height: 260px;">
				<img src="${webappRoot}/resources/report/${imageFolder}${medaImage}" style="width:934px;height:260px;"/>
			</div>
		</div>

		<p style="float: left;width: 964px;height: 20px;font-size: 14px;color: #222222;font-weight: 700;margin-bottom: 5px;padding-left: 3px;line-height: 16px;">인입 추이분석 발생내역</p>
		<div style="position:relative;float: left;width: 964px;margin-bottom: 40px;">
			<table style="border-spacing: 0;border: 0 none;width: 100%;float: left;font-size: 12px;line-height: 12px;font-family: 'engMed', 'korMed';color: #fff;letter-spacing: 0;border-collapse: collapse;">
				<thead style="background-color: #847c7c;border-bottom: 1px solid #111;border-top: 1px solid #111;">
					<tr>
						<th style="width:40px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">No.</th>
						<th style="width:234px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">알림명</th>
						<th style="width:150px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">알림발생일시</th>
						<th style="width:100px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">인입건수</th>
						<th style="width:100px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">비교건수</th>
						<th style="width:85px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">알림설정</th>
						<th style="width:85px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">초과</th>
						<th style="width:85px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;text-align: center;border-top: none;">SMS전송</th>
						<th style="width:85px;vertical-align: middle;font-weight: normal;height: 30px;border-right: none;text-align: center;border-top: none;">E-mail전송</th>
					</tr>
				</thead>
				<tbody>
					<c:choose><c:when test="${plcy != null}"><c:forEach var="item" items="${plcy}" varStatus="status">
					<tr>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;">${item.NO}</td>
						<td style="max-width:218px;border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;" title="${item.ANLYS_PLCY_NM}">${item.ANLYS_PLCY_NM}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;">${item.S_OCCRRNC_DT}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.S_OCCRRNC_CNT} 건</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.S_CMPR_CNT} 건</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.S_NOTIFTN_STDVAL} %</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.S_EXCESS}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;">${item.S_SMS_XMSN_ALTV}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;border-right: none;">${item.S_EMAIL_XMSN_ALTV}</td>
					</tr>
					</c:forEach></c:when><c:otherwise><tr style="border-top: 1px solid #111;"><td colspan="9" style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: none;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;">알림 발생 내역이 않습니다.</td></tr></c:otherwise></c:choose>
				</tbody>
			</table>
		</div>

		<h1 style="width:964px;font-size: 18px;font-weight: 600;border-bottom: 1px solid #ccc;padding-bottom: 15px;margin-bottom: 20px;">서비스 이용현황</h1>
		<p style="float: left;width: 964px;height: 20px;font-size: 14px;color: #222222;font-weight: 700;margin-bottom: 5px;padding-left: 3px;line-height: 16px;">당일 이용서비스 현황</p>
		<div style="box-sizing: border-box;float: left;width: 964px;margin-bottom: 40px;">
			<table style="border-spacing: 0;border: 0 none;width: 964px;float: left;font-size: 12px;line-height: 12px;font-family: 'engMed', 'korMed';color: #fff;letter-spacing: 0;border-collapse: collapse;">
				<colgroup>
					<col width="11%"><col width="7%"><col width="7%">
					<col width="11%"><col width="7%"><col width="7%">
					<col width="11%"><col width="7%"><col width="7%">
					<col width="11%"><col width="7%"><col width="7%">
				</colgroup>
				<thead style="background-color: #847c7c;border-bottom: 1px solid #111;border-top: 1px solid #111;">
					<tr id="first" style="">
						<th colspan="3" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 2px solid #aaa;text-align: center;border-top: none;">서비스 Top 10</th>
						<th colspan="3" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 2px solid #aaa;text-align: center;border-top: none;">마지막 서비스 Top 10</th>
						<th colspan="3" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 2px solid #aaa;text-align: center;border-top: none;">상담원 연결 Top 10</th>
						<th colspan="3" style="vertical-align: middle;font-weight: normal;height: 30px;border-right: none;text-align: center;border-top: none;">Time-Out Top 10</th>
					</tr>
					<tr>
						<th style="width:106px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">서비스명</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">건수</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #aaa;border-top: 1px solid #928b8b;text-align: center;">증감<br/><span style="font-size:10px;">(전월대비)</span></th>
						<th style="width:106px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">서비스명</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">건수</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #aaa;border-top: 1px solid #928b8b;text-align: center;">증감<br/><span style="font-size:10px;">(전월대비)</span></th>
						<th style="width:106px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">서비스명</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">건수</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #aaa;border-top: 1px solid #928b8b;text-align: center;">증감<br/><span style="font-size:10px;">(전월대비)</span></th>
						<th style="width:106px;vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">거래명</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: 1px solid #928b8b;border-top: 1px solid #928b8b;text-align: center;">건수</th>
						<th style="vertical-align: middle;font-weight: normal;height: 30px;border-right: none;border-top: 1px solid #928b8b;text-align: center;">증감<br/><span style="font-size:10px;">(전월대비)</span></th>
					</tr>
				</thead>
				<tbody>
					<c:choose><c:when test="${srvc != null}"><c:forEach var="item" items="${srvc}" varStatus="status">
					<tr <c:if test="${status.index == 0}"> style="border-top: 1px solid #111;"</c:if>>
						<td style="max-width:100px;border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;" title="${item.SRVC_NM_1}">${item.SRVC_NM_1}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.CNT_1}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 2px solid #aaa;border-bottom: 1px solid #dddddd;color: ${item.COLOR_1};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.RT_1} %</td>
						<td style="max-width:100px;border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;" title="${item.SRVC_NM_2}">${item.SRVC_NM_2}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.CNT_2}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 2px solid #aaa;border-bottom: 1px solid #dddddd;color: ${item.COLOR_2};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.RT_2} %</td>
						<td style="max-width:100px;border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;" title="${item.SRVC_NM_3}">${item.SRVC_NM_3}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.CNT_3}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 2px solid #aaa;border-bottom: 1px solid #dddddd;color: ${item.COLOR_3};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.RT_3} %</td>
						<td style="max-width:100px;border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;" title="${item.SRVC_NM_4}">${item.SRVC_NM_4}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: 1px solid #dddddd;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;">${item.CNT_4}</td>
						<td style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-bottom: 1px solid #dddddd;color: ${item.COLOR_4};white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: right;border-right: none;">${item.RT_4} %</td>
					</tr>
					</c:forEach></c:when><c:otherwise><tr style="border-top: 1px solid #111;"><td colspan="12" style="border-spacing: 10px;vertical-align: middle;font-weight: normal;height: 36px;padding: 0 8px;border-right: none;border-bottom: 1px solid #dddddd;color: #333;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;text-align: center;">알림 발생 내역이 않습니다.</td></tr></c:otherwise></c:choose>
				</tbody>
			</table>
		</div>
		
		<p style="float: left;width: 964px;height: 20px;font-size: 14px;color: #222222;font-weight: 700;margin-bottom: 5px;padding-left: 3px;line-height: 16px;">서비스 비교현황</p>
		<div style="box-sizing: border-box;float: left;width: 964px;margin-bottom: 40px;">
			<div style="margin: 0;outline: 0;box-sizing: border-box;float: left;width: calc(50% - 15px);margin-right: 30px;border: 1px solid #ccc;padding: 15px;overflow-y: hidden;">
				<p style="font-size: 12px;font-weight: 500;width: 100%;margin-bottom: 10px;">당일 / 전일 Top -5 서비스</p>
				<div style="position:relative;width: 435px;height: 340px;">
					<img src="${webappRoot}/resources/report/${imageFolder}${srvcImage1}" style="width:435px;height:340px;"/>
				</div>
				<ul style="position:relative;padding:0px;margin:0px;list-style-type:none;width: 435px;text-align: center;font-size: 12px;font-family: dotum;line-height: 20px;height: 20px;">
					<li style="float:left;width:50%;text-align:right;padding:0px;margin:0px;">
						<span style="padding: 0;border: 0px;outline: 0;;background: transparent;display: inline-block;width: 10px;height: 10px;background-color:#ff6384;border-radius: 50%;margin-right: 2px;line-height: 12px;"></span>
						<span style="text-align: left;font-family: dotum;margin-right: 2px;line-height: 12px;color: #999;">당일</span>
					</li>
					<li style="float:right;width:49%;text-align:left;padding:0px;margin:0px;">
						<span style="padding: 0;border: 0px;outline: 0;;background: transparent;display: inline-block;width: 10px;height: 10px;background-color:#34a2eb;border-radius: 50%;margin-right: 2px;line-height: 12px;"></span>
						<span style="text-align: left;font-family: dotum;margin-right: 2px;line-height: 12px;color: #999;">전일</span>
					</li>
				</ul>
			</div>
			<div class="boxTwo" style="margin: 0;outline: 0;box-sizing: border-box;float: left;width: calc(50% - 15px);border: 1px solid #ccc;padding: 15px;overflow-y: hidden;margin-right: 0;">
				<p class="caption" style="font-size: 12px;font-weight: 500;width: 100%;margin-bottom: 10px;">당일 / 전주 평균 Top -5 서비스</p>
				<div style="position:relative;width: 435px;height: 340px;">
					<img src="${webappRoot}/resources/report/${imageFolder}${srvcImage2}" style="width:435px;height:340px;"/>
				</div>
				<ul style="position:relative;padding:0px;margin:0px;list-style-type:none;width: 435px;text-align: center;font-size: 12px;font-family: dotum;line-height: 20px;height: 20px;">
					<li style="float:left;width:50%;text-align:right;padding:0px;margin:0px;">
						<span style="padding: 0;border: 0px;outline: 0;;background: transparent;display: inline-block;width: 10px;height: 10px;background-color:#ff6384;border-radius: 50%;margin-right: 2px;line-height: 12px;"></span>
						<span style="text-align: left;font-family: dotum;margin-right: 2px;line-height: 12px;color: #999;">당일</span>
					</li>
					<li style="float:right;width:49%;text-align:left;padding:0px;margin:0px;">
						<span style="padding: 0;border: 0px;outline: 0;;background: transparent;display: inline-block;width: 10px;height: 10px;background-color:#34a2eb;border-radius: 50%;margin-right: 2px;line-height: 12px;"></span>
						<span style="text-align: left;font-family: dotum;margin-right: 2px;line-height: 12px;color: #999;">전주 평균</span>
					</li>
				</ul>
			</div>
		</div>

		<p style="float: left;width: 100%;height: 20px;font-size: 14px;color: #222222;font-weight: 700;margin-bottom: 5px;padding-left: 3px;line-height: 16px;">당일 서비스 현황 분포도</p>
		<div style="text-align:center;margin: 0;outline: 0;box-sizing: border-box;float: left;width: 100%;height:433px;border: 1px solid #ccc;padding: 15px 15px 15px 282px;margin-bottom: 0;">
			<ul style="position:relative;list-style-type:none;float: right;text-align: left;font-size: 12px;font-family: dotum;line-height: 20px;padding-right: 5px;height: 20px;">
				<c:forEach var="item" items="${srvcPieChart}" varStatus="status"><li>
					<span style="padding: 0;border: 0px;outline: 0;;background: transparent;display: inline-block;width: 10px;height: 10px;background-color: ${item.COLOR};border-radius: 50%;margin-right: 2px;line-height: 12px;"></span>
					<span style="text-align: left;font-family: dotum;margin-right: 2px;line-height: 12px;color: #999;" title="${item.CNT} 건">${item.SRVC_NM}</span>
				</li></c:forEach>
			</ul>
			<div style="position:relative;width: 400px;height: 400px;">
				<img src="${webappRoot}/resources/report/${imageFolder}${pieImage}" style="width:400px;height:400px;"/>
			</div>
		</div>
	</div>
</div> 