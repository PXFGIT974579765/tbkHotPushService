<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page errorPage="/WEB-INF/error.jsp" %>
 <%@ include file="../common/include.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    <title>优品热推</title>
    <meta name="description" content="优品热推">
    <meta name="keywords" content="优品，热推，淘宝，天猫，秒杀">
    <meta content="yes" name="apple-mobile-web-app-capable"><!-- 这meta的作用就是删除默认的苹果工具栏和菜单栏允许全屏模式浏览-->
    <meta name="apple-touch-fullscreen" content="yes"><!-- 开启对web app程序的支持  -->
    <meta content="telephone=no,email=no" name="format-detection"><!-- iPhone会自动把你这个文字加链接样式、并且点击这个数字还会自动拨号！telephone=no就忽略页面中的数字识别为电话号码 ,email=no 忽略识别邮箱 -->
    <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />
    <script type="text/javascript" src="<%=basePath%>iscroll/js/iscroll.js"></script>
    <script src="<%=basePath%>iscroll/js/index.js"></script>
    <link rel="stylesheet" href="<%=basePath%>iscroll/css/jquery.mmenu.all.css"/>
    <script src="<%=basePath%>iscroll/js/reset.js"></script>
    <script src="<%=basePath%>iscroll/js/bd.js"></script>
    <script src="<%=basePath%>iscroll/js/ellipsis.js"></script>
    <link rel="stylesheet" href="<%=basePath%>iscroll/css/reset.css"/>
    	<link href="<%=basePath%>css/mofang2.css" rel="stylesheet" type="text/css" />
  </head>
  <script type="text/javascript">
	function ajaxSubmit() {
		
		var el;
		var s
		el = document.getElementById('thelist');
		 var formParam = $("#filterForm").serialize();
		 $("#loading-mask").fadeIn();
		$.ajax({
			data:formParam,
			url:'<%=basePath%>user/ajaxLoadProduct.do',
			type:'post',
			dataType:'json',
			error:function(data){
				alert('系统出错');
			},
			success:function(page){
				 $("#loading-mask").fadeOut();
				var data=page.list;
				if(data!=null){
					s=createElements(data,page);
					$(el);
					$(el).children("li").remove();
					myScroll.refresh();	
					$(el).append(s);
					myScroll.refresh();	
				}
			}
		});
		
	}
	function StringBuffer() {
	    this.string = new Array();
	 }
	 StringBuffer.prototype.append = function (str) {
	     this.string.push(str);
	     return this;    //方便链式操作
	 };
	 StringBuffer.prototype.toString = function () {
	     return this.string.join("");
	 };
	$(function(){
		$('.ellipsis').ellipsis();
	});
		var myScroll,
		pullDownEl, pullDownOffset,
		pullUpEl, pullUpOffset,
		generatedCount = 0;

	function pullDownAction () {
		setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
			var el;
		    var elms;
			el = document.getElementById('thelist');
			 var formParam = $("#filterForm").serialize();
			
			$.ajax({
				data:formParam,
				url:'<%=basePath%>user/ajaxLoadProduct.do',
				type:'post',
				dataType:'json',
				error:function(data){
				},
				success:function(page){
					var data=page.list;
					if(data!=null){
						elms=createElements(data,page);
						$(el);
						$(el).children("li").remove();
						$(el).append(elms);
					}
				}
			});
			myScroll.refresh();		// Remember to refresh when contents are loaded (ie: on ajax completion)
		}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
	}
  
	function pullUpAction () {
		setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
			var el;
		    var elms;
			el = document.getElementById('thelist');
			 var formParam = $("#filterForm").serialize();
			 formParam+='&currentPage='+$("#currentPage").val();
			 formParam+='&pageRows='+$("#pageRows").val();
			
			$.ajax({
				data:formParam,
				url:'<%=basePath%>user/ajaxLoadProduct.do',
				type:'post',
				dataType:'json',
				error:function(data){
				},
				success:function(page){
					var data=page.list;
					if(data!=null){
						elms=createElements(data,page);
						$(el);
						$(el).append(elms);
					}
				}
			});
			
			myScroll.refresh();		// Remember to refresh when contents are loaded (ie: on ajax completion)
		}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
	}
	  function createElements(product,page){
	    	var s="";
	    	$("#currentPage").val((page.currentPage)*1+1);
	    	$("#pageRows").val(page.pageRows);
	    	
	    	for(var i=0;i<product.length;i++){
	    		s+='<li class="goods-item">';
		    	s+='	<a href="'+product[i].tbkUrl+' " class="img" target="_blank">';
		    	s+='	<img src="'+product[i].productImgUrl+' ">';
		    	s+='	</a>';
		    	s+='	<div class="padding">';
		    	s+='		<a target="_blank" href="'+product[i].tbkUrl+' " class="title clearfix">';
		    	if(product[i].platCategory=='天猫'){
		    	    s+='<i class="tmall"></i>';
		    	}
		    	if(product[i].platCategory=='淘宝'){
		    	  s+='<i class="taobao"></i>';
		    	}
		    	s+='		<span>'+product[i].productName+' </span>';
		    	s+='		</a>';
		    	s+='		<div class="coupon-wrap clearfix">';
		    	s+='			 <span class="price">'+product[i].couponDetails+' </span>原价￥<span class="num">'+product[i].productPrice+'  </span>元';
		    	s+='		</div>';
		    	s+='	</div>';
		    	s+='	<div class="price-wrap clearfix">';
		    	s+='		<div class="price">';
		    	s+='			<a href="'+product[i].couponTgUrl+' " class="btn" target="_blank">领券</a>';
		    	s+='			<a href="'+product[i].tbkUrl+' " class="btn" target="_blank">购买</a>';
		    	s+='		</div>';
		    	s+='	</div>';
		    	s+='	</li>';
	    	}
	    	return s;
	    }

	function loaded() {
		pullDownEl = document.getElementById('pullDown');
		pullDownOffset = pullDownEl.offsetHeight;
		pullUpEl = document.getElementById('pullUp');	
		pullUpOffset = pullUpEl.offsetHeight;
		
		myScroll = new iScroll('wrapper', {
			scrollbarClass: 'myScrollbar', /* 重要样式 */
			useTransition: false, /* 此属性不知用意，本人从true改为false */
			click:true,
			taps:true,
			mouseWheel: true,
			topOffset: pullDownOffset,
			onRefresh: function () {
				if (pullDownEl.className.match('loading')) {
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
				} else if (pullUpEl.className.match('loading')) {
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				}
			},
			onScrollMove: function () {
				if (this.y > 5 && !pullDownEl.className.match('flip')) {
					console.log(1);
					pullDownEl.className = 'flip';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
					this.minScrollY = 0;
				} else if (this.y < 5 && pullDownEl.className.match('flip')) {
					console.log(2);
					pullDownEl.className = '';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
					this.minScrollY = -pullDownOffset;
				} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
					console.log(3);
					pullUpEl.className = 'flip';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
					this.maxScrollY = this.maxScrollY;
				} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
					console.log(4);
					pullUpEl.className = '';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
					this.maxScrollY = pullUpOffset;
				}
			},
			onScrollEnd: function () {
				if (pullDownEl.className.match('flip')) {
					pullDownEl.className = 'loading';
					pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
					pullDownAction();	// Execute custom function (ajax call?)
				} else if (pullUpEl.className.match('flip')) {
					pullUpEl.className = 'loading';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';				
					pullUpAction();	// Execute custom function (ajax call?)
				}
			}
		});
		
		setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
	}

	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
</script>
<style type="text/css" media="all">
body,ul,li {
	padding:0;
	margin:0;
	border:0;
}

body {
	font-size:12px;
	-webkit-user-select:none;
    -webkit-text-size-adjust:none;
	font-family:helvetica;
}

#header {
	position:absolute; z-index:2;
	top:0; left:0;
	width:100%;
	height:45px;
	line-height:45px;
    padding:0;
	color:#eee;
	font-size:20px;
	text-align:center;
}

#header a {
	color: #f3f3f3;
    text-decoration: none;
    font-weight: bold;
    text-shadow: 0 -1px 0 rgba(0,0,0,0.5);
    z-index: 222;
    display: inline-block;
    width: 1rem;
    vertical-align: middle;
    margin-left: -1rem;
}

#footer {
	position:absolute; z-index:2;
	bottom:0; left:0;
	width:100%;
	height: 1rem;
	line-height: 1rem;
	background-color: #EF0D8D;
	padding:0;
	color: white;
    text-align: center;
    border-radius: 0.1rem 0.1rem 0 0;
}

#footer a{
   font-size: 0.49rem;
}

#wrapper {
	position:absolute; z-index:1;
	top:0.9rem; bottom:1rem; left:-9999px;
	width:100%;
	background:#fff;
	overflow:auto;
}

#scroller {
	position:absolute; z-index:1;
/*	-webkit-touch-callout:none;*/
	-webkit-tap-highlight-color:rgba(0,0,0,0);
	width:100%;
	padding:0;
}


#myFrame {
	position:absolute;
	top:0; left:0;
}
.hto-title3 a {
    display: inline-block;
}



/**
 *
 * Pull down styles
 *
 */
#pullDown, #pullUp {
	background:#fff;
	height:40px;
	line-height:40px;
	padding:5px 10px;
	border-bottom:1px solid #ccc;
	font-weight:bold;
	font-size:14px;
	color:#888;
}
#pullDown .pullDownIcon, #pullUp .pullUpIcon  {
	display:block; float:left;
	width:40px; height:40px;
	background:url(pull-icon@2x.png) 0 0 no-repeat;
	-webkit-background-size:40px 80px; background-size:40px 80px;
	-webkit-transition-property:-webkit-transform;
	-webkit-transition-duration:250ms;	
}
#pullDown .pullDownIcon {
	-webkit-transform:rotate(0deg) translateZ(0);
}
#pullUp .pullUpIcon  {
	-webkit-transform:rotate(-180deg) translateZ(0);
}

#pullDown.flip .pullDownIcon {
	-webkit-transform:rotate(-180deg) translateZ(0);
}

#pullUp.flip .pullUpIcon {
	-webkit-transform:rotate(0deg) translateZ(0);
}
.coupon-wrap {
    margin: 0rem 0;
    padding-left: 0rem;
}
.coupon-wrap .price {
    color: #f15482;
    font-weight: bold;
    font-size: 0.36rem;
}
.coupon-wrap .num {
    color: #f15482;
    text-decoration: line-through;
    font-size: 0.4rem;
}

#pullDown.loading .pullDownIcon, #pullUp.loading .pullUpIcon {
	background-position:0 100%;
	-webkit-transform:rotate(0deg) translateZ(0);
	-webkit-transition-duration:0ms;

	-webkit-animation-name:loading;
	-webkit-animation-duration:2s;
	-webkit-animation-iteration-count:infinite;
	-webkit-animation-timing-function:linear;
}

@-webkit-keyframes loading {
	from { -webkit-transform:rotate(0deg) translateZ(0); }
	to { -webkit-transform:rotate(360deg) translateZ(0); }
}

</style>
<body>
 <input type="hidden" value="${currentPage+1 }" name="currentPage"  id="currentPage"/>
<input type="hidden" value="${pageRows }" name="pageRows" id="pageRows"/>
<div id="header">
<form action="" id="filterForm" name="filterForm" >
<div class="mm-search">
<input placeholder="请输入产品关键词" autocomplete="off" name="productName" type="text"><a href="javascript:void(0);" onclick="ajaxSubmit();"><img src="<%=basePath%>iscroll/images/seaicon.png"></img></a>
</div>
</form>
</div>
<div id="wrapper" style="overflow: hidden; left: 0px;">
	<div id="scroller" class="hot-content p-l-15 p-r-15 m-t-30 m-b-25 goods-list main-container direct_dft" >
		<div id="pullDown">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
		</div>

		<ul id="thelist" class="clearFloat">
		<c:forEach items="${products}" var="product" varStatus="status">
<!-- 						<li> -->
<!-- 							<div class="hto-img"> -->
<!-- 								<a href="${product.tbkUrl }" target="_blank"> -->
<!-- 									<div class="hto-img-content m-r-18"> -->
<!-- 										<img src="${product.productImgUrl }" alt="" /> -->
<!-- 									</div> -->
<!-- 								</a> -->
<!-- 								<div class="hto-p"> -->
<!-- 								   <a href="${product.tbkUrl }" target="_blank"> -->
<!-- 									<h3 class="hto-ph3 f30">${product.productName }</h3></a> -->
<!-- 									<div class="coupon-wrap clearfix"> -->
<!-- 										<span class="price">${product.couponDetails }</span>，原价￥<span -->
<!-- 											class="num">${product.productPrice } </span>元 -->
<!-- 									</div> -->
<!-- 									<p class="hto-title3 clearFloat"> -->
<!-- 										<a href="${product.couponTgUrl }" target="_blank">  -->
<!-- 										<input  class="adicon" value="领券" type="button"/ > -->
<!-- 										</a> -->
<!-- 										<a href="${product.tbkUrl }" target="_blank">  -->
<!-- 										<input  class="adicon" value="购买" type="button"/ > -->
<!-- 										</a> -->
<!-- 									</p> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 					    </li> -->

              <li class="goods-item">
				<a href="${product.tbkUrl }" class="img" target="_blank">
				<img src="${product.productImgUrl }">
				</a>
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
						 <span class="price">${product.couponDetails }</span>原价￥<span class="num">${product.productPrice } </span>元
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
		</ul>
		<div id="pullUp">
			<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
		</div>
	</div>
<div style="position: absolute; z-index: 100; width: 7px; bottom: 2px; top: 2px; right: 1px; pointer-events: none; transition-property: opacity; overflow: hidden; opacity: 1;"><div style="position: absolute; z-index: 100; background: rgba(0, 0, 0, 0.5) none repeat scroll 0% 0% padding-box; border: 1px solid rgba(255, 255, 255, 0.9); box-sizing: border-box; width: 100%; border-radius: 3px; pointer-events: none; transition-property: transform; transform: translate(0px, 15.2589px) translateZ(0px); transition-timing-function: cubic-bezier(0.33, 0.66, 0.66, 1); height: 167px; transition-duration: 200ms;"></div></div></div>
<div id="footer"><a href="http://weibo.com/u/6024816711?topnav=1&wvr=6&topsug=1&is_all=1" target="_blank">二师兄挑白菜</a>
</div>

<div id="loading-mask" style="position:absolute;top:0px; left:0px;display:none; width:100%; height:200%; background:#EDD8D8;filter:alpha(opacity=80);-moz-opacity:0.8; opacity:0.8; z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-373px 0px 0px -54px; text-align:center; 
 border:1px solid #D75BC8; width:52px; height:52px;  font-size:14px;border-radius: 5px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="<%=basePath%>images/loading.gif" align="absmiddle" /> </div>
</div>  
</body>


</html>
