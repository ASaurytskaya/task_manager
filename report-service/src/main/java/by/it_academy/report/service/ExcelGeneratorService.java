package by.it_academy.report.service;

import by.it_academy.report.core.dto.Audit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGeneratorService {

    private final List<Audit> auditList;
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGeneratorService(List<Audit> auditList) {
        this.auditList = auditList;
        workbook = new XSSFWorkbook();
    }

    public ByteArrayInputStream generateExcelFile() throws IOException {
        writeHeader();
        write();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void writeHeader() {
        sheet = workbook.createSheet("Report");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Номер записи", style);
        createCell(row, 1, "UUID записи", style);
        createCell(row, 2, "Дата и время создания", style);
        createCell(row, 3, "UUID пользователя", style);
        createCell(row, 4, "Почта пользователя", style);
        createCell(row, 5, "ФИО пользователя", style);
        createCell(row, 6, "Роль пользователя", style);
        createCell(row, 7, "Сообщение", style);
        createCell(row, 8, "Тип", style);
        createCell(row, 9, "UUID объекта", style);
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int count = 1;
        for (Audit record: auditList) {
            Row row = sheet.createRow(rowCount++);
            createCell(row, 0, count, style);
            createCell(row, 1, record.getUuid().toString(), style);
            createCell(row, 2, record.getDtCreate().toString(), style);
            createCell(row, 3, record.getUser().getUuid().toString(), style);
            createCell(row, 4, record.getUser().getMail(), style);
            createCell(row, 5, record.getUser().getFio(), style);
            createCell(row, 6, record.getUser().getUserRole().getAuthority(), style);
            createCell(row, 7, record.getText(), style);
            createCell(row, 8, record.getType().getValue(), style);
            createCell(row, 9, record.getId(), style);
            count++;
        }
    }
}
