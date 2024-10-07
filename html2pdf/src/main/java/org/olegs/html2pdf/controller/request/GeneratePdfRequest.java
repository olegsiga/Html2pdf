package org.olegs.html2pdf.controller.request;

import java.util.Map;

public class GeneratePdfRequest {
    private String htmlTemplate;
    private Map<String, Object> data;

    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    public void setHtmlTemplate(String htmlTemplate) {
        this.htmlTemplate = htmlTemplate;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
