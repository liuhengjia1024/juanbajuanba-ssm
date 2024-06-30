package com.liuhengjia.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MailUtil {
    private static final Logger log = LoggerFactory.getLogger(MailUtil.class);

    private MailUtil() {
    }

    public static boolean sendMail(String to, String text, String title) {
        try {
            Properties props = loadPropertiesFile("mail.properties");
            Session mailSession = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
                }
            });
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(props.getProperty("mail.user")));
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
            message.setSubject(title);
            message.setContent(text, "text/html;charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public static Properties loadPropertiesFile(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = MailUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
                return properties;
            } else {
                log.error("File not found: {}", fileName);
                return properties;
            }
        } catch (IOException e) {
            log.error("File not found: {}", fileName);
            return properties;
        }
    }
}
