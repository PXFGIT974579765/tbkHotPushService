package com.pxf.first.frame.app.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.app.system.Config;

public class ReportExcelUtil {
	 // �����ʽ ���� �»��� б�� ���� ��ɫ
	private static WritableFont wf_head = new WritableFont(WritableFont.ARIAL, 11,
			WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,    
            jxl.format.Colour.BLACK);
	private static WritableCellFormat wcf_head1 = new WritableCellFormat(wf_head);     
    
	public static ResultMsg WriteToExcel(List list, String fileName, String sheetName, Vector<String> columnName){
		WritableWorkbook workbook = null;  
        WritableSheet sheet = null;  
        try {
        	wcf_head1.setBackground(jxl.format.Colour.WHITE);    
			wcf_head1.setAlignment(jxl.format.Alignment.CENTRE);
			wcf_head1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE); // ���ô�ֱ���뷽ʽ
			wcf_head1.setBorder(Border.ALL, BorderLineStyle.HAIR); 
			wcf_head1.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf_head1.setOrientation(Orientation.HORIZONTAL);
		} catch (WriteException e1) {
			e1.printStackTrace();
		}     
        ResultMsg result;
        PropertyUtil pro=PropertyUtil.getInstance(Config.PRO_FILE_SYSTEM);
		//��������Ŀ¼
		String savePath = PropertyUtil.getProperty(Config.FILE_SAVE_PATH) ;
		//�õ��ļ����������
		String saveFilename = UploadUtil.makeFileName(fileName);
		//�õ��ļ��ı���Ŀ¼
		String realSavePath = UploadUtil.makePath(saveFilename, savePath);
		Map<String,String> fileNameMap = new HashMap<String,String>(); 
        int rowNum = 1; // �ӵ�һ�п�ʼд�� 
        File file=null;
        try {  
        	file=new File((new StringBuilder(String.valueOf(realSavePath))).append(File.separator).append(saveFilename).toString());
        	// ����Excel�ļ� 
            workbook = Workbook.createWorkbook(file);  
            // ������Ϊ sheetName �Ĺ�����   
            sheet = workbook.createSheet(sheetName, 0); 
           
            writeCol(sheet, columnName, 0,wcf_head1); // ���Ƚ�����д��  
              
        }catch(Exception e) {  
            e.printStackTrace();  
        }  
        finally {  
            try {  
                // �ر�  
                workbook.write();  
                workbook.close();  
                String realName = file.getName().substring(file.getName().indexOf("_")+1);
                fileNameMap.put(file.getName(), realName);
                result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), fileNameMap);
			    return result;
            }catch(Exception e) {  
                e.printStackTrace();  
            }  
        }
		return null;
		
	}
	/*** 
     * ������д�빤����  
     * @param sheet Ҫд��Ĺ����� 
     * @param col Ҫд����������� 
     * @param rowNum Ҫд����һ�� 
     * @throws WriteException  
     * @throws RowsExceededException  
     */  
    private static void writeCol(WritableSheet sheet, Vector<String> col, int rowNum,WritableCellFormat format) throws RowsExceededException, WriteException {  
        int size = col.size(); // ��ȡ���ϴ�С  
          
        for(int i = 0; i < size; i++) { // д��ÿһ��  
        	sheet.setColumnView(i, 24);
            Label label = new Label(i, rowNum, col.get(i),format);   
            sheet.addCell(label);  
        }  
    }

}
