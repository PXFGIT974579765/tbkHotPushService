<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/FileUpload.css" />
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
        <span id="" >上传附件</span>
   </div>
<!-- 上半部分 -->
   <div class="content-up">
      <form name="basicInfoForm" id="basicInfoForm">
         <input type="hidden" value="${file.orderBasicId }" name="orderBasicId">
         <input type="hidden" value="${file.fileId }" name="fileId">
         <input type="hidden" value="${file.fileSaveName }" name="fileSaveName" id="fileSaveName">
         <input type="hidden" value="${file.filePerAcc }" name="filePerAcc">
          <div class="content-item_">
				<table id="table_basic">
				     <tr><th>操作</th><td><span id="file_upload"></span></td></tr>
				     <tr><th><i>*</i>是否仅自己可见</th><td><input type="radio" class="read-auth" name="fileFlag" value="toSelf" checked="true">是
								<input type="radio" class="read-auth" name="fileFlag" value="toAll" >否</td></tr>
				     <tr><th><i>*</i>文件名</th><td><input type="text" value="${file.fileRelName}" name="fileRelName" id="fileRelName" readonly ></td></tr>
				     <tr><th><i>*</i>文件描述</th><td><input type="text" value="${file.fileDesc}" name="fileDesc" id="fileDesc"></td></tr>
				     <tr><th><i>*</i>上传作者</th><td><input id="filePerName" type="text" name="filePerName" value="" readonly ></input></td></tr>
				     <tr><th><i>*</i>上传时间</th><td><input id="createTime" type="text" name="createTime"   value="${file.createTime }" readonly ></input></td></tr>
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
	 var curStageId='${file.stageId}';
	 var bugetFlag='${buget.bugetFlag}';
	 var typeExt = '*.jpg;*.jpge;*.gif;*.png;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.pdf;*.tif;*.tiff';
	$(function(){
		var token=$("#token",window.opener.document).val();
		var myName=$("#myName",window.opener.document).val();
		$("#token").val(token);
		$("#myName").val(myName);
		$("#filePerName").val(myName);
		 $("#stage").append(sb.toString());
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
		 toUploadify();
		 initValidadte();
		 
	});
	var time;
	var fileRelName;
	var fileSaveName;
	function toUploadify(){
  		$('#file_upload').uploadifive({
  			'height': 20,//上传按钮的高度
  			'width': 80,//上传按钮的长度
  			'buttonText': '上传附件',//上传按钮的名字
  			'method'       : 'post',
  			'fileObjName' : 'upload',
  			'uploadScript' : 'file/upload.do',
  			'fileType':typeExt,//文件格式
  			'auto':true,
  			'itemTemplate' : '<div class="uploadifive-queue-item"><span class="filename"></span> | <span class="fileinfo"></span><div class="close"></div></div>',
  			'fileSizeLimit':'10MB',//上传文件的大小限制。
  	        'onUploadComplete':function(file, data, response){
  	        	data=eval("("+data+")");
  	        	if(data.code==0){
  	        		var fileMap=data.p2pdata;
  	        		for(var key in fileMap){
  	        			if(key=="time"){
  	        				time=fileMap[key];
  	        			}else{
  	        				fileSaveName=key;
  	        			    fileRelName=fileMap[key];
  	        			}
//   	        			insertFileRow(saveFileName,fileName,time);
  	        		}
  	        		$('#file_upload').uploadifive('clearQueue');
  	        		fillInputs();
  	        	}else{
  	        		jAlert(data.msg,'提示');
  	        	}
  	        	$('#file_upload').uploadifive('clearQueue');
  	        },
  	        'onError'      : function(errorType) {
  	        	$('#file_upload').uploadifive('clearQueue');
            },
            'onError'      : function(errorType) {
                alert('The error was: ' + errorType);
            }
  		});

		}
	function fillInputs(){
		if (fileRelName != '' && fileRelName.length > 0) {
			var fileDesc=fileRelName;
			if (fileRelName.lastIndexOf(".") != -1) { //有拓展名
				fileDesc = fileRelName.substring(0, fileRelName.lastIndexOf("."));
			}
			$("#fileRelName").val(fileRelName);
			$("#fileDesc").val(fileDesc);
			$("#createTime").val(time);
			$("#fileSaveName").val(fileSaveName);
			$("#filePerName").val($("#myName").val());
		}
	}
	function initValidadte(){
		$("#basicInfoForm").validate({
			 rules:{
				 'fileDesc':{
					 maxlength:100,
					 required:true
				 },
				 'stageId':{
					 maxlength:20,
					 required:true
				 }
			 },
			 messages:{
				 'fileDesc':{
					 maxlength:'文件描述过长',
					 required:'文件描述不能为空'
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
			    url : 'order/saveFile.do',
			    type : 'post',
			    dataType : 'json',
			    error : function(data) {
			     	$("#loading-mask").fadeOut();
			      	alert(data.msg);
			    },
			    success : function(data) {
			       	$("#loading-mask").fadeOut();
				    if (data.code == 0) {
					    var file=data.p2pdata;
					    jAlert('保存成功', '提示');
				    	window.opener.insertFileRow(file);
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
