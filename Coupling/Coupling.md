# Coupling in Java
- **Coupling refers to the degree of dependency between two classes and modules.**
- **It indicates how much one class or module knows about another.**

## Types of Coupling
1. Tight Coupling
2. Loose Coupling

### Tight Coupling:
When one class or module is highly dependent on another clas or module then tight coupling happens.
Changing in one module may have significant impact on the other. This can lead to code which is difficult to maintain and modify the Code.


### For Excample

Email Service Class
```java

package com.tight_couple;

public class EmailService {

    void sendEmail( String message){
        System.out.println("Email sent :" + message);
    }
}
```


Notification Class

```java
package com.tight_couple;

public class Notification {

    EmailService emailService= new EmailService();

    void notifyUser(String msg){
        emailService.sendEmail(msg);
    }
}
```


Main Method
```java
package com.tight_couple;

public class TightCoupleExample {
    public static void main(String[] args) {
        Notification notification = new Notification();
        notification.notifyUser("Welcome user");
    }
}
```

EmailService class is tight coupled with Notification Class.

### If Customer wants to change the Message type Email->SMS
Step 1: Create another class and method for sending SMS
```java
class SMSService {
    void sendSMS(String message) {
        System.out.println("SMS sent: " + message);
    }
}

```

### Problem
To use this SMS class we have to edit the notification Class

```java
class Notification {
    private SMSService smsService = new SMSService(); // ❌ Changed here

    void notifyUser(String msg) {
        smsService.sendSMS(msg); // ❌ Changed here
    }
}

```

Every time when you change the message type then you have to open notification class and edit which increases the risk of bugs.



## Loose Coupling
Loose coupling refers a situation where one class or modules is independent of inner working of another class or module.
Changes of one module are less likely impact on another.

You can achieve it by-
1. Interfaces
2. Dependency Injection

### Example Using Interfaces

 MessageService Interface

```java
package com.loose_coupling;

public interface MessageService {
    void sendMessage(String message);
}
```

Email Service Class
```java
package com.loose_coupling;

public class EmailService implements MessageService{

    public void sendMessage(String message){
        System.out.println("Email sent: " + message);
    }
}
```

Notification Class
```java
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

```

Main Method
```java
package com.loose_coupling;

public class LooseCoupleExample {
    public static void main(String[] args) {

        MessageService messageService= new EmailService(); //Injected EmailService
        Notification notification= new Notification(messageService);
        notification.NotifyUser("Welcome User");
    }
}
```

### Requirement Change
Email -> SMS

Step1: Create the SMS class which implements the MessageService Interfce
```java
package com.loose_coupling;

public class SMSService  implements MessageService{
    @Override
    public void sendMessage(String message){
        System.out.println("SMS Service" +message);
    }
}
```

Step2: We have to change just one line in main method

```java
package com.loose_coupling;

public class LooseCoupleExample {
    public static void main(String[] args) {

        MessageService messageService = new SMSService(); // Change only here
        Notification notification= new Notification(messageService);
        notification.NotifyUser("Welcome User");
    }
}

```









