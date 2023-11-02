package com.MVP.Utils;

import javafx.embed.swing.SwingFXUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;

import com.MVP.Entite.Blog;

public class PdfCreator {

public static void createPDF(String title, String content, Image image) throws IOException, DocumentException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("blog.pdf"));
    document.open();
    document.add(new Paragraph(title));
    com.itextpdf.text.Image pdfImage = com.itextpdf.text.Image.getInstance(image.getUrl().toExternalForm());
    pdfImage.setAbsolutePosition(0, 0);
    pdfImage.scaleAbsolute(document.getPageSize().getWidth(), document.getPageSize().getHeight());
    document.add(pdfImage);
    document.add(new Paragraph(content));
    document.close();
}



}
