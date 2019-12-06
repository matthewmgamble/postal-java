package ca.mgamble.postal.api.message;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@NoArgsConstructor
public class EmbeddedImage {
    private String cidReferenceName;
    private String contentType;
    private String data;

    public EmbeddedImage(String cidReferenceName, String contentType, String data) {
        this.cidReferenceName = cidReferenceName;
        this.contentType = contentType;
        this.data = data;
    }

    public EmbeddedImage(String cidReferenceName, String contentType, byte[] data) {
        this(cidReferenceName, contentType, new String(Base64.getEncoder().encode(data)));
    }
}
