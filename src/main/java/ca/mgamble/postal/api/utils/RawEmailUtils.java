package ca.mgamble.postal.api.utils;

import ca.mgamble.postal.api.message.PostalMessage;
import org.simplejavamail.converter.internal.mimemessage.MimeMessageHelper;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

public class RawEmailUtils {

    public String convertPostalMessageToRawMessage(PostalMessage message) throws MessagingException, IOException {
        EmailPopulatingBuilder emailPopulatingBuilder = buildEmailFromPostalMessage(message);

        MimeMessage mimeMessage = MimeMessageHelper.produceMimeMessage(
                emailPopulatingBuilder.buildEmail(),
                Session.getInstance(new Properties(), null)
        );

        String base64Message;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            mimeMessage.writeTo(outputStream);
            base64Message = new String(Base64.getEncoder().encode(outputStream.toByteArray()));
        }
        return base64Message;
    }

    private EmailPopulatingBuilder buildEmailFromPostalMessage(PostalMessage message) {
        EmailPopulatingBuilder emailPopulatingBuilder = EmailBuilder.startingBlank()
                .from(message.getFrom())
                .withReplyTo(message.getReply_to())
                .withSubject(message.getSubject())
                .withHTMLText(message.getHtml_body())
                .withPlainText(message.getPlain_body());

        message.getAttachments().forEach(attachment -> emailPopulatingBuilder.withAttachment(
                attachment.getName(),
                Base64.getDecoder().decode(attachment.getData().getBytes()),
                attachment.getContent_type()
                                         )
        );

        message.getEmbeddedImages().forEach(embeddedImage -> emailPopulatingBuilder.withEmbeddedImage(
                embeddedImage.getCidReferenceName(),
                Base64.getDecoder().decode(embeddedImage.getData()),
                embeddedImage.getContentType()
                                            )
        );
        return emailPopulatingBuilder;
    }

}
