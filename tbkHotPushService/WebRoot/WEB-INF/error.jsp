<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>错误提示页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    系统出错，请联系管理员
    <br>
    <hr size="3"  color="red" align="left">
    错误信息：<br><%=exception.getMessage() %>
    <br><div style='text-align: center;width:100%'><a href='#' style='color:#06b5ff;' onmouseover="this.style.cssText='color:gray;'"
    onmouseout="this.style.cssText='color:#06b5ff;'" >返回登录页面>></a></div>
  </body>
</html>
