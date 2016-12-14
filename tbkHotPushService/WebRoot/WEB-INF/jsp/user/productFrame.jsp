<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page errorPage="/WEB-INF/error.jsp" %>
 <%@ include file="../common/include.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>优品热推</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="language" content="zh-CN">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="优品，热推，淘宝，天猫，秒杀">
    <meta name="description" content="优品热推">
	<link href="<%=basePath%>css/mofang.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/index_1.css" rel="stylesheet" type="text/css" />
	
  </head>
<script type="text/javascript">
  function openLoginForm(){
       $("#login-content").fadeIn();  
   }
   function closeLoginForm(){
       $("#login-content").fadeOut();  
   }
	$(function(){
		var currentPage=$("#pageRows").val();
		var pageTotal=$("#pageTotal").val();
		var rowsTotal=$("#rowsTotal").val();
		var pageRows=$("#pageRows").val();
		var page=new Array();
		page.currentPage=currentPage;
		page.pageTotal=pageTotal;
		page.rowsTotal=rowsTotal;
// 		page.pageRows=pageRows;
		window.parent.initPage(page);
	});
	
</script>

<body>


<div id="layout">
<input type="hidden" name="currentPage" id="currentPage" value="${currentPage}">
<input type="hidden" name="pageTotal" id="pageTotal" value="${pageTotal }">
<input type="hidden" name="rowsTotal" id="rowsTotal" value="${rowsTotal }">
<input type="hidden" name="pageRows" id="pageRows" value="${pageRows}">
<div class="goods-list main-container direct_dft">
		<ul class="clearfix">
		<c:forEach items="${products}" var="product" varStatus="status">
							<li class="goods-item">
				<a href="${product.tbkUrl }" class="img" target="_blank">
				<img src="${product.productImgUrl }">
				</a>
				<div class="padding">
					<a target="_blank" href="http://try.shihuizhu.com/item/72302" class="title clearfix">
					<i class="tmall"></i>
					<span>${product.productName }</span>
					</a>
					<div class="coupon-wrap clearfix">
						 <span class="price">${product.couponDetails }</span>，原价￥<span class="num">${product.productPrice } </span>元
					</div>
				</div>
				<div class="price-wrap clearfix">
					<div class="price">
						<a href="${product.couponTgUrl }" class="btn" target="_blank">领券</a>
						<a href="${product.tbkUrl }" class="btn" target="_blank">购买</a>
					</div>
				</div>
				</li>
		</c:forEach>				
	</div>
	
</div>

  </body>
</html>
