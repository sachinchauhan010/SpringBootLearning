package com.loose_coupling;

public class EmailService implements MessageService{

    public void sendMessage(String message){
        System.out.println("Email sent: " + message);
    }
}
