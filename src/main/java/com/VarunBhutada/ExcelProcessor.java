package com.VarunBhutada;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelProcessor {
    public List<String> getUnregisteredEmails(String filePath, int emailCol, int statusCol) {
        List<String> emails = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                Cell statusCell = row.getCell(statusCol);
                if (statusCell != null &&
                    "registered".equalsIgnoreCase(statusCell.getStringCellValue())) {
                    continue;
                }

                Cell emailCell = row.getCell(emailCol);
                if (emailCell != null) {
                    emails.add(emailCell.getStringCellValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }
}