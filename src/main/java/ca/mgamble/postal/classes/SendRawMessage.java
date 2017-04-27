package ca.mgamble.postal.classes;

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
