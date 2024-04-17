package com.jong1.springbatch.batch;

import java.util.Date;
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
public class JobParameterTest implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final Job job;

    public JobParameterTest(JobLauncher jobLauncher, @Qualifier(value = "parameterJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Win java -jar {jar파일명} name=user1 seq(long)=2L date(date)=2024/04/17 age(double)=16.5
        //Mac java -jar {jar파일명} 'name=user1' 'seq(long)=2L' 'date(date)=2024/04/17' 'age(double)=16.5'
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("name", "user1")
            .addLong("seq", 2L)
            .addDate("date", new Date())
            .addDouble("age", 16.5)
            .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}
