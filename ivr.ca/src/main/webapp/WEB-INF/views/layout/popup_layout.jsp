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
	<div id="contentArea" style="width:100%;">
		<div class="boardBox">
			<tiles:insertAttribute name="content"/>
		</div>
	</div> 
	<div id="tooltips" class="tooltips"></div>
	<iframe id="temp" name="temp" src="about:blank;" style="display:none;"></iframe>
	<div id="loading" style="display:none;">
	  <img id="loading-image" src="${webappRoot}/resources/images/loader.gif" alt="Loading..." />
	</div>
</body>
</html>