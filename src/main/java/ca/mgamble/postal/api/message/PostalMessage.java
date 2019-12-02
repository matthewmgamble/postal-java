package ca.mgamble.postal.api.message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

@Data
public class PostalMessage {

    private List<String> to;
    private List<String> cc;
    private List<String> bcc;
    private List<Attachment> attachments;
    private List<EmbeddedImage> embeddedImages;

    @SerializedName("Headers")
    private List<Header> headers;

    private String from;
    private String subject;
    private String tag;
    private String reply_to;
    private String plain_body;
    private String html_body;
    private boolean bounce;

    public PostalMessage(List<String> to, List<String> cc, List<String> bcc, List<Attachment> attachments, List<EmbeddedImage> embeddedImages,
                         List<Header> headers, String from, String subject, String tag, String reply_to, String plain_body, String html_body, boolean bounce) {
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.attachments = attachments;
        this.embeddedImages = embeddedImages;
        this.headers = headers;
        this.from = from;
        this.subject = subject;
        this.tag = tag;
        this.reply_to = reply_to;
        this.plain_body = plain_body;
        this.html_body = html_body;
        this.bounce = bounce;
    }

    public PostalMessage() {
        this.to = new ArrayList<>();
        this.cc = new ArrayList<>();
        this.bcc = new ArrayList<>();
    }

    public void addTo(String rcptTo) {
        if (this.to == null) {
            this.to = new ArrayList<>();
        }
        this.to.add(rcptTo);
    }

    public void addCC(String rcptCC) {
        if (this.cc == null) {
            this.cc = new ArrayList<>();
        }
        this.cc.add(rcptCC);
    }

    public void addBCC(String rcptBCC) {
        if (this.bcc == null) {
            this.bcc = new ArrayList<>();
        }
        this.bcc.add(rcptBCC);
    }

    public void addAttachment(String name, String content_type, String data) {
        this.addAttachment(new Attachment(name, content_type, data));
    }

    public void addAttachment(Attachment attachment) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        this.attachments.add(attachment);
    }

    public void addEmbeddedImage(EmbeddedImage embeddedImage) {
        if (this.embeddedImages == null) {
            this.embeddedImages = new ArrayList<>();
        }
        this.embeddedImages.add(embeddedImage);
    }

    public void addHeader(Header header) {
        if (this.headers == null) {
            this.headers = new ArrayList<>();
        }
        this.headers.add(header);
    }

    public void addHeader(String key, String value) {
        this.addHeader(new Header(key, value));
    }

    public String getReplyTo() {
        return reply_to;
    }

    public void setReplyTo(String reply_to) {
        this.reply_to = reply_to;
    }

    public String getPlainBody() {
        return plain_body;
    }

    public void setPlainBody(String plain_body) {
        this.plain_body = plain_body;
    }

    public String getHtmlBody() {
        return html_body;
    }

    public void setHtmlBody(String html_body) {
        this.html_body = html_body;
    }
}
