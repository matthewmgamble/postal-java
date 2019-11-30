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
import ca.mgamble.postal.api.response.PostalApiResponse;
import ca.mgamble.postal.api.response.SendRawMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.IOException;

import lombok.Setter;
import org.apache.log4j.Logger;
import org.asynchttpclient.*;
import java.util.concurrent.Future;
import java.util.logging.Level;

/**
 *
 * @author mgamble
 */
public class PostalService implements Closeable {

    private static final Logger logger = Logger.getLogger(PostalService.class);

    private static final String JSON = "application/json";
    private final AsyncHttpClient client;

    @Setter
    private String url;

    @Setter
    private String apiKey;

    private boolean closed = false;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    public PostalService() {
        this.client = new DefaultAsyncHttpClient();
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

    public PostalApiResponse sendRawmessage(SendRawMessage message) throws Exception {
        Future<Response> f = client.executeRequest(buildRequest("POST", "send/raw", gson.toJson(message)));
        Response r = f.get();
        if (r.getStatusCode() != 200) {

            throw new Exception("Could not send raw message");
        } else {
            return gson.fromJson(r.getResponseBody(), PostalApiResponse.class);

        }
    }
    public PostalApiResponse sendMessage(PostalMessage message) throws Exception {

        // Simple pre-flight checks
        if (message.getTo().size() > 50) {
            throw new Exception("Too many recipients");
        }
        if (message.getCc().size() > 50) {
            throw new Exception("Too many CC contacts");
        }
        if (message.getBcc().size() > 50) {
            throw new Exception("Too many BCC contacts");
        }

        Future<Response> future = client.executeRequest(buildRequest("POST", "send/message", gson.toJson(message)));
        Response response = future.get();
        if (response.getStatusCode() != 200) {
            throw new Exception("Could not send message");
        } else {
            return gson.fromJson(response.getResponseBody(), PostalApiResponse.class);
        }
    }

    private Request buildRequest(String type, String subUrl) {
        RequestBuilder builder = new RequestBuilder(type);
        return builder.setUrl(this.url + "/api/v1/" + subUrl)
                .addHeader("Accept", JSON)
                .addHeader("Content-Type", JSON)
                .addHeader("x-server-api-key", this.apiKey)
                .build();
    }

    private Request buildRequest(String type, String subUrl, String requestBody) {
        RequestBuilder builder = new RequestBuilder(type);
        return builder.setUrl(this.url + "/api/v1/" + subUrl)
                .addHeader("Accept", JSON)
                .addHeader("Content-Type", JSON)
                .addHeader("x-server-api-key", this.apiKey)
                .setBody(requestBody)
                .build();
    }

}
