package com.pxf.first.frame.controller.upload.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.app.utils.ReportExcelUtil;
import com.pxf.first.frame.app.utils.UploadUtil;
import com.pxf.first.frame.controller.base.controller.BaseController;
import com.pxf.first.frame.enty.product.bo.FileInfo;
import com.pxf.first.frame.service.order.service.IOrderMgrService;

@Controller
@RequestMapping(value = "/file")
public class UploadController extends BaseController {
	private static final Logger LOG = Logger.getLogger(UploadController.class);
	@Autowired
	private IOrderMgrService orderService;// ע��ҵ��ӿ�
	@RequestMapping(value = "/upload.do")
	public void uploadFile(HttpServletRequest request,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ResultMsg result=UploadUtil.uploadFile(request);
		writeObjAsJsonObjToClient(response, result);
	}
	
	@RequestMapping(value = "/download.do")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,String fileId,String filePerAcc,String myAccount) throws JsonGenerationException, JsonMappingException, IOException {
		ResultMsg result;
		FileInfo file=this.orderService.queryByFileId(fileId);
		if(file!=null&&file.getFileFlag().equals("toSelf")){
			if(filePerAcc!=null&&!filePerAcc.equals(myAccount)){
				result=new ResultMsg(ResultStatusCode.FILE_IS_PRIVATE.getCode(), ResultStatusCode.FILE_IS_PRIVATE.getMsg(), null);
			}else{
				result=UploadUtil.downLoadFile(request, response);
			}
		}else{
			result=UploadUtil.downLoadFile(request, response);
		}
		if(result!=null){
			writeObjAsJsonObjToClient(response, result);
		}
	}
	
	@RequestMapping(value = "/newBugetTemplate.do")
	public void newBugetTemplate(HttpServletRequest request,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ResultMsg result;
		String[] dataItems=request.getParameterValues("dataItem");
		Map<Integer,String> map=new TreeMap<Integer, String>();
		if(dataItems!=null){
			for (int i = 0; i < dataItems.length; i++) {
				int key=Integer.parseInt(dataItems[i].split("-")[1]);
				String value=dataItems[i].split("-")[0];
				map.put(key, value);
			}
			Set<Integer> keySet=map.keySet();
			Vector<String> vc=new Vector<String>();
			for (int key:keySet) {
				vc.add(map.get(key));
			}
			result=ReportExcelUtil.WriteToExcel(null,"预算模板.xls","sheet",vc);
		}else{
			result=new ResultMsg(ResultStatusCode.NO_SELECT_DATA_ITEM.getCode(), ResultStatusCode.NO_SELECT_DATA_ITEM.getMsg(), null);
		}
		writeObjAsJsonObjToClient(response, result);
	}
	


}
