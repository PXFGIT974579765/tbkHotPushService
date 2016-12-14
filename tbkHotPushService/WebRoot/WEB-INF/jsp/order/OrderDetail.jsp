<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css"href="<%=basePath%>css/OrderDetail.css" />
   <base href="<%=basePath%>">
   
  
  <body>
  <input type="hidden" value="" name="token" id="token">
  <input type="hidden" value="" name="myName" id="myName">
    <div id="loading-mask" style="position:absolute;top:0px; left:0px; width:100%; height:100%; background:rgba(0,0,0,0.5); z-index:20000">
<div id="pageloading" style="position:absolute; top:50%; left:50%; margin:-120px 0px 0px -120px; text-align:center; 
 border:2px solid #8DB2E3; width:200px; height:40px;  font-size:14px;padding:10px; font-weight:bold; background:#fff; color:#15428B;"> 
    <img src="http://localhost:8080/my-first-frame/easyui/easyui/images/loading.gif" align="absmiddle" /> 正在加载中,请稍候...</div>
</div>
<div class="content">
    <div class="title1" >
        <span id="" style="float:left;">当前阶段>>订单管理>>修改订单</span>
    </div>
    <div class="title-bar" >
        <ul>
            <li id="tab_000">基础信息</li>
            <li id="tab_001">阶段信息</li>
            <li id="tab_002">相关附件</li>
            <li id="tab_003">收支明细</li>
        </ul>
    </div>
		<div class="tab-content">
			<div class="content-item_000">
				<table id="table_basic">
					<caption>基础信息</caption>
					<tr>
						<th>订单名:</th>
						<td colspan="7">${orderBasic.orderName }</td>
					</tr>
					<tr>
						<th>订单编号:</th>
						<td colspan="7">${orderBasic.orderId }</td>
					</tr>
					<tr>
						<th>订单发起人:</th>
						<td colspan="7">${userDic[orderBasic.estaPerAcc] }</td>
					</tr>
					<tr>
					    <th>订单类型:</th>
						<td>${orderTypeDic[orderBasic.orderTypeId]}</td>
					    <th>建筑面积(平米):</th>
						<td colspan="5">${orderBasic.orderArea }</td>
					</tr>
					
					<tr>
					   <th>订单已完成到阶段:</th>
						<td>${stageDic[orderBasic.curStageId] }</td>
						<th>当前正在操作阶段:</th>
						<td colspan="5">${stageDic[orderBasic.nextStageId] }</td>
					</tr>
					<tr>
					    <th>订单发起时间:</th>
						<td >${orderBasic.startTime }</td>
						<th>订单结束时间:</th>
						<td >${((orderBasic.finishTime=='')||(orderBasic.finishTime==null))?'订单尚未结束':orderBasic.finishTime }</td>
						<th>订单当前状态:</th>
						<td colspan="3">${stateDic[orderBasic.curStateId] }</td>
					
					</tr>
					<tr>
						<th>订单前期预算:</th>
						<td>${((orderBasic.buget=='')||(orderBasic.buget==null))?'订单尚未作出预算':orderBasic.buget }</td>
						<th>订单实际费用:</th>
						<td colspan="5">${((orderBasic.relBuget=='')||(orderBasic.relBuget==null))?'订单尚未作出消费':orderBasic.relBuget }</td>
					</tr>
					<tr>
						<th>业主姓名:</th>
						<td colspan="7">${owner.ownerName }</td>
					</tr>
					<tr>
						<th>业主类型:</th>
						<td>${ownerTypeDic[owner.ownerTypeId] }</td>
						<th>业主性别:</th>
						<td colspan="5">${owner.ownerSex }</td>
					</tr>
					<tr>
						<th>业主地址:</th>
						<td>${owner.ownerAddr }</td>
						<th>业主电话:</th>
						<td colspan="5">${owner.ownerPhone }</td>
					</tr>
					<tr>
						<th>业主描述:</th>
						<td colspan="7">${owner.ownerRemark }</td>
					</tr>
				</table>
			</div>
			<div class="content-item_001">
				<table id="table_stages">
					<caption>已完成阶段</caption>
					<tr>
						<th style="text-align: center;">阶段名称</th>
						<th style="text-align: center;">阶段状态</th>
						<th style="text-align: center;">阶段开始时间</th>
						<th style="text-align: center;">结束时间</th>
						<th style="text-align: center;">阶段操作人</th>
						<th style="text-align: center;">操作</th>
					</tr>
					<c:forEach items="${orderDetail}" var="detail" varStatus="status">
						<c:if test="${detail.id!=null&&detail.id!=''&&detail.curStateId eq '1' }">
							<tr class="stage-item">
								<td style="text-align: center;">${stageDic[detail.curStageId] }</td>
								<td style="text-align: center;">${stateDic[detail.curStateId] }</td>
								<td style="text-align: center;">${detail.beginTime }</td>
								<td style="text-align: center;">${detail.finishTime }</td>
								<td style="text-align: center;">${userDic[detail.curPerAcc] }</td>
								<td style="text-align: center;">
								   <a href="javascript:void(0);" class="btn-query" onclick="queryStageDetail('${detail.id}','${ detail.curPerAcc}','${detail.curStageId }');">查看</a> &nbsp;
								   <a href="javascript:void(0);" class="btn-edit" onclick="editStageDetail('${detail.id}','${ detail.curPerAcc}','${detail.curStageId }');">修改</a>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
				<c:forEach items="${orderDetail}" var="detail" varStatus="status">
					<c:if test="${detail.curStateId eq '0' and flag eq 'edit'}">
					<form action="" id="basicInfoForm">
					<input type="hidden" value="${orderBasic.orderId }" name="orderId">
<!-- 					<input type="hidden" value="" name="detailId" id="detailId"> -->
					<table id="table_cur_stage">
						<caption>当前阶段（${stageDic[detail.curStageId] }）</caption>
						<tr>
							<th>当前阶段:</th>
							<td colspan="1">${stageDic[detail.curStageId] }<input type="hidden" value="${detail.curStageId }" name="curStageId"></td>
							<th><i>*</i>下一阶段:</th>
							<td ><select id="stage" name="nextStageId"></select></td>
						</tr>
						<tr>
							<th>操作人:</th>
							<td colspan="4">${userDic[detail.curPerAcc] }<input type="hidden" value="${detail.curPerAcc }" name="curPerAcc"></td>
							
						</tr>
							<tr>
								<th> <i>*</i>下一步办理人1:</th>
								<td colspan="4"><select id="role" name="role" style="width:110px">
										<option value="">请选择</option>
								</select> <select id="nextPerAcc_1" name="nextPerAcc" style="width:110px">
										<option value="">请选择</option>
								</select>
								<div class="btn-add-nextPer">新增</div>
								</td>
							</tr>
							<tr >
								<th> 是否邮件通知下一办理人:</th>
								<td colspan="4"><input type="radio" class="email-notice" name="email-notice" value="1">是
								<input type="radio" class="email-notice" name="email-notice" value="0" checked="true">否
								</td>
							</tr>
							<tr>
							  <th>备注:</th>
							  <td colspan="4" id="fileIndex_0"><textarea  rows="" cols="" name="remark" id="remark">${detail.remark }</textarea></div>
							 </td>
							</tr>
							<tr>
							  <th>预算信息:</th>
							  <td colspan="4">请点击上传附件进行预算上传，或者
							  <a href="javascript:void(0);" onclick="openBugetWindow(this);">  生成预算模板</a>
							  </td>
							</tr>
							<tr>
							  <th>附件信息:</th>
							  <td colspan="4" id="fileIndex_0"><div class="btn-upload" onclick="selectTabOfFile();" style="width: 70px;">上传附件</div>
							 </td>
							</tr>
						</table>
						</form>
						<div class="title" style="text-align:center"><div class="btn-send-save" onclick="tempSaveOrder('temp-save')">暂存</div>&nbsp;&nbsp;<div class="btn-temp-save" onclick="sendSaveOrder('send-save')">保存</div></div>
					</c:if>
				</c:forEach>
			</div>
			<div class="content-item_002">
				<table id="table_files">
					<tr class="file-item">
						<th style="text-align: center;">附件名称</th>
						<th style="text-align: center;">附件描述</th>
						<th style="text-align: center;">上传时间</th>
						<th style="text-align: center;">上传作者</th>
						<th style="text-align: center;">是否仅自己查看</th>
						<th style="text-align: center;">所处阶段</th>
						<th style="text-align: center;">操作</th>
					</tr>
					<c:forEach items="${files}" var="file" varStatus="status">
							<tr class="file-item-${file.fileId}">
								<td style="text-align: center;"><a href="javascript:void(0);" onclick="downLoadFile('${file.fileSaveName}','${file.filePerAcc }',this,'${file.stageId }','${file.fileFlag }','${file.fileId }')"> ${file.fileRelName }</a></td>
								<td style="text-align: center;">${file.fileDesc }</td>
								<td style="text-align: center;">${file.createTime}</td>
								<td style="text-align: center;">${userDic[file.filePerAcc] }</td>
									<td style="text-align: center;">${file.fileFlag=='toSelf'?'是':'否' }</td>
								<td style="text-align: center;">${stageDic[file.stageId]}</td>
								<td style="text-align: center;">
								<c:if test="${myAccount!=file.filePerAcc }">
								       无操作权限
								</c:if>
								<c:if test="${myAccount==file.filePerAcc }">
								   <a href="javascript:void(0);" class="btn-edit" onclick="openFileUploadWindow('${file.fileId}');">修改</a>
								   <a href="javascript:void(0);" class="btn-delete" onclick="deleteFileInfo('${file.fileId}',this);">删除</a></td>
							    </c:if>
							</tr>
					</c:forEach>
				</table>
				<div class="title"><span id="file_upload1" onclick="openFileUploadWindow()">上传附件</span></div>
			</div>
			<div class="content-item_003">
				<table id="table_bugets">
					<tr class="buget-item">
						<th style="text-align: center;">预算描述</th>
						<th style="text-align: center;">金额（元）</th>
						<th style="text-align: center;">收支状态</th>
						<th style="text-align: center;">时间</th>
						<th style="text-align: center;">涉及者姓名</th>
						<th style="text-align: center;">登记者姓名</th>
						<th style="text-align: center;">所处阶段</th>
						<th style="text-align: center;">操作</th>
					</tr>
					<c:forEach items="${bugets}" var="buget" varStatus="status">
							<tr class="buget-item-${buget.bugetId}">
								<td style="text-align: center;">${buget.bugetDesc }</td>
								<td style="text-align: center;">${buget.buget }</td>
								<td style="text-align: center;">${buget.bugetFlag=='in'?'收入':'支出'}</td>
								<td style="text-align: center;">${buget.bugetTime}</td>
								<td style="text-align: center;">${buget.personName}</td>
								<td style="text-align: center;">${userDic[buget.bugetPerAcc]}</td>
								<td style="text-align: center;">${stageDic[buget.stageId]}</td>
								<td style="text-align: center;">
								<c:if test="${buget.bugetPerAcc==myAccount }">
								    <a href="javascript:void(0);" class="btn-edit" onclick="openEditBugetWindow('${buget.bugetId}');" >修改</a>
								    <a href="javascript:void(0);" class="btn-delete" onclick="deleteBugetInfo('${buget.bugetId}',this);">删除</a>
								</c:if>
								<c:if test="${buget.bugetPerAcc!=myAccount }"> 无操作权限      </c:if>
								</td>
							</tr>
					</c:forEach>
				</table>
				<div class="title"><span onclick="openEditBugetWindow()">新增</span></div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	window.onload = function() {
		$('#loading-mask').fadeOut();
		
	};
	var isSelf = '${isSelf}';
	var isGrey = 1;
	var account='${myAccount}';
	$("#token").val(parent.document.getElementById("token").value);
	$("#myName").val($("#myName",parent.document).html());
	function StringBuffer() {
		this.string = new Array();
	}
	StringBuffer.prototype.append = function(str) {
		this.string.push(str);
		return this; //方便链式操作
	};
	StringBuffer.prototype.toString = function() {
		return this.string.join("");
	};
	Array.prototype.remove = function(dx) {
		if (isNaN(dx) || dx > this.length) {
			return false;
		}
		for (var i = 0, n = 0; i < this.length; i++) {
			if (this[i] != this[dx]) {
				this[n++] = this[i];
			}
		}
		this.length -= 1;
	};
	var lastLiIndex = 999;
	var trLiIndex;
	var curLiIndex;
	
	var flag = '${flag}';
	var curStageId;

	var orderTypeDic = '${orderTypeDic}';
	var ownerTypeDic = '${ownerTypeDic}';
	var roleTypeDic = '${roleTypeDic}';
	var stageDic = '${stageDic}';
	var stageDicMap =new Array();
	
	<c:forEach var='item' items='${stageDic}' varStatus='status'>
	stageDicMap['${item.key}'] = '${item.value}';
	</c:forEach>
	var curStageName='${stageDic[orderBasic.nextStageId] }';
	var stateDic = '${stateDic}';
	var nextPerCounts=1;
	var maxPerCounts=5;
	var fileCounts=0;
	var orderId='${orderBasic.orderId}';
	
		//附件上传
    var typeExtFlag = '${typeExtFlag}';
	$(function() {
		$(".title-bar ul li").hover(function() {
			$(this).css('background', '#C6D1E1');
		}, function() {
			$(this).css('background', '#7AA6E9');
		});
		$(".title-bar ul li").click(function() {
			var index = $(this).attr("id").split("_")[1];
			selectLiAndContentItem(index);
			$(this).unbind('hover');
		});
		$("table[id^=table_]").find("caption").click(function() {
			$(this).parent().find("tbody").toggle();
		});
		loadStageDic("stage");
		if (flag == 'edit'||flag=='query') {
			curStageId = '${nextStageId}';
			selectLiAndContentItem("001");
			$("#tab_" + "001").unbind('hover');
		}
		
		initialRoleClick($("#role"));
		$(".btn-add-nextPer").click(function(){
			for (var i = 1; i < nextPerCounts+1; i++) {
				if($("#nextPerAcc_"+i).val()==''){
					jAlert('请选择第'+i+'个办理人','提示');
					return;
				}
			}
			if(nextPerCounts==maxPerCounts){
				jAlert('最多可添加'+maxPerCounts+'个办理人','提示');
				return;
			}
			var rowHtml=insertNextPerRow(nextPerCounts+1);
			$("select[id^=nextPerAcc_]:last").parent().parent().after(rowHtml);
			nextPerCounts++;
			initialRoleClick($("#nextPerAcc_"+nextPerCounts).parent().find("select[id=role]"));
		});
		$(".email-notice").change(function(){
			var value=$("input[name='email-notice']:checked").val(); 
			operaRadioByValue(value);
		});
		
	});
	//暂存
	function tempSaveOrder(saveType){
		addRequiredRules();
		if($("#basicInfoForm").valid()){
			ajaxSubmit(saveType);
		}
	}
	//发送
	function sendSaveOrder(saveType){
		addRequiredRules();
		$("select[id^='nextPerAcc_']").rules("add",{required: true,messages:{required:"下一步办理人不能为空"}});
        if($("#basicInfoForm").valid()){
        	ajaxSubmit(saveType);
		}
	}
	//添加校验规则
	function addRequiredRules(){
		$("#basicInfoForm").validate({
			 rules:{
				 remark:{
					 maxlength:100
				 },
			 },
			 messages:{
				 remark:{
					 maxlength:'备注长度不能超过100'
				 },
			 },
			   /* 失去焦点时不验证 */  
			  onfocusout : false
		});
		if($("input[id^='email']").length!=0){
			alert(4);
			$("input[id^='email']").each(function(){
				$(this).rules("add", { email: true, messages: { email: "请输入邮件格式"} });
			});
			
		
		}
		
	}
	function ajaxSubmit(saveType){
		$("#loading-mask").fadeIn();
		var formParam = $("#basicInfoForm").serialize();
		formParam += '&token=' + $("#token").val()+'&saveType='+saveType;
		$.ajax({
			data : formParam,
			url : 'order/saveOrderBasic.do',
			type : 'post',
			dataType : 'json',
			error : function(data) {
				$("#loading-mask").fadeOut();
				alert(data.msg);
			},
			success : function(data) {
				$("#loading-mask").fadeOut();
				if (data.code == 0) {
					if(saveType=='send-save'){
						var detail=data.p2pdata;
						addStageItem(detail);
					}
					jAlert('保存成功', '提示');
				} else {
					jAlert(data.msg, '提示');
				}
			}

		});
	}
	function refresh(){
		location.reload();
	}
	function addStageItem(detail){
		var sb=new StringBuffer();
		sb.append('<tr class="stage-item">')
		.append('<td style="text-align: center;">').append(stageDicMap[detail.curStageId]).append('</td>')
		.append('<td style="text-align: center;">').append(detail.curStateId=='0'?'正在进行':'已经结束').append('</td>')
		.append('<td style="text-align: center;">').append(detail.beginTime).append('</td>')
		.append('<td style="text-align: center;">').append(detail.finishTime ).append('</td>')
		.append('<td style="text-align: center;">').append($('#myName').val()).append('</td>')
		.append('<td style="text-align: center;">')
		.append('  <a href="javascript:void(0);" class="btn-query" onclick="queryStageDetail(\'detail.id\',\' detail.curPerAcc\',\' detail.curStageId\');" >查看</a> &nbsp;')
		.append('  <a href="javascript:void(0);" class="btn-edit" onclick="editStageDetail(\'detail.id}\',\'detail.curPerAcc\',\' detail.curStageId\');">修改</a>')
		.append('</td>')
	    .append('</tr>');
		$(".stage-item:last").after(sb.toString());
		$("#table_cur_stage").remove();
	}
	
	function selectLiTab(index) {
		$(".title-bar ul li").each(function() {
			if ($(this).attr("id").split("_")[1] == index) {
				$(this).css('background', '#C6D1E1');
			} else {
				$(this).css('background', '#7AA6E9');
			}
			if ($(this).attr("id").split("_")[1] == lastLiIndex) {
				if (lastLiIndex != trLiIndex) {
					$(this).hover(function() {
						$(this).css('background', '#C6D1E1');
					}, function() {
						$(this).css('background', '#7AA6E9');
					});
				}

			}

		});
	}
	function selectTabContentItem(index) {
		$("div[class^=content-item]").css('display', 'none');
		$(".content-item_" + index).css('display', 'block');
	}
	function deleteFileInfo(fileId,obj){
		var parent=$(obj).parent().parent();
		jConfirm('确定要删除此附件信息？','警告',function(sure){
			if(sure){
				$("#loading-mask").fadeIn();
				$.ajax({
					data:{
						token:$("#token").val(),
						fileId:fileId
					},
					url:'order/deleteFile.do',
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
	}
	function deleteBugetInfo(bugetId,obj){
		var parent=$(obj).parent().parent();
		jConfirm('确定要删除此附件信息？','警告',function(sure){
			if(sure){
				$("#loading-mask").fadeIn();
				$.ajax({
					data:{
						token:$("#token").val(),
						bugetId:bugetId
					},
					url:'order/deleteBuget.do',
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
	}
	function openBugetWindow(obj){
		var url='<%=basePath%>order/openWindow.do?token='+$('#token').val();
		window.open(url,"预算模板数据项选择","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=400");
	}
	function openFileUploadWindow(fileId){
		if(fileId==null||fileId==''){
			fileId='';
		}
		var url='<%=basePath%>order/openFileUploadWindow.do?token='+$('#token').val()+'&orderId='+orderId+'&fileId='+fileId;
		window.open(url,"预算信息","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=400");
	
	}
	function openEditBugetWindow(bugetId){
		if(bugetId==null||bugetId==''){
			bugetId='';
		}
		var url='<%=basePath%>order/openBugetItemWindow.do?token='+$('#token').val()+'&bugetId='+bugetId+'&orderId='+orderId;
		window.open(url,"预算信息","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=400");
	
	}
    function queryStageDetail(detailId,curPerAcc,curStage){
    	var url='<%=basePath%>order/openStageWindow.do?token='+$('#token').val()+'&orderId='+orderId+'&detailId='+detailId+'&type=query'+'&stageId='+curStage;
		window.open(url,"预算信息","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=400");
	}
	function editStageDetail(detailId,curPerAcc,curStage){
		if(curPerAcc!=account){
			jAlert('只有本人才可以修改','提示');
			return;
		}
		var url='<%=basePath%>order/openStageWindow.do?token='+$('#token').val()+'&orderId='+orderId+'&detailId='+detailId+'&type=edit'+'&stageId='+curStage;
		window.open(url,"预算信息","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=400");
	}
	function selectTabOfFile(){
		selectLiAndContentItem("002");
		$("#tab_" + "002").unbind('hover');
	}
	function insertBugetItem(buget){
		var sb=new StringBuffer();
		var bugetId=buget.bugetId;
		sb.append('<tr class="buget-item-').append(buget.bugetId).append('">')
		.append('<td style="text-align: center;">').append(buget.bugetDesc).append('</td>')
		.append('<td style="text-align: center;">').append(buget.buget ).append('</td>')
		.append('<td style="text-align: center;">').append(buget.bugetFlag=='in'?'收入':'支出').append('</td>')
		.append('<td style="text-align: center;">').append(buget.bugetTime).append('</td>')
		.append('<td style="text-align: center;">').append(buget.personName).append('</td>')
		.append('<td style="text-align: center;">').append($('#myName').val()).append('</td>')
		.append('<td style="text-align: center;">').append(stageDicMap[buget.stageId]).append('</td>')
		.append('<td style="text-align: center;">');
		if(account==buget.bugetPerAcc){
			sb.append('<a href="javascript:void(0);" class="btn-edit" onclick="openEditBugetWindow(\'').append(buget.bugetId).append('\')" >修改</a>');
			sb.append('<a href="javascript:void(0);" class="btn-edit" onclick="deleteBugetInfo(\'').append(buget.bugetId).append('\',this);">删除</a>');
		}else{
			sb.append('无操作权限');
		}
	    sb.append('</td></tr>');
		var hasEq=0;
		$("tr[class^=buget-item]").each(function(){
			var cls=$(this).attr('class');
			var id=cls.substring(cls.lastIndexOf("-")+1);
			if(id==bugetId){
				hasEq=1;
				$(this).after(sb.toString());
				$(this).remove();
				return false;
			}
		});
		if(hasEq=="0"){
		   $("tr[class^=buget-item]:last").after(sb.toString());
		}
	}
	
	function insertFileRow(file) {
		var sb=new StringBuffer();
		var fileId=file.fileId;
		sb.append('<tr class="file-item-').append(file.fileId).append('">')
		.append('<td style="text-align: center;"><a href="javascript:void(0);" onclick="downLoadFile(\'').append(file.fileSaveName).append('\',\'').append(file.filePerAcc).append('\',this,\'').append(file.stageId).append('\',\'').append(file.fileFlag).append('\',\'').append(file.fileId).append('\')">').append(file.fileRelName).append('</a></td>')
		.append('<td style="text-align: center;">').append(file.fileDesc ).append('</td>')
		.append('<td style="text-align: center;">').append(file.createTime).append('</td>')
		.append('<td style="text-align: center;">').append($('#myName').val()).append('</td>')
		.append('<td style="text-align: center;">').append(file.fileFlag=='toSelf'?'是':'否').append('</td>')
		.append('<td style="text-align: center;">').append(stageDicMap[file.stageId]).append('</td>')
		.append('<td style="text-align: center;"> ');
		if(file.filePerAcc!=account){
			sb.append('无操作权限');
		}else{
			sb.append('<a href="javascript:void(0);" class="btn-edit" onclick="openFileUploadWindow(\'').append(file.fileId).append('\')">修改</a>&nbsp;')
			.append('<a href="javascript:void(0);" class="btn-delete" onclick="deleteFileInfo(\'').append(file.fileId).append('\',this);">删除</a></td>');
			
		}
	    sb.append('</tr>');
		var hasEq=0;
		$("tr[class^=file-item]").each(function(){
			var cls=$(this).attr('class');
			var id=cls.substring(cls.lastIndexOf("-")+1);
			if(id==fileId){
				hasEq=1;
				$(this).after(sb.toString());
				$(this).remove();
				return false;
			}
		});
		if(hasEq=="0"){
		   $("tr[class^=file-item]:last").after(sb.toString());
		}
		
	}
	function downLoadFile(saveFileName,perAcc,obj,stageId,fileFlag,fileId){
		if(perAcc!=account){
			if(fileFlag=='toSelf'){
				jAlert('文件仅自己可以预览','提示');
				return false;
			}
		}
		var obj1=$(obj);
		var url='<%=basePath%>/file/download.do?token='+$("#token").val()+'&saveFileName='+encodeURI(encodeURI(saveFileName))+'&filePerAcc='+perAcc+'&orderId='+orderId+'&stageId='+stageId+'&fileId='+fileId;
		obj1.attr('href',url);
	}
	function operaRadioByValue(value) {
		if (value == '1') {
			if ($(".nextPerEmails").length == 0) {
				var sb = new StringBuffer();
				sb.append('<tr class="nextPerEmails" >');
				sb.append('<th rowspan="').append(nextPerCounts).append(
						'">确认办理人邮箱:</th>');
				for (var i = 1; i < nextPerCounts + 1; i++) {
					var account = $("#nextPerAcc_" + i).val();
					var name = $("#nextPerAcc_" + i).find("option:selected").html();
					var email = '';
					loadEmailByAccount(account, function(data) {
						email = data;
					});
					sb.append('<th colspan="1">').append(name).append(':</th>');
					sb.append('<td colspan="3"><input type="text" id="email_').append(i).append('\" name="email" value="').append(email).append('"></td>');
					sb.append('</tr>');
					if (i < nextPerCounts) {
						sb.append('<tr class="nextPerEmails">');
					}
				}
				$("input[name='email-notice']").parent().parent().after(
						sb.toString());
			}
		}
		if (value == '0') {
			if ($(".nextPerEmails").length != 0) {
				$(".nextPerEmails").remove();
			}
		}
	}
	function loadEmailByAccount(account, callBack) {
		var email = '';
		$.ajax({
			data : {
				token : $("#token").val(),
				account : account
			},
			url : 'order/loadEmail.do',
			type : 'post',
			async : false,
			dataType : 'json',
			error : function(data) {
				jAlert(data.msg, '提示');
			},
			success : function(data) {
				if (data.code == 0) {
					email = data.resultValue;
					callBack(email);
				}
			}
		});
	}
	function initialRoleClick(obj) {
		obj.click(function() {
			if ($(this).find("option").length == 1) {
				loadRoleType($(this));
			}
		}).change(function() {
			var roleId = $(this).val();
			var obj = $(this).parent().find("select[id^=nextPerAcc]");
			obj.find("option[value='']").siblings().remove();
			loadTypeUser(roleId, obj);
		});
	}
	function insertNextPerRow(index) {
		var sb = new StringBuffer();
		sb.append('<tr>');
		sb.append('<th> <i>*</i>下一步办理人').append(index).append(':</th>');
		sb.append('<td colspan="5"><select id="role" name="role" style="width:110px">');
		sb.append('<option value="">请选择</option>');
		sb.append('</select> <select id="nextPerAcc_').append(index).append('" name="nextPerAcc" style="width:110px">');
		sb.append('<option value="">请选择</option>');
		sb.append('</select>');
		sb.append('<div class="btn-delete-nextPer" onclick="deleteNextPer(this);">删除</div>');
		sb.append('</td>');
		sb.append('</tr>');
		return sb.toString();
	}
	function deleteNextPer(obj1){
		obj=$(obj1);
		obj.parent().find("select[id=role]").unbind("click").unbind("change");
		obj.parent().parent().remove();
		obj.unbind('click');
		nextPerCounts--;
		var index=1;
		$("select[id^=nextPerAcc_]").each(function(){
			$(this).attr('id','nextPerAcc_'+index);
			var html='<i>*</i>下一步办理人'+index;
			$(this).parent().parent().find("th").html(html);
			index++;
		});
	}
	var nextStageName = '';
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
							sb.append('<option value="').append(data[i][typeId + 'Id']).append('">').append(data[i][typeId + 'Name']).append('</option>');
						}
					}
					$("#" + typeId).append(sb.toString());
					$("#" + typeId).change(function() {
						if ($(this).find("option:selected").html() != nextStageName) {
							jAlert('您确定要跳过之前的阶段吗', '提示');
						}
					});
				}
			}

		});
	}
	var roleArray = new Array();
	function loadRoleType(obj) {
		$.ajax({
			data : {
				token : $("#token").val()
			},
			url : 'userMgr/loadRoleDic.do',
			type : 'post',
			dataType : 'json',
			error : function(data) {
				jAlert(data.msg, '提示');
			},
			success : function(data) {
				if (data != null) {
					var sb = new StringBuffer();
					for (var i = 0; i < data.length; i++) {
						roleArray[data[i].roleId] = data[i].roleName;
						sb.append('<option value="').append(data[i].roleId).append('">').append(data[i].roleName).append('</option>');
					}
					obj.append(sb.toString());
				}
			}

		});
	}

	function loadTypeUser(roleTypeId, obj) {
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
							for ( var key in data) {
								sb.append('<option value="').append(key).append('">').append(data[key]).append('</option>');
							}
							obj.append(sb.toString());
						}
					}
				}

		});
	}
	function selectLiAndContentItem(index) {
		if (lastLiIndex == 999) {
			lastLiIndex = index;
		} else {
			lastLiIndex = trLiIndex;
		}
		trLiIndex = index;
		curLiIndex = index;
		selectLiTab(index);
		selectTabContentItem(index);
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
