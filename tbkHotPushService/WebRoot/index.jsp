<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript" src="<%=basePath%>js/jquery-1.7.1.min.js"></script>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
<script type="text/javascript">
  $(function(){
//    平台、设备和操作系统  
//      var system ={  
//        win : false,  
//        mac : false,  
//        xll : false  
//      };  

//  检测平台  

//      var p = navigator.platform;  
//      system.win = p.indexOf("Win") == 0;  
//      system.mac = p.indexOf("Mac") == 0;  
//      system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);  
//      跳转语句  

//      if(system.win||system.mac||system.xll){  
// 	       $("#form").attr('action','user/toIndex.do');  
//      }else{  
// 	       $("#form").attr('action','user/toApp.do'); 
//      }
     $("#form").attr('action','user/toIndex.do');  
     $("#form").submit();
	  
  });

</script>
  
  <body>
  <form action="" id="form"></form>
</html>
