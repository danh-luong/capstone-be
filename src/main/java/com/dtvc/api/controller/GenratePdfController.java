package com.dtvc.api.controller;

import com.dtvc.api.service.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class GenratePdfController {

    @Autowired
    private PdfGenerator pdfGenerator;

    @PostMapping("/pdf")
    public String generatePdf() {
//        pdfGenerator.generatePdf();
        return "success";
    }
}
