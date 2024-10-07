package org.olegs.html2pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest   // Loads the full application context
@AutoConfigureMockMvc  // Automatically configures MockMvc
public class PdfControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Make sure MockMvc is autowired

    @Test
    public void testGeneratePdf() throws Exception {
        // Sample HTML template and data to pass
        String htmlTemplate = "<html><body><h1>Invoice</h1><p>Price: <span th:text=\"${price}\"></span></p></body></html>";
        String requestBody = "{ \"htmlTemplate\": \"" + htmlTemplate + "\", " +
                "\"data\": { \"price\": \"100.00\", \"vat\": \"20.00\", \"total\": \"120.00\" } }";

        // Perform POST request to /api/pdf/generate
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pdf/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))  // Expect PDF content type
                .andExpect(header().string("Content-Disposition", "attachment; filename=generated.pdf"))
                .andDo(print());  // Output the result to the console for debugging
    }
}
