package com.jong1.cafekiosk.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {

    public boolean sendMail(String fromEmail, String toEmail, String title, String content) {
        log.info("메일 전송");
        // 실제 구현하진 않지만, 구현하더라도 테스트에서는 해당 기능들에 대해서 모킹처리하는 방향 연습
        throw new IllegalArgumentException("메일 전송에 실패했습니다.");
//        return Boolean.TRUE;
    }
}
