
package ca.mgamble.postal.classes;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
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

public enum ErrorCode  implements Serializable {
    @SerializedName("InvalidServerAPIKey")
    InvalidServerAPIKey("InvalidServerAPIKey"),
    
    @SerializedName("NoRecipients")
    NoRecipients("NoRecipients"),
    
    @SerializedName("NoContent")
    NoContent("NoContent"),
    
    @SerializedName("NotEnoughCredits")
    NotEnoughCredits("NotEnoughCredits"),
    
    @SerializedName("TooManyToAddresses")
    TooManyToAddresses("TooManyToAddresses"),
    
    @SerializedName("TooManyCCAddresses")
    TooManyCCAddresses("TooManyCCAddresses"),
    
    @SerializedName("TooManyBCCAddresses")
    TooManyBCCAddresses("TooManyBCCAddresses"),
    
    @SerializedName("FromAddressMissing")
    FromAddressMissing("FromAddressMissing"),
    
    @SerializedName("UnauthenticatedFromAddress")
    UnauthenticatedFromAddress("UnauthenticatedFromAddress"),
    
    @SerializedName("AttachmentMissingName")
    AttachmentMissingName("AttachmentMissingName"),
    
    @SerializedName("AttachmentMissingData")
    AttachmentMissingData("AttachmentMissingData"),
    
    @SerializedName("ValidationError")
    ValidationError("ValidationError");

    private final String text;

    ErrorCode(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static ErrorCode fromString(String text) {
        if (text != null) {
            for (ErrorCode b : ErrorCode.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }
}
