package com.ibk.ivr.ca.common.vo;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.ibk.ivr.ca.common.Constants;
import com.ibk.ivr.ca.common.util.DateUtil;

public abstract class DefaultXlsxView extends AbstractXlsxView {
	
	protected CellStyle headerStyle;
	
	protected CellStyle intStyle;
	
	protected CellStyle floatStyle;
	
	protected CellStyle percentStyle;
	
	protected CellStyle ymdStyle;
	
	protected CellStyle style;
	
	protected Font font;
	
	protected Workbook workbook;
	
	protected CreationHelper createHelper;
	
	public DefaultXlsxView() {
		super();
	}
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = (String)model.get(Constants.FILE_NAME);
		this.workbook = workbook;
		this.createHelper = workbook.getCreationHelper();
		
		this.create(model, request);
		response.setHeader("Content-Disposition", "attachment;filename=".concat(URLEncoder.encode(fileName, "UTF-8")).concat("_").concat(DateUtil.getDateString("yyyyMMddHHmm")).concat(".xlsx;"));
		response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
		response.setContentType("application/download; utf-8");
	}
	
	protected abstract void create(Map<String, Object> model, HttpServletRequest request) throws Exception;

	protected CellStyle getTitleStyle() {
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.LEFT);
		titleStyle.setIndention((short)1);
        
        Font titleFont = workbook.createFont();
		titleFont.setFontName("Arial");
		titleFont.setBold(true);
		titleFont.setColor(IndexedColors.DARK_GREEN.getIndex());
		titleFont.setUnderline((byte)2);
		titleFont.setFontHeight((short)(400));
		titleStyle.setFont(titleFont);
        return titleStyle;
	}
	
	protected CellStyle getHeaderStyle() {
		if(headerStyle == null) {
			headerStyle = workbook.createCellStyle();
			headerStyle.setAlignment(HorizontalAlignment.CENTER);
			headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND );
			headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			headerStyle.setBorderBottom(BorderStyle.THIN);
			headerStyle.setBorderLeft(BorderStyle.THIN);
			headerStyle.setBorderRight(BorderStyle.THIN);
			headerStyle.setBorderTop(BorderStyle.THIN);
	        
	        Font titleFont = workbook.createFont();
			titleFont.setFontName("Arial");
			titleFont.setBold(true);
			titleFont.setColor(IndexedColors.BLACK.getIndex());
			headerStyle.setFont(titleFont);
		}
        return headerStyle;
	}
	
	protected CellStyle getIntStyle() {
		if(intStyle == null) {
			DataFormat df = workbook.createDataFormat();
			intStyle = workbook.createCellStyle();
			intStyle.setDataFormat(df.getFormat("#,###"));
			intStyle.setAlignment(HorizontalAlignment.RIGHT);
			intStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			intStyle.setBorderBottom(BorderStyle.THIN);
			intStyle.setBorderLeft(BorderStyle.THIN);
			intStyle.setBorderRight(BorderStyle.THIN);
			intStyle.setBorderTop(BorderStyle.THIN);
	        
			intStyle.setFont(getFont());
		}
        return intStyle;
	}
	
	protected CellStyle getFloatStyle() {
		if(floatStyle == null) {
			DataFormat df = workbook.createDataFormat();
			floatStyle = workbook.createCellStyle();
			floatStyle.setDataFormat(df.getFormat("#,###.00"));
			floatStyle.setAlignment(HorizontalAlignment.RIGHT);
			floatStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			floatStyle.setBorderBottom(BorderStyle.THIN);
			floatStyle.setBorderLeft(BorderStyle.THIN);
			floatStyle.setBorderRight(BorderStyle.THIN);
			floatStyle.setBorderTop(BorderStyle.THIN);
	        
			floatStyle.setFont(getFont());
		}
        return floatStyle;
	}
	
	protected CellStyle getPercentStyle() {
		if(percentStyle == null) {
			DataFormat df = workbook.createDataFormat();
			percentStyle = workbook.createCellStyle();
			percentStyle.setDataFormat(df.getFormat(BuiltinFormats.getBuiltinFormat(10)));
			percentStyle.setAlignment(HorizontalAlignment.RIGHT);
			percentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			percentStyle.setBorderBottom(BorderStyle.THIN);
			percentStyle.setBorderLeft(BorderStyle.THIN);
			percentStyle.setBorderRight(BorderStyle.THIN);
			percentStyle.setBorderTop(BorderStyle.THIN);
	        
			percentStyle.setFont(getFont());
		}
        return percentStyle;
	}
	
	protected CellStyle getYmdStyle() {
		if(ymdStyle == null) {
			DataFormat df = workbook.createDataFormat();
			
			ymdStyle = workbook.createCellStyle();
			ymdStyle.setDataFormat(df.getFormat("m/d/yy"));
			ymdStyle.setAlignment(HorizontalAlignment.CENTER);
			ymdStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			ymdStyle.setBorderBottom(BorderStyle.THIN);
			ymdStyle.setBorderLeft(BorderStyle.THIN);
			ymdStyle.setBorderRight(BorderStyle.THIN);
			ymdStyle.setBorderTop(BorderStyle.THIN);
	        
			ymdStyle.setFont(getFont());
		}
        return ymdStyle;
	}
	
	protected CellStyle getStyle() {
		if(style == null) {
			style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setBorderBottom(BorderStyle.THIN);
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.THIN);
	        
			style.setFont(getFont());
		}
        return style;
	}
	
	protected Font getFont() {
		if(font == null) {
			font = workbook.createFont();
			font.setFontName("Arial");
			font.setColor(IndexedColors.BLACK.getIndex());
		}
		return font;
	}
	
	protected void createTitle(Sheet sheet, short r, String title) {
		Row titleRow = sheet.createRow(r);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(createHelper.createRichTextString(title));
        titleCell.setCellStyle(getTitleStyle());
	}
	
	protected void createHeader(Sheet sheet, short r, String[] titles, int[] width) {
		Row headerRow = sheet.createRow(r);
		for(int c = 0; c < titles.length; c++) {
            Cell cell = headerRow.createCell(c);
            cell.setCellValue(createHelper.createRichTextString(titles[c]));
            cell.setCellStyle(getHeaderStyle());

            sheet.setColumnWidth(c, width[c]*256);
		}
	}
	
	protected void createStringCell(Row row, short c, String value) {
		Cell cell = row.createCell(c);
        cell.setCellStyle(getStyle());
        cell.setCellValue(createHelper.createRichTextString(value));
	}
	
	protected void createNumberCell(Row row, short c, int value) {
		Cell cell = row.createCell(c);
		cell.setCellStyle(this.getIntStyle());
		cell.setCellValue(value);
	}
	
	protected void createNumberCell(Row row, short c, long value) {
		Cell cell = row.createCell(c);
		cell.setCellStyle(this.getIntStyle());
		cell.setCellValue(value);
	}
	
	protected void createNumberCell(Row row, short c, float value) {
		Cell cell = row.createCell(c);
		cell.setCellStyle(this.getFloatStyle());
		cell.setCellValue(value);
	}
	
	protected void createNumberCell(Row row, short c, double value) {
		Cell cell = row.createCell(c);
		cell.setCellStyle(this.getFloatStyle());
		cell.setCellValue(value);
	}
	
	protected void createPercentCell(Row row, short c, double value) {
		Cell cell = row.createCell(c);
		cell.setCellStyle(this.getPercentStyle());
		cell.setCellValue(value);
	}
	
	protected void crateYMDCell(Row row, short c, String value) throws ParseException {
		 Cell cell1 = row.createCell(c);
         cell1.setCellValue(DateUtil.parseDate(value, "yyyyMMdd"));
         cell1.setCellStyle(this.getYmdStyle());
	}
}
