package com.example.dynamicPdf.services;

import com.lowagie.text.DocumentException;
import com.example.dynamicPdf.configs.Constants;
import com.example.dynamicPdf.dataobjects.request.InvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

@Service
public class PdfGeneratorService {

    @Autowired
    private TemplateEngine templateEngine;

    public String generatePdf(InvoiceRequest request) throws IOException {
        int hash = request.hashCode();
        String fileName = hash + ".pdf";
        String outputPath = Constants.INVOICES_DIRECTORY + fileName;
        File pdfFile = new File(outputPath);
        if(pdfFile.exists() && !pdfFile.isDirectory()) {
            return fileName;
        }

        String htmlContent = generateHtmlContent(request);
        convertHtmlToPdf(htmlContent, outputPath);

        return fileName;
    }

    private void convertHtmlToPdf(String htmlContent, String outputPath) throws IOException, DocumentException {
        try (OutputStream os = new FileOutputStream(outputPath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
        }
    }

    private String generateHtmlContent(InvoiceRequest request) {
        Context context = new Context();
        context.setVariable("invoice", request);
        return templateEngine.process("invoice.html", context);
    }
}
