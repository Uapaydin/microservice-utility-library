package com.turkcell.microserviceutil.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowSourceData {
    private List<CellSourceData> cellList;
    private int rowNumber;

    public boolean isRowEmpty(){
        boolean result = false;
        if(cellList==null || cellList.isEmpty()){
            return true;
        }
        for (CellSourceData cellSourceData : cellList) {
            if(!result){
                result = (cellSourceData.getValue() == null || String.valueOf(cellSourceData.getValue()).isEmpty() );
            }else{
                return true;
            }
        }
        return result;
    }

}
