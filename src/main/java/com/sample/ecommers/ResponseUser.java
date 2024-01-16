package com.sample.ecommers;

public class ResponseUser {
    private String token;
    private String user;

    public ResponseUser (){};

    public ResponseUser (String token, String user){
        this.token = token;
        this.user = user;
    }

}
