package ca.mgamble.postal.api.message;

import java.util.ArrayList;
import java.util.List;

public class PostalRawMessageBuilder {
    private String mail_from;
    private String data;
    private boolean bounce;
    private List<String> rcpt_to;

    public PostalRawMessageBuilder from(String mail_from) {
        this.mail_from = mail_from;
        return this;
    }

    public PostalRawMessageBuilder withData(String data) {
        this.data = data;
        return this;
    }

    public PostalRawMessageBuilder withBounce(boolean bounce) {
        this.bounce = bounce;
        return this;
    }

    public PostalRawMessageBuilder to(String rcptTo) {
        if (this.rcpt_to == null) {
            this.rcpt_to = new ArrayList<>();
        }
        this.rcpt_to.add(rcptTo);
        return this;
    }

    public PostalRawMessageBuilder tos(List<String> tos) {
        this.rcpt_to = tos;
        return this;
    }

    public PostalRawMessage build() {
        return new PostalRawMessage(mail_from, data, bounce, rcpt_to);
    }
}
