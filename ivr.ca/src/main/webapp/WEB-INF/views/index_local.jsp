<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ibk.ivr.ca.common.util.DateUtil"%>
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
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/reset.css" >
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/fonts.css" />
	<link rel="stylesheet" type="text/css" href="${webappRoot}/resources/css/login.css" >
	<script type="text/javascript" src="${webappRoot}/resources/lib/jquery/jquery-3.3.1.min.js"></script>
	<script type="text/javascript">
		function activex_error() {
			if(confirm("업무인증 Client 프로그램이 설치되어 있지 않습니다. 업무인증 Client 프로그램 설치-> 업무인증 로그인 후 다시 시도하여 주십시오. 프로그램 수동 설치 화면으로 이동하시겠습니까?"))
				self.location.replace("http://sso.ibk.co.kr/nls3/NCInstall.html");
		}
	</script>

	<!--NC클라이언트 오브젝트 ID-->
	<OBJECT ID="NEXESS_API" CLASSID="CLSID:D4F62B67-8BA3-4A8D-94F6-777A015DB612" onError="activex_error();" width=0 height=0> </OBJECT>
	<script type="text/javascript">
		if(NEXESS_API.nodeName == 'OBJECT') {
			if(!NEXESS_API.GetTicketAppWithEnc) {
				NEXESS_API = {
					GetTicketAppWithEnc:function(APP_ID) {
						return 'test&&test';
					},
					APIOpenLogin:function(url, ticket) {
						
					},
					GetExtentionalField:function(id, field) {
						return "kimyu@m8.co.kr";
					}
				};
			}	
		}
	
		var b = false;
		
		//업무인증 Ticket 추출 및 APPJD 접근 이력 업데이트
		if(NEXESS_API) {
			b = true;
			
			var ticket = NEXESS_API.GetTicketAppWithEnc('TBKCDA001');//업무코드 확인 필요
			//NC 클라이언트에서 인증 정보를 가져옴
			//용답코드 성공: 인증티켓, 실패: 2: 로그인 전 상태, 3: 중복 로그인, 4: 권한이 없음
			if(ticket.length == 1) {
				if(ticket == 2) {
					alert("업무인증 시스템에 로그인 되어 있지 않습니다. 업무인증 로그인 후 재접속하여 주십시오");
				} else if(ticket == 3) {
					alert("업무인증 로그인 정보가 유효하지 않습니다. 업무인증 로그인 후 재접속하여 주십시오");
				} else if(ticket == 4) {
					alert("업무인증 연동 로그인 권한이 없습니다.");
				} else {
					b = false;
				}
				//업무 시스템 로그인 페이지로 이동(?)
				NEXESS_API.APIOpenLogin('', ticket);
				//로그인 상태가 아니므로 인증창 호출후 해당 주소로 이동
				noConfirmClose();
			} else {
				while(ticket.indexOf("+") != -1) {
					ticket = ticket.replace("+", "%2b");
				}
				//alert(ticket);
				//self.location.replace("./NCLoginTestEncVerifyTicket.jsp?ticket="+ escape(ticket));
				//인증티켓 유효성 검증 및 업무인증 ID 획득 페이지로 이동

				$.ajax({
					type: 'post',
					data:'ticket=' + escape(ticket),
					cache:false,
					url:'${webappRoot}/lgn/sso',
					dataType: 'json',
					beforeSend:function() {
						$('#loading').show();
					}
				}).done(function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						/*$('#loading').show();
						var email = NEXESS_API.GetExtentionalField(data.data.usrId, "email");
						$.ajax({
							type: 'post',
							data:'email=' + email,
							cache:false,
							url:'${webappRoot}/system/usr/updatesso',
							dataType: 'json'
						}).always(function(data, textStatus, jqXHR) {
							$('#loading').hide();
						});*/
						window.location.href = '${webappRoot}/';
					} else if(data.status == 404) {
						alert("고객성향분석 시스템 사용 권한이 없습니다.");
						noConfirmClose();
					} else {
						alert("오류가 발생하여 접속이 종료됩니다.");
						noConfirmClose();
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);
					alert("오류가 발생하여 접속이 종료됩니다.");
					noConfirmClose();
				}).always(function(data, textStatus, jqXHR) {
					$('#loading').hide();
				});
			}
		}
		
		//브라우저 창 닫기 참고 코드
		function noConfirmClose() {
			parent.window.open('about:blank', '_self').close();
			parent.window.opener=self;
			parent.self.close();
		}
		
		function login() {
			var _id = document.getElementById('empNr');
			if(!_id.value) {
				alert('직원번호를 입력하십시요.');
				document.getElementById("btn_login").removeAttribute('disabled');
				_id.focus();
				return;
			}
			var _pwd = document.getElementById('pswd');
			if(!_pwd.value) {
				alert('OTP를 입력하십시요.');
				document.getElementById("btn_login").removeAttribute('disabled');
				_pwd.focus();
				return;
			}

			$('#loading').show();
			$.ajax({
		        type: 'post',
		        contentType: 'application/json; charset=utf-8;',
		        data: JSON.stringify({empNr:_id.value, pswd:_pwd.value}),
				cache:false,
				dataType: 'json',
				url:'${webappRoot}/otp'
			}).done(function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					window.location.href = '${webappRoot}/';
				} else if(data.status == 404) {
					alert("직원번호가 맞지 않습니다.");
				} else if(data.status == 406) {
					if(data.message) alert(data.message);
					else alert("OTP 생성 오류.");
				} else {
					alert("시스템 접속에 오류가 발생하고 있습니다. 잠시 후 다시 시도해 주십시요.");
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR);
				alert("시스템 접속에 오류가 발생하고 있습니다. 잠시 후 다시 시도해 주십시요.");
			}).always(function(data, textStatus, jqXHR) {
				document.getElementById("btn_login").removeAttribute('disabled');
				$('#loading').hide();
			});	
		}
		
		(function($, window, document) {
			$(function() {
				$('#btn_login').on('click', function(e) {
		 			e.target.setAttribute('disabled', true);
		 			login();
		        });
				
				$('#pswd').on('keydown', function(e) {
					var code = e.keyCode || e.charCode;
		            if(code.keyCode == 13) {
		            	$('#btn_login').attr('disabled', true);
		     			login();	
		            }
		        });
		        document.getElementById('empNr').focus();
			});
		}(window.jQuery, window, document));
	</script>
</head>
<body>
	<div id="loginarea">
		<div class="loginbox">
			<div class="inputarea">
				<p><input type="text" name="empNr" id="empNr" title="직원번호"  class="inputidpw" placeholder="직원번호"></p>
				<p><input type="password" name="pswd" id="pswd" title="OTP"  class="inputidpw" placeholder="OTP"></p>
				<p><a class="loginbtn" id="btn_login">LOGIN</a></p>
			</div>
			<div class="disc">
				<p class="txt">서비스를 이용하기 위해서는 로그인을 하셔야 합니다.
			</div>
		</div>
	</div>
	<div id="loading" style="display:none;">
	  <img id="loading-image" src="${pageContext.request.contextPath}/resources/images/loader.gif" alt="Loading..." />
	</div>
</body>
</html>