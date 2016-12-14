<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/addOrder.css" />
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
        <span id="" style="float:left;">当前阶段>>订单管理>>订单发起</span>
   </div>
<!-- 上半部分 -->
   <div class="content-up">
      <form name="basicInfoForm" id="basicInfoForm">
      <input type="hidden" name="orderId" id="orderId" value="${orderBasic.orderId }">
      <input type="hidden" name="owner.ownerId" id="owner.ownerId" value="${owner.ownerId }">
       <div class="col">
         <label for="orderName" class="error"></label>
          <span>订单名:</span>
          <input name="orderName" id="orderName"  value="${(orderBasic.orderName==null||orderBasic.orderName=='')?'官渡区王先生':orderBasic.orderName }" style="color:grey"><i>*</i>
      </div>
      <div class="col">
          <label for="owner.ownerName" class="error"></label>
          <span>业主姓名:</span>
          <input name="owner.ownerName" id="owner.ownerName" value="${owner.ownerName }"><i>*</i>
      </div>
      <div class="col">
          <label for="owner.ownerAddr" class="error"></label>
          <span>业主地址:</span>
          <input name="owner.ownerAddr" id="owner.ownerAddr" value="${owner.ownerAddr }"><i>*</i>
      </div>
        <div class="col">
          <label for="orderType" class="error"></label>
          <span>订单类型:</span>
          <select id="orderType" name="orderTypeId" >
                <option value="">请选择</option>  
                <c:if test="${(orderBasic.orderTypeId!=null)&&(orderBasic.orderTypeId!='') }">
                    <option value="${orderBasic.orderTypeId}" selected="selected">${orderTypeDic[orderBasic.orderTypeId] }</option> 
                </c:if>
          </select>
          <i>*</i>
      </div>
       <div class="col">
          <label for="ownerType" class="error"></label>
          <span>业主类型:</span>
          <select id="ownerType" name="owner.ownerTypeId">
                  <option value="">请选择</option>  
                   <c:if test="${owner.ownerTypeId!=null&&owner.ownerTypeId!='' }">
                        <option value="${owner.ownerTypeId}" selected="selected">${ownerTypeDic[owner.ownerTypeId] }</option> 
                   </c:if>
          </select>
          <i>*</i>
      </div>
      <div class="col">
          <label for="owner.ownerSex" class="error"></label>
          <span>业主性别:</span>
          <select name="owner.ownerSex" id="sex">
          <c:choose>
             <c:when test="${owner.ownerSex=='女' }">
                <option value ="男"  >男</option>
                <option value ="女" selected='selected'>女</option>
             </c:when>
             <c:otherwise>
                  <option value ="男" selected='selected' >男</option>
                  <option value ="女">女</option>
             </c:otherwise>
           </c:choose>
                
          </select>
          <i>*</i>
      </div>
       <div class="col">
          <label for="establishPersonName" class="error"></label>
          <span>订单发起人:</span>
          <input name="establishPersonName" id="establishPersonName" value="${orderBasic.estaPerAcc}" readonly>
          <i>*</i>
      </div>
      <div class="col">
          <label for="owner.ownerPhone" class="error"></label>
          <span>业主电话:</span>
          <input name="owner.ownerPhone" id="owner.ownerPhone" value="${owner.ownerPhone}">
          <i>*</i>
      </div>
      <div class="col">
          <label for="orderArea" class="error"></label>
          <span>建筑面积:</span>
          <input id="orderArea" name="orderArea" style="width:170px" value="${orderBasic.orderArea}"></input>
          <lable>平米</<lable>
          <i>*</i>
      </div>
      <div class="col">
          <label for="ownerType" class="error"></label>
          <span>当前阶段:</span>
          <input name="" id="" value="订单发起" readonly>
          <i>*</i>
      </div>
        <div class="col">
          <label for="ownerType" class="error"></label>
          <span>下一阶段:</span>
          <select id="stage" name="nextStageId">
          </select>
          <i>*</i>
      </div>
      <div class="col">
          <label for="nextPerAcc" class="error"></label>
          <span>下一办理人:</span>
          <select id="role" name="role" style="width:110px">
              <option value="">请选择</option>  
          </select>
          <select id="nextPerAcc" name="nextPerAcc" style="width:110px">
              <option value="">请选择</option>  
              
          </select>
          <i>*</i>
      </div>
       <div class="colspan">
          <label for="owner.ownerRemark" class="error"></label>
          <span>业主描述:</span>
          <textarea rows="3" cols="3" name="owner.ownerRemark" id="remark">${(owner.ownerRemark==null||owner.ownerRemark=='')?'可以输入业主的大概预算，性格等,最多输入200字':owner.ownerRemark }</textarea>
      </div>
     
      </form>
   </div>
<!--    中间标题部分 -->
   <div class="title">
        <c:if test="${orderBasic.orderId==null||orderBasic.orderId=='' }">
           <span id="reset">重新添加</span>
        </c:if>
        <span id="temp-save">暂存</span>
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
<!-- <div class="error-box"><label for="orderArea" class="error"></label></div> -->
</body>
<script type="text/javascript">
	window.onload = function() {
		$('#loading-mask').fadeOut();
	};
	$("#token").val(parent.document.getElementById("token").value);
	var myName=parent.document.getElementById("myName").innerHTML;
	$("#establishPersonName").val(myName);
	var curStageName='订单发起';
	var nextStageName='';
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
	 var isGrey=1;
	 var isGrey1=1;
	 function initValidate(){
		 $("#basicInfoForm").validate({
			 rules:{
				 orderName:{
					 maxlength:50,
					 required:true
				 },
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
				 'nextPerAcc':{
					 maxlength:32
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
				 },
				 'nextStageId':{
					 maxlength:5
				 }
			 },
			 messages:{
				 orderName:{
					 maxlength:'长度不能超过50',
					 required:'订单名不能为空'
				 },
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
				 'nextPerAcc':{
					 maxlength:'长度过长'
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
				 },
				 'nextStageId':{
					 maxlength:5
				 }
			 },
// 			 errorPlacement: function(error, element) { //错误信息位置设置方法
// 				 error.appendTo( $(".error-box") ); //这里的element是录入数据的对象
// 				 },
			   /* 失去焦点时不验证 */  
			  onfocusout : false
		});
	 }
	 function addRequiredRules(){
		 $('#owner\\.ownerAddr').rules("add",{required: true,messages:{required:"地址不能为空"}});
		 $('#ownerType').rules("add",{required: true,messages:{required:"业主类型不能为空"}});
		 $('#nextPerAcc').rules("add",{required: true,messages:{required:"下一步办理人不能为空"}});
		 $('#sex').rules("add",{required: true,messages:{required:"业主性别不能为空"}});
		 $('#owner\\.ownerPhone').rules("add",{required: true,messages:{required:"业主电话不能为空"}});
		 $('#orderArea').rules("add",{required: true,messages:{required:"建筑面积不能为空"}});
		 $('#stage').rules("add",{required: true,messages:{required:"下一办理阶段不能为空"}});
	 }
	 function decRequiredRules(){
		 $('#owner\\.ownerAddr').rules("add",{required: false,messages:{required:"地址不能为空"}});
		 $('#ownerType').rules("add",{required: false,messages:{required:"业主类型不能为空"}});
		 $('#nextPerAcc').rules("add",{required: false,messages:{required:"下一步办理人不能为空"}});
		 $('#sex').rules("add",{required: false,messages:{required:"业主性别不能为空"}});
		 $('#owner\\.ownerPhone').rules("add",{required: false,messages:{required:"业主电话不能为空"}});
		 $('#orderArea').rules("add",{required: false,messages:{required:"建筑面积不能为空"}});
		 $('#stage').rules("add",{required: false,messages:{required:"下一办理阶段不能为空"}});
	 }
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
							$("#nextPerAcc").append(sb.toString());
						}
					}else{
					}
				}

			});
		}
	 function resetInput(){
		 $("input").val('');
		 $("#remark").val('可以输入业主的大概预算，性格等,最多输入200字').css('color','grey');
		 isGrey1=1;
		 $("#orderName").val('官渡区王先生').css('color','grey');
		 isGrey=1;
		 $("#establishPersonName").val(myName);
		 $("#token").val(parent.document.getElementById("token").value);
	 }
	$(function(){
		initValidate();
		loadStageDic("stage");
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
		if($("#orderName").val()!='官渡区王先生'){
			$("#orderName").css('color','black');
			isGrey=0;
		}
		$("#role").click(function(){
			if($(this).find("option").length==1){
				loadRoleType();
			}
		}).change(function(){
			var roleId=$(this).val();
			$("#nextPerAcc").find("option[value='']").siblings().remove();
			loadTypeUser(roleId);
		});
		$("#remark").focus(function(){
			if($(this).val().trim()=='可以输入业主的大概预算，性格等,最多输入200字'){
				$(this).val('').css('color','black');
				isGrey1=0;
			}
		}).blur(function(){
			if($(this).val().trim()==''){
				$(this).val('可以输入业主的大概预算，性格等,最多输入200字').css('color','grey');
				isGrey1=1;
			}
		});
		if($("#remark").val()!='可以输入业主的大概预算，性格等,最多输入200字'){
			$("#remark").css('color','black');
			isGrey1=0;
		}
		$("#orderType").click(function(){
			if($(this).find("option").length==1||$(this).find("option").length==2){
		     	loadTypeDic("orderType");
			}
		});
		$("#reset").click(function(){
			resetInput();
		});
		$("#ownerType").click(function(){
			if($(this).find("option").length==1){
		    	loadTypeDic("ownerType");
			}
		});
		$("#send-save").click(function(){
			addRequiredRules();
			if($("#basicInfoForm").valid()){
       		     if(isGrey==1){
    				jAlert('订单名不能为空','提示');
    				return;
    			 }
       		     if(isGrey1==1){
       		    	 $("#remark").val('');
       		     }
       	         sendDataToServerForSave($(this).attr("id"));
        	 }
		});
         $("#temp-save").click(function(){
        	 decRequiredRules();
        	  if($("#basicInfoForm").valid()){
        		     if(isGrey==1){
     				    jAlert('订单名不能为空','提示');
     			    	return;
     			     }
        		     if(isGrey1==1){
           		    	 $("#remark").val('');
           		     }
        	         sendDataToServerForSave($(this).attr("id"));
         	 }
		});
	});
	
	function sendDataToServerForSave(saveType) {
		$("#loading-mask").fadeIn();
		var formParam = $("#basicInfoForm").serialize();
		formParam += '&token=' + $("#token").val()+'&saveType='+saveType;
		$.ajax({
			data : formParam,
			url : 'order/addOrderBasic.do',
			type : 'post',
			dataType : 'json',
			error : function(data) {
				$("#loading-mask").fadeOut();
				alert(data.msg);
				if( $("#remark").val()==''){
					$("#remark").val('可以输入业主的大概预算，性格等,最多输入200字').css('color','grey');
					isGrey1=1;
				}
			},
			success : function(data) {
				$("#loading-mask").fadeOut();
				if( $("#remark").val()==''){
					$("#remark").val('可以输入业主的大概预算，性格等,最多输入200字').css('color','grey');
					isGrey1=1;
				}
				if (data.code == 0) {
					if(saveType=='send-save'){
						resetInput();
					}
					var resultValue=data.resultValue;
					resultValue=eval('('+resultValue+')');
					$("#orderId").val(resultValue.orderId);
					$("#owner\\.ownerId").val(resultValue.ownerId);
					jAlert('添加订单成功', '提示');
				} else {
					jAlert(data.msg, '提示');
				}
			}

		});
	}
	function loadStageDic(typeId) {
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
					var start = 0;
					for (var i = 0; i < data.length; i++) {
						if (data[i][typeId + 'Name'] == curStageName) {
							start = 1;
							if (i < (data.length - 1)) {
								nextStageName = data[i + 1][typeId + 'Name'];
							} else {
								nextStageName = data[i][typeId + 'Name'];
							}
							continue;
						}
						if (start == 1) {
							sb.append('<option value="').append(
									data[i][typeId + 'Id']).append('">')
									.append(data[i][typeId + 'Name']).append(
											'</option>');
						}
					}
					$("#" + typeId).append(sb.toString());
					$("#" + typeId)
							.change(
									function() {
										if ($(this).find("option:selected")
												.html() != nextStageName) {
											jAlert('您确定要跳过之前的阶段吗', '提示');
										}
									});
					// 				    $("#"+typeId+" option").each(function(){
					// 						 var html=$.trim($(this).html());
					// 						 nextStageName=$.trim(nextStageName);
					// 						 if(html==nextStageName){
					// 							 $(this).attr('selected','selected');
					// 							 return;
					// 						 }
					// 					 });
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
						sb.append('<option value="').append(
								data[i][typeId + 'Id']).append('">').append(
								data[i][typeId + 'Name']).append('</option>');
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
