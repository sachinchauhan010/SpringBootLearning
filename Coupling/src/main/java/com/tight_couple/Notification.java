package com.tight_couple;

public class Notification {

    EmailService emailService= new EmailService();

    void notifyUser(String msg){
        emailService.sendEmail(msg);
    }
}
