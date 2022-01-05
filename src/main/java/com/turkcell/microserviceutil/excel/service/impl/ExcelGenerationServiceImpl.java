package com.turkcell.microserviceutil.excel.service.impl;

import com.turkcell.microserviceutil.excel.model.CellSourceData;
import com.turkcell.microserviceutil.excel.model.ExcelSourceData;
import com.turkcell.microserviceutil.excel.model.RowSourceData;
import com.turkcell.microserviceutil.excel.model.SheetSourceData;
import com.turkcell.microserviceutil.excel.service.ExcelGenerationService;
import com.turkcell.microserviceutil.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ExcelGenerationServiceImpl implements ExcelGenerationService {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    @Override
    public Workbook generateWorkBook(ExcelSourceData excelSourceData) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFFont boldFont = wb.createFont();
        boldFont.setBold(true);
        boldFont.setItalic(false);
        XSSFCellStyle headerStyle = wb.createCellStyle();
        headerStyle.setFont(boldFont);
        headerStyle.setFillForegroundColor(excelSourceData.getHeaderBackgroundColor().getIndex());
        headerStyle.setFillPattern(excelSourceData.getHeaderFillPattern());
        headerStyle.setBorderBottom(excelSourceData.getHeaderBorderStyle());
        headerStyle.setBorderTop(excelSourceData.getHeaderBorderStyle());
        headerStyle.setBorderLeft(excelSourceData.getHeaderBorderStyle());
        headerStyle.setBorderRight(excelSourceData.getHeaderBorderStyle());

        XSSFCellStyle valueStyle = wb.createCellStyle();
        valueStyle.setFont(boldFont);
        valueStyle.setFillForegroundColor(excelSourceData.getValueBackgroundColor().getIndex());
        valueStyle.setFillPattern(excelSourceData.getValueFillPattern());
        valueStyle.setBorderBottom(excelSourceData.getValueBorderStyle());
        valueStyle.setBorderTop(excelSourceData.getValueBorderStyle());
        valueStyle.setBorderLeft(excelSourceData.getValueBorderStyle());
        valueStyle.setBorderRight(excelSourceData.getValueBorderStyle());

        for (SheetSourceData ssd : excelSourceData.getSheetList()) {
            XSSFSheet sheet = wb.createSheet(ssd.getSheetName());
            for (int i = 0; i < ssd.getDefaultColumnCount(); i++) {
                sheet.setColumnWidth(i, ssd.getColumnSize() * 1000);
            }
            for (RowSourceData rsd : ssd.getRowSourceDataList()) {
                XSSFRow row = null;
                if (sheet.getRow(rsd.getRowNumber()) == null) {
                    row = sheet.createRow(rsd.getRowNumber());
                } else {
                    row = sheet.getRow(rsd.getRowNumber());
                }
                for (CellSourceData csd : rsd.getCellList()) {
                    XSSFCell cell = row.createCell(csd.getColumnNumber());
                    cell.setCellValue(String.valueOf(csd.getValue()));
                    cell.setCellStyle(csd.isHeader() ? headerStyle : valueStyle);
                }
            }
            for (CellRangeAddress mergeRegion : ssd.getMergeRegions()) {
                sheet.addMergedRegion(mergeRegion);
            }
        }
        return wb;
    }

    @Override
    public byte[] streamExcel(ExcelSourceData excelSourceData) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            generateWorkBook(excelSourceData).write(bos);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Excel generation failed: " + e.getMessage());
        }

    }

    @Override
    public ExcelSourceData readExcelFromStream(InputStream inputStream) {
        try {
            return parseWorkBookToModel(new XSSFWorkbook(inputStream));
        } catch (IOException e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Given stream is not applicable to WorkBook: " + e.getMessage());
        }
    }

    private ExcelSourceData parseWorkBookToModel(XSSFWorkbook workBook) {
        List<SheetSourceData> excelSheets = new ArrayList<>();
        for(Sheet sheet: workBook){
            excelSheets.add(parseSheetToModel(sheet));
        }
        return new ExcelSourceData(excelSheets);
    }

    private SheetSourceData parseSheetToModel(Sheet sheetToParse) {
        SheetSourceData parsedSheet = new SheetSourceData();
        parsedSheet.setSheetName(sheetToParse.getSheetName());
        List<RowSourceData> rowSourceData = new ArrayList<>();
        int i = 0;
        for(Row row : sheetToParse){
            rowSourceData.add(parseRowToModel(row, i));
            i++;
        }
        parsedSheet.setRowSourceDataList(rowSourceData);
        return parsedSheet;
    }

    private RowSourceData parseRowToModel(Row rowToParse, int columnIndex) {
        RowSourceData rowSourceData = new RowSourceData();
        rowSourceData.setRowNumber(columnIndex);
        List<CellSourceData> cellList = new ArrayList<>();
        for(Cell cell: rowToParse){
            cellList.add(parseCellToModel(cell));
        }
        rowSourceData.setCellList(cellList);
        return rowSourceData;
    }

    private CellSourceData parseCellToModel(Cell sourceCell) {
        CellSourceData parsedCell = new CellSourceData();
        XSSFCell cellToParse = (XSSFCell)sourceCell;
        parsedCell.setCellType(cellToParse.getCellTypeEnum());
        if (CellType.STRING.equals(cellToParse.getCellTypeEnum())) {
            parsedCell.setValue(cellToParse.getStringCellValue());
        } else if (CellType.BOOLEAN.equals(cellToParse.getCellTypeEnum())) {
            parsedCell.setValue(cellToParse.getBooleanCellValue());
        } else if (CellType.NUMERIC.equals(cellToParse.getCellTypeEnum())) {
            parsedCell.setValue(cellToParse.getNumericCellValue());
        } else {
            parsedCell.setCellType(CellType.STRING);
            parsedCell.setValue(cellToParse.getStringCellValue());
        }
        return parsedCell;


    }
}
