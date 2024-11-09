package com.example.dynamicPdf.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.example.dynamicPdf.configs.Constants;
import com.example.dynamicPdf.dataobjects.request.InvoiceRequest;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PdfGeneratorService {

    @Autowired
    private TemplateEngine templateEngine;

    public String generatePdf(InvoiceRequest request) throws IOException, NoSuchAlgorithmException {
        String hash = generateHash(request);
        String fileName = hash + ".pdf";
        String outputPath = Constants.INVOICES_DIRECTORY + File.separator + fileName;
        File pdfFile = new File(outputPath);
        if(pdfFile.exists() && !pdfFile.isDirectory()) {
            return fileName;
        }

        String htmlContent = generateHtmlContent(request);
        convertHtmlToPdf(htmlContent, outputPath);

        return fileName;
    }

    private void convertHtmlToPdf(String htmlContent, String outputPath) throws IOException, DocumentException {
        OutputStream outputStream = new FileOutputStream(outputPath);

        ITextRenderer renderer = new ITextRenderer();
        System.out.println(htmlContent);
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

    private String generateHtmlContent(InvoiceRequest request) {
        Context context = new Context();
        context.setVariable("invoice", request);
        return templateEngine.process("invoice.html", context);
    }

    private String generateHash(InvoiceRequest request) throws NoSuchAlgorithmException, JsonProcessingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(request);
        byte[] hash = digest.digest(jsonString.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(hash);
    }
}
