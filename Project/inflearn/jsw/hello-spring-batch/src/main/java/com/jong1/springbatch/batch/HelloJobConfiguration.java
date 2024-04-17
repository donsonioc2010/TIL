package com.jong1.springbatch.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class HelloJobConfiguration extends DefaultBatchConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;

    @Bean(name = "helloJob")
    public Job helloJob(JobRepository jobRepository) {
        return new JobBuilder("helloJob", jobRepository)
            .start(helloStep1(jobRepository))
            .next(helloStep2(jobRepository)) // 선택
            .build();
    }

    @Bean(name = "helloStep1")
    public Step helloStep1(JobRepository jobRepository) {
        return new StepBuilder("helloStep1", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("Hello, Spring Batch Step1!");
                // null을 주면 디폴트로 스텝을 무한반복한다. FINISHED를 리턴하면 스텝이 종료된다.
                return RepeatStatus.FINISHED;
//            }, transactionManager)
            }, getTransactionManager())
            .build();
    }

    @Bean(name = "helloStep2")
    public Step helloStep2(JobRepository jobRepository) {
        return new StepBuilder("helloStep2", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("Hello, Spring Batch Step2!");
//                return RepeatStatus.CONTINUABLE;
                return RepeatStatus.FINISHED;
//            }, transactionManager)
            }, getTransactionManager())
            .build();
    }

}
