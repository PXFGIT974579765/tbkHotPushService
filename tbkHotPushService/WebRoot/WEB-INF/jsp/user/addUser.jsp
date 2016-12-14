<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css"href="<%=basePath%>css/addUser.css" />
   <base href="<%=basePath%>">
   
  
  <body>
  <input type="hidden" value="" name="token" id="token">
    <div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center; 
 border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="http://localhost:8080/my-first-frame/easyui/easyui/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>
<div class="content">
<!-- 上半部分 -->
   <div class="content-up">
      <form name="basicInfoForm" id="basicInfoForm">
      <div class="col">
          <label for="userName" class="error"></label>
          <span>姓名:</span>
          <input name="userName" id="userName"><i>*</i>
      </div>
      <div class="col">
          <label for="account" class="error"></label>
          <span>账号:</span>
          <input name="account" id="account"><i>*</i>
      </div>
      <div class="col">
         <label for="pswd" class="error"></label>
          <span>密码:</span>
          <input name="pswd" id="pswd" value="请勿输入汉字" style="color:grey"><i>*</i>
      </div>
      <div class="col">
          <span>性别:</span>
          <select name="sex" id="sex">
                <option value ="" checked="true">--请选择--</option>
                <option value ="男" >男</option>
                <option value ="女">女</option>
          </select>
      </div>
       <div class="col">
          <label for="email" class="error"></label>
          <span>邮箱:</span>
          <input name="email" id="email">
      </div>
      <div class="col">
          <label for="phone" class="error"></label>
          <span>电话:</span>
          <input name="phone" id="phone">
      </div>
      <div class="col">
          <span>角色:</span>
          <select id="role" name="role"></select>
      </div>
      </form>
   </div>
<!--    中间标题部分 -->
   <div class="title">
        <span id="con-add">继续添加</span>
        <span id="send-save">发送保存</span>
   </div>
<!--    下半部分 -->
   <div class="content-bottom">
       <form name="usersForm" id="usersForm" >
       </form>
       <div class="content-list">
       </div>
   </div>
</div>
</body>
<script type="text/javascript">
	window.onload = function() {
		$('#loading-mask').fadeOut();
	};
	$("#token").val(parent.document.getElementById("token").value);
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
	 var roleArray=new Array();
	 Array.prototype.remove=function(dx) 
	 { 
	   if(isNaN(dx)||dx>this.length){return false;} 
	   for(var i=0,n=0;i<this.length;i++) 
	   { 
	     if(this[i]!=this[dx]) 
	     { 
	       this[n++]=this[i];
	     } 
	   } 
	   this.length-=1;
	 };
	 var index;
	$(function(){
		index=0;
		initValidate();
		$("#role").click(function(){
			if($(this).find("option").length==0){
				loadRoleType();
			}
		});
		$("#pswd").focus(function(){
			if($(this).val().trim()=='请勿输入汉字'){
				$(this).val('').css('color','black');
			}
		}).blur(function(){
			if($(this).val()==''){
				$(this).val('请勿输入汉字').css('color','grey');
			}
		});
		$("#send-save").click(function(){
			if($("#user").length!=0){
				return;
			}
			if($("#basicInfoForm").valid()){
				if($.trim($("#pswd").val())=='请勿输入汉字'){
					jAlert('请输入密码','提示');
				}else{
					if(hasChina($("#pswd").val())){
						jAlert('密码不能包含汉字','提示');
					}else{
						alertDialog($("#userName").val(),$("#pswd").val(),$("#account").val(),
							   $("#email").val(),$("#sex").val(),$("#phone").val(),roleArray[$("#role").val()]);
					}
				}
			}
		});
		$("#con-add").click(function(){
			$("#userName").val('');
			$("#pswd").val('请勿输入汉字').css('color','grey');
			$("#account").val('');
			$("#email").val('');
			$("#sex").val('');
			$("#phone").val('');
			$("#select-role").val('');
		});
	});
	function alertDialog(userName,pswd,account,email,sex,phone,role){
		var sb=new StringBuffer();
		sb.append('<div class="box" ></div>')
		.append('<div class="center-box" id="user">')
		.append('<div class="box-bar">')
		.append('<div class="box-bar-icon-close" style="font-size:20px">关闭</div> ')
		.append('<div class="box-bar-title" style="font-size:20px">请确认信息</div>')
		.append('</div>')
		.append('<ul>')
		.append('<li><span>角&nbsp;&nbsp;&nbsp;色：</span><input type="text"')
		.append(' name="role-show"')
		.append(' id="role-show"').append(' readonly value="').append(role).append('" /></li>')
		.append('<li><span>姓&nbsp;&nbsp;&nbsp;名：</span><input type="text"')
		.append(' name="userName"')
		.append(' id="userName"').append(' readonly value="').append(userName).append('" /></li>')
		.append('<li><span>账&nbsp;&nbsp;&nbsp;号：</span><input type="text" ')
		.append(' name="account"')
		.append(' id="account"').append(' readonly value="').append(account).append('" /></li>')
		.append('<li><span>密&nbsp;&nbsp;&nbsp;码：</span><input type="text"')
		.append(' name="pswd"')
		.append(' id="pswd"').append(' readonly value="').append(pswd).append('" /></li>')
		.append('<li><span>邮&nbsp;&nbsp;&nbsp;箱：</span><input type="text" ')
		.append(' name="email"')
		.append(' id="email"').append(' readonly  value="').append(email).append('" /></li>')
		.append('<li><span>电&nbsp;&nbsp;&nbsp;话：</span><input type="text" ')
		.append(' name="phone"')
		.append(' id="phone"').append(' readonly value="').append(phone).append('" /></li>')
		.append('<li><span>性&nbsp;&nbsp;&nbsp;别：</span><input type="text" ')
		.append(' name="sex"')
		.append(' id="sex"').append(' readonly value="').append(sex).append('" /></li>').append('</ul>  ')
		.append('<div class="hint-in3" id="btn_add_user">发送</div>')
		.append('</div>');
		 $("#usersForm").append(sb.toString());
		 $("#user").css({
			 "width":"400px",
			 "height":"300px",
			 "position":"fixed",
			 "margin":"0 auto",
			 "top":"50%",
			 "left":"50%",
			 "margin-left":"-200px",
			 "margin-top":"-150px",
			 "z-index":"100"
		 });
		 $(".center-box  ul li input").css({
			 "width":"260px"
		 });
		 $("#btn_add_user").css({
			 "left":"100px",
			 "bottom":"15px",
			 "height":"30px",
			 "line-height":"30px"
		 });
		 $(".box").css({
			 "z-index":"10",
			 "display":"block"
		 });
		 initDialog();
	}
	function initDialog(){
		$(".box-bar-icon-close").click(function(){
			 $("#user").remove();
			 $(".box").remove();
		 });
		$("#btn_add_user").click(function(){
			$("#loading-mask").fadeIn();
			 var formParam = $("#basicInfoForm").serialize();
			 formParam+='&token='+$("#token").val();
			$.ajax({
				data:formParam,
				url:'userMgr/addUser.do',
				type:'post',
				dataType:'json',
				error:function(data){
					$("#loading-mask").fadeOut();
					alert(data.msg);
				},
				success:function(data){
					$("#loading-mask").fadeOut();
					if(data.code==0){
						jAlert('添加用户成功','提示');
						$("#user").remove();
						$(".box").remove();
						addToListBox();
					}else{
						jAlert(data.msg,'提示');
					}
				}
				
			});
		});
	}
	function loadRoleType(){
		$.ajax({
			data:{token:$("#token").val()},
			url:'userMgr/loadRoleDic.do',
			type:'post',
			dataType:'json',
			error:function(data){
				jAlert(data.msg,'提示');
			},
			success:function(data){
				if(data!=null){
					var sb=new StringBuffer();
					for (var i = 0; i < data.length; i++) {
						roleArray[data[i].roleId]=data[i].roleName;
				     	sb.append('<option value="').append(data[i].roleId).append('">').append(data[i].roleName).append('</option>');
					}
				    $("#role").append(sb.toString());
				}
			}
			
		});
	}
	function initValidate(){
		$("#basicInfoForm").validate({
			 rules:{
				 userName:{
					 required:true,
					 maxlength:32
				 },
				 account:{
					 required:true,
					 maxlength:20,
				 },
				 pswd:{
					 required:true,
					 maxlength:32
				 },
				 email:{
					 required:false,
					 maxlength:20,
					 email: true
				 },
				 phone:{
					 rangelength:[5,11]
				 }
			 },
			 messages:{
				 userName:{
					 required:'姓名不能为空',
					 maxlength:'姓名长度不能超过32'
				 },
				 account:{
					 required:'账号不能为空',
					 maxlength:'账号长度不能超过20',
				 },
				 pswd:{
					 required:'密码不能为空',
					 maxlength:'密码长度不能超过32',
				 },
				 email:{
					 maxlength:'邮箱长度不能超过20',
					 email:'邮箱不正确'
				 },
				 phone:{
					 rangelength:'请输入正确的电话长度'
				 }
			 },
			   /* 失去焦点时不验证 */  
			  onfocusout : false
		});
	}
	function addToListBox(){
		var sb=new StringBuffer();
		 sb.append('<div class="list-box" id="user_').append(index).append('">')
	       .append('<div class="list-box-bar">')
	       .append('  <div class="list-box-bar-icon-close" style="font-size:20px">收起</div> ')
	       .append('  <div class="list-box-bar-title" style="font-size:20px">').append($("#userName").val()).append('</div>')
	       .append(' </div>')
	       .append('	<ul>')
           .append('	<li><span>角&nbsp;&nbsp;&nbsp;色：</span><input type="text"')
           .append('	 name="role-show"')
    	   .append('	id="role-show" readonly value="').append(roleArray[$("#role").val()]).append('" /></li>')
   		   .append('<li><span>姓&nbsp;&nbsp;&nbsp;名：</span><input type="text"')
   		   .append(' name="userName"')
  		   .append('id="userName" readonly value="').append($("#userName").val()).append('" /></li>')
   		   .append('<li><span>账&nbsp;&nbsp;&nbsp;号：</span><input type="text" ')
  		   .append(' name="account"')
  		   .append(' id="account" readonly value="').append($("#account").val()).append('" /></li>')
  		   .append('<li><span>密&nbsp;&nbsp;&nbsp;码：</span><input type="text"')
  		   .append(' name="pswd"')
      	   .append(' id="pswd" readonly value="').append($("#pswd").val()).append('" /></li>')
      	   .append('<li><span>邮&nbsp;&nbsp;&nbsp;箱：</span><input type="text" ')
      	   .append(' name="email"')
           .append('	id="email" readonly  value="').append($("#email").val()).append('" /></li>')
           .append('	<li><span>电&nbsp;&nbsp;&nbsp;话：</span><input type="text" ')
    	   .append('	 name="phone"')
   		   .append(' id="phone" readonly value="').append($("#phone").val()).append('" /></li>')
    	   .append('	<li><span>性&nbsp;&nbsp;&nbsp;别：</span><input type="text" ')
      	   .append(' name="sex"')
  		   .append(' id="sex" readonly value="').append($("#sex").val()).append('" /></li>')
  		   .append(' </ul>  ')
	       .append('</div>');
		 $(".content-list").append(sb.toString());
		 index++;
		 initUserBox(index);
		
	}
	function initUserBox(index){
		$(".list-box-bar-icon-close").unbind("click"); 
		$(".list-box-bar-icon-close").click(function(){
			$(this).parent().next().slideToggle(10,"linear");  
			if($(this).text()=='收起'){
				$(this).text('展开');
			}else{
				$(this).text('收起');
			}
		});
	}
	function hasChina(str){
		var reg = /.*[\u4e00-\u9fa5]+.*$/;
		if(reg.test(str)){
		   return true;
		}
		return false;
	}
</script>
</html>
