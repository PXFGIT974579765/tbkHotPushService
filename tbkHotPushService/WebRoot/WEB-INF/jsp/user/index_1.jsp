<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page errorPage="/WEB-INF/error.jsp" %>
 <%@ include file="../common/include.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>优品热推</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta name="language" content="zh-CN">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="优品，热推，淘宝，天猫，秒杀">
    <meta name="description" content="优品热推">
	<link href="<%=basePath%>css/mofang.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath%>css/index_1.css" rel="stylesheet" type="text/css" />
	
  </head>
<script type="text/javascript">
	function doSubmit() {
	    $("#loading-mask").fadeIn();
		var formParam = $("#login-form").serialize();
		$.ajax({
					data : formParam,
					type : "post",
					dataType : 'json',
					url : "user/login.do",
					cache : "true",
					error : function(data) {
					    $("#loading-mask").fadeOut();
						alert("出错了！！:" + json.msg);
					},
					success : function(json) {
					 $("#loading-mask").fadeOut();
						//将后端传过来的对象转换为json字符串
// 						var result = JSON.stringify(data.result);
// 						将json字符串转换为js对象
// 						var json = JSON.parse(result);
						if (json.code == 30003) {
							$("#notice").text(json.msg);
						} else if (json.code == 0) {
						    document.write("<form action='<%=basePath%>user/index.do' method='post' name='form1' style='display:none'>");  
                            document.write("<input type='hidden' name='token' value='"+json.token+"'/>");  
                            document.write("</form>");  
                            document.form1.submit();
						}
					}
				});
	}
	function initPage(page){
		if($("#pageToolbar").length!=0){
			$("#pageToolbar").remove();
		}
		$(".pages").append('<div id="pageToolbar"></div>');
	
		$('#pageToolbar').Paging(
				{
					pagesize:page.pageRows,
					count:page.rowsTotal,
					current:page.currentPage,
					toolbar:true,
					callback:function(currentPage,pageRows,pageTotal){ 
						queryByCondition(currentPage,pageRows,pageTotal);
		            }
				});
	}
	$(function(){
		initAdPage();
		var currentPage=$("#currentPage").val()*1;
		var pageTotal=$("#pageTotal").val()*1;
		var rowsTotal=$("#rowsTotal").val()*1;
		var pageRows=$("#pageRows").val()*1;
		var myPage=new Array();
		myPage.currentPage=currentPage;
		myPage.pageTotal=pageTotal;
		myPage.rowsTotal=rowsTotal;
		myPage.pageRows=pageRows;
		initPage(myPage);
	});
	function queryByCondition(currentPage,pageRows,pageTotal){
		$("#currentPage").val(currentPage);
		$("#pageTotal").val(pageTotal);
		$("#pageRows").val(pageRows);
		$("#loading-mask").fadeIn();
		 inputPrice();
		 var url='<%=basePath%>user/loadProducts.do';
		 $("#filterForm1").attr('action',url);
		 $("#filterForm1").submit();
		 $("#loading-mask").fadeOut();
	}
	function inputPrice(){
		var priceF=$("#price_1").val()*1;
		var priceT=$("#price_2").val()*1;
		if(priceF>=priceT){
			$("#price_2_2").val(priceF);
			$("#price_1_1").val(priceT);
		}
		if(priceF<=priceT){
			$("#price_2_2").val(priceT);
			$("#price_1_1").val(priceF);
		}
		if(priceF==0&&priceT==0){
			$("#price_2_2").val("");
			$("#price_1_1").val("");
		}
	}
	function ajaxSubmit(){
		inputPrice();
		queryByCondition(0,0,null);
	}
	function initAdPage(){
		var Page = (function() {
			var $navArrows = $( '#nav-arrows' ).hide(),
				$shadow = $( '#shadow' ).hide(),
				slicebox = $( '#sb-slider' ).slicebox( {
					onReady : function() {
						$navArrows.show();
						$shadow.show();
					},
					orientation : 'r',
					cuboidsRandom : true,
					disperseFactor : 30
				} ),
				init = function() {
					initEvents();
				},
				initEvents = function() {
					// add navigation events
					$navArrows.children( ':first' ).on( 'click', function() {
						slicebox.next();
						return false;
					} );
					$navArrows.children( ':last' ).on( 'click', function() {
						slicebox.previous();
						return false;
					} );
				};
				return { init : init };
		})();
		Page.init();
	}
	function addFavorite(){
		window.external.AddFavorite(location.href, document.title);
	}
</script>

<body>
<div id="header-user">
	<div id="header-user-navigator">
		<div class="navigator-c1 fl">你很难找到的优惠券</div>
		<ul class="navigator-c1 fr">
		    <li><a href="http://weibo.com/u/6024816711?topnav=1&wvr=6&topsug=1&is_all=1" target="_blank" >官方微博</a></li>
		     <li><a href="javascript:void(0);" >企鹅群:296681502</a></li>
<!-- 			<li><a href="javascript:void(0);" rel="favorite" onclick="window.external.AddFavorite('<%=basePath %>', '：：优品热推 ：：');" title="加入收藏，方便下次打开">加入收藏</a></li> -->
			<li><a class="call_me" target="_blank" href="tencent://message/?uin=1115370800">联系我们 </a></li>
		</ul>
	</div>
</div>
<div id="header-info-common">
	<div id="header">
		<a class="logo fl" href="<%=basePath%>"><img src="<%=basePath%>images/logo.png" data-bd-imgshare-binded="1"></a>
		<div class="header-search">
			<div class="search">
			   <form method="post" action="" id="filterForm1" name="filterForm1"  >
			        <input type="hidden" id="price_1_1" class="text" name="priceF" />
			        <input type="hidden" id="price_2_2"  class="text" name="priceT"/>
				    <input type="hidden" name="currentPage" id="currentPage" value="${currentPage}">
                    <input type="hidden" name="pageTotal" id="pageTotal" value="${pageTotal }">
                    <input type="hidden" name="rowsTotal" id="rowsTotal" value="${rowsTotal }">
                    <input type="hidden" name="pageRows" id="pageRows" value="${pageRows}">
                     <input type="hidden" name="admin" id="admin" value="${admin}">
					<input autocomplete="off" placeholder="输入关键词" class="input_key js-query" id="kw" name="productName" value="${productName }" type="text">
<!-- 					<input name="token" value="4nh71rca7olcffmsmdiurtm660" type="hidden"> -->
					<button rel="search" type="button" onclick="ajaxSubmit();">搜索</button>
				</form>
			</div>
			<div class="promise fr"></div>
		</div>
	</div>
</div>

<div id="nav-inner">
	<div id="nav">
		<a class="" href="javascript:void(0);">今日上新</a>
	</div>
</div>



<div id="layout">
<!-- 	<div class="search-wrap main-container"> -->
<!-- 	<div class="wrapper"> -->
<!-- 				<ul id="sb-slider" class="sb-slider"> -->
<!-- 					<li> -->
<!-- 						<img src="<%=basePath%>images/1.jpg" alt="image1"/> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<img src="<%=basePath%>images/2.jpg" alt="image2"/>< -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<img src="<%=basePath%>images/3.jpg" alt="image3"/> -->
<!-- 					</li> -->
<!-- 					<li> -->
<!-- 						<img src="<%=basePath%>images/4.jpg" alt="image4"/> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 				<div id="nav-arrows" class="nav-arrows"> -->
<!-- 					<a href="#">Next</a> -->
<!-- 					<a href="#">Previous</a> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 	</div> -->

	<form action="" method="get" onsubmit="return screen();" target="_self" data-pjax="true" id="filterform">
<!--     <div class="cat-wrap main-container"> -->
<!--         <div class="cat-list clearfix"> -->
<!--             <ul class="clearfix"> -->
<!--                 <li class=""><a href="javascript:void(0);">全部类别</a></li> -->
<!--                 <li class="active"><a href="">服饰鞋包</a></li> -->
<!--                 <li class=""><a href="">食品保健</a></li> -->
<!--                 <li class=""><a href="">家装家饰</a></li> -->
<!--              </ul> -->
<!--              <ul class="clearfix"> -->
<!--                 <li class=""><a href="javascript:void(0);">全部类别</a></li> -->
<!--                 <li class="active"><a href="">服饰鞋包</a></li> -->
<!--                 <li class=""><a href="">食品保健</a></li> -->
<!--                 <li class=""><a href="">家装家饰</a></li> -->
<!--              </ul> -->
<!--              <ul class="clearfix"> -->
<!--                 <li class=""><a href="javascript:void(0);">全部类别</a></li> -->
<!--                 <li class="active"><a href="">服饰鞋包</a></li> -->
<!--                 <li class=""><a href="">食品保健</a></li> -->
<!--                 <li class=""><a href="">家装家饰</a></li> -->
<!--              </ul> -->
<!--         </div> -->
<!--     </div> -->
    <div class="tag-wrap main-container clearfix">
        <div class="tags">
            <span class="text">条件搜索</span>
           <span class="text">价格：</span>
           <input type="text" id="price_1" class="text" name="price" value="${priceF}"/>~<input type="text" id="price_2"  class="text" name="price" value="${priceT}"/>元
        </div>
    </div>
    <input class="submit" type="submit">
</form>

	
<div class="goods-list main-container direct_dft">
		<ul class="clearfix">
		<c:forEach items="${products}" var="product" varStatus="status">
				<li class="goods-item">
				<a href="${product.tbkUrl }" class="img" target="_blank">
				<img src="${product.productImgUrl }">
				</a>
				<c:if test="${isAdmin eq 'yes' }">
				<div class="productIncome">${product.productIncome }</div>
				</c:if>
				<div class="padding">
					<a target="_blank" href="${product.tbkUrl }" class="title clearfix">
					<c:if test="${product.platCategory eq '天猫' }">
					      <i class="tmall"></i>
					</c:if>
					<c:if test="${product.platCategory eq '淘宝' }">
					      <i class="taobao"></i>
					</c:if>
					
					
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
	<div class="pages">
	</div>
</div>




<div id="footer">
	<div class="footer-wrapper">
		<img src="<%=basePath%>images/bottom.png" data-bd-imgshare-binded="1">
		<div class="text">
			  Copyright © 2016 &nbsp;优品热推 &nbsp;&nbsp;企鹅:1115370800</a>
		</div>
	</div>
</div>
    
</div>
<div id="loading-mask" style="position:absolute;top:0px; left:0px;display:none; width:100%; height:200%; background:#EDD8D8;filter:alpha(opacity=80);-moz-opacity:0.8; opacity:0.8; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-373px 0px 0px -54px; text-align:center; 
 border:1px solid #D75BC8; width:52px; height:52px;  font-size:14px;border-radius: 5px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="<%=basePath%>images/loading.gif" align="absmiddle" /> </div>
</div>  

  </body>
</html>
