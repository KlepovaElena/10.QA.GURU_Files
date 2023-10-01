package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipTests {

    private static final String zip = "ZIPexample.zip";

    ClassLoader cl = ZipTests.class.getClassLoader();

    @Test
    @DisplayName("csv")
    void csvTest() throws Exception {
        try (InputStream zipStream = cl.getResourceAsStream(zip);
             ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(zipStream))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String csvFile = "CSVexample.csv";
                if (zipEntry.getName().equals(csvFile)) {
                    Reader reader = new InputStreamReader(zipInputStream);
                    CSVReader csvReader = new CSVReader(reader);

                    List<String[]> content = csvReader.readAll();

                    assertEquals(2, content.size());

                    final String[] firstRow = content.get(0);
                    final String[] secondRow = content.get(1);

                    Assertions.assertArrayEquals(new String[]{"п»їEnglish", "201C"}, firstRow);
                    Assertions.assertArrayEquals(new String[]{"Maths", "305A"}, secondRow);
                }
            }
        }
    }

    @Test
    void xlsTest() throws Exception {
        try (InputStream zipStream = cl.getResourceAsStream(zip);
             ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(zipStream))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String xlsFile = "XLSXexample.xlsx";
                if (zipEntry.getName().equals(xlsFile)) {
                    XLS xls = new XLS(zipInputStream);

                    assertEquals("Teacher", xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
                    assertEquals("Subject", xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue());
                    assertEquals("Sam Blanche", xls.excel.getSheetAt(0).getRow(1).getCell(0).getStringCellValue());
                    assertEquals("James Evans", xls.excel.getSheetAt(0).getRow(2).getCell(0).getStringCellValue());
                    assertEquals("PE", xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue());
                    assertEquals("Maths", xls.excel.getSheetAt(0).getRow(2).getCell(1).getStringCellValue());
                }
            }
        }
    }

    @Test
    void pdfTest() throws Exception {
        try (InputStream zipStream = cl.getResourceAsStream(zip);
             ZipInputStream zipInputStream = new ZipInputStream(Objects.requireNonNull(zipStream))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String pdfFile = "PDFexample.pdf";
                if (zipEntry.getName().equals(pdfFile)) {
                    PDF pdf = new PDF(zipInputStream);
                    assertTrue(pdf.text.contains("This is PDF!"));
                }
            }
        }
    }
}