<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/BugetItem.css" />
   <base href="<%=basePath%>">
   
  
  <body>
  <input type="hidden" value="" name="token" id="token">
    <div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center; 
 border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="http://localhost:8080/my-first-frame/easyui/easyui/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>
<div class="content">
 <div class="title1" >
        <span id="" >预算明细</span>
   </div>
<!-- 上半部分 -->
   <div class="content-up">
      <form name="basicInfoForm" id="basicInfoForm">
         <input type="hidden" value="${buget.orderId }" name="orderId">
         <input type="hidden" value="${buget.bugetId }" name="bugetId">
          <input type="hidden" value="${buget.bugetPerAcc }" name="bugetPerAcc">
          <div class="content-item_">
				<table id="table_basic">
				     <tr><th><i>*</i>预算描述</th><td><input type="text" value="${buget.bugetDesc}" name="bugetDesc" ></td></tr>
				     <tr><th><i>*</i>金额（元）</th><td><input type="text" value="${buget.buget }" name="buget"></td></tr>
				     <tr><th><i>*</i>收支状态</th><td><select id="buget-way" name="bugetFlag" ><option value="">请选择</option><option value="in">收入</option><option vlaue="out">支出</option></select></td></tr>
				     <tr><th><i>*</i>时间</th><td><input id="bugetTime" type="text" name="bugetTime" onClick="WdatePicker()" value="${buget.bugetTime }" ></input></td></tr>
				     <tr><th><i>*</i>涉及者姓名</th><td><input type="text" value="${buget.personName}" name="personName"></td></tr>
				     <tr><th><i>*</i>登记者者姓名</th><td><input type="text" id="bugetPerName" name="bugetPerName" readonly></td></tr>
				     <tr><th><i>*</i>所处阶段</th><td><select id="stage" name="stageId"></select></td></tr>
				</table>
				<div class="title"><span onclick="ajaxSubmit();">保存</span></div>
		 </div>      
      </form>
   </div>
<!--    中间标题部分 -->
<!--    下半部分 -->
</div>
<!-- <div class="error-box"><label for="orderArea" class="error"></label></div> -->
</body>
<script type="text/javascript">
	window.onload = function() {
		$('#loading-mask').fadeOut();
	};
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
	 var sb=new StringBuffer();
	 sb.append('<option value="">请选择</option>');
	 <c:forEach items="${stageDic}" var="stage" varStatus="status">
         sb.append('<option value="${stage.stageId}">${stage.stageName}</option>');
	 </c:forEach>
	 var curStageId='${buget.stageId}';
	 var bugetFlag='${buget.bugetFlag}';
	$(function(){
		var token=$("#token",window.opener.document).val();
		$("#token").val(token);
		 $("#stage").append(sb.toString());
		 var myName=$("#myName",window.opener.document).val();
		 $("#bugetPerName").val(myName);
		 $("#stage option").each(function(){
			 if($(this).val()==curStageId){
				 $(this).attr('selected','selected');
				 return false;
			 }
		 });
		 $("#buget-way option").each(function(){
			 if($(this).val()==bugetFlag){
				 $(this).attr('selected','selected');
				 return false;
			 }
		 });
		 
		 initValidadte();
		 
	});
	function initValidadte(){
		$("#basicInfoForm").validate({
			 rules:{
				 'bugetDesc':{
					 maxlength:30,
					 required:true
				 },
				 'buget':{
					 maxlength:10,
					 required:true
				 },
				 'bugetFlag':{
					 maxlength:20,
					 required:true
				 },
				 'bugetTime':{
					 maxlength:10,
					 required:true
				 },
				 'personName':{
					 maxlength:20,
					 required:true
				 },
				 'stageId':{
					 maxlength:20,
					 required:true
				 }
			 },
			 messages:{
				 'bugetDesc':{
					 maxlength:'预算描述过长',
					 required:'预算描述不能为空'
				 },
				 'buget':{
					 maxlength:'费用过长',
					 required:'费用不能为空'
				 },
				 'bugetFlag':{
					 maxlength:'',
					 required:'请选择收支状态'
				 },
				 'bugetTime':{
					 maxlength:'',
					 required:'请选择时间'
				 },
				 'personName':{
					 maxlength:'姓名过长',
					 required:'请输入姓名'
				 },
				 'stageId':{
					 maxlength:'',
					 required:'请选择当前状态'
				 }
			 },
			 onfocusout : true
		});
	}
	function ajaxSubmit(){
	    if($("#basicInfoForm").valid()){
		     $("#loading-mask").fadeIn();
		     var formParam = $("#basicInfoForm").serialize();
		     formParam += '&token=' + $("#token").val();
		     $.ajax({
		       	data : formParam,
			    url : 'order/addBugetItem.do',
			    type : 'post',
			    dataType : 'json',
			    error : function(data) {
			     	$("#loading-mask").fadeOut();
			      	alert(data.msg);
			    },
			    success : function(data) {
			       	$("#loading-mask").fadeOut();
				    if (data.code == 0) {
					    var buget=data.p2pdata;
					    jAlert('保存成功', '提示');
				    	window.opener.insertBugetItem(buget);
					    window.close();
				    } else {
				    	jAlert(data.msg, '提示');
				    }
		    	}

		     });
		}
	}
	
	function hasChina(str) {
		var reg = /.*[\u4e00-\u9fa5]+.*$/;
		if (reg.test(str)) {
			return true;
		}
		return false;
	}
</script>
</html>
