package com.recordent.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.recordent.entity.EmployeeRecord;
import com.recordent.entity.User;
import com.recordent.service.EmployeeService;
import com.recordent.service.SessionService;
import com.recordent.service.UserService;


@RestController
@RequestMapping("recordent/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private SessionService sessionService;

    @GetMapping
    public List<EmployeeRecord> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/viewall/{userId}")
    public List<EmployeeRecord> getEmployeesByToken(@RequestParam("token") String token, @PathVariable Long userId) {
    	System.out.println("Validation from Service====>> "+sessionService.validateSession(token));
        if (sessionService.validateSession(token) == null) {
            throw new RuntimeException("Invalid token");
        }
        return employeeService.getEmployeesByUserId(userId);
    }

    @PostMapping("/create/{userId}")
    public EmployeeRecord createEmployee(@RequestParam("token") String token, @PathVariable Long userId, @RequestBody EmployeeRecord employee) {
        User user = new User();
        user.setId(userId);

        employee.setUser(user);
        if (sessionService.validateSession(token) == null) {
            throw new RuntimeException("Invalid token");
        }
        return employeeService.createEmployee(employee);
    }


    @PutMapping("/edit/{userId}/{employeeId}")
    public EmployeeRecord updateEmployee(@RequestParam("token") String token, @PathVariable Long userId, @PathVariable Long employeeId, @RequestBody EmployeeRecord employeeDetails) {
        User user = new User();
        user.setId(userId);
        employeeDetails.setUser(user); 
        if (sessionService.validateSession(token) == null) {
            throw new RuntimeException("Invalid token");
        }
        return employeeService.updateEmployee(employeeId, employeeDetails);
    }

    @DeleteMapping("/delete/{userId}/{employeeId}")
    public ResponseEntity<String> removeEmployee(@RequestParam("token") String token, @PathVariable Long userId, @PathVariable Long employeeId) {
        employeeService.deleteEmployee(userId, employeeId);
        if (sessionService.validateSession(token) == null) {
            throw new RuntimeException("Invalid token");
        }
        return ResponseEntity.ok("Employee removed successfully");
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<String> handleFileUpload(@RequestParam("token") String token, @RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook;

            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file format");
            }

            Sheet sheet = workbook.getSheetAt(0);
            List<EmployeeRecord> employees = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; 

                EmployeeRecord employee = new EmployeeRecord();
                employee.setName(row.getCell(0).getStringCellValue());
                employee.setDept(row.getCell(1).getStringCellValue());
                employee.setSalary(row.getCell(2).getNumericCellValue());

                User user = new User();
                user.setId(userId);
                employee.setUser(user);

                employees.add(employee);
            }

            employeeService.saveAll(employees);
            workbook.close();

            if (sessionService.validateSession(token) == null) {
                throw new RuntimeException("Invalid token");
            }
            return ResponseEntity.ok("File uploaded successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }

    @GetMapping("/downloadpdf")
    public ResponseEntity<InputStreamResource> downloadPdf(@RequestParam("token") String token, @RequestParam("userId") Long userId) throws IOException {
        User currentUser = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<EmployeeRecord> employees = employeeService.getEmployeesByUserId(userId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out, new WriterProperties()
                .setStandardEncryption(null, (currentUser.getName() + "@" + currentUser.getId()).getBytes(),
                        EncryptionConstants.ALLOW_PRINTING, EncryptionConstants.ENCRYPTION_AES_128));

        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        float[] columnWidths = {2, 2, 2, 2};
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));

        table.addCell(new Cell().add(new Paragraph("ID").setBold()));
        table.addCell(new Cell().add(new Paragraph("Name").setBold()));
        table.addCell(new Cell().add(new Paragraph("Department").setBold()));
        table.addCell(new Cell().add(new Paragraph("Salary").setBold()));

        for (EmployeeRecord employee : employees) {
            table.addCell(new Cell().add(new Paragraph(employee.getId().toString())));
            table.addCell(new Cell().add(new Paragraph(employee.getName())));
            table.addCell(new Cell().add(new Paragraph(employee.getDept())));
            table.addCell(new Cell().add(new Paragraph(employee.getSalary().toString())));
        }

        document.add(table);
        document.close();

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=employees.pdf");

        if (sessionService.validateSession(token) == null) {
            throw new RuntimeException("Invalid token");
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(out.size())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
