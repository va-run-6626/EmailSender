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

            // Process Excel
            ExcelProcessor processor = new ExcelProcessor();
            List<String> unregistered = processor.getUnregisteredEmails(excelPath, emailCol, statusCol);

            // Send emails
            EmailSender sender = new EmailSender(
                prop.getProperty("email.host"),
                Integer.parseInt(prop.getProperty("email.port")),
                prop.getProperty("email.from"),
                prop.getProperty("email.password")
            );

            sender.sendReminders(unregistered);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}