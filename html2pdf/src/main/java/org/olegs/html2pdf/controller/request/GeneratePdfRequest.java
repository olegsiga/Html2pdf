package org.olegs.html2pdf.controller.request;

import java.util.Map;

public class GeneratePdfRequest {
    private String htmlTemplate;
    private Map<String, String> data;

    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
