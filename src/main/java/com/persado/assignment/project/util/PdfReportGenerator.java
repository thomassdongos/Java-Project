package com.persado.assignment.project.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.persado.assignment.project.model.Loan;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;

public class PdfReportGenerator {
    private static final Logger logger = Logger.getLogger(PdfReportGenerator.class.getName());

    public static void generateReport(List<Loan> loans) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("loan_report.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Book Name"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Loan Date"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("User"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            for (Loan loan : loans) {
                table.addCell(loan.getBook().getBookName());
                table.addCell(loan.getLoanDate().toString());
                table.addCell(loan.getUser().getFirstname() + " " + loan.getUser().getLastname());
            }

            document.add(new Paragraph("Books Loaned Last Week:"));
            document.add(new Paragraph(""));
            document.add(table);


            document.close();
        } catch (DocumentException e) {
            logger.severe("Unable to create PDF: " + e);
        } catch (Exception e) {
            logger.severe("An unexpected exception occurred while creating the PDF: " + e);

        }
    }
}

