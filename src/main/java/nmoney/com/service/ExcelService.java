package nmoney.com.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

    public void readXssfFile() {
        String filePath = "C:\\Users\\Nam\\Desktop\\test-file.xlsx";
        try (FileInputStream file = new FileInputStream(filePath)) {
            System.out.println("read file successfully");
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Map<Integer, List<String>> data = new HashMap<>();
            int i = 0;
            for (Row row : sheet) {
//                data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    System.out.println("Cell: " + cell.getCellType());
                    if (cell.getCellType() == CellType.STRING) {
                        System.out.println("Cell value: " + cell.getStringCellValue());
                    } else {
                        System.out.println("Cell numeric value: " + cell.getNumericCellValue());
                    }
//                    System.out.println(cell.);
//                    switch (cell.getCellType()) {
//                        case STRING: ...break;
//                        case NUMERIC: ...break;
//                        case BOOLEAN: ...break;
//                        case FORMULA: ...break;
//                        default:
//                            data.get(new Integer(i)).add(" ");
//                    }
                }
//                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void takeXssfFile(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println("Cell: " + cell.getCellType());
                if (cell.getCellType() == CellType.STRING) {
                    System.out.println("Cell value: " + cell.getStringCellValue());
                } else {
                    System.out.println("Cell numeric value: " + cell.getNumericCellValue());
                }

            }
        }

        createNewFile();
    }

    public static void createNewFile() {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
            workbook.write(outputStream);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public byte[] generateExcelFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        out.close();
        byte[] fileBytes = out.toByteArray();
        return fileBytes;

    }

    // provide url resource which can get excel file directly
    private static final String url = "url_sample";

    public void getDataFromResourceUrl() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

        Workbook workbook = new XSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println("Cell: " + cell.getCellType());
                if (cell.getCellType() == CellType.STRING) {
                    System.out.println("Cell value: " + cell.getStringCellValue());
                } else if (cell.getCellType() == CellType.NUMERIC) {
                    System.out.println("Cell numeric value: " + cell.getNumericCellValue());
                }

            }
        }
    }
}
