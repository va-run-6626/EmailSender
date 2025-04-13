package com.VarunBhutada;

import java.util.List;
import java.util.Properties;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            // Read configuration
            String excelPath = prop.getProperty("excel.file.path");
            int emailCol = Integer.parseInt(prop.getProperty("email.column"));
            int statusCol = Integer.parseInt(prop.getProperty("status.column"));

            String emailPassword = System.getProperty("EMAIL_PASSWORD");
            if(emailPassword == null) {
                emailPassword = System.getenv("EMAIL_PASSWORD");
            }
            if(emailPassword == null) {
                emailPassword = prop.getProperty("email.password");
            }

            // Process Excel
            ExcelProcessor processor = new ExcelProcessor();
            List<String> unregistered = processor.getUnregisteredEmails(excelPath, emailCol, statusCol);

            // Send emails
            EmailSender sender = new EmailSender(
                prop.getProperty("email.host"),
                Integer.parseInt(prop.getProperty("email.port")),
                prop.getProperty("email.from"),
                emailPassword
            );

            sender.sendReminders(unregistered);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}