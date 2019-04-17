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
            Row row = sheet.getRow(1);
            for (int j=0;j<3;j++) {
                Cell cell = row.getCell(j);
                cell.setCellType(CellType.STRING);
                String value = row.getCell(j).getStringCellValue();
                extractedRow.add(value);
            }
        }

        return extractedRows;
    }
}
