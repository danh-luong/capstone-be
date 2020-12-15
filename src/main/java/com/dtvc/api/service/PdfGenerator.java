package com.dtvc.api.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import core.domain.PunishmentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;

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

            BaseFont titleFont = BaseFont.createFont(new FileSystemResource("").getFile().getAbsolutePath() + "\\src\\main\\resources\\OpenSans-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            Paragraph title = new Paragraph();
            title.setFont(new Font(titleFont, 20));
            title.add("BIÊN BẢN VI PHẠM HÀNH CHÍNH");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            BaseFont bf = BaseFont.createFont(new FileSystemResource("").getFile().getAbsolutePath() + "\\src\\main\\resources\\OpenSans-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            StringBuilder headContent = new StringBuilder();
            headContent.append("Thời gian vi phạm: " + df.format(punishmentReport.getCreatedDate()) + "\n");
            headContent.append("Địa điểm: " + punishmentReport.getLocation() + "\n");
            headContent.append("Biển số xe:" + punishmentReport.getLicensePlate() + "\n");
            Chunk headChunk = new Chunk(headContent.toString(), new Font(bf, 14));
            Paragraph headParagraph = new Paragraph();
            headParagraph.add(headChunk);
            document.add(headParagraph);

            URL url = new URL(punishmentReport.getImage().getUrl());
            java.awt.Image imageAwt = ImageIO.read(url);
            Image image = Image.getInstance(imageAwt, null);
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleAbsolute(350f, 350f);
            document.add(image);

            StringBuilder violationTypeString = new StringBuilder();
            violationTypeString.append("Loại vi phạm: ");
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
