package com.lojosoft.cloud.metrics.memvisualizer.persistence;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsReader {

    public static List<List<String>> readXls(File file) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        List<List<String>> extractedRows = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        for (int i=0;i<=sheet.getLastRowNum();i++) {
            List<String> extractedRow = new ArrayList<>();
            extractedRows.add(extractedRow);
            Row row = sheet.getRow(i);
            for (int j=0;j<3;j++) {
                Cell cell = row.getCell(j);
                String value;
                if (cell.getCellTypeEnum() == CellType.STRING) {
                    value = row.getCell(j).getStringCellValue();
                }
                else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    Double doubleval = row.getCell(j).getNumericCellValue();
                    value = String.valueOf(Math.round(doubleval));
                }
                else {
                    value = "1";
                }
                extractedRow.add(value);
            }
        }

        return extractedRows;
    }
}
