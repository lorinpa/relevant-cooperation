/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.utils;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.pa.dto.RegistrationDTO;

/**
 *
 * @author mwave
 */
public class MailRegistration {
    
    private final String SUBJECT = "Relevant Coop: Your registration info.";
    private final String FROM_ADDRESS = "comessage@lorintech.com";
    private final String MAIL_SETTINGS_LOOKUP = "java:jboss/mail/lorintech";
    private final String NEW_ACCOUNT = "Welcome to Relevant Coop. Your password is: ";
    private final String NEW_ACCOUNT_REMINDER = "Note! You can change your password once you have logged in successfully.";
    private final String FORGOT_PASSWORD = "Your password is: ";
    
    private static MailRegistration instance = null;

    private MailRegistration() {

    }

    public static MailRegistration getInstance() {
        if (instance == null) {
            instance = new MailRegistration();
        }
        return instance;
    }

    public void sendRegistration(RegistrationDTO registration) throws Exception {
        Context initContext = null;
        Session session;
        initContext = new InitialContext();
        session = (Session) initContext.lookup(MAIL_SETTINGS_LOOKUP);
        Address from = new InternetAddress(FROM_ADDRESS);
        Address to = new InternetAddress(registration.getEmail());
        MimeMessage message = new MimeMessage(session);
        message.setFrom(from);
        message.setRecipient(Message.RecipientType.TO, to);
        message.setSubject(SUBJECT);

        Multipart multipart = new MimeMultipart();

        // PLAIN TEXT
        BodyPart messageBodyPart = new MimeBodyPart();
        if (registration.isIsNewAccount()) {
            messageBodyPart.setText(NEW_ACCOUNT+ registration.getPassword());
        } else {
            messageBodyPart.setText(FORGOT_PASSWORD+ registration.getPassword());
        }
        multipart.addBodyPart(messageBodyPart);

        // HTML TEXT
        messageBodyPart = new MimeBodyPart();
        String htmlText;
        if (registration.isIsNewAccount()) {
            htmlText = "<p>" + NEW_ACCOUNT+ registration.getPassword() + "</p>";
        } else {
            htmlText = "<p>" +FORGOT_PASSWORD+ registration.getPassword() + "</p>";
        }
        messageBodyPart.setContent(htmlText,  "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);
        Transport.send(message);
      

    }

}
