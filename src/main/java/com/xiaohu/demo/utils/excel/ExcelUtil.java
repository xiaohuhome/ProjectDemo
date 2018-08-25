package com.xiaohu.demo.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.xiaohu.demo.utils.date.DateUtils;

/**
 * 
 * <p>标题: ExcelUtil.java</p>
 * <p>业务描述: </p>
 */
@SuppressWarnings("deprecation")
public class ExcelUtil {
	/**
	 * 获得内容单元格样式.
	 * 
	 * @param wb
	 * @return
	 */
	private static HSSFWorkbook nowBook_c = null;
	private static HSSFWorkbook nowBook_r = null;
	private static HSSFCellStyle style_content_c = null;
	private static HSSFCellStyle style_content_r = null;
	public static HSSFCellStyle getCellStyle_Content(HSSFWorkbook wb,short alignStyle) {
		
		// 定义字体样式
		if(alignStyle == HSSFCellStyle.ALIGN_CENTER){
			if(nowBook_c != null && nowBook_c == wb && style_content_c != null ){
				return style_content_c;
			} 
			else {
				HSSFFont font_content = wb.createFont();
				font_content.setFontName(HSSFFont.FONT_ARIAL);
				font_content.setFontHeightInPoints((short)10);
				font_content.setColor(HSSFColor.BLACK.index);
				
				// 定义列印样式			
				// 内容样式
				style_content_c = wb.createCellStyle();
				style_content_c.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style_content_c.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				style_content_c.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style_content_c.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style_content_c.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style_content_c.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style_content_c.setFont(font_content);
				style_content_c.setWrapText(true);
				nowBook_c = wb;
			}
			
			return style_content_c;
		} else if(alignStyle == HSSFCellStyle.ALIGN_RIGHT	){
			if(nowBook_r != null && nowBook_r == wb && style_content_r != null ){
				return style_content_r;
			} 
			else {
				HSSFFont font_content = wb.createFont();
				font_content.setFontName(HSSFFont.FONT_ARIAL);
				font_content.setFontHeightInPoints((short)10);
				font_content.setColor(HSSFColor.BLACK.index);
				
				// 定义列印样式			
				// 内容样式
				style_content_r = wb.createCellStyle();
				style_content_r.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				style_content_r.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				style_content_r.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style_content_r.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style_content_r.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style_content_r.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style_content_r.setFont(font_content);
				style_content_r.setWrapText(true);
				nowBook_r = wb;
			}
			
			return style_content_r;
		} else {
			return null;
		}
		
	}

	/**
	 * 对某个sheet进行纵向合并单元格.
	 * 
	 * @param sheet 
	 * @param intStartRow 合并开始行
	 * @param mergeColumn 要合并的列
	 * @param relyColumn  依赖的列数组
	 */
	public static void mergeCell(HSSFSheet sheet, int intStartRow, int mergeColumn, String[] relyColumn) {
		int rowLength = sheet.getLastRowNum();

		// 合并m行
		int m = 1;
		
		for (int i = intStartRow; i < rowLength; i++) {
			String strContent1 = getStringValue(sheet.getRow(i).getCell((short)mergeColumn));
			
			for (int j = i + 1; j < rowLength + 1; j++) {
				String strContent2 = getStringValue(sheet.getRow(j).getCell((short)mergeColumn));
			
				if (strContent1.equals(strContent2)) {
					
					if (relyColumn != null && relyColumn.length > 0) {
						
						if (isApply(sheet, i, j, relyColumn)) {
							m++;
						}
						else {
							break;
						}
					}
					else {
						m++;
					}
				}
				else {
					break;
				}
				
				if (m > 1) {
					sheet.addMergedRegion(new Region(i, (short) mergeColumn, i+m-1, (short) mergeColumn));
				}
				
				i += (m - 1);
				m = 1;
			}
		}
	}
	
	/**
	 * 对某个sheet进行纵向合并单元格(WorkBook版本).
	 * 
	 * @param sheet 
	 * @param intStartRow 合并开始行
	 * @param mergeColumn 要合并的列
	 * @param relyColumn  依赖的列数组
	 */
	public static void mergeCell(Sheet sheet, int intStartRow, int mergeColumn, String[] relyColumn) {
		int rowLength = sheet.getLastRowNum();
		// 合并m行
		int m = 1;
		for (int i = intStartRow; i < rowLength; i++) {
			String strContent1 = getStringValue(sheet.getRow(i).getCell(mergeColumn));
			if(strContent1 == null){
				strContent1 = "";
			}
			for (int j = i + 1; j < rowLength+1; j++) {
				String strContent2 = getStringValue(sheet.getRow(j).getCell(mergeColumn));
				if(strContent2 == null){
					strContent2 = "";
				}
				if (strContent1.equals(strContent2)) {
					
					if (relyColumn != null && relyColumn.length > 0) {
						
						if (isApply(sheet, i, j, relyColumn)) {
							m++;
						}
						else {
							break;
						}
					}
					else {
						m++;
					}
				}
				else {
					break;
				}
			}
			
			if (m > 1) {
				sheet.addMergedRegion(new CellRangeAddress(i,i+m-1, mergeColumn,  mergeColumn));
			}
			
			i += (m - 1);
			m = 1;			
		}
	}
	
	/**
	 * 判断单元格内容是否相同.
	 * 
	 * @param i 行号
	 * @param j 行号
	 * @param relyColumn 列号数组
	 * @return
	 */
	private static boolean isApply(HSSFSheet sheet, int i, int j, String[] relyColumn) {
		
		// 定义返回值
		boolean blnApply = true;
		
		for (int k = 0; k < relyColumn.length; k++) {
			int intColumn = Integer.parseInt(relyColumn[k]);
			String strContent1 = getStringValue(sheet.getRow(i).getCell((short)intColumn));
			String strContent2 = getStringValue(sheet.getRow(j).getCell((short)intColumn));
	
			if (((strContent1 != null) || (strContent2 != null)) && (!strContent1.trim().equals(strContent2.trim()))) {
				blnApply = false;
				break;
			}
		}
 
		return blnApply;
	}
	
	/**
	 * 对某个sheet进行纵向合并单元格.
	 * 
	 * @param sheet 
	 * @param intStartRow 合并开始行
	 * @param mergeColumn 要合并的列
	 * @param relyColumn  依赖的列数组
	 */
	public static void mergeRow(Sheet sheet, int intStartColumn, int mergeRow) {
		int colLength = sheet.getRow(0).getLastCellNum();

		// 合并m行
		int m = 1;
		
		for (int i = intStartColumn; i < colLength; i++) {
			String strContent1 = getStringValue(sheet.getRow(mergeRow).getCell((short)i));
			
			for (int j = i + 1; j < colLength; j++) {
				String strContent2 = getStringValue(sheet.getRow(mergeRow).getCell((short)j));
			
				if (strContent1.equals(strContent2)) {
					m++;
				}
				else {
					break;
				}
			}
			
			if (m > 1) {
				sheet.addMergedRegion(new CellRangeAddress(mergeRow, mergeRow, i,  i+m-1));
			}
			
			i += (m - 1);
			m = 1;			
		}
	}	
	
	/**
	 * 获得某单元格的内容.
	 * @param cell
	 * @return
	 */
	public static String getStringValue(HSSFCell cell) {
	  
    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
      return cell.getRichStringCellValue().toString();
    }
    else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
      
      if (HSSFDateUtil.isCellDateFormatted(cell)) {
      	return DateUtils.formatDate(cell.getDateCellValue(), "yyyy-MM-dd");
      }
      else {
        return String.valueOf(cell.getNumericCellValue());
      }
    }
    else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
      return ""; 
    }	
    
    return null;
	}
	
	/**
	 * 判断单元格内容是否相同(WorkBook版）.
	 * 
	 * @param i 行号
	 * @param j 行号
	 * @param relyColumn 列号数组
	 * @return
	 */
	private static boolean isApply(Sheet sheet, int i, int j, String[] relyColumn) {
		
		// 定义返回值
		boolean blnApply = true;
		
		for (int k = 0; k < relyColumn.length; k++) {
			int intColumn = Integer.parseInt(relyColumn[k]);
			String strContent1 = getStringValue(sheet.getRow(i).getCell((short)intColumn));
			String strContent2 = getStringValue(sheet.getRow(j).getCell((short)intColumn));
	
			if (((strContent1 != null) || (strContent2 != null)) && (!strContent1.trim().equals(strContent2.trim()))) {
				blnApply = false;
				break;
			}
		}
 
		return blnApply;
	}
	
	/**
	 * 获得某单元格的内容.(WorkBook版本)
	 * @param cell
	 * @return
	 */
	public static String getStringValue(Cell cell) {
		
	if(cell == null){
		return "";
	}
    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
      return cell.getRichStringCellValue().toString();
    }
    else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
      
      if (HSSFDateUtil.isCellDateFormatted(cell)) {
        return DateUtils.formatDate(cell.getDateCellValue(), "yyyy-MM-dd");
      }
      else {
        return String.valueOf(cell.getNumericCellValue());
      }
    }
    else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
      return ""; 
    }	
    
    return null;
	} 
}
