package com.jong1.springbatch.config;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
//@Component
@RequiredArgsConstructor
public class BatchScheduling {
    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;

//    @Scheduled(fixedDelay = 3000)
//    @Scheduled(cron = "0/3 * * * * *")
    public void dbJob() throws NoSuchJobException, JobInstanceAlreadyCompleteException,
        JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("======= Db job ========");
        jobLauncher.run(jobRegistry.getJob("dbJob"), new JobParameters(new HashMap<>()));
    }

//    @Scheduled(fixedDelay = 5000)
//    @Scheduled(cron = "0/5 * * * * *")
    public void helloJob() throws NoSuchJobException, JobInstanceAlreadyCompleteException,
        JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("======= Hello job ========");
        jobLauncher.run(jobRegistry.getJob("helloJob"), new JobParameters(new HashMap<>()));
    }

//    @Scheduled(fixedDelay = 2000)
//    @Scheduled(cron = "0/2 * * * * *")
    public void newJob() throws NoSuchJobException, JobInstanceAlreadyCompleteException,
        JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("======= New job ========");
        jobLauncher.run(jobRegistry.getJob("newJob"), new JobParameters(new HashMap<>()));
    }

    public void allRun() {
        try {
            log.error("======= All Run ========");
            jobLauncher.run(jobRegistry.getJob("dbJob"), new JobParameters(new HashMap<>()));
            jobLauncher.run(jobRegistry.getJob("helloJob"), new JobParameters(new HashMap<>()));
            jobLauncher.run(jobRegistry.getJob("newJob"), new JobParameters(new HashMap<>()));
        }
        catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        }
        catch (JobRestartException e) {
            throw new RuntimeException(e);
        }
        catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        }
        catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
        catch (NoSuchJobException e) {
            throw new RuntimeException(e);
        }
    }
}
