package com.jong1.datajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        //여기서 UserId를 반환한다든지, SecurityContextHolder에서 정보를 반환하든지.. Member조회를해서 반환하든지 자유임
        //여기서 반환한게 @CreatedBy, @LastModifiedBy에 들어간다.
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
