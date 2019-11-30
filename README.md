# postal-java
Java Client API for the Postal email platform (https://github.com/atech/postal/)

Currently the client supports two basic operations - Send and SendRaw - the other API functions for accessing messages will be added at a later date.

Usage of the API is simple:

            PostalService postalService = new PostalService("<<POSTAL_SERVER_URL>>", "API_KEY");
 
            PostalMessage message = new PostalMessageBuilder()
                            .withFrom("no-reply@my-domain.com")
                            .addTo(to)
                            .withSubject("Testing with attachments")
                            .withPlainBody("Please view this email in a modern email client!")
                            .withHtmlBody("<html><h1>html body</h1><h2>Please check your attachments!</h2></html>")
                            .withTag("tag")
                            .addAttachment(new Attachment("test-csv.csv", "application/octet-stream", new String(Base64.getEncoder().encode(tblBordByte))))
                            .build();
        
            postal.sendMessage(message);
        
