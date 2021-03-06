package com.dtvc.api.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import core.domain.PunishmentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PdfGenerator {

    @Autowired
    ServletContext context;

    public String generatePdf(PunishmentReport punishmentReport) {
        String pdfName = "Violation" + punishmentReport.getCaseId() + ".pdf";
        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfName));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 25, Font.BOLD, BaseColor.BLACK);

            Paragraph title = new Paragraph();
            title.setFont(titleFont);
            title.add("Punishment Report");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            BaseFont bf = BaseFont.createFont(new FileSystemResource("").getFile().getAbsolutePath() + "\\src\\main\\resources\\OpenSans-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            StringBuilder headContent = new StringBuilder();
            headContent.append("Violation Date: " + df.format(punishmentReport.getCreatedDate()) + "\n");
            headContent.append("Location: " + punishmentReport.getLocation() + "\n");
            headContent.append("License Plate:" + punishmentReport.getLicensePlate() + "\n");
            Chunk headChunk = new Chunk(headContent.toString(), new Font(bf, 14));
            Paragraph headParagraph = new Paragraph();
            headParagraph.add(headChunk);
            document.add(headParagraph);

            Image image = Image.getInstance(punishmentReport.getImage().getUrl());
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleAbsolute(350f, 350f);
            document.add(image);

            StringBuilder violationTypeString = new StringBuilder();
            violationTypeString.append("Violation Type: ");
            Chunk violationTitle = new Chunk(violationTypeString.toString(), new Font(bf, 13));

            BaseFont typeOfViolation = BaseFont.createFont(new FileSystemResource("").getFile().getAbsolutePath() + "\\src\\main\\resources\\OpenSans-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            StringBuilder violationContentString = new StringBuilder();
            violationContentString.append(punishmentReport.getViolationType().getName() + "\n");
            Chunk violationContent = new Chunk(violationContentString.toString().trim(), new Font(typeOfViolation, 14));
            Paragraph bodyContent = new Paragraph();
            bodyContent.add(violationTitle);
            bodyContent.add(violationContent);
            bodyContent.setAlignment(Element.ALIGN_CENTER);
            document.add(bodyContent);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FileSystemResource("").getFile().getAbsolutePath() + "\\" + pdfName;
    }
}
