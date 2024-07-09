package com.seso.audit_trail.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ResponseData {
    private Object data;
    private String message;
    @JsonIgnore
    private HttpStatus httpStatus;

    public ResponseData(Object data, String message){
        this.data = data;
        this.message = message;
    }

    public ResponseData(Object data, String message, HttpStatus httpStatus){
        this.data = data;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
