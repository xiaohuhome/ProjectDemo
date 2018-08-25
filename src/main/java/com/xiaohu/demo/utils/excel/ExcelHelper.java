package com.xiaohu.demo.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <p>标题: ExcelHelper.java</p>
 */
public class ExcelHelper {
    private final static String XLS = "xls";  
    private final static String XLSX = "xlsx";  
    private final static String SEPARATOR = "|";  
  
    /** 
     * 由Excel文件的Sheet导出至List 
     *  
     * @param file 
     * @param sheetNum 
     * @return 
     */  
    public static List<String> exportListFromExcel(File file, int sheetNum)  
            throws IOException {  
        return exportListFromExcel(new FileInputStream(file),  
                FilenameUtils.getExtension(file.getName()), sheetNum);  
    }  
  
    /** 
     * 由Excel流的Sheet导出至List 
     *  
     * @param is 
     * @param extensionName 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    public static List<String> exportListFromExcel(InputStream is,  
            String extensionName, int sheetNum) throws IOException {  
  
        Workbook workbook = null;  
  
        if (extensionName.toLowerCase().equals(XLS)) {  
            workbook = new HSSFWorkbook(is);  
        } else if (extensionName.toLowerCase().equals(XLSX)) {  
            workbook = new XSSFWorkbook(is);  
        }  
        return exportListFromExcel(workbook, sheetNum);  
    }  
  
    /** 
     * 由指定的Sheet导出至List 
     *  
     * @param workbook 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    private static List<String> exportListFromExcel(Workbook workbook,  
            int sheetNum) {  
  
        Sheet sheet = workbook.getSheetAt(sheetNum);  
  
        // 解析公式结果  
        FormulaEvaluator evaluator = workbook.getCreationHelper()  
                .createFormulaEvaluator();  
  
        List<String> list = new ArrayList<String>();  
  
        int minRowIx = sheet.getFirstRowNum();  
        int maxRowIx = sheet.getLastRowNum();  
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {  
            Row row = sheet.getRow(rowIx);  
            StringBuilder sb = new StringBuilder();  
  
            short minColIx = row.getFirstCellNum();  
            short maxColIx = row.getLastCellNum();  
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {  
            	if(sheet.isColumnHidden(colIx)){
            		continue;
            	}
                Cell cell = row.getCell(new Integer(colIx));  
                CellValue cellValue = evaluator.evaluate(cell);  
                if (cellValue == null) { 
                	sb.append(SEPARATOR);
                    continue;  
                }  
                sb.append(SEPARATOR);
                switch (cellValue.getCellType()) {  
                case Cell.CELL_TYPE_BOOLEAN:  
                    sb.append(cellValue.getBooleanValue());  
                    break;  
                case Cell.CELL_TYPE_NUMERIC:  
                    // 这里的日期类型会被转换为数字类型，需要判别后区分处理  
                    if (DateUtil.isCellDateFormatted(cell)) {  
                    	Date d = cell.getDateCellValue();
                    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        sb.append(sdf.format(d));  
                    } else {  
                    	
                    	Double tmpDou = cellValue.getNumberValue();//转换单元格值的类型为double类型
                        Long tmpMno = tmpDou.longValue();//把单元格的值1.2222的值转换成正常输入的数字
                        sb.append(tmpMno.toString().trim());  
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    sb.append(cellValue.getStringValue());  
                    break;  
                case Cell.CELL_TYPE_FORMULA: 
                	sb.append(" ");  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                	sb.append(" ");  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                	sb.append(" ");  
                    break;  
                default:  
                    break;  
                }  
            }  
            list.add(sb.toString());  
        }  
        return list;  
    }  
    
    public static void main(String[] args) {
    	String path = "C://aa.xlsx"; 
    	List<String> list = null;
        try {
			list = ExcelHelper.exportListFromExcel(new File(path), 0);
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        for(String str:list){
        	System.out.println(str);
        }
	}
}
