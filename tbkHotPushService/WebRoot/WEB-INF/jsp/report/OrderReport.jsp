<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css"href="<%=basePath%>css/OrderReport.css" />
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
        <span id="" style="float:left;margin-left:20px;">输入查询条件</span>
   </div>
<!-- 上半部分 -->
   <div class="content-up">
      <form name="basicInfoForm" id="basicInfoForm">
      <input type="hidden" value="${isSelf }" name="isSelf">
      <div class="col">
          <span>区域:</span>
          <input name="owner.ownerAddr" id="owner.ownerAddr">
      </div>
        <div class="col">
          <span>订单类型:</span>
          <select id="orderType" name="orderTypeId">
              <option value="">请选择</option>         
          </select>
      </div>
       <div class="col">
          <span>业主类型:</span>
          <select id="ownerType" name="owner.ownerTypeId">
              <option value="">请选择</option>  
          </select>
      </div>
       <div class="col">
          <span>当前状态:</span>
          <select id="state" name="curStateId">
              <option value="">请选择</option>  
          </select>
      </div>
       <div class="col">
          <span>当前阶段:</span>
          <select id="stage" name="curStageId">
              <option value="">请选择</option>  
          </select>
      </div>
      <div class="col">
          <span>业主性别:</span>
          <select name="owner.ownerSex" id="sex">
                <option value ="" >请选择</option>
                <option value ="男" >男</option>
                <option value ="女">女</option>
          </select>
      </div>
       <div class="col">
          <span>订单发起人:</span>
          <select id="role" name="role" style="width:110px">
              <option value="">请选择</option>  
          </select>
          <select id="estaPerAcc" name="estaPerAcc" style="width:110px">
              <option value="">请选择</option>  
          </select>
      </div>
      <div class="col">
          <span>建筑面积:</span>
          <input id="orderAreaFrom" name="orderAreaFrom" style="width:110px"></input>~
          <input id="orderAreaTo" name="orderAreaTo" style="width:110px"></input>
          <lable >平米</lable>
      </div>
       <div class="col">
          <span>订单收入:</span>
          <input id="bugetFrom" name="bugetFrom" style="width:110px"></input>~
          <input id="bugetTo" name="bugetTo" style="width:110px"></input>
          <lable >元</lable>
      </div>
      <div class="col">
          <span>发起时间:</span>
          <input id="orderTimeFrom" name="startTimeFrom" onClick="WdatePicker()" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'orderTimeTo\',{d:-1});}'})" style="width:115px"></input>~
          <input id="orderTimeTo" name="startTimeTo" onClick="WdatePicker()"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'orderTimeFrom\',{d:1});}'})" style="width:115px"></input>
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
	var isSelf='${isSelf}';
	var isGrey=1;
	var account=$("#account",parent.document).val();
	$("#token").val(parent.document.getElementById("token").value);
// 	var myName=parent.document.getElementById("myName").innerHTML;
// 	$("#establishPersonName").val(myName);
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
	  function queryByCondition(currentPage,pageRows,pageTotal){
			$("#loading-mask").fadeIn();
			 var formParam = $("#basicInfoForm").serialize();
			 formParam+='&token='+$("#token").val();
			 formParam+='&currentPage='+currentPage;
			 formParam+='&pageRows='+pageRows;
			$.ajax({
				data:formParam,
				url:'order/queryByCondition.do',
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

		function initTableList(data,page){
			var sb=new StringBuffer();
			sb.append('<div class="table">')
	         .append('<div class="thead">')
	            .append('<div class="tr">')
	                .append('<div class="td" style="width:50px">序号</div>')
	               .append(' <div class="td" style="width:170px">订单编号</div>')
	                .append('<div class="td" style="width:120px">当前阶段</div>')
	               .append(' <div class="td" style="width:120px">下一操作</div>')
	               .append(' <div class="td" style="width:120px">下一操作人</div>')
	                .append('<div class="td" style="width:170px">订单名</div>')
	                .append('<div class="td" style="width:120px">订单状态</div>')
	                 .append('<div class="td" style="width:120px">订单类型</div>')
	                .append('<div class="td" style="width:120px">订单发起人</div>')
	                .append(' <div class="td" style="width:120px">业主姓名</div>')
	                .append('<div class="td" style="width:170px">订单周期</div>')
	               .append(' <div class="td" style="width:170px">业主地址</div>')
	               .append(' <div class="td" style="width:120px">业主电话</div>')
	               .append(' <div class="td" style="width:120px">预算(万元)</div>')
	               .append(' <div class="td" style="width:128px">建筑大小(平米)</div>')
	           .append(' </div>')
	        	.append(' </div>')
	        	.append('<div class="tbody">');
			var startIndex=(page.currentPage-1)*page.pageRows;
			for(var i=0;i<data.length;i++){
				sb.append('<div class="tr">')
				.append('<div class="td" style="width:50px">').append(startIndex+i+1).append('</div>')
				.append('<div class="td" id="orderId" style="width:170px">')
				.append('<a href="').append('javascript:void(0);').append('" rel="').append(data[i].nextStageId).append('"')
				.append('" class="btn-orderId">').append(data[i].orderId).append('</a>&nbsp;')
				.append('</div>');
				if(data[i].curStageId==data[i].nextStageId){
			    	sb.append('<div class="td" id="curStageId" style="width:120px;text-align: left;"> ').append(data[i].curStageName).append('<span style="color:red">(正在进行)</span></div>');
				}else{
					sb.append('<div class="td" id="curStageId" style="width:120px;text-align: left;"> ').append(data[i].curStageName).append('<span style="color:#285dc9">(已完成)</span></div>');
				}
				if(account==data[i].nextPerAcc){
				    sb.append('<div class="td" style="width:120px">')
			    	.append('<a href="').append('javascript:void(0);').append('" rel="').append(data[i].nextStageId).append('"')
			    	.append(' class="btn-nextStageName">待').append(data[i].nextStageName).append('</a>&nbsp;')
				    .append('</div>');
				}else{
					sb.append('<div class="td" id="nextStageName" style="width:120px">待').append(data[i].nextStageName).append('</div>');
				}
				sb.append('<div class="td" id="nextPerName" style="width:120px">').append(data[i].nextPerName).append('</div>')
				.append('<div class="td" id="orderName" style="width:170px">').append(data[i].orderName).append('</div>')
				.append('<div class="td" id="curStateId" style="width:120px"> ').append(data[i].curStateName).append('</div>')
				.append('<div class="td" id="orderName" style="width:120px">').append(data[i].orderTypeName).append('</div>')
				.append('<div class="td" id="estaPerAcc" style="width:120px"> ').append(data[i].estaPerAccName).append('</div>')
				.append('<div class="td" id="ownerName" style="width:120px"> ').append(data[i].ownerName).append('</div>')
				.append('<div class="td" id="time" style="width:170px"> ').append(data[i].startTime+"~").append((data[i].finishTime==null||data[i].finishTime=='')?'至今':data[i].finishTime).append('</div>')
				.append('<div class="td" id="ownerAddr" style="width:170px"> ').append(data[i].ownerAddr).append('</div>')
				.append('<div class="td" id="ownerPhone" style="width:120px"> ').append(data[i].ownerPhone).append('</div>')
				.append('<div class="td" id="buget" style="width:120px"> ').append((data[i].buget==null)?'还未做预算':data[i].buget).append('</div>')
				.append('<div class="td" id="buget" style="width:128px"> ').append(data[i].orderArea).append('</div>')
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
		function initOprBtn(){
			$(".btn-orderId").click(function(){
				var orderId=$(this).html();
				var orderName=$(this).parent().parent().find(".td[id=orderName] ").html();
				var subtitle=orderName+'订单详情';
				var icon='icon icon-search';
				var nextStageId=$(this).attr('rel');
				var url='order/toEditOrder.do?orderId='+orderId+'&nextStageId='+nextStageId+'&flag=query';
				parent.addTab(subtitle,url,icon,orderId);
			});
			$(".btn-nextStageName").click(function(){
				var orderId=$(this).parent().parent().find(".td[id=orderId] a").html();
				var orderName=$(this).parent().parent().find(".td[id=orderName] ").html();
				var subtitle='编辑'+orderName+'订单信息';
				var icon='icon icon-edit';
				var nextStageId=$(this).attr('rel');
				var url='order/toEditOrder.do?orderId='+orderId+'&nextStageId='+nextStageId+'&flag=edit';
				parent.addTab(subtitle,url,icon,orderId+nextStageId);
			});
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
	$(function(){
		  
		$("#orderName").focus(function(){
			if($(this).val().trim()=='官渡区王先生'){
				$(this).val('').css('color','black');
				isGrey=0;
			}
		}).blur(function(){
			if($(this).val()==''){
				$(this).val('官渡区王先生').css('color','grey');
				isGrey=1;
			}
		});
		$("#role").click(function(){
			if($(this).find("option").length==1){
				loadRoleType();
			}
		}).change(function(){
			var roleId=$(this).val();
		    $("#estaPerAcc").find("option[value='']").siblings().remove();
			loadTypeUser(roleId);
		});
		$("#remark").focus(function(){
			if($(this).val().trim()=='可以输入业主的大概预算，性格等,最多输入200字'){
				$(this).val('').css('color','black');
			}
		}).blur(function(){
			if($(this).val().trim()==''){
				$(this).val('可以输入业主的大概预算，性格等,最多输入200字').css('color','grey');
			}
		});
		jQuery.validator.addMethod("chkArea", function(value, element) {
			var areaFrom = ($("#orderAreaFrom").val()==null)?'0':$("#orderAreaFrom").val();
			var areaTo = ($("#orderAreaTo").val()==null)?'0':$("#orderAreaTo").val();
			var f = true;
			if(eval(areaFrom)>eval(areaTo)){
			     f=false;
			}else{
		        f=true;
			}
			return f;       
		}, "建筑面积前面不能比后面大"); 
		jQuery.validator.addMethod("chkBuget", function(value, element) {
			var bugetFrom = ($("#bugetFrom").val()==null)?'0':$("#bugetFrom").val();
			var bugetTo = ($("#bugetTo").val()==null)?'0':$("#bugetTo").val();
			var f = true;
			if(eval(bugetFrom)>eval(bugetTo)){
			     f=false;
			}else{
		        f=true;
			}
			return f;       
		}, "预算前面不能比后面大"); 
		$("#basicInfoForm").validate({
			rules:{
				'orderAreaFrom':{
					chkArea:true
				},
				'orderAreaTo':{
					chkArea:true
				},
				'bugetFrom':{
					chkBuget:true
				},
				'bugetTo':{
					chkBuget:true
				}
			},
			messages:{}
		});
		$("#basicInfoForm").valid();
		$("#orderAreaFrom").on('input',function(e){
			$("#basicInfoForm").valid();
		});
		$("#orderAreaTo").on('input',function(e){
			$("#basicInfoForm").valid();
		});
		$("#bugetFrom").on('input',function(e){
			$("#basicInfoForm").valid();
		});
		$("#bugetTo").on('input',function(e){
			$("#basicInfoForm").valid();
		});
		$("#orderType").click(function(){
			if($(this).find("option").length==1){
		     	loadTypeDic("orderType");
			}
		});
		$("#ownerType").click(function(){
			if($(this).find("option").length==1){
		    	loadTypeDic("ownerType");
			}
		});
	   $("#state").click(function(){
		   if($(this).find("option").length==1){
		    	loadTypeDic("state");
			}
	   });
	   $("#stage").click(function(){
		   if($(this).find("option").length==1){
		    	loadTypeDic("stage");
			}
	   });
	   $("#btn-query").click(function(){
		   if($("#basicInfoForm").valid()){
			   if(isGrey==1){
					  $("#orderName").val('');
				   }
					if($(".table").length!=0){
						$(".table").remove();
					}
					queryByCondition(0,0,null);
					if(isGrey==1){
						  $("#orderName").val('官渡区王先生');
					}
		   }
		  
		});
	});
	var roleArray=new Array();
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
	function loadTypeUser(roleTypeId) {
		$.ajax({
			data : {
				token : $("#token").val(),
				roleId : roleTypeId
			},
			url : 'order/loadUsersByRole.do',
			type : 'post',
			dataType : 'json',
			error : function(data) {
				jAlert('系统出错', '提示');
			},
			success : function(result) {
				if (result.code == 0) {
					var data = result.p2pdata;
					if (data != null) {
						var sb = new StringBuffer();
						for (var key in data) {
							sb.append('<option value="').append(key).append('">').append(data[key]).append('</option>');
						}
						$("#estaPerAcc").append(sb.toString());
					}
				}else{
				}
			}

		});
	}
	function loadTypeDic(typeId) {
		var url = 'order/' + typeId + '.do';
		$.ajax({
			data : {
				token : $("#token").val()
			},
			url : url,
			type : 'post',
			dataType : 'json',
			error : function(data) {
				jAlert(data.msg, '提示');
			},
			success : function(data) {
				if (data != null) {
					var sb = new StringBuffer();
					for (var i = 0; i < data.length; i++) {
						sb.append('<option value="').append(data[i][typeId + 'Id']).append('">').append(data[i][typeId + 'Name']).append('</option>');
					}
					$("#" + typeId).append(sb.toString());
				}
			}

		});
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
