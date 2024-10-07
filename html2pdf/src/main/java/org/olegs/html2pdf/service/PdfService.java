package org.olegs.html2pdf.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdf(String htmlTemplate, Map<String, Object> data) {
        // 1. Fill the template with dynamic data
        String processedHtml = generateHtmlFromTemplate(htmlTemplate, data);

        // 2. Convert HTML to PDF
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating PDF", e);
        }
    }

    private String generateHtmlFromTemplate(String htmlTemplate, Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process(htmlTemplate, context);
    }
}
