package com.example.web_task_manager.mail;

import com.example.web_task_manager.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * Class for sending mail messages.
 */
public class MailSender {
    private static final Logger log = LoggerFactory.getLogger(MailSender.class);
    private static final String USERNAME = Properties.MAIL_LOGIN_OUT;
    private static final String PASSWORD = Properties.MAIL_PASSWORD_OUT;// evlgcqtbgkrvktzy _123aBcqwe53412P sfcbhaepcweijobd
    private static Session session;

    /**
     * Init YandexSMTP.
     */
    public MailSender() {
        java.util.Properties props;
        props = getYandexSMTPProps();

        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
    }

    /**
     * @return Properties of YandexSMTP.
     */
    private java.util.Properties getYandexSMTPProps() {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "587"); //587 465
        props.put("mail.smtp.ssl.trust", "smtp.yandex.ru");
        return props;
    }

    /**
     * Sends message to mail.
     *
     * @param to      Mail where to send message
     * @param subject Title of message
     * @param text    Message text
     */
    public boolean sendMessage(String to, String subject, String text) { //dezen53412gml@gmail.com

        try {
            log.info("sending message...");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            log.info("message sent successfully...");
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("cause: " + ex.getCause());
        }
        return false;
    }

    /**
     * Sends message to default mail from Properties file.
     *
     * @param subject Title of message
     * @param text    Message text
     */
    public boolean sendMessage(String subject, String text) { //dezen53412gml@gmail.com
        return sendMessage(Properties.DEFAULT_MAIL_IN, subject, text);
    }
}