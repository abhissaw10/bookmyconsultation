package com.bmc.notificationservice.freemarker;

import com.bmc.notificationservice.EmailService;
import com.bmc.notificationservice.model.Prescription;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class PrescriptionEmail {

    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final EmailService emailService;
    private final String SUBJECT = "Prescription for Appointment";

    public void sendEmail(Prescription prescription) throws IOException, MessagingException, TemplateException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("prescription", prescription);
        Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
            .getTemplate("prescription.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        emailService.sendSimpleMessage(prescription.getUserEmailId(),SUBJECT,htmlBody);
    }
}
