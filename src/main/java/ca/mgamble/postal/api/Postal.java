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


import ca.mgamble.postal.classes.SendMessage;
import ca.mgamble.postal.classes.SendRawMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.asynchttpclient.*;
import java.util.concurrent.Future;
import java.util.logging.Level;

/**
 *
 * @author mgamble
 */
public class Postal implements Closeable {
    
    private static final String JSON = "application/json";
    //   private final boolean closeClient;
    private final AsyncHttpClient client;
    private final String url;
    private final String apiKey;
    private final Version version = new Version();
    //  private final ObjectMapper mapper;
    private final Logger logger;
    private boolean closed = false;
    Gson gson = new GsonBuilder().serializeNulls().create();
    
    public Postal(String url, String apiKey) {
        this.logger = Logger.getLogger(Postal.class);
        this.url = url;
        this.client = new DefaultAsyncHttpClient();
        this.apiKey = apiKey;
        
    }

    //////////////////////////////////////////////////////////////////////
    // Closeable interface methods
    //////////////////////////////////////////////////////////////////////
    public boolean isClosed() {
        return closed || client.isClosed();
    }
    
    public void close() {
        if (!client.isClosed()) {
            try {
                client.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Postal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closed = true;
    }
    
    public String getClientVersion() {
        return version.getBuildNumber();
    }
    
    public String sendRawmessage(SendRawMessage message) throws Exception {
         Future<Response> f = client.executeRequest(buildRequest("POST", "send/raw", gson.toJson(message)));
        Response r = f.get();
        if (r.getStatusCode() != 200) {
            
            throw new Exception("Could not send raw message");
        } else {
            return r.getResponseBody();
            
        }
    }
    public String sendMessage(SendMessage message) throws Exception {
        
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
        
        Future<Response> f = client.executeRequest(buildRequest("POST", "send/message", gson.toJson(message)));
        Response r = f.get();
        if (r.getStatusCode() != 200) {
            
            throw new Exception("Could not send message");
        } else {
            return r.getResponseBody();
            
        }
    }
    
    private Request buildRequest(String type, String subUrl) {
        RequestBuilder builder = new RequestBuilder(type);
        Request request = builder.setUrl(this.url + "/api/v1/" + subUrl)
                .addHeader("Accept", JSON)
                .addHeader("Content-Type", JSON)
                .addHeader("x-server-api-key", this.apiKey)
                .build();
        return request;
    }
    
    private Request buildRequest(String type, String subUrl, String requestBody) {
        RequestBuilder builder = new RequestBuilder(type);
        Request request = builder.setUrl(this.url + "/api/v1/" + subUrl)
                .addHeader("Accept", JSON)
                .addHeader("Content-Type", JSON)
                .addHeader("x-server-api-key", this.apiKey)
                .setBody(requestBody)
                .build();
        return request;
    }
    
}
