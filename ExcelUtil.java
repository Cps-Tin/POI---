package cn.cps.util;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author _Cps
 * @create 2019-02-16 11:11
 */
public class ExcelUtil {

    /**
     * ����Excel
     * @param sheetName sheet����
     * @param title ����
     * @param values ����
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String []title, String [][]values){

        // 1.����һ��HSSFWorkbook����Ӧһ��Excel�ļ�
        HSSFWorkbook wb = new HSSFWorkbook();

        // 2.��workbook�����һ��sheet,��ӦExcel�ļ��е�sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(18);

        // 3.��sheet����ӱ�ͷ��0��,ע���ϰ汾poi��Excel����������������
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(30);

        // 4.1.���ñ�ͷ��Ԫ����ʽ
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont titleFont1 = wb.createFont();//����
        titleFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//����Ӵ�
        style1.setFont(titleFont1);//���õ���Ԫ����
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);//���и�ʽ
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//��ֱ����

        // 4.2.�������ݵ�Ԫ����ʽ
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont titleFont2 = wb.createFont();//����
        titleFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//�����������Ӵ�
        style2.setFont(titleFont2);//���õ���Ԫ����
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//���и�ʽ
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//��ֱ����

        //������Ԫ�����
        HSSFCell cell = null;

        //��������,��������ʽ
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellStyle(style1);
            cell.setCellValue(title[i]);
        }

        //�������ݣ���������ʽ
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //�����ݰ�˳�򸳸���Ӧ���ж���
                cell = row.createCell(j);
                cell.setCellStyle(style2);
                cell.setCellValue(values[i][j]);
            }
        }
        return wb;
    }



}
