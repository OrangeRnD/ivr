<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
String msg = null;
String url = (String)request.getAttribute("javax.servlet.error.url");
int status = response.getStatus();

if(status == 404)
	msg = "해당 페이지를 찾을 수 업습니다.";
else
	msg = (String)request.getAttribute("javax.servlet.error.message");

if(msg != null) {
	if(msg.indexOf("CannotRenderException") != -1)
		msg = "해당 페이지를 찾을 수 업습니다.";
	else 
		msg = "일시적으로 오류가 발생하였습니다. 잠시 후 다시 시도해 주십시요.";
} else {
	msg = "일시적으로 오류가 발생하였습니다. 잠시 후 다시 시도해 주십시요.";
}
if(url == null)
	url = "/";
%><%if(request.getMethod().equals("POST")) {%>{"status":<%=status%>,"message":error,"data":null,"size":0,"data_type":0}<% } else { %><script type="text/javascript">alert('<%=msg%>');window.location.href = '<%=request.getContextPath()%><%=url%>';</script><%}%>