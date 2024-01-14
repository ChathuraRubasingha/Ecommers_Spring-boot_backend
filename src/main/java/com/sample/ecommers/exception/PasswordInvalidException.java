package com.sample.ecommers.exception;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException (){
        super(" Password does not match! ");
    }
}
