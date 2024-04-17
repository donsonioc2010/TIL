package com.jong1.springbatch.batch;

import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration  {

    private final PlatformTransactionManager transactionManager;
    @Bean(name = "parameterJob")
    public Job job(JobRepository jobRepository) {
        return new JobBuilder("parameterJob", jobRepository)
            .start(step1(jobRepository))
            .next(step2(jobRepository))
            .build();
    }

    @Bean(name = "parameterStep1")
    public Step step1(JobRepository jobRepository) {
        return new StepBuilder("parameterStep1", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                JobParameters jobParameters =
                    contribution.getStepExecution().getJobExecution().getJobParameters();
                String name = jobParameters.getString("name");
                Long seq = jobParameters.getLong("seq");
                Date date = jobParameters.getDate("date");
                Double age = jobParameters.getDouble("age");

                log.info("ParameterStep1 >>> name: {}, seq: {}, date: {}, age: {}", name, seq, date, age);
                return RepeatStatus.FINISHED;
            },transactionManager)
            .build();
    }

    @Bean(name = "parameterStep2")
    public Step step2(JobRepository jobRepository) {
        return new StepBuilder("parameterStep2", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                Map<String, Object> jobParameters =
                    chunkContext.getStepContext().getJobParameters();
                log.info("ParameterStep2 >>> jobParameters: {}", jobParameters);
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }
}
