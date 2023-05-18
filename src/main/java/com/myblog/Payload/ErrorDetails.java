package com.myblog.Payload;
import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    //constructor based injection
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    // generate getters only

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
