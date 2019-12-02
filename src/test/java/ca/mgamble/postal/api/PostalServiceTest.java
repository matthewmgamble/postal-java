package ca.mgamble.postal.api;

import ca.mgamble.postal.api.message.Attachment;
import ca.mgamble.postal.api.message.PostalMessage;
import ca.mgamble.postal.api.message.PostalMessageBuilder;
import ca.mgamble.postal.api.response.OperationStatus;
import ca.mgamble.postal.api.response.PostalApiResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

import static ca.mgamble.postal.api.PostalRawServiceTest.FROM_ADRESS;
import static ca.mgamble.postal.api.PostalRawServiceTest.POSTAL_API_KEY;
import static ca.mgamble.postal.api.PostalRawServiceTest.POSTAL_URL;
import static ca.mgamble.postal.api.PostalRawServiceTest.TO_ADRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostalServiceTest {

    private PostalService postalService;

    @BeforeEach
    void setUp() {
        postalService = new PostalService(POSTAL_URL, POSTAL_API_KEY);
    }

    @Disabled("Only used to test sending email via API Postal, not to be run automatically")
    @Test
    public void sendMailTest_withAttachments() throws Exception {
        // GIVEN
        PostalMessage message = buildPostalMessageWithAttachments(TO_ADRESS);
        // WHEN
        PostalApiResponse postalApiResponse = postalService.sendMessage(message);
        // THEN
        assertNotNull(postalApiResponse);
        assertEquals(OperationStatus.SUCCESS, postalApiResponse.getStatus());
    }

    @Test
    public void onSendMail_withBadPostalServiceUrl_shouldThrowException() throws Exception {
        // GIVEN
        PostalMessage message = buildPostalMessageWithAttachments("test@email.com");
        // WHEN
        Exception exception = assertThrows(Exception.class, () -> postalService.sendMessage(message));
        // THEN
        assertEquals("Could not send message, server responded with 404", exception.getMessage());
    }

    private PostalMessage buildPostalMessageWithAttachments(String to) throws IOException {
        PostalMessage message = new PostalMessageBuilder()
                .from(FROM_ADRESS)
                .to(to)
                .withSubject("Testing with attachments")
                .withPlainBody("Please view this email in a modern email client!")
                .withHtmlBody("<html><h1>html body</h1><h2>Please check your attachments!</h2></html>")
                .withTag("tag_name")
                .build();

        try (InputStream inputStream = Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("test-csv.csv"))) {
            byte[] tblBordByte = IOUtils.toByteArray(inputStream);
            message.addAttachment(new Attachment("test-csv.csv", "application/octet-stream", new String(Base64.getEncoder().encode(tblBordByte))));
        }

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("test-text.txt")) {
            byte[] tblBordByteTxt = IOUtils.toByteArray(Objects.requireNonNull(inputStream));
            message.addAttachment(new Attachment("test-text.txt", "text/plain", new String(Base64.getEncoder().encode(tblBordByteTxt))));
        }

        return message;
    }
}
