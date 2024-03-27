package com.jong1.cafekiosk.spring.api.service.mail;

import com.jong1.cafekiosk.spring.client.mail.MailSendClient;
import com.jong1.cafekiosk.spring.domain.history.mail.entity.MailSendHistory;
import com.jong1.cafekiosk.spring.domain.history.mail.repository.MailSendHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    public boolean sendMail(String fromEmail, String toEmail, String title, String content) {
        boolean result = mailSendClient.sendMail(fromEmail, toEmail, title, content);

        if (result) {
            mailSendHistoryRepository.save(
                MailSendHistory.builder()
                    .fromEmail(fromEmail)
                    .toEmail(toEmail)
                    .subject(title)
                    .content(content)
                    .build()
            );
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
