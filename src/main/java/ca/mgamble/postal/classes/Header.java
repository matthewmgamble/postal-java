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
public class Header {
    private String key;
    private String value;

    
    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
