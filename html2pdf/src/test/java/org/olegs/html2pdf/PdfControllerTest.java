package org.olegs.html2pdf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.olegs.html2pdf.controller.PdfController;
import org.olegs.html2pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PdfController.class)  // Focuses on the controller only
@ContextConfiguration(classes = Main.class)  // Application context
public class PdfControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private PdfService pdfService;
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Autowired
    private MockMvc mockMvc;  // Make sure MockMvc is autowired
    @Test
    void contextLoads() {
    }
    @Test
    public void testGeneratePdf() throws Exception {
        String htmlTemplate = "<html><body><h1>Invoice</h1><p>Price: <span th:text=\"${price}\"></span></p></body></html>";
// Escape double quotes and backslashes in the htmlTemplate string
        htmlTemplate = htmlTemplate.replace("\"", "\\\"");

        String requestBody = "{ \"htmlTemplate\": \"" + htmlTemplate + "\", " +
                "\"data\": { \"price\": \"100.00\", \"vat\": \"20.00\", \"total\": \"120.00\" } }";

// Print the resulting JSON string
        System.out.println(requestBody);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pdf/generate")
                        .contentType(MediaType.APPLICATION_JSON)  // Correct content type
                        .content(requestBody))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))  // Expect PDF content type
                .andDo(print());

    }
}
