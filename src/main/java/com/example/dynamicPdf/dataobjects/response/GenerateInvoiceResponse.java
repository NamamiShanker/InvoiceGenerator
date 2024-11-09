package com.example.dynamicPdf.dataobjects.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GenerateInvoiceResponse extends BaseResponse {

    private final String pdfPath;

    public GenerateInvoiceResponse(Integer statusCode, String message, String pdfPath) {
        super(statusCode, message);
        this.pdfPath = pdfPath;
    }
}
