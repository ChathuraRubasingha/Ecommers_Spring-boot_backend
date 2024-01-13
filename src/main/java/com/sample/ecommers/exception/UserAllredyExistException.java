package com.sample.ecommers.exception;

public class UserAllredyExistException extends RuntimeException{
    public UserAllredyExistException (){

        super("User already exists ");
    }
}
