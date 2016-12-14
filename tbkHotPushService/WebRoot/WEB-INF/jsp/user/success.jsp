<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page errorPage="/WEB-INF/error.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>云单追踪管理系统</title>
  </head>
	   
<%@ include file="../common/include.jsp" %>
<base href="<%=basePath%>">
<script charset="utf-8" language="javascript" type="text/javascript" src='<%=basePath%>easyui/easyui/js/accordion_load.js'> </script>
<script type="text/javascript">
var name='${user.name}';
var email='${user.email}';
var phone='${user.phone}';
var sex='${user.sex}';
function initDialog(){
	$("#btn_update_myInfo").css('display','none');
	 $("#sex[value="+sex+"]").attr("checked",true);
	 $('ul li input').dblclick(function(){
		 $(this).removeAttr("readonly");
		 $("#btn_update_myInfo").css('display','');
	  });
	 $(".box-bar-icon-close").click(function(){
		 $("#updateMyInfo").remove();
	 });
	 $("#btn_update_myInfo").click(function(){
		 if($("#myInfoForm").valid()){
			 $('#loading-mask').fadeIn();
			 var formParam = $("#myInfoForm").serialize();
			 formParam+='&token='+$("#token").val();
			 $.ajax({
				 data:formParam,
				 type:'post',
				 dataType:'json',
				 url : "user/updateMyInfo.do",
				 error:function(data){
					 $('#loading-mask').fadeOut();
					 jAlert('系统出错,修改失败','提示');
				 },
				 success : function(user) {
					 if (user!=null){
							jAlert('修改信息成功','提示');
							$('#loading-mask').fadeOut();
							name=user.userName;
							phone=user.phone;
							sex=user.sex;
							email=user.email;
							$("#updateMyInfo").remove();
						}else{
							jAlert(data.msg,'提示');
						}
					}
			 });
		 }
	 });
	 $("#myInfoForm").validate({
		 rules:{
			 userName:{
				 required:true,
				 maxlength:32
			 },
			 email:{
				 required:true,
				 maxlength:20,
				 email: true
			 },
			 phone:{
				 required:true,
				 rangelength:[5,11]
			 }
		 },
		 messages:{
			 userName:{
				 required:'姓名不能为空',
				 maxlength:'姓名长度不能超过32'
			 },
			 email:{
				 required:'邮箱不能为空',
				 maxlength:'邮箱长度不能超过20',
				 email:'邮箱不正确'
			 },
			 phone:{
				 required:'电话不能为空',
				 rangelength:'请输入正确的电话长度'
			 }
		 }
	 });
}
   $(function(){
	   $("#myName").hover(function(e){
			var x = -100;
		    var y = 20; 
			var sb=new StringBuffer();
			sb.append('<div class="center-box" id="myInfo">')
			.append('<div class="box-bar">')
			.append('<div class="box-bar-title">个人信息</div>')
			.append('</div>')
			.append('<ul>')
			.append('<li><span>角&nbsp;&nbsp;&nbsp;色：</span><i>').append('${user.roleName}').append(' </i></li>')
			.append('<li><span>姓&nbsp;&nbsp;&nbsp;名：</span><i>').append(name).append(' </i></li>')
			.append('<li><span>邮&nbsp;&nbsp;&nbsp;箱：</span><i>').append(email).append('</i></li>')
			.append('<li><span>电&nbsp;&nbsp;&nbsp;话：</span><i>').append(phone).append('</i></li>')
			.append('<li><span>性&nbsp;&nbsp;&nbsp;别：</span><i>').append(sex).append('</i></li>')
			.append('</ul>  ')
			.append('</div>');
			 $("body").append(sb.toString());
			 $("#myInfo").css({
				 "top":(e.pageY + y) + "px",
				 "left": (e.pageX + x) + "px"
			 });
		},function(e){
			$("#myInfo").remove();
		}).mousemove(function(e){
			$("#myInfo").css({
				 "top":(e.pageY + y) + "px",
				 "left": (e.pageX + x) + "px"
			 });
		}).click(function(e){
			if($("#updateMyInfo").length!=0){
				return;
			}
			var sb=new StringBuffer();
			sb
			.append('<div class="center-box" id="updateMyInfo">')
			.append('<div class="box-bar">')
			.append('<div class="box-bar-icon-close" style="font-size:20px">关闭</div> ')
			.append('<div class="box-bar-title" style="font-size:20px">个人信息</div>')
			.append('</div>')
			.append('<form name="myInfoForm" id="myInfoForm" >')
			.append('<ul>')
			.append('<li><span>角&nbsp;&nbsp;&nbsp;色：</span><i>').append('${user.roleName}').append(' </i></li>')
			.append('<li><small>*</small><span>姓&nbsp;&nbsp;&nbsp;名：</span><input type="text" name="userName" readonly value="').append(name).append('" /></li>')
			.append('<li><small>*</small><span>邮&nbsp;&nbsp;&nbsp;箱：</span><input type="text" name="email"  readonly  value="').append(email).append('" /></li>')
			.append('<li><small>*</small><span>电&nbsp;&nbsp;&nbsp;话：</span><input type="text" name="phone"  readonly value="').append(phone).append('" /></li>')
			.append('<li><small>*</small><span>性&nbsp;&nbsp;&nbsp;别：</span><input type="radio" name="sex" readonly id="sex" value="男" style="width:20px;display:inline-block;" /> 男' )
			.append('<input type="radio" name="sex" id="sex" readonly value="女" style="width:20px;display:inline-block;"/> 女').append('</li>')
			.append('<li><small>注：以上带有*的均可以修改，双击后面的列即可进入修改状态</small>')
			.append('</ul>  ')
			.append('</form>')
			.append('<div class="hint-in3" id="btn_update_myInfo">确定</div>')
			.append('</div>');
			 $("body").append(sb.toString());
			 $("#updateMyInfo").css({
				 "width":"500px",
				 "height":"300px",
				 "margin":"0 auto",
				 "top":"50%",
				 "left":"50%",
				 "margin-left":"-250px",
				 "margin-top":"-150px"
			 });
			 initDialog();
		});
	 
	  
   });
</script>
  
  <body class="easyui-layout" style="overflow-y: hidden"  fit="true"   scroll="no">
  <input type="hidden" value="${token }" name="token" id="token">
  <input type="hidden" value="${user.account }" name="account" id="account">
<noscript>
<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
</div></noscript>

<div id="loading-mask" style="position:relative;top:0px; left:0px; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center; 
 border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="http://localhost:8080/my-first-frame/easyui/easyui/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>

    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">欢迎您！
        <a href="javascript:void(0)" id="myName">${user.name }</a>
        <a href="javascript:void(0)" id="editpwd">修改密码</a> <a href="user/toLogin.do" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; "><img src="<%=basePath%>easyui/easyui/images/blocks.gif" width="20" height="20" align="absmiddle" /> 云单追踪管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">© &nbsp;此系统由腾云登峰提供技术支持     &nbsp;  QQ:974579765</div>
    </div>
    <div region="west" split="true"  title="导航菜单" style="width:180px;" id="west">
			<div id="nav">
		<!--  导航内容 -->
				
			</div>

    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<div title="首页" style="padding:20px;overflow:hidden; color:red; " >欢迎使用
		</div>
		</div>
    </div>
    
    
    <!--修改密码窗口-->
    <div class="box"></div>
    <div class="hint">
	    <div class="hint-in1">
		    <div class="hint2">修改密码</div>
		    <div class="hint3"></div>
	    </div>
	    <div class="login-info">
	        <span class="text-up">旧密码:</span>
	        <input name="userName" id="pswd" type="text" name="pswd" value="" class="login-input"/>
	    </div>
	    <div class="login-info">
	        <span class="text-up">新密码:</span>
	        <input name="userName" id="newPwd" type="text" name="newPwd" value="" class="login-input"/>
	    </div>
	    <div class="login-info">
	        <span class="text-up">再次输入密码:</span>
	        <input name="userName" id="newPwd1" type="text" name="newPwd1"  value="" class="login-input"/>
	    </div>
	     <div class="login-info">
	        <span class="text-up" id="notice"></span>
	    </div>
	    <div class="hint-in3" id="btn_update_pwd">确定</div>
    </div>

	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="close">关闭</div>
		<div id="closeall">全部关闭</div>
		<div id="closeother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="closeright">当前页右侧全部关闭</div>
		<div id="closeleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="exit">退出</div>
	</div>
</body>
</html>
