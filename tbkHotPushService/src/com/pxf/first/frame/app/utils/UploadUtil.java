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
		//��������Ŀ¼
		String savePath = PropertyUtil.getProperty(Config.FILE_SAVE_PATH) ;
		Map<String,String> fileNameMap = new HashMap<String,String>();
		//�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/temp");
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()){
			tmpFile.mkdirs();
		}
		//��Ϣ��ʾ
		String message = "";
		//1������һ��DiskFileItemFactory����
		DiskFileItemFactory fac = new DiskFileItemFactory();
		//2������һ���ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(fac);
		//����ϴ��ļ�������������
		upload.setHeaderEncoding("utf-8");
		//���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
		fac.setSizeThreshold(1024*100);
		//�����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
		fac.setRepository(tmpFile);
		//�����ϴ������ļ��Ĵ�С�����ֵ��Ŀǰ������Ϊ1024*1024�ֽڣ�Ҳ����10MB
		upload.setFileSizeMax(1024*1024*10);
		//�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ5���ļ�
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
				//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
				name = name.substring(name.lastIndexOf("\\")+1);
				if (name != null && !name.trim().equals("")) {
					if (name.lastIndexOf(".") >= 0)
						extName = name.substring(name.lastIndexOf("."));
					File file = null;
					//��ȡitem�е��ϴ��ļ���������
					InputStream in;
					//�ļ����������
					String saveFilename;
					//�ļ��ı���Ŀ¼
					String realSavePath;
					FileOutputStream out;
					//һ�²����Ƿ�ֹ�ظ��ļ�
					do {
							//�õ��ļ����������
							saveFilename = makeFileName(name);
							//�õ��ļ��ı���Ŀ¼
							realSavePath = makePath(saveFilename, savePath);
							//����һ���ļ������
							file = new File((new StringBuilder(String.valueOf(realSavePath))).append(File.separator).append(saveFilename).toString());
					} while (file.exists());
					try {
							in = item.getInputStream();
							out = new FileOutputStream(file);
							//����һ��������
							byte buffer[] = new byte[1024];
							//�ж��������е������Ƿ��Ѿ�����ı�ʶ
							int len = 0;
							//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
							while((len=in.read(buffer))>0){
								//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
								out.write(buffer, 0, len);
							}
							//�ر�������
							in.close();
							//�ر������
							out.close();
							//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
							item.delete();
							message = "�ļ��ϴ��ɹ���";
							/**
							 *�����ļ������ϴ�����ļ�����uuid_�ļ�������ʽȥ���������ģ�ȥ���ļ�����uuid_����
							 *file.getName().indexOf("_")�����ַ����е�һ�γ���"_"�ַ���λ�ã�����ļ��������ڣ�9349249849-88343-8344_��_��_��.avi
							 *��ôfile.getName().substring(file.getName().indexOf("_")+1)����֮��Ϳ��Եõ���_��_��.avi����
							*/
							String realName = file.getName().substring(file.getName().indexOf("_")+1);
							//file.getName()�õ������ļ���ԭʼ���ƣ����������Ψһ�ģ���˿�����Ϊkey��realName�Ǵ����������ƣ��п��ܻ��ظ�
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
		//��������Ŀ¼
		String fileSaveRootPath=PropertyUtil.getProperty(Config.FILE_SAVE_PATH) ;
		//ͨ���ļ����ҳ��ļ�������Ŀ¼
		String path = findFileSavePathByFileName(saveFileName,fileSaveRootPath);
		 //�õ�Ҫ���ص��ļ�
		File file = new File(path + File.separator + saveFileName);
		 //����ļ�������
		if (!file.exists()) {
			result=new ResultMsg(ResultStatusCode.FILE_HAS_BE_DELETED.getCode(), ResultStatusCode.FILE_HAS_BE_DELETED.getMsg(), null);
			return result;
		}
		//�����ļ���
		String realname = saveFileName.substring(saveFileName.indexOf("_")+1);
		//������Ӧͷ��������������ظ��ļ�
//		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+new String(realname.getBytes("gbk"),"iso-8859-1"));
		//��ȡҪ���ص��ļ������浽�ļ�������
		FileInputStream in = new FileInputStream(path + File.separator + saveFileName);
		//���������
		OutputStream out = response.getOutputStream();
		//���������� 
		byte buffer[] = new byte[1024];
		int len = 0;
		// ѭ�����������е����ݶ�ȡ������������
		while ((len = in.read(buffer)) > 0) {
			// ��������������ݵ��������ʵ���ļ�����
			out.write(buffer, 0, len);
		}
		// �ر��ļ������� 50
		in.close();
		// �ر������ 52
		out.close();
		return null;
	}

	/**
	 * @Method: makeFileName
	 * @Description: �����ϴ��ļ����ļ������ļ����ԣ�uuid+"_"+�ļ���ԭʼ����
	 * @Anthor:�°�����
	 * @param filename
	 *            �ļ���ԭʼ����
	 * @return uuid+"_"+�ļ���ԭʼ����
	 */
	public static String makeFileName(String filename) { // 2.jpg
		// Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * * Ϊ��ֹһ��Ŀ¼�������̫���ļ���Ҫʹ��hash�㷨��ɢ�洢
	 * 
	 * @Method: makePath
	 * @Description:
	 * @Anthor:�°�����
	 * 
	 * @param filename
	 *            �ļ�����Ҫ�����ļ������ɴ洢Ŀ¼
	 * @param savePath
	 *            �ļ��洢·��
	 * @return �µĴ洢Ŀ¼
	 */
	public static String makePath(String filename, String savePath) {
		// �õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf;
		// 0--15
		int dir2 = (hashcode & 0xf0) >> 4;
		// 0-15
		// �����µı���Ŀ¼ 173
		String dir = savePath + "\\" + dir1 + "\\" + dir2;
		// upload\2\3 upload\3\5
		// File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		// ���Ŀ¼������
		if (!file.exists()) {
			// ����Ŀ¼ 179
			file.mkdirs();
		}
		return dir;
	}
	/**      * @Method: findFileSavePathByFileName
     * @Description: ͨ���ļ����ʹ洢�ϴ��ļ���Ŀ¼�ҳ�Ҫ���ص��ļ�������·��
     * @Anthor:�°�����
     * @param filename Ҫ���ص��ļ���
     * @param saveRootPath �ϴ��ļ�����ĸ�Ŀ¼��Ҳ����/WEB-INF/uploadĿ¼
     * @return Ҫ���ص��ļ��Ĵ洢Ŀ¼
 */ 
public static String findFileSavePathByFileName(String filename,
		String saveRootPath) {
	int hashcode = filename.hashCode();
	int dir1 = hashcode & 0xf; // 0--15 66
	int dir2 = (hashcode & 0xf0) >> 4; // 0-15 67
	String dir = saveRootPath + File.separator + dir1 + File.separator + dir2; // upload\2\3   // upload\3\5
	File file = new File(dir);
	if (!file.exists()) {
		// ����Ŀ¼ 71
		file.mkdirs();
	}
	return dir;
}


}
