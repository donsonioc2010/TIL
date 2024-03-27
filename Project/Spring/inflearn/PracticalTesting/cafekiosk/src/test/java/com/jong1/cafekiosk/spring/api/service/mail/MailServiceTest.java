package com.jong1.cafekiosk.spring.api.service.mail;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import com.jong1.cafekiosk.spring.client.mail.MailSendClient;
import com.jong1.cafekiosk.spring.domain.history.mail.entity.MailSendHistory;
import com.jong1.cafekiosk.spring.domain.history.mail.repository.MailSendHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMaik() {
        // given
        //위의 @Mock으로 대체가능
//        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        // 위의 @InjectMocks로 대체가능
//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        Mockito.when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
            .thenReturn(true);

/*        Mockito.when(mailSendHistoryRepository.save(any(MailSendHistory.class)))
            .thenReturn(new MailSendHistory()); 기본값으로 null던져준다.*/

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        Assertions.assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}