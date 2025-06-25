package de.hems.backend.components;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.spongepowered.configurate.ConfigurationNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailManager {

    private final ConfigurationManager configurationManager;
    private JavaMailSender mailSender;

    @Autowired
    public MailManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        ConfigurationNode config = configurationManager.getConfig();
        if (config.node("first-time").getBoolean()) return;
        if (config.node("settings", "mail", "own").getBoolean()) {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(config.node("secrets", "smtp", "host").getString());
            mailSender.setPort(config.node("secrets", "smtp", "port").getInt());

            mailSender.setUsername(config.node("secrets", "smtp", "username").getString());
            mailSender.setPassword(config.node("secrets", "smtp", "password").getString());

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", config.node("secrets", "smtp", "properties", "mail", "transport", "protocol").getString());
            props.put("mail.smtp.auth", config.node("secrets", "smtp", "properties", "mail", "smtp", "auth").getString());
            props.put("mail.smtp.starttls.enable", config.node("secrets", "smtp", "properties", "mail", "smtp", "starttls", "enable").getString());
            props.put("mail.debug", "true");//config.node("secrets", "smtp", "properties", "mail", "debug").getString());
            this.mailSender = mailSender;
        } else {
            this.mailSender = null;
        }
    }
    
    public boolean sendMail(String to, String subject, String text) throws MessagingException {
        if (!configurationManager.getConfig().node("settings", "mail", "own").getBoolean()) {
            if (mailSender == null) return false;
            MimeMessage m = mailSender.createMimeMessage();
            m.setSubject(subject);
            m.setText(text);
            m.setRecipients(MimeMessage.RecipientType.TO, to);
            mailSender.send(m);
            return true;
        }
        //TODO:
        return false;
    }
}
