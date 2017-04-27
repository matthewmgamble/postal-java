/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mgamble.postal.classes;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author mgamble
 */
public class SendMessage {

    @SerializedName("to")
    private ArrayList<String> to;

    @SerializedName("cc")
    private ArrayList<String> cc;

    @SerializedName("bcc")
    private ArrayList<String> bcc;

    @SerializedName("attachments")
    private ArrayList<Attachment> attachments;
    
    @SerializedName("Headers")
    private ArrayList<Header> headers;
    
    private String from;
    private String subject;
    private String tag;
    private String reply_to;
    private String plain_body;
    private String html_body;
    private boolean bounce;
    /*
     $this->attributes['headers'] = [];
        $this->attributes['attachments'] = [];
     */
    /**
     * @return the to
     */
    public ArrayList<String> getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public void addTo(String rcptTo) {
        if (this.to == null) {
            this.to = new ArrayList<>();
        }
        this.to.add(rcptTo);
    }

    /**
     * @return the cc
     */
    public ArrayList<String> getCc() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public void addCC(String rcptCC) {
        if (this.cc == null) {
            this.cc = new ArrayList<>();
        }
        this.cc.add(rcptCC);
    }

    /**
     * @return the bcc
     */
    public ArrayList<String> getBcc() {
        return bcc;
    }

    /**
     * @param bcc the bcc to set
     */
    public void setBcc(ArrayList<String> bcc) {
        this.bcc = bcc;
    }

    public void addBCC(String rcptBCC) {
        if (this.bcc == null) {
            this.bcc = new ArrayList<>();
        }
        this.bcc.add(rcptBCC);
    }

    /**
     * @return the attachments
     */
    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * @param attachments the attachments to set
     */
    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
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

    /**
     * @return the headers
     */
    public ArrayList<Header> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(ArrayList<Header> headers) {
        this.headers = headers;
    }

    public void addHeader(Header header) {
        if(this.headers == null) {
            this.headers = new ArrayList<>();
        }
        this.headers.add(header);
    }
    
    public void addHeader(String key, String value) {
        this.addHeader(new Header(key, value));
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * @return the reply_to
     */
    public String getReply_to() {
        return reply_to;
    }

    /**
     * @param reply_to the reply_to to set
     */
    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    /**
     * @return the plain_body
     */
    public String getPlain_body() {
        return plain_body;
    }

    /**
     * @param plain_body the plain_body to set
     */
    public void setPlain_body(String plain_body) {
        this.plain_body = plain_body;
    }

    /**
     * @return the html_body
     */
    public String getHtml_body() {
        return html_body;
    }

    /**
     * @param html_body the html_body to set
     */
    public void setHtml_body(String html_body) {
        this.html_body = html_body;
    }

    /**
     * @return the bounce
     */
    public boolean isBounce() {
        return bounce;
    }

    /**
     * @param bounce the bounce to set
     */
    public void setBounce(boolean bounce) {
        this.bounce = bounce;
    }
}
