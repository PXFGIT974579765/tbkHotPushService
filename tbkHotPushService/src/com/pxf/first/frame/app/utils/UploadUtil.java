package com.pxf.first.frame.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.app.system.Config;

public class UploadUtil {
	public static ResultMsg uploadFile(HttpServletRequest request){
		ResultMsg result;
		PropertyUtil pro=PropertyUtil.getInstance(Config.PRO_FILE_SYSTEM);
		//附件保存目录
		String savePath = PropertyUtil.getProperty(Config.FILE_SAVE_PATH) ;
		Map<String,String> fileNameMap = new HashMap<String,String>();
		//上传时生成的临时文件保存目录
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/temp");
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()){
			tmpFile.mkdirs();
		}
		//消息提示
		String message = "";
		//1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory fac = new DiskFileItemFactory();
		//2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(fac);
		//解决上传文件名的中文乱码
		upload.setHeaderEncoding("utf-8");
		//设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
		fac.setSizeThreshold(1024*100);
		//设置上传时生成的临时文件的保存目录
		fac.setRepository(tmpFile);
		//设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是10MB
		upload.setFileSizeMax(1024*1024*10);
		//设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为5个文件
		upload.setSizeMax(1024*1024*10*5);
		List fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			return null;
		}
		Iterator it = fileList.iterator();
		String name = "";
		String extName = "";
		while (it.hasNext()) {
			FileItem item = (FileItem) it.next();
			if (!item.isFormField()) {
				name = item.getName();
				if(name==null || name.trim().equals("")){
					continue;
				}
				//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
				name = name.substring(name.lastIndexOf("\\")+1);
				if (name != null && !name.trim().equals("")) {
					if (name.lastIndexOf(".") >= 0)
						extName = name.substring(name.lastIndexOf("."));
					File file = null;
					//获取item中的上传文件的输入流
					InputStream in;
					//文件保存的名称
					String saveFilename;
					//文件的保存目录
					String realSavePath;
					FileOutputStream out;
					//一下操作是防止重复文件
					do {
							//得到文件保存的名称
							saveFilename = makeFileName(name);
							//得到文件的保存目录
							realSavePath = makePath(saveFilename, savePath);
							//创建一个文件输出流
							file = new File((new StringBuilder(String.valueOf(realSavePath))).append(File.separator).append(saveFilename).toString());
					} while (file.exists());
					try {
							in = item.getInputStream();
							out = new FileOutputStream(file);
							//创建一个缓冲区
							byte buffer[] = new byte[1024];
							//判断输入流中的数据是否已经读完的标识
							int len = 0;
							//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
							while((len=in.read(buffer))>0){
								//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
								out.write(buffer, 0, len);
							}
							//关闭输入流
							in.close();
							//关闭输出流
							out.close();
							//删除处理文件上传时生成的临时文件
							item.delete();
							message = "文件上传成功！";
							/**
							 *处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
							 *file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849-88343-8344_阿_凡_达.avi
							 *那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到阿_凡_达.avi部分
							*/
							String realName = file.getName().substring(file.getName().indexOf("_")+1);
							//file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
							fileNameMap.put(file.getName(), realName);
							fileNameMap.put("time", DateUtil.dateTime("yyyy-MM-dd"));
							result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), fileNameMap);
						    return result;
						} catch (IOException e) {
							result=new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), null);
							return result;
						}
				}
			}
		}
		return null;
	}
	
	public static ResultMsg downLoadFile(HttpServletRequest request,HttpServletResponse response)throws JsonGenerationException, JsonMappingException, IOException{
		ResultMsg result;
		String saveFileName = java.net.URLDecoder.decode(request.getParameter("saveFileName"),"utf-8");
		PropertyUtil pro=PropertyUtil.getInstance(Config.PRO_FILE_SYSTEM);
		//附件保存目录
		String fileSaveRootPath=PropertyUtil.getProperty(Config.FILE_SAVE_PATH) ;
		//通过文件名找出文件的所在目录
		String path = findFileSavePathByFileName(saveFileName,fileSaveRootPath);
		 //得到要下载的文件
		File file = new File(path + File.separator + saveFileName);
		 //如果文件不存在
		if (!file.exists()) {
			result=new ResultMsg(ResultStatusCode.FILE_HAS_BE_DELETED.getCode(), ResultStatusCode.FILE_HAS_BE_DELETED.getMsg(), null);
			return result;
		}
		//处理文件名
		String realname = saveFileName.substring(saveFileName.indexOf("_")+1);
		//设置响应头，控制浏览器下载该文件
//		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+new String(realname.getBytes("gbk"),"iso-8859-1"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(path + File.separator + saveFileName);
		//创建输出流
		OutputStream out = response.getOutputStream();
		//创建缓冲区 
		byte buffer[] = new byte[1024];
		int len = 0;
		// 循环将输入流中的内容读取到缓冲区当中
		while ((len = in.read(buffer)) > 0) {
			// 输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		// 关闭文件输入流 50
		in.close();
		// 关闭输出流 52
		out.close();
		return null;
	}

	/**
	 * @Method: makeFileName
	 * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * @Anthor:孤傲苍狼
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称
	 */
	public static String makeFileName(String filename) { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * 
	 * @Method: makePath
	 * @Description:
	 * @Anthor:孤傲苍狼
	 * 
	 * @param filename
	 *            文件名，要根据文件名生成存储目录
	 * @param savePath
	 *            文件存储路径
	 * @return 新的存储目录
	 */
	public static String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf;
		// 0--15
		int dir2 = (hashcode & 0xf0) >> 4;
		// 0-15
		// 构造新的保存目录 173
		String dir = savePath + "\\" + dir1 + "\\" + dir2;
		// upload\2\3 upload\3\5
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录 179
			file.mkdirs();
		}
		return dir;
	}
	/**      * @Method: findFileSavePathByFileName
     * @Description: 通过文件名和存储上传文件根目录找出要下载的文件的所在路径
     * @Anthor:孤傲苍狼
     * @param filename 要下载的文件名
     * @param saveRootPath 上传文件保存的根目录，也就是/WEB-INF/upload目录
     * @return 要下载的文件的存储目录
 */ 
public static String findFileSavePathByFileName(String filename,
		String saveRootPath) {
	int hashcode = filename.hashCode();
	int dir1 = hashcode & 0xf; // 0--15 66
	int dir2 = (hashcode & 0xf0) >> 4; // 0-15 67
	String dir = saveRootPath + File.separator + dir1 + File.separator + dir2; // upload\2\3   // upload\3\5
	File file = new File(dir);
	if (!file.exists()) {
		// 创建目录 71
		file.mkdirs();
	}
	return dir;
}


}
