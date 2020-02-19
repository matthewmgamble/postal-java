/**
 *
 * @author mgamble
 */
/*
The MIT License (MIT)

Copyright (c) 2017 Matthew M. Gamble https://www.mgamble.ca

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package ca.mgamble.postal.api;


import ca.mgamble.postal.api.message.PostalMessage;
import ca.mgamble.postal.api.message.PostalRawMessage;
import ca.mgamble.postal.api.message.PostalRawMessageBuilder;
import ca.mgamble.postal.api.response.PostalApiResponse;
import ca.mgamble.postal.api.utils.RawEmailUtils;
import ca.mgamble.postal.api.utils.StringEscapeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Setter;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;

import javax.mail.MessagingException;
import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mgamble
 */
public class PostalService implements Closeable {

    private static final String JSON = "application/json";
    private final AsyncHttpClient client;

    @Setter
    private String url;

    @Setter
    private String apiKey;

    private boolean closed = false;
    private Gson gson = new GsonBuilder().serializeNulls().create();
    private final RawEmailUtils rawEmailUtils;

    public PostalService() {
        this.client = new DefaultAsyncHttpClient();
        rawEmailUtils = new RawEmailUtils();
    }

    public PostalService(String url, String apiKey) {
        this();
        this.url = url;
        this.apiKey = apiKey;
    }

    public boolean isClosed() {
        return closed || client.isClosed();
    }

    public void close() {
        if (!client.isClosed()) {
            try {
                client.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(PostalService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closed = true;
    }

    public PostalApiResponse sendRawmessage(PostalRawMessage message) throws Exception {
        Future<Response> f = client.executeRequest(buildRequest("POST", "send/raw", gson.toJson(message)));
        Response r = f.get();
        if (r.getStatusCode() != 200) {
            throw new Exception("Could not send raw message");
        } else {
            return gson.fromJson(r.getResponseBody(), PostalApiResponse.class);
        }
    }

    public PostalApiResponse sendMessage(PostalMessage message) throws Exception {

        validateRecipientsMaxLength(message);
        encodeAccentedCharactersFromMessage(message);

        if (message.getEmbeddedImages() != null && message.getEmbeddedImages().size() > 0) {
            return sendRawmessage(computeRawMessage(message));
        }

        Future<Response> future = client.executeRequest(buildRequest("POST", "send/message", gson.toJson(message)));
        Response response = future.get();
        if (response.getStatusCode() != 200) {
            throw new Exception("Could not send message, server responded with " + response.getStatusCode());
        } else {
            return gson.fromJson(response.getResponseBody(), PostalApiResponse.class);
        }
    }

    private PostalRawMessage computeRawMessage(PostalMessage message) throws IOException, MessagingException {
        return new PostalRawMessageBuilder()
                .from(message.getFrom())
                .tos(message.getTo())
                .withData(rawEmailUtils.convertPostalMessageToRawMessage(message))
                .build();
    }

    private void validateRecipientsMaxLength(PostalMessage message) throws Exception {
        if (message.getTo().size() > 50) {
            throw new Exception("Too many recipients");
        }
        if (message.getCc().size() > 50) {
            throw new Exception("Too many CC contacts");
        }
        if (message.getBcc().size() > 50) {
            throw new Exception("Too many BCC contacts");
        }
    }

    private void encodeAccentedCharactersFromMessage(PostalMessage message) {
        if (message.getHtmlBody() != null) {
            message.setHtmlBody(StringEscapeUtils.encodeHtml(message.getHtmlBody()));
        }
        if (message.getPlainBody() != null) {
            message.setPlainBody(StringEscapeUtils.encodeHtml(message.getPlainBody()));
        }
        //if (message.getSubject() != null) {
        //    message.setSubject(StringEscapeUtils.encodeHtml(message.getSubject()));
        //}
    }

    private Request buildRequest(String type, String subUrl) {
        validatePostalParameters();
        RequestBuilder builder = new RequestBuilder(type);
        return builder.setUrl(this.url + "/api/v1/" + subUrl)
                .addHeader("Accept", JSON)
                .addHeader("Content-Type", JSON)
                .addHeader("x-server-api-key", this.apiKey)
                .build();
    }

    private void validatePostalParameters() {
        if (this.url == null) {
            throw new RuntimeException("Postal URL can't be null");
        }
        if (!isValidURL(this.url)) {
            throw new RuntimeException("URL format is incorrect.");
        }
        if (this.apiKey == null) {
            throw new RuntimeException("Postal API Key can't be null");
        }
    }

    private boolean isValidURL(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        }
    }


    private Request buildRequest(String type, String subUrl, String requestBody) {
        validatePostalParameters();
        RequestBuilder builder = new RequestBuilder(type);
        return builder.setUrl(this.url + "/api/v1/" + subUrl)
                .addHeader("Accept", JSON)
                .addHeader("Content-Type", JSON)
                .addHeader("x-server-api-key", this.apiKey)
                .setCharset(StandardCharsets.UTF_8)
                .setBody(requestBody)
                .build();
    }

}
