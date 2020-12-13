package com.isabella.FirebaseDemo.Model;

public class Message {

    private String message;

    // Default Constructor
    public Message(){

    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
