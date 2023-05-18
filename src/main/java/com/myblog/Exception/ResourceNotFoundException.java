package com.myblog.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)  //This is responsible to send response to postman
public class ResourceNotFoundException extends RuntimeException {//For custom exception 'extends RuntimeException' is mandatory
    private String resourceName;//post ,table name
    private String fieldName;//id  ,column name
    private long fieldValue;// 25 ,value

    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) { //constructor
        super(String.format("%s not found with %s :'%s'",resourceName, fieldName,fieldValue));//post not found with id:'100'
        this.resourceName = resourceName;//used to access non-static members
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    //generate getters only

    public String getResourceName() {
        return resourceName;
    }
    public String getFieldName() {
        return fieldName;
    }
    public long getFieldValue() {
        return fieldValue;
    }
}
