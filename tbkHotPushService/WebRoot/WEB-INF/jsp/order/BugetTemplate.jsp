<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>云单追踪管理系统</title>
  </head>
  <%@ include file="../common/include.jsp" %>
  <link rel="stylesheet" type="text/css" href="<%=basePath%>css/BugetTemplate.css" />
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
        <span id="" >预算数据项选择</span>
   </div>
<!-- 上半部分 -->
   <div class="content-up">
      <form name="basicInfoForm" id="basicInfoForm">
      <small>注:以下输入框中填写数据项在Excel中的序号</small>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="序号" />序号</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="施工项目名称" />施工项目名称</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="单位" />单位</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="工程量" />工程量</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="单价" />单价</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="小计金额" />小计金额</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="工艺材料及说明" />工艺材料及说明</lable>
      </div>
      <div class="col">
           <input name="index" type="text" id="index"  />
          <lable><input name="dataItem" type="checkbox" value="工程结算方式" />工程结算方式</lable>
      </div>
      
      </form>
   </div>
<!--    中间标题部分 -->
   <div class="title">
        <span id="btn-close" onclick="javascript:window.close();">关闭</span>
        <span id="btn-newTemplate">生成Excel模板</span>
        <span id="btn-newDataItem" onclick="addDataItem();">新增数据项</span>
   </div>
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
	$(function(){
		var token=$("#token",window.opener.document).val();
		$("#token").val(token);
		$("#btn-newTemplate").click(function(){
			if(checkInputNotNull()){
			   if(!checkInputIndexHasEq()){
				   setValueIndex();
				   ajaxSubmit();
			   }
			}
		});
	});
	function ajaxSubmit(){
		$("#loading-mask").fadeIn();
		var formParam = $("#basicInfoForm").serialize();
		formParam += '&token=' + $("#token").val();
		$.ajax({
			data : formParam,
			url : 'file/newBugetTemplate.do',
			type : 'post',
			dataType : 'json',
			error : function(data) {
				$("#loading-mask").fadeOut();
				resetInputValue();
				alert(data.msg);
			},
			success : function(data) {
				$("#loading-mask").fadeOut();
				resetInputValue();
				if (data.code == 0) {
					var fileMap=data.p2pdata;
  	        		for(var key in fileMap){
  	        			saveFileName=key;
  	        			fileName=fileMap[key];
  	        			insertFileRow(saveFileName,fileName);
  	        		}
					jAlert('生成预算模板成功', '提示');
				} else {
					jAlert(data.msg, '提示');
				}
			}

		});
	}
	function insertFileRow(saveFileName, fileName) {
		if($(".content-bottom").length==0){
			var sb=new StringBuffer();
			sb.append('<div class="content-bottom">')
	        .append('</div>');
			$(".title").after(sb.toString());
		}
		
		
		if (fileName != '' && fileName.length > 0) {
			var sb=new StringBuffer();
			if($(".content-bottom").find("a").length!=0){
				$(".content-bottom").find("a").remove();
			}
			sb.append('<a href="" onclick="toDownLoadFile(this);" rel="').append(saveFileName).append('">').append(fileName).append('</a>');
			sb.append('<input id="saveFileName" type="hidden" value="').append(saveFileName).append('">');
			$(".content-bottom").append(sb.toString());
		}
	}
	function toDownLoadFile(obj){
		var obj1=$(obj);
		var url='<%=basePath%>/file/download.do?token='+$("#token").val()+'&saveFileName='+encodeURI(encodeURI(saveFileName));
		obj1.attr('href',url);
	}
	function setValueIndex(){
		$("input[type=checkbox]:checked").each(function(i,e){ 
			var index=$(this).parent().parent().find("#index").val().trim();
			var value=$(this).val();
			$(this).val(value+'-'+index);
		});
	}
	function resetInputValue(){
		$("input[type=checkbox]:checked").each(function(i,e){ 
			var value=$(this).val();
			if (value.lastIndexOf("-") != -1) { 
				value = value.substring(0, value.lastIndexOf("-"));
			}
			$(this).val(value);
		});
	}
	function checkInputIndexHasEq(){
		var hasEq=0;
		$("input[type=checkbox]:checked").each(function(i,e){ 
			var value=$(this).parent().parent().find("#index").val().trim();
			var obj=$(this);
			$("input[type=checkbox]:checked").each(function(j,e1){ 
				var value1=$(this).parent().parent().find("#index").val().trim();
				var obj1=$(this);
				if(value==value1){
					if(i!=j){
						jAlert('"'+obj.val()+'"'+'的序号和'+'"'+obj1.val()+'"'+'的序号相等','提示');
						hasEq=1;
						return false;
					}
				}
			});
		});
		if(hasEq==1){
			return true;
		}else{
			return false;
		}
	}
	function checkInputNotNull(){
		var hasNull=0;
		$("input[type=checkbox]:checked").each(function(){ 
		    var index=$(this).parent().parent().find("#index").val();
		    var value=$(this).val();
		    if(index==null||index==''){
		    	jAlert('请填写'+'"'+value+'"'+'的序号','提示');
		    	hasNull=1;
		    	return false;
		    }
		  });
		if(hasNull==1){
	    	return false;
		}else{
			return true;
		}
	}
	function addDataItem(){
		var sb=new StringBuffer();
		jPrompt('输入数据项名称','','提示',function(result){
		     sb.append('<div class="col" style="margin-left:9px">')
              .append('<input name="index" type="text" id="index"  />')
              .append('<lable><input name="dataItem" type="checkbox" value="').append(result).append('" />').append(result).append('</lable>')
             .append('</div>');
		$("div[class=col]:last").after(sb.toString());
			
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
