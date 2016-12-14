<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-cn" />
<link href="<%=basePath%>easyui/easyui/css/default.css" rel="stylesheet"
	type="text/css" />


<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/paging.css" />

<!--  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/demo.css" /> -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/slicebox.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/custom.css" />
		
<script charset="utf-8" language="javascript" type="text/javascript"
	src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/modernizr.custom.46884.js"></script>

<!-- 分页 -->
<script charset="utf-8" language="javascript" type="text/javascript"
	src='<%=basePath%>js/query.js'>
</script>
<script charset="utf-8" language="javascript" type="text/javascript"
	src='<%=basePath%>js/paging.js'>
</script>
<script charset="utf-8" language="javascript" type="text/javascript"
	src='<%=basePath%>js/require.js'>
</script>
<script type="text/javascript" src="<%=basePath%>js/jquery.slicebox.js"></script>


