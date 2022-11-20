package com.gznznzjsn.amigoscodeloginandregistration.email;


public interface EmailSender {
    void send(String to, String emailText);
}
