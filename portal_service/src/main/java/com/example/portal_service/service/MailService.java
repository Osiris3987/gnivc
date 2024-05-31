package com.example.portal_service.service;

import com.example.portal_service.model.user.User;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {
    private final Configuration configuration;
    private final JavaMailSender mailSender;

    public void sendMail(User user, String password, Properties params) {
        sendRegistrationEmail(user, password, params);
    }

    @SneakyThrows
    private void sendRegistrationEmail(User user, String password, Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Thanks for registration " + user.getFirstName()+ " !");
        helper.setTo(user.getEmail());
        String emailContent = getRegistrationEmailContent(user, password, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }
    @SneakyThrows
    private String getRegistrationEmailContent(final User user,
                                               final String password,
                                               final Properties properties) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getFirstName());
        model.put("username", user.getUsername());
        model.put("password", password);
        configuration.getTemplate("register.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }
}
