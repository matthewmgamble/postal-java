# postal-java
Java Client API for the Postal email platform (https://github.com/atech/postal/)

Currently the client supports two basic operations - Send and SendRaw - 
the other API functions for accessing messages will be added at a later date.

## Usage

```java
PostalService postalService = new PostalService("<<POSTAL_SERVER_URL>>", "API_KEY");

PostalMessage message = new PostalMessageBuilder()
                .from("no-reply@my-domain.com")
                .to("email@company.com")
                .cc("email@company.com")
                .bcc("email@company.com")
                .withSubject("Testing with attachments")
                .withPlainBody("Please view this email in a modern email client!")
                .withHtmlBody("<html><h1>html body</h1><h2>Please check your attachments!</h2></html>")
                .withTag("tag")
                .addAttachment(new Attachment("test-csv.csv", "application/octet-stream", attachmentByte))
                .build();

postal.sendMessage(message);
```

## Embedded images

If you want to use embedded image simply add it with 

`.addEmbeddedImage(new EmbeddedImage("cidReference", "image/png", googleLogo))`

And reference if in the html body of your email with 

`<img src="cid:google" width="140">`
        
You can put Base64 encoded documents for attachments and embedded image or the byte[] of your document
in the methods `addAttachment` and `addEmbeddedImage` 

