package com.loose_coupling;

public class SMSService  implements MessageService{
    @Override
    public void sendMessage(String message){
        System.out.println("SMS Service" +message);
    }
}
