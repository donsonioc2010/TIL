package com.jong1.springbatch.batch;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
public class JobRunnter implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    public JobRunnter(JobLauncher jobLauncher, @Qualifier(value = "instanceJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.error("hd");

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "user2")
            .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
