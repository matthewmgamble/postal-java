package ca.mgamble.postal.api.message;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostalMessageBuilderTest {

    @Test
    void builderTest() {
        PostalMessage message = new PostalMessageBuilder()
                .withFrom("no-reply@my-domain.com")
                .addTo("email@gmail.com")
                .addTo("email2@gmail.com")
                .addCc("cc@gmail.com")
                .withSubject("Testing with attachments")
                .withPlainBody("Please view this email in a modern email client!")
                .withHtmlBody("<html><h1>html body</h1><h2>Please check your attachments!</h2></html>")
                .addAttachment(new Attachment("test-csv.csv", "application/octet-stream", "base64String"))
                .withTag("tag")
                .build();

        assertEquals("no-reply@my-domain.com", message.getFrom());
        assertEquals(Arrays.asList("email@gmail.com", "email2@gmail.com"), message.getTo());
        assertEquals(new ArrayList<>(), message.getBcc());
        assertEquals(Collections.singletonList("cc@gmail.com"), message.getCc());
        assertEquals("Testing with attachments", message.getSubject());
    }
}
