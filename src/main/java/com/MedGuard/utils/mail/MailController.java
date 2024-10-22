package com.MedGuard.utils.mail;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/mail")
@RequiredArgsConstructor
@Hidden
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("new-mail/{mail}")
    public String sendEmail(@PathVariable String mail
            , @RequestBody MailStructure mailStructure
    ){
        mailService.sendEmail(mail,mailStructure);
        return "Mail sent Successfully";
    }

}
