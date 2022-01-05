package com.turkcell.microserviceutil.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class SheetSourceData {
    private int defaultColumnCount = 20;
    private String sheetName;
    private int columnSize;
    private Map<Integer, Integer> columnIndexMap;
    private List<CellRangeAddress> mergeRegions;
    private List<RowSourceData> rowSourceDataList;

    public SheetSourceData(int defaultColumnCount, String sheetName, int columnSize, Map<Integer, Integer> columnIndexMap, List<CellRangeAddress> mergeRegions) {
        this.defaultColumnCount = defaultColumnCount;
        this.sheetName = sheetName;
        this.columnSize = columnSize;
        this.columnIndexMap = columnIndexMap;
        this.mergeRegions = mergeRegions;
        generateIndexMap();
    }

    public SheetSourceData(String sheetName) {
        this.sheetName = sheetName;
        this.columnSize = 8;
        this.mergeRegions = new ArrayList<>();
        generateIndexMap();
    }

    public SheetSourceData() {
        this.columnSize = 8;
        this.mergeRegions = new ArrayList<>();
        generateIndexMap();
    }

    private void generateIndexMap() {
        columnIndexMap = new HashMap<>();
        for (int i = 0; i < defaultColumnCount; i++) {
            columnIndexMap.put(i, 0);
        }
    }

    private boolean mergeExist(CellRangeAddress cellAddress) {
        if (mergeRegions == null) {
            return false;
        }
        return getMergeRegions().stream().anyMatch(mergeItem ->
                mergeItem.getFirstColumn() == cellAddress.getFirstColumn() &&
                mergeItem.getLastColumn() == cellAddress.getLastColumn() &&
                mergeItem.getFirstRow() == cellAddress.getFirstRow() &&
                mergeItem.getLastRow() == cellAddress.getLastRow());
    }

    public void addMergeRegion(CellRangeAddress cellAddress) {
        if (!mergeExist(cellAddress)) {
            this.getMergeRegions().add(cellAddress);
        }

    }

}
