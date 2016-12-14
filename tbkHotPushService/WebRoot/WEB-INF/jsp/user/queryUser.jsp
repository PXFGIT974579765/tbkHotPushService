<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css"href="<%=basePath%>css/queryUser.css" />
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
          <input name="userName" id="userName">
      </div>
      <div class="col">
          <label for="account" class="error"></label>
          <span>账号:</span>
          <input name="account" id="account">
      </div>
      <div class="col">
          <span>性别:</span>
          <select name="sex" id="sex">
                <option value ="" selected="selected">--请选择--</option>
                <option value ="男" >男</option>
                <option value ="女">女</option>
          </select>
      </div>
       <div class="col">
          <span>角色:</span>
          <select id="role" name="role"></select>
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
      </form>
   </div>
<!--    中间标题部分 -->
   <div class="title">
        <span id="btn-query">查询</span>
   </div>
<!--    下半部分 -->
   <div class="content-bottom">
       <div class="table-list">
           
<!--            table-->
          
        
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
		loadRoleType();
		$("#btn-query").click(function(){
			if($(".table").length!=0){
				$(".table").remove();
			}
			queryByCondition(0,0,null);
		});
	});
	function queryByCondition(currentPage,pageRows,pageTotal){
		$("#loading-mask").fadeIn();
		 var formParam = $("#basicInfoForm").serialize();
		 formParam+='&token='+$("#token").val();
		 formParam+='&currentPage='+currentPage;
		 formParam+='&pageRows='+pageRows;
		$.ajax({
			data:formParam,
			url:'userMgr/queryByCondition.do',
			type:'post',
			dataType:'json',
			error:function(data){
				$("#loading-mask").fadeOut();
				jAlert('系统出错','提示');
			},
			success:function(page){
				$("#loading-mask").fadeOut();
				var data=page.list;
				if(data!=null){
					initTableList(data,page);
				}else{
					jAlert('没有查询出结果','提示');
				}
			}
		});
	}
	function updateRow(user,parent){
		parent.find(".td[id=name]").html(user.userName);
		parent.find(".td[id=account]").html(user.account);
		parent.find(".td[id=email]").html(user.email);
		parent.find(".td[id=phone]").html(user.phone);
		parent.find(".td[id=sex]").html(user.sex);
		parent.find(".td[id=role]").html(roleArray[user.roleType]);
	}
	function initOprBtn(){
		$(".btn-update").click(function(){
			var parent=$(this).parent().parent();
			var userName=parent.find(".td[id=name]").html();
			var account=parent.find(".td[id=account]").html();
			var email=parent.find(".td[id=email]").html();
			var phone=parent.find(".td[id=phone]").html();
			var sex=parent.find(".td[id=sex]").html();
			var role=parent.find(".td[id=role]").html();
			var userId=$(this).parent().find("input[id=id]").val();
			alertDialog(userName,account,email,sex,phone,role,userId,parent);
		});
		$(".btn-delete").click(function(){
			var parent=$(this).parent().parent();
			var message='确定删除'+parent.find(".td[id=name]").html()+"的信息吗?";
			var account=parent.find(".td[id=account]").html();
			jConfirm(message,'警告',function(sure){
				if(sure){
					$("#loading-mask").fadeIn();
					$.ajax({
						data:{
							token:$("#token").val(),
							account:account
						},
						url:'userMgr/deleteUser.do',
						type:'post',
						dataType:'json',
						error:function(data){
							$("#loading-mask").fadeOut();
							jAlert('系统出错','提示');
						},
						success:function(data){
							$("#loading-mask").fadeOut();
							if(data.code==0){
								jAlert('删除信息成功','提示');
								parent.remove();
							}else{
								jAlert(data.msg,'提示');
							}
						}
					});
				}
			});
		});
	}
	function alertDialog(userName,account,email,sex,phone,role,userId,parent){
		var sb=new StringBuffer();
		sb.append('<div class="box" ></div>')
		.append('<div class="center-box" id="user">')
		.append('<div class="box-bar">')
		.append('<div class="box-bar-icon-close" style="font-size:20px">关闭</div> ')
		.append('<div class="box-bar-title" style="font-size:20px">请确认信息</div>')
		.append('</div>').append('<form id="user-form" name="user-form">')
		.append('<ul>')
		.append('<li><span>角&nbsp;&nbsp;&nbsp;色：</span><select ')
		.append(' name="role"')
		.append(' id="role1">');
		for(var i=1;i<roleArray.length;i++){
			sb.append('<option value="').append(i).append('" >').append(roleArray[i]).append('</option>');
		}
		sb.append('" </select></li>')
		.append('<li><span>姓&nbsp;&nbsp;&nbsp;名：</span><input type="text"')
		.append(' name="userName"')
		.append(' id="userName"').append('  value="').append(userName).append('" /></li>')
		.append('<li><span>账&nbsp;&nbsp;&nbsp;号：</span><input type="text" ')
		.append(' name="account"')
		.append(' id="account"').append(' value="').append(account).append('" /></li>')
		.append('<li><span>邮&nbsp;&nbsp;&nbsp;箱：</span><input type="text" ')
		.append(' name="email"')
		.append(' id="email"').append('  value="').append(email).append('" /></li>')
		.append('<li><span>电&nbsp;&nbsp;&nbsp;话：</span><input type="text" ')
		.append(' name="phone"')
		.append(' id="phone"').append('  value="').append(phone).append('" /></li>')
		.append('<input type="hidden" id="id" name="id" value="').append(userId).append('"/>')
		.append('<li><span>性&nbsp;&nbsp;&nbsp;别：</span><select  ')
		.append(' name="sex"')
		.append(' id="sex1">').append('<option value="男">男</option>').append('<option value="女">女</option>').append('"</select></li>').append('</ul>  ').append('</form>')
		.append('<div class="hint-in3" id="btn_update_user">发送</div>')
		.append('</div>');
		 $(".content-bottom").append(sb.toString());
		 $("#sex1 option").each(function(){
			 var html=$.trim($(this).html());
			 sex=$.trim(sex);
			 if(html==sex){
				 $(this).attr('selected','selected');
				 return;
			 }
		 });
		 $("#role1 option").each(function(){
			 var html=$.trim($(this).html());
			 sex=$.trim(role);
			 if(html==sex){
				 $(this).attr('selected','selected');
				 return;
			 }
		 });
		 $("#role1").find("option[text="+role+"]").attr('selected','selected');
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
		 $("#btn_update_user").css({
			 "left":"100px",
			 "bottom":"15px",
			 "height":"30px",
			 "line-height":"30px"
		 });
		 $(".box").css({
			 "z-index":"10",
			 "display":"block"
		 });
		 initDialog(parent);
	}
	function initDialog(parent){
		$(".box-bar-icon-close").click(function(){
			 $("#user").remove();
			 $(".box").remove();
		 });
		$("#btn_update_user").click(function(){
			$("#loading-mask").fadeIn();
			 var formParam = $("#user-form").serialize();
			 formParam+='&token='+$("#token").val();
			$.ajax({
				data:formParam,
				url:'userMgr/updateUser.do',
				type:'post',
				dataType:'json',
				error:function(data){
					$("#loading-mask").fadeOut();
					jAlert('系统出错','提示');
				},
				success:function(data){
					$("#loading-mask").fadeOut();
					if(data!=null){
						jAlert('修改用户成功','提示');
						$("#user").remove();
						$(".box").remove();
						updateRow(data,parent);
					}else{
						jAlert('系统出错','提示');
					}
				}
				
			});
		});
	}
	function initTableList(data,page){
		var sb=new StringBuffer();
		sb.append('<div class="table">')
         .append('<div class="thead">')
            .append('<div class="tr">')
                .append('<div class="td">序号</div>')
               .append(' <div class="td">姓名</div>')
                .append('<div class="td">账号</div>')
               .append(' <div class="td">电话</div>')
               .append(' <div class="td">角色</div>')
               .append(' <div class="td">邮箱</div>')
               .append(' <div class="td">性别</div>')
               .append(' <div class="td" style="border-right:none">操作</div>')
           .append(' </div>')
        	.append(' </div>')
        	.append('<div class="tbody">');
		var startIndex=(page.currentPage-1)*page.pageRows;
		for(var i=0;i<data.length;i++){
			sb.append('<div class="tr">')
			.append('<div class="td">').append(startIndex+i+1).append('</div>')
			.append('<div class="td" id="name">').append(data[i].name).append('</div>')
			.append('<div class="td" id="account">').append(data[i].account).append('</div>')
			.append('<div class="td" id="phone"> ').append(data[i].phone).append('</div>')
			.append('<div class="td" id="role"> ').append(roleArray[data[i].roleType]).append('</div>')
			.append('<div class="td" id="email"> ').append(data[i].email).append('</div>')
			.append('<div class="td" id="sex"> ').append(data[i].sex).append('</div>')
			.append('<div class="td" style="border-right:none">')
			.append('<a href="javascript:void(0);" class="btn-update">修改</a>&nbsp;')
			.append('<a href="javascript:void(0);" class="btn-delete">删除</a>&nbsp;')
			.append('<a href="javascript:void(0);" class="btn-detail">查看</a>')
			.append('<input type="hidden" id="id" name="id" value="').append(data[i].id).append('"/>')
			.append('</div>')
			.append('</div>');
		}
		 sb.append('</div>')
         .append('<div class="tfoot">')
             .append('<div id="pageToolbar"></div>   ')          
        .append(' </div>')
      .append('</div>');
		 $(".table-list").append(sb.toString());
		 initOprBtn();
		 initPage(page);
	}
	function initPage(page){
		$('#pageToolbar').Paging(
				{
					pagesize:page.pageRows,
					count:page.rowsTotal,
					current:page.currentPage,
					toolbar:true,
					callback:function(currentPage,pageRows,pageTotal){ 
						$(".table").remove();
						queryByCondition(currentPage,pageRows,pageTotal);
		            }
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
					sb.append('<option value="">--请选择--</option>');
					for (var i = 0; i < data.length; i++) {
						roleArray[data[i].roleId]=data[i].roleName;
				     	sb.append('<option value="').append(data[i].roleId).append('">').append(data[i].roleName).append('</option>');
					}
				    $("#role").append(sb.toString());
				}
			}
			
		});
	}
	var roleArray=new Array();
</script>
</html>
