# postal-java
Java Client API for the Postal email platform (https://github.com/atech/postal/)

Currently the client supports two basic operations - Send and SendRaw - the other API functions for accessing messages will be added at a later date.

Usage of the API is simple:

            PostalService postalService = new PostalService("<<POSTAL_SERVER_URL>>", "API_KEY");
 
            PostalMessage message = new PostalMessage();
              message.setFrom("example@domain.tld");
              message.addTo("user@domain.tld");
              message.setSubject("This is an API example!");
              message.setPlainBody("This is the plain body.  Very plain.");
              message.addAttachment(new Attachment("test-csv.csv", "application/octet-stream", new String(Base64.getEncoder().encode(tblBordByte))));
        
            postal.sendMessage(message);
        
