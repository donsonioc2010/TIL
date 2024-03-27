package com.jong1.cafekiosk.spring.api.service.mail;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MailServiceSpyTest {

    @Spy
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @Test
    @DisplayName("메일 전송 테스트")
    void sendMaik() {
        // given
        //@Spy를 사용하는 경우에는 when을 쓸수없다.
//        Mockito.when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
//            .thenReturn(true);

        // 스파이는 실제 객체를 쓰기 떄문...
        doReturn(true)
            .when(mailSendClient)
            .sendMail(anyString(), anyString(), anyString(), anyString());

        // when
        boolean result = mailService.sendMail("", "", "", "");

        // then
        Assertions.assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}