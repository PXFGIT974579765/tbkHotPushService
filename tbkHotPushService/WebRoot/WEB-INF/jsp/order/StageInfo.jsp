<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/StageInfo.css" />
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
         <input type="hidden" value="${order.orderId }" name="orderId">
         <input type="hidden" value="${owner.ownerId }" name="owner.ownerId">
          <div class="content-item_">
          <c:if test="${stageId eq '000' }">
               <table id="table_basic">
					<caption>基础信息</caption>
					<tr>
						<th>订单名:</th>
						<td colspan="7">${order.orderName }</td>
					</tr>
					<tr>
						<th>订单编号:</th>
						<td >${order.orderId }</td>
						<th>订单发起人:</th>
						<td colspan="5">${userDic[order.estaPerAcc] }</td>
					</tr>
					<tr>
					    <th>订单类型:</th>
					    <c:if test="${type eq 'query' }"><td>${orderTypeDic[order.orderTypeId]}</td></c:if>
						<c:if test="${type eq 'edit' }"><td>
						   <select id="orderType" name="orderTypeId" >
                                   <option value="">请选择</option>  
                                   <option value="${order.orderTypeId}" selected="selected">${orderTypeDic[order.orderTypeId] }</option> 
                          </select>
						</td></c:if>
					    <th>建筑面积(平米):</th>
					    <c:if test="${type eq 'query' }"><td colspan="5">${order.orderArea }</td></c:if>
						<c:if test="${type eq 'edit' }"><td colspan="5"><input type="text" value=" ${order.orderArea }" name="orderArea"></td></c:if>
					</tr>
					<tr>
						<th>业主姓名:</th>
						 <c:if test="${type eq 'query' }"><td colspan="7">${owner.ownerName }</td></c:if>
						 <c:if test="${type eq 'edit' }"><td colspan="5"><input type="text" value="${owner.ownerName }" name="owner.ownerName"></td></c:if>
					</tr>
					<tr>
						<th>业主类型:</th>
						<c:if test="${type eq 'query' }"><td>${ownerTypeDic[owner.ownerTypeId] }</td></c:if>
						<c:if test="${type eq 'edit' }"><td>
						    <select id="ownerType" name="owner.ownerTypeId">
                              <option value="">请选择</option>  
                              <option value="${owner.ownerTypeId}" selected="selected">${ownerTypeDic[owner.ownerTypeId] }</option> 
                         </select>
						</td></c:if>
						<th>业主性别:</th>
								<c:if test="${type eq 'query' }">
									<td colspan="5">${owner.ownerSex }</td>
								</c:if>
								<c:if test="${type eq 'edit' }">
									<td><select name="owner.ownerSex" id="sex">
											<c:choose>
												<c:when test="${owner.ownerSex=='女' }">
													<option value="男">男</option>
													<option value="女" selected='selected'>女</option>
												</c:when>
												<c:otherwise>
													<option value="男" selected='selected'>男</option>
													<option value="女">女</option>
												</c:otherwise>
											</c:choose>

									</select></td>
								</c:if>
							</tr>
					<tr>
						<th>业主地址:</th>
						<c:if test="${type eq 'query' }">
							<td>${owner.ownerAddr }</td>
						</c:if>
						<c:if test="${type eq 'edit' }">
							<td><input name="owner.ownerAddr" id="owner.ownerAddr" type="text" value="${owner.ownerAddr }"></td>
						</c:if>
						
						<th>业主电话:</th>
						<c:if test="${type eq 'query' }">
							<td colspan="5">${owner.ownerPhone }</td>
						</c:if>
						<c:if test="${type eq 'edit' }">
							<td colspan="5"><input name="owner.ownerPhone" id="owner.ownerPhone" type="text" value="${owner.ownerPhone}"></td>
						</c:if>
						
					</tr>
					<tr>
						<th>业主描述:</th>
						<c:if test="${type eq 'query' }">
							<td colspan="7">${owner.ownerRemark }</td>
						</c:if>
						<c:if test="${type eq 'edit' }">
							 <td colspan="7"><textarea rows="3" cols="3" name="owner.ownerRemark" id="remark">${(owner.ownerRemark==null||owner.ownerRemark=='')?'可以输入业主的大概预算，性格等,最多输入200字':owner.ownerRemark }</textarea>
						     </td>
						</c:if>
						
					</tr>
				</table>
          </c:if>
           <c:if test="${stageId ne '000' }">
           <input type="hidden" value="${detailId }" name="detailId">
           <table id="table_basic">
               <caption>基础信息</caption>
               <tr><th>阶段名称:</th><td>${stageDic[detail.curStageId] }</td></tr>
               <tr><th>阶段状态:</th><td>${detail.curStateId=='0'?'正在进行':'已经完成' }</td></tr>
               <tr><th>阶段开始时间:</th><td>${detail.beginTime }</td></tr>
               <tr><th>结束时间:</th><td>${detail.finishTime }</td></tr>
               <tr><th>阶段操作人:</th><td>${userDic[detail.curPerAcc] }</td></tr>
               <c:if test="${type eq 'query' }">
                   <tr><th rowspan="1">备注:</th><td rowspan="1">${(detail.remark==''||detail.remark==null)?'无':detail.remark}</td></tr>
               </c:if>
               <c:if test="${type eq 'edit' }">
                   <tr><th rowspan="1">备注:</th><td rowspan="1"><textarea rows="" cols="" name="remark"> ${(detail.remark==''||detail.remark==null)?'无':detail.remark}</textarea></td></tr>
               </c:if>
           </table>
           </c:if>
             <c:if test="${type eq 'edit' }">
                 <div class="title"><span onclick="ajaxSubmit();">保存</span></div>
             </c:if>
             <c:if test="${type eq 'query' }">
                 <div class="title"><span onclick="javascript:window.close();">关闭</span></div>
             </c:if>
				
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
	 var curStageId='${buget.stageId}';
	 var bugetFlag='${buget.bugetFlag}';
	$(function(){
		var token=$("#token",window.opener.document).val();
		$("#token").val(token);
		 $("#stage").append(sb.toString());
		 var myName=$("#myName",window.opener.document).val();
		 $("#bugetPerName").val(myName);
		 if($("#orderType").length!=0){
			 $("#orderType").click(function(){
					if($(this).find("option").length==1||$(this).find("option").length==2){
				     	loadTypeDic("orderType");
					}
			 });
		 }
		 if( $("#ownerType").length!=0){
			 $("#ownerType").click(function(){
					if($(this).find("option").length==1||$(this).find("option").length==2){
				    	loadTypeDic("ownerType");
					}
			 });
			 initBasicValidate();
		 }else{
			 $("basicInfoForm").validate({
				 rules:{
					 'remark':{
						 maxlength:200
					 }
				 },
				 messages:{
					 'remark':{
						 maxlength:'备注不能超过200'
					 }
				 }
			 });
		 }
		 
		
		 
		 initValidadte();
		 
	});
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
						sb.append('<option value="').append(
								data[i][typeId + 'Id']).append('">').append(
								data[i][typeId + 'Name']).append('</option>');
					}
					$("#" + typeId).append(sb.toString());
				}
			}

		});
	}
	 function initBasicValidate(){
		 $("#basicInfoForm").validate({
			 rules:{
				 'owner.ownerName':{
					 maxlength:20,
					 required:true
				 },
				 'owner.ownerAddr':{
					 maxlength:100
				 },
				 'orderTypeId':{
					 maxlength:5,
					 required:true
				 },
				 'owner.ownerTypeId':{
					 maxlength:5
				 },
				 'owner.ownerSex':{
					 maxlength:4
				 },
				 'owner.ownerPhone':{
					 maxlength:11
				 },
				 'owner.ownerRemark':{
					 maxlength:200
				 },
				 'orderArea':{
					 maxlength:10
				 }
			 },
			 messages:{
				 'owner.ownerName':{
					 maxlength:'长度不能超过20',
					 required:'业主姓名不能为空'
				 },
				 'owner.ownerAddr':{
					 maxlength:'长度不能超过50'
				 },
				 'orderTypeId':{
					 maxlength:'长度不能超过100',
					 required:'订单类型不能为空'
				 },
				 'owner.ownerTypeId':{
					 maxlength:'长度不能超过5'
				 },
				 'owner.ownerSex':{
					 maxlength:'长度不能超过4'
				 },
				 'owner.ownerPhone':{
					 maxlength:'长度不能超过11'
				 },
				 'owner.ownerRemark':{
					 maxlength:'长度不能超过200'
				 },
				 'orderArea':{
					 maxlength:'长度不能超过10'
				 }
			 },
			   /* 失去焦点时不验证 */  
			  onfocusout : false
		});
	 }
	
	function ajaxSubmit(){
	    if($("#basicInfoForm").valid()){
		     $("#loading-mask").fadeIn();
		     var formParam = $("#basicInfoForm").serialize();
		     formParam += '&token=' + $("#token").val();
		     $.ajax({
		       	data : formParam,
			    url : 'order/editStageInfo.do',
			    type : 'post',
			    dataType : 'json',
			    error : function(data) {
			     	$("#loading-mask").fadeOut();
			      	alert(data.msg);
			    },
			    success : function(data) {
			       	$("#loading-mask").fadeOut();
				    if (data.code == 0) {
				    	jAlert('保存成功','提示');
				    	//说明修改的是订单基础信息
				    	if( $("#ownerType").length!=0){
				    		window.opener.refresh();
				    	}
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
