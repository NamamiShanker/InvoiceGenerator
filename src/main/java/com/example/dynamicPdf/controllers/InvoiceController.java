package com.example.dynamicPdf.controllers;

import com.example.dynamicPdf.configs.Constants;
import com.example.dynamicPdf.dataobjects.request.InvoiceRequest;
import com.example.dynamicPdf.dataobjects.response.GenerateInvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dynamicPdf.services.PdfGeneratorService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class InvoiceController {


    @Autowired
    PdfGeneratorService pdfGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<GenerateInvoiceResponse> generateInvoice(@RequestBody InvoiceRequest request) {
        try {
            String filePath = pdfGeneratorService.generatePdf(request);
            GenerateInvoiceResponse response = new GenerateInvoiceResponse(HttpStatus.OK.value(), null, filePath);
            return ResponseEntity.ok(response);
        }
        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadInvoice(@RequestParam String fileName) {
        try {
            Path filePath = Paths.get(Constants.INVOICES_DIRECTORY, fileName).normalize();
            File file = filePath.toFile();

            if(file.exists() && !file.isDirectory()) {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}
