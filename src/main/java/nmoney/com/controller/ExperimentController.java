package nmoney.com.controller;

import nmoney.com.service.ExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/experiment")
public class ExperimentController {
    private ExcelService excelService;

    public ExperimentController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/test-poi-apache")
    public void testPoiApachePackage() {
        excelService.readXssfFile();
    }

    @PostMapping("/import")
    public void importXssfFile(@RequestParam("file") MultipartFile file) throws IOException {
        excelService.takeXssfFile(file);
    }

    @PostMapping("/generate-file")
    public byte[] exportXssfFile() throws IOException {
        return excelService.generateExcelFile();
    }

    @PostMapping("/generate-excel")
    public ResponseEntity<byte[]> exportExcelFile() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=generated-file.xlsx");
        return ResponseEntity.ok().headers(httpHeaders).body(excelService.generateExcelFile());
    }

    @GetMapping("/read-exchange-rate")
    public void readExchangeRate() throws IOException {
        excelService.getDataFromResourceUrl();
    }
}
