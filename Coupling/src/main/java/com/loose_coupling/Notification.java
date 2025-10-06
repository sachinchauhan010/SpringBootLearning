package com.loose_coupling;

public class Notification {

    // create the beam of Interface
    MessageService messageService;

    //Dependency is injected from outside
    public Notification( MessageService messageService){
        this.messageService = messageService;
    }

    void NotifyUser(String message){
        messageService.sendMessage( message);
    }
}
