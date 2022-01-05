package com.turkcell.microserviceutil.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
@AllArgsConstructor
public class ExcelSourceData {
    private IndexedColors headerBackgroundColor;
    private IndexedColors valueBackgroundColor;
    private FillPatternType headerFillPattern;
    private FillPatternType valueFillPattern;
    private BorderStyle headerBorderStyle;
    private BorderStyle valueBorderStyle;
    private List<SheetSourceData> sheetList;
    private DateFormat dateFormat;

    public ExcelSourceData(List<SheetSourceData> sheetList){
        this.sheetList = sheetList;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        headerBackgroundColor = IndexedColors.PALE_BLUE;
        valueBackgroundColor = IndexedColors.WHITE;
        headerFillPattern = FillPatternType.SOLID_FOREGROUND;
        valueFillPattern = FillPatternType.SOLID_FOREGROUND;
        headerBorderStyle = BorderStyle.THIN;
        valueBorderStyle = BorderStyle.THIN;
    }
    public
    ExcelSourceData(){
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        headerBackgroundColor = IndexedColors.PALE_BLUE;
        valueBackgroundColor = IndexedColors.WHITE;
        headerFillPattern = FillPatternType.SOLID_FOREGROUND;
        valueFillPattern = FillPatternType.SOLID_FOREGROUND;
        headerBorderStyle = BorderStyle.THIN;
        valueBorderStyle = BorderStyle.THIN;
    }

}
