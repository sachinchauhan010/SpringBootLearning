package com.loose_coupling;

public class LooseCoupleExample {
    public static void main(String[] args) {

        MessageService messageService = new SMSService(); // Change only here
        Notification notification= new Notification(messageService);
        notification.NotifyUser("Welcome User");
    }
}
