package com.jong1.springbatch.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@RequiredArgsConstructor
public class JobInstanceConfiguration extends DefaultBatchConfiguration {

    @Bean(name = "instanceJob")
    public Job job(JobRepository jobRepository) {
        return new JobBuilder("instanceJob", jobRepository)
            .start(step1(jobRepository))
            .next(step2(jobRepository))
            .build();
    }

    @Bean(name = "instanceStep1")
    public Step step1(JobRepository jobRepository) {
        return new StepBuilder("instanceStep1", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                System.out.println("Step1 Was Executed!");
                return RepeatStatus.FINISHED;
            }, getTransactionManager())
            .build();
    }

    @Bean(name = "instanceStep2")
    public Step step2(JobRepository jobRepository) {
        return new StepBuilder("instanceStep2", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                System.out.println("Step2 Was Executed!");
                return RepeatStatus.FINISHED;
            }, getTransactionManager())
            .build();
    }
}
