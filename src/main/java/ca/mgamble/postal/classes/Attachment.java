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
public class Attachment {
    private String name;
    private String content_type;
    private String data;

    
    public Attachment(String name, String content_type, String data) {
        this.name = name;
        this.content_type = content_type;
        this.data = data;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the content_type
     */
    public String getContent_type() {
        return content_type;
    }

    /**
     * @param content_type the content_type to set
     */
    public void setContent_type(String content_type) {
        this.content_type = content_type;
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
}
