package com.lojosoft.cloud.metrics.memvisualizer.persistence;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsReader {

    public static List<String> getWorksheetNames(File file) throws IOException, InvalidFormatException {
        List<String> sheetNames = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file);
        for (int i=0;i<workbook.getNumberOfSheets();i++ ){
            String sheet = workbook.getSheetName(i);
            sheetNames.add(sheet);
        }
        return sheetNames;
    }

    public static List<List<String>> readXls(File file, String worksheetName) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(file);
        List<List<String>> extractedRows = new ArrayList<>();
        Sheet sheet = workbook.getSheet(worksheetName);
        if (sheet == null) {
            return null;
        }
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
