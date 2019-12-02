package ca.mgamble.postal.api;

import ca.mgamble.postal.api.message.Attachment;
import ca.mgamble.postal.api.message.EmbeddedImage;
import ca.mgamble.postal.api.message.PostalMessage;
import ca.mgamble.postal.api.message.PostalMessageBuilder;
import ca.mgamble.postal.api.response.OperationStatus;
import ca.mgamble.postal.api.response.PostalApiResponse;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PostalRawServiceTest {

    public static final String TO_ADRESS = "email@test.com";
    public static final String POSTAL_URL = "https://postal.domain.com";
    public static final String POSTAL_API_KEY = "API_KEY";
    public static final String FROM_ADRESS = "no-reply@my-domain.com";

    private PostalService postalService;

    @BeforeEach
    void setUp() {
        postalService = new PostalService(POSTAL_URL, POSTAL_API_KEY);
    }

    @Disabled("Only used to test sending email via API Postal, not to be run automatically")
    @Test
    public void sendMailTest_withEmbeddedImage_withAttachment_shouldSendRawMessage() throws Exception {
        // GIVEN
        byte[] googleLogo = IOUtils.toByteArray(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("google.png")));
        byte[] csvFile = IOUtils.toByteArray(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("test-csv.csv")));
        PostalMessage message = new PostalMessageBuilder()
                .from(FROM_ADRESS)
                .to(TO_ADRESS)
                .withSubject("Postal Java Google")
                .withPlainBody("Please view this email in a modern email client!")
                .withHtmlBody(getEmailBody())
                .addEmbeddedImage(new EmbeddedImage("google", "image/png", googleLogo))
                .addAttachment(new Attachment("test-csv.csv", "application/octet-stream", csvFile))
                .withTag("tag_name")
                .build();
        // WHEN
        PostalApiResponse postalApiResponse = postalService.sendMessage(message);
        // THEN
        assertNotNull(postalApiResponse);
        assertEquals(OperationStatus.SUCCESS, postalApiResponse.getStatus());
    }

    public String getEmailBody() throws IOException {
        File file = new File(this.getClass().getClassLoader().getResource("email.html").getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
