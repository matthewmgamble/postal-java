package ca.mgamble.postal.api;

import ca.mgamble.postal.api.message.PostalMessage;
import ca.mgamble.postal.api.response.OperationStatus;
import ca.mgamble.postal.api.response.PostalApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostalServiceTest {
    private PostalService postalService;

    @BeforeEach
    void setUp() {
        postalService = new PostalService("url", "apiKey");
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
}
