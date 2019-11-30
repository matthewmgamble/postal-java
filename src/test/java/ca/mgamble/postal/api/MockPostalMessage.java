package ca.mgamble.postal.api;

import ca.mgamble.postal.api.message.Attachment;
import ca.mgamble.postal.api.message.PostalMessage;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;

public class MockPostalMessage {

    public static PostalMessage mock(String to) throws IOException {
        PostalMessage message = new PostalMessage();
        message.setFrom("no-reply@my-domain.com");
        message.addTo(to);
        message.setSubject("Testing with attachments");
        message.setPlainBody("Please view this email in a modern email client!");
        message.setHtmlBody("<html><h1>html body</h1><h2>Please check your attachments!</h2></html>");
        message.setTag("tag");

        try (InputStream inputStream = Objects.requireNonNull(MockPostalMessage.class.getClassLoader().getResourceAsStream("test-csv.csv"))) {
            byte[] tblBordByte = IOUtils.toByteArray(inputStream);
            message.addAttachment(new Attachment("test-csv.csv", "application/octet-stream", new String(Base64.getEncoder().encode(tblBordByte))));
        }

        try (InputStream inputStream = MockPostalMessage.class.getClassLoader().getResourceAsStream("test-text.txt")) {
            byte[] tblBordByteTxt = IOUtils.toByteArray(Objects.requireNonNull(inputStream));
            message.addAttachment(new Attachment("test-text.txt", "text/plain", new String(Base64.getEncoder().encode(tblBordByteTxt))));
        }

        return message;
    }
}
