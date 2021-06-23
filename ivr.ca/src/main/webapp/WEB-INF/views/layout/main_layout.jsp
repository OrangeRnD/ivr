<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ibk.ivr.ca.common.util.DateUtil"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	response.setHeader ("Pragma", "No-cache");
	response.setDateHeader ("Expires", 0);
	response.setHeader ("Cache-Control", "no-cache");
	String version = DateUtil.getDateString("yyyyMMddHH");
%>
<c:set var="version" value="<%=version%>" scope="request"></c:set>
<c:set var="requestURI" value="<%=response.getHeader(\"Request-URI\")%>" scope="request"></c:set>
<c:set var="webappRoot" value="<%=pageContext.getServletContext().getContextPath()%>" scope="request"></c:set>
<!DOCTYPE html>
<html lang="ko" class=" js  no-overflowscrolling">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="pragma" content="no-cache">
	<title>고객성향분석</title>
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/reset.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/fonts.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/layout.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/style.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/contents.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/app.css?v=${version}" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/lib/datepicker/bootstrap-datepicker.standalone.min.css" />
	<script type="text/javascript" src="${webappRoot}/resources/lib/jquery/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="${webappRoot}/resources/lib/jquery/jquery.sortElements.js"></script>
	<script type="text/javascript" src="${webappRoot}/resources/lib/chartjs/Chart.2.8.0.min.js"></script>
	<script type="text/javascript" src="${webappRoot}/resources/lib/chartjs/chartjs-plugin-labels.min.js"></script>
	<script type="text/javascript" src="${webappRoot}/resources/lib/datepicker/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="${webappRoot}/resources/lib/datepicker/bootstrap-datepicker.ko.min.js"></script>
	<script type="text/javascript" src="${webappRoot}/resources/js/common.js?v=${version}"></script>
	<script type="text/javascript" src="${webappRoot}/resources/js/utils.js?v=${version}"></script>
	<script type="text/javascript" src="${webappRoot}/resources/js/app.js?v=${version}"></script>
	<script type="text/javascript" src="${webappRoot}/resources/js/ca/<tiles:getAsString name="js"/>.js?v=${version}"></script>
	<script type="text/javascript">app.constants.webappRoot= '${webappRoot}';</script>
</head>
<body>
	<!--header-->
	<div id="header">
		<div class="logo" id="ico_logo">
			<img src="${webappRoot}/resources/images/deotis_logo.jpg">
		</div>
		
		<div class="gnbTop">
			<div class="gnbLeft">
				<a class="btnMenu" id="btn_menu">menu</a>
				<div class="location" id="location">
					<!-- <span>홈</span>
					<span>데이터분석</span>
					<span>이상거래 분석</span> -->
				</div>
			</div>
			<div class="gnbRight">
				<span class="name">${session.empNm}</span><span class="txt">님</span>
				<!-- <a class="setting">정보수정</a> -->
				<a class="logout" id="logout">로그아웃</a>
			</div>
		</div>
	</div> 
	<!--header끝-->

	<div id="contentArea">
		<div class="boardBox">
			<tiles:insertAttribute name="content"/>
		</div>
	</div> 

	<!-- leftmenu 시작-->
	<div id="mainNav">
		<div class="lnbmenu">
			<ul class="sidebar-menu" id="sidebar-menu">
				<li class="treeview ">
					<a id="mn_ldin">인입현황</a>
					<ul class="treeview-menu">
						<li id="mn_ldin_status"><a href="${webappRoot}/ldin/status">실시간텔레뱅킹현황</a></li>
						<li id="mn_ldin_progress"><a href="${webappRoot}/ldin/progress">인입추이(일별,월별)</a>
						<li id="mn_ldin_analysis"><a href="${webappRoot}/ldin/analysis">실시간인입추이비교</a></li>
						<li id="mn_ldin_policy_result"><a href="${webappRoot}/ldin/policy/result">알림발생현황관리</a></li>
						<li id="mn_ldin_callstatus"><a href="${webappRoot}/ldin/callstatus">인입총괄현황</a></li>
						<li id="mn_ldin_ars"><a href="${webappRoot}/ldin/ars">ARS유형별이용현황</a></li>
						<li id="mn_ldin_speakars"><a href="${webappRoot}/ldin/speakars">말로하는ARS유형별이용현황</a></li>
						<li id="mn_ldin_callanalysis"><a href="${webappRoot}/ldin/callanalysis">대표번호이용현황</a>
					</ul>
				</li>
				<li class="treeview ">
					<a id="mn_use">이용현황</a>
					<ul class="treeview-menu">
						<li id="mn_use_tranalysis"><a href="${webappRoot}/use/tranalysis">실시간거래발생비교</a></li>
						<li id="mn_use_erranalysis"><a href="${webappRoot}/use/erranalysis">실시간에러발생비교</a></li>
						<li id="mn_use_status"><a href="${webappRoot}/use/status">거래,에러현황조회</a></li>
						<li id="mn_use_service"><a href="${webappRoot}/use/service">자주쓰는서비스이용현황</a></li>
						<li id="mn_use_serviceanalysis"><a href="${webappRoot}/use/serviceanalysis">전체서비스현황조회</a></li>
						<li id="mn_use_call"><a href="${webappRoot}/use/call">고객콜검색</a></li>
						<li id="mn_use_tr"><a href="${webappRoot}/use/tr">거래검색</a></li>
					</ul>
				</li>
				<li class="treeview ">
					<a id="mn_analysis">유형분석</a>
					<ul class="treeview-menu">
						<li id="mn_analysis_pattern"><a href="${webappRoot}/analysis/pattern">서비스이용고객패턴분석</a></li>
						<li id="mn_analysis_error"><a href="${webappRoot}/analysis/error">에러코드유형분석</a></li>
					</ul>
				</li>
				<li class="treeview ">
					<a id="mn_data">데이터분석</a>
					<ul class="treeview-menu">
						<li id="mn_data_overtr"><a href="${webappRoot}/data/overtr">이상현상분석</a></li>
						<li id="mn_data_tranalysis"><a href="${webappRoot}/data/tranalysis">거래발생일변동량분석</a>
					</ul>
				</li>
				<c:if test="${session.authyGrpCd == '1'}"><li class="treeview ">
					<a id="mn_system">시스템관리</a>
					<ul class="treeview-menu">
						<li id="mn_system_policy"><a href="${webappRoot}/system/policy">알림관리</a></li>
						<li id="mn_system_report"><a href="${webappRoot}/system/report">일일보고서</a></li>
						<li id="mn_system_usr"><a href="${webappRoot}/system/usr">사용자관리</a></li>
						<%-- <li id="mn_system_authygrpmenu"><a href="${webappRoot}/system/authygrpmenu">메뉴별권한</a></li> --%>
						<li id="mn_system_cdclsfctn"><a href="${webappRoot}/system/cdclsfctn">코드관리</a></li>
						<li id="mn_system_srvccd"><a href="${webappRoot}/system/srvccd">서비스코드</a></li>
						<li id="mn_system_trcd"><a href="${webappRoot}/system/trcd">거래코드</a></li>
						<li id="mn_system_errcd"><a href="${webappRoot}/system/errcd">에러코드</a></li>
						<li id="mn_system_lgnhist"><a href="${webappRoot}/system/lgnhist">로그인이력</a></li>
					</ul>
				</li></c:if>
			</ul>
		</div>
		<%-- <div  class="btnGuide"><a href="${webappRoot}">이용가이드 다운로드</a></div>
		<div class="viewDisc">
			<p class="discTit">화면설명</p>
			<p class="discTxt">회귀분석을 응용하여 인입콜의 추이, 서비스 이용의 추이, 에러코드 발생의 추이를 소위 분석 예측변인으로 잡고, 각각 그 Bounce의 정도가 높은 상황을 탐지하는 할 수 있는 화면<br><br> 즉 정규분포를 벗어나는 거래패턴에 대한 탐지를 하여 상황 알림<br><br>특정패턴(이상거래) 내역에 대한 발생 내역과 정책설정 내역</p>
		</div> --%>
	</div>
	<div id="tooltips" class="tooltips"></div>
<!--leftmenu 끝-->
	<iframe id="temp" name="temp" src="about:blank;" style="display:none;"></iframe>
	<div id="loading" style="display:none;">
	  <img id="loading-image" src="${webappRoot}/resources/images/loader.gif" alt="Loading..." />
	</div>
</body>
</html>