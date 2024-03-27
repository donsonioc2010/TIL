package com.jong1.cafekiosk.spring.domain.history.mail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Comment("메일발송이력")
@Table(name = "tbl_mail_history")
public class MailSendHistory {

    @Id
    @Comment("메일발송이력ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromEmail;
    private String toEmail;
    private String subject;
    private String content;

    @Builder
    private MailSendHistory(Long id, String fromEmail, String toEmail, String subject, String content) {
        this.id = id;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.subject = subject;
        this.content = content;
    }
}
