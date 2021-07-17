package com.bmc.notificationservice.freemarker;

import com.bmc.notificationservice.EmailService;
import com.bmc.notificationservice.model.Appointment;
import com.bmc.notificationservice.model.Doctor;
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

import static com.bmc.notificationservice.model.DoctorStatus.APPROVED;
import static com.bmc.notificationservice.model.DoctorStatus.REJECTED;

@RequiredArgsConstructor
@Component
public class DoctorStatusEmail {

    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final EmailService emailService;
    private final String SUBJECT_APPROVED = "Doctor Registration Approved";
    private final String SUBJECT_REJECTED = "Doctor Registration Rejected";

    public void sendEmail(Doctor doctor) throws IOException, MessagingException, TemplateException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("doctor", doctor);
        if(doctor.getStatus().equals(APPROVED)) {
            Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
                .getTemplate("doctorapproval.ftl");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
            emailService.sendSimpleMessage(doctor.getEmailId(),SUBJECT_APPROVED,htmlBody);
        }else if(doctor.getStatus().equals(REJECTED)){
            Template freemarkerTemplate = freeMarkerConfigurer.getConfiguration()
                .getTemplate("doctorrejection.ftl");
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
            emailService.sendSimpleMessage(doctor.getEmailId(),SUBJECT_REJECTED,htmlBody);
        }

    }
}
