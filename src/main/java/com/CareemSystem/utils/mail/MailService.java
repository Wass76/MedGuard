package com.CareemSystem.utils.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

//    @Autowired
    private final JavaMailSender mailSender;

//    @Value("$(spring.mail.username)")
//    private String fromMail;
    public void sendEmail(String mail , MailStructure mailStructure){
        SimpleMailMessage simpleMailMessage =  new SimpleMailMessage();
        simpleMailMessage.setFrom("wasee.tenbakji@gmail.com");
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);

        mailSender.send(simpleMailMessage);

    }
}
