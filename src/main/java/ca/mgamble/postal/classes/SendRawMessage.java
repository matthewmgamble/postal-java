/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.mgamble.postal.classes;

/**
 *
 * @author mgamble
 */
public class SendRawMessage {
    private String mail_from;
    private String rcpt_to;
    private String data;
    private boolean bounce;

    /**
     * @return the mail_from
     */
    public String getMail_from() {
        return mail_from;
    }

    /**
     * @param mail_from the mail_from to set
     */
    public void setMail_from(String mail_from) {
        this.mail_from = mail_from;
    }

    /**
     * @return the rcpt_to
     */
    public String getRcpt_to() {
        return rcpt_to;
    }

    /**
     * @param rcpt_to the rcpt_to to set
     */
    public void setRcpt_to(String rcpt_to) {
        this.rcpt_to = rcpt_to;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
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
