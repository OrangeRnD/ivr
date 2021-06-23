<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ibk.ivr.ca.common.util.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	response.setHeader ("Pragma", "No-cache");
	response.setDateHeader ("Expires", 0);
	response.setHeader ("Cache-Control", "no-cache");
%>
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
	<script type="text/javascript" src="${webappRoot}/resources/lib/jquery/jquery-3.3.1.min.js"></script>
	<script type="text/javascript">
		(function($, window, document) {
			$(function() {
				$('#btn_mail').on('click', function() {
					if(!document.mail.to.value) {
						alert('주소를 입력하십시요.');
						return;
					}
					if(!document.mail.subject.value) {
						alert('제목를 입력하십시요.');
						return;
					}
					if(!document.mail.content.value) {
						alert('내용을 입력하십시요.');
						return;
					}
					var $frm = $(document.mail);
					$.ajax({
						type: 'post',
						data:$frm.serialize(),
						cache:false,
						url:'${webappRoot}/mail',
						dataType: 'json'
					}).done(function(data, textStatus, jqXHR) {
						alert('메일이 발송되었습니다.');
					}).fail(function(jqXHR, textStatus, errorThrown) {
						alert("오류가 발생하였습니다.");
					}).always(function(data, textStatus, jqXHR) {
					});
				});
			});
		}(window.jQuery, window, document));
	</script>
</head>
<body>
	<form name="mail">
		주소 : <input type="text" name="to" value="" placeholder="메일주소"/><br/>
		제목 : <input type="text" name="subject" value="" placeholder="제목"/><br/>
		내용 : <textarea name="content" rows="10" cols="100"></textarea><br/><br/><br/>
		<span id="btn_mail" style="cursor:pointer;line-height:30px;width:100px;border:1px solid #000;display:inline-block;text-align:center;background-color:#2e34a7;color:#f3eeee;">발송</span>
	</form>
</body>
</html>