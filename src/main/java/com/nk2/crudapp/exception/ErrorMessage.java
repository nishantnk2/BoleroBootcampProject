package com.nk2.crudapp.exception;

import lombok.Data;

@Data
public class ErrorMessage {
    private String message;
    private Integer code;

    public ErrorMessage(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

}
