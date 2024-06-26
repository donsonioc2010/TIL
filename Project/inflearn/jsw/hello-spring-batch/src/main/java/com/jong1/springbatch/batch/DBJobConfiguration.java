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
public class DBJobConfiguration {

    private final PlatformTransactionManager transactionManager;

    @Bean(name = "dbJob")
    public Job job(JobRepository jobRepository) {
        return new JobBuilder("dbJob", jobRepository)
            .start(step1(jobRepository))
            .next(step2(jobRepository))
            .build();
    }

    @Bean(name = "dbStep1")
    public Step step1(JobRepository jobRepository) {
        return new StepBuilder("dbStep1", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                System.out.println("Step1 Was Executed!");
                log.error("Step1 Was Executed!");
                return RepeatStatus.FINISHED;
            }, transactionManager).build();
    }

    @Bean(name = "dbStep2")
    public Step step2(JobRepository jobRepository) {
        return new StepBuilder("dbStep2", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                System.out.println("Step2 Was Executed!");
                log.error("Step2 Was Executed!");
                return RepeatStatus.FINISHED;
            }, transactionManager).build();
    }
}
