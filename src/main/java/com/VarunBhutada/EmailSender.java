package com.VarunBhutada;

import java.util.List;
import java.util.Properties;
import jakarta.mail.*; // Changed here
import jakarta.mail.internet.*;

public class EmailSender {
    private final Properties props;
    private final String username;
    private final String password;

    public EmailSender(String host, int port, String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
    }

    public void sendReminders(List<String> recipients) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject("Event Registration Reminder");

//            String msgContent = "Dear Participant,\n\n" +
//                "We noticed you haven't registered for the upcoming event. " +
//                "Please complete your registration at [Registration Link].\n\n" +
//                "Best regards,\nEvent Team";
            String htmlContent = "<html><body>"
                + "<p>Dear Participant,</p>"
                + "<p>We noticed you haven't registered for the upcoming event.</p>"
                + "<p>Please complete your registration at: "
                + "<a href='https://www.varunbhutada.com'>www.varunbhutada.com</a></p>"
                + "<p>Best regards,<br/>Event Team</p>"
                + "</body></html>";

            for (String email : recipients) {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setContent(htmlContent,"text/html");
                Transport.send(message);
                System.out.println("Sent reminder to: " + email);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}