package com.turkcell.microserviceutil.excel.service;

import com.turkcell.microserviceutil.excel.model.ExcelSourceData;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;

public interface ExcelGenerationService {
    Workbook generateWorkBook(ExcelSourceData excelSourceData);
    byte[] streamExcel(ExcelSourceData excelSourceData);
    ExcelSourceData readExcelFromStream(InputStream stream);

}
