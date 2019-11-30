package ca.mgamble.postal.api.message;

import java.util.ArrayList;
import java.util.List;

public class PostalMessageBuilder {

    private List<String> to = new ArrayList<>();
    private List<String> cc = new ArrayList<>();
    private List<String> bcc = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
    private String from;
    private String subject;
    private String tag;
    private String reply_to;
    private String plain_body;
    private String html_body;
    private boolean bounce;

    public PostalMessageBuilder addTo(String to) {
        this.to.add(to);
        return this;
    }

    public PostalMessageBuilder addCc(String cc) {
        this.cc.add(cc);
        return this;
    }

    public PostalMessageBuilder addBcc(String bcc) {
        this.bcc.add(bcc);
        return this;
    }

    public PostalMessageBuilder addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        return this;
    }

    public PostalMessageBuilder addHeader(Header header) {
        this.headers.add(header);
        return this;
    }

    public PostalMessageBuilder withFrom(String from) {
        this.from = from;
        return this;
    }

    public PostalMessageBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public PostalMessageBuilder withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public PostalMessageBuilder withReplyTo(String reply_to) {
        this.reply_to = reply_to;
        return this;
    }

    public PostalMessageBuilder withPlainBody(String plain_body) {
        this.plain_body = plain_body;
        return this;
    }

    public PostalMessageBuilder withHtmlBody(String html_body) {
        this.html_body = html_body;
        return this;
    }

    public PostalMessageBuilder withBounce(boolean bounce) {
        this.bounce = bounce;
        return this;
    }

    public PostalMessage build() {
        return new PostalMessage(to, cc, bcc, attachments, headers, from, subject, tag, reply_to, plain_body, html_body, bounce);
    }
}
