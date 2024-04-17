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
public class JobConfiguration extends DefaultBatchConfiguration {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;

    @Bean(name = "newJob")
    public Job job(JobRepository jobRepository) {
        return new JobBuilder("newJob", jobRepository)
            .start(step1(jobRepository))
            .next(step2(jobRepository))
            .build();
    }

    @Bean(name = "newStep1")
    public Step step1(JobRepository jobRepository) {
        return new StepBuilder("newStep1", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("New Step 1 Was Executed");
                return RepeatStatus.FINISHED;
//            }, transactionManager)
            }, getTransactionManager())
            .build();
    }

    @Bean(name = "newStep2")
    public Step step2(JobRepository jobRepository) {
        return new StepBuilder("newStep2", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                log.info("New Step 2 Was Executed");
                return RepeatStatus.FINISHED;
//            }, transactionManager)
            }, getTransactionManager())
            .build();
    }

}
