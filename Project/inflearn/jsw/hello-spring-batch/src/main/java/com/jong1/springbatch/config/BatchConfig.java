package com.jong1.springbatch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;

/**
 * 총 4개의 설정 클래스를 실행시키며, 스프링 배치의 모든 초기화 및 실행 구성이 이루어진다.
 * 스프링 부트 배치의 자동 설정 클래스가 실행됨으로 Bean으로 등록된 모든 Job을 검색하여 초기화와 동시어 Job을 수행하도록 구성한다.
 */
@Configuration
//@EnableBatchProcessing
public class BatchConfig {

}
