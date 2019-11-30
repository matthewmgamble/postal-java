package ca.mgamble.postal.api;

import ca.mgamble.postal.api.message.PostalMessage;
import ca.mgamble.postal.api.response.OperationStatus;
import ca.mgamble.postal.api.response.PostalApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostalServiceTest {
    private PostalService postalService;

    @BeforeEach
    void setUp() {
        postalService = new PostalService("https://postal.domain.com", "apiKey");
    }

    @Disabled("Only used to test sending email via API Postal, not to be run automatically")
    @Test
    public void sendMailTest() throws Exception {
        // GIVEN
        PostalMessage message = MockPostalMessage.mock("test@email.com");
        // WHEN
        PostalApiResponse postalApiResponse = postalService.sendMessage(message);
        // THEN
        assertNotNull(postalApiResponse);
        assertEquals(OperationStatus.SUCCESS, postalApiResponse.getStatus());
    }

    @Test
    public void onSendMail_withBadPostalServiceUrl_shouldThrowException() throws Exception {
        // GIVEN
        PostalMessage message = MockPostalMessage.mock("test@email.com");
        // WHEN
        Exception exception = assertThrows(Exception.class, () -> postalService.sendMessage(message));
        // THEN
        assertEquals("Could not send message, server responded with 404", exception.getMessage());
    }
}
