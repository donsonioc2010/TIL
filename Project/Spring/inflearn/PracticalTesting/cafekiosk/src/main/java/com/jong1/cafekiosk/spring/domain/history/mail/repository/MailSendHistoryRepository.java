package com.jong1.cafekiosk.spring.domain.history.mail.repository;

import com.jong1.cafekiosk.spring.domain.history.mail.entity.MailSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {

}
