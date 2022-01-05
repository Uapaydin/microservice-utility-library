package com.turkcell.microserviceutil.excel.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellType;

@Data
@NoArgsConstructor
public class CellSourceData {
    private Object value;
    private CellType cellType;
    private int columnNumber;
    private boolean isHeader;

    public CellSourceData(String value, CellType cellType, int columnNumber) {
        this.value = value;
        this.cellType = cellType;
        this.columnNumber = columnNumber;
    }

    public CellSourceData(String value, CellType cellType, int columnNumber, boolean isHeader) {
        this.value = value;
        this.cellType = cellType;
        this.columnNumber = columnNumber;
        this.isHeader = isHeader;
    }

    public String getStringValue() {
        return String.valueOf(getValue());
    }
}
