package one.bca.day2task;

import one.bca.day2task.tasklet.BaseTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {

    @Bean
    public Step initializeStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager){
        return new StepBuilder("initializeStep", jobRepository)
                .tasklet(new BaseTasklet("Job Initialization"), transactionManager)
                .allowStartIfComplete(true).build();
    }

    @Bean
    public Step dataRestructureStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager){
        return new StepBuilder("dataRestructureStep", jobRepository)
                .tasklet(new BaseTasklet("Restructuring Data Format"), transactionManager)
                .allowStartIfComplete(true).build();
    }

    @Bean
    public Step updateDatabaseStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager){
        return new StepBuilder("updateDatabaseStep", jobRepository)
                .tasklet(new BaseTasklet("Updating Database"), transactionManager)
                .allowStartIfComplete(true).build();
    }

    @Bean
    public Step backupDatabaseStep(JobRepository jobRepository, DataSourceTransactionManager transactionManager){
        return new StepBuilder("backupDatabaseStep", jobRepository)
                .tasklet(new BaseTasklet("Database Backup"), transactionManager)
                .allowStartIfComplete(true).build();
    }

    @Bean
    public Job job(JobRepository jobRepository, DataSourceTransactionManager transactionManager){
        return new JobBuilder("day2task", jobRepository)
                .start(initializeStep(jobRepository, transactionManager))
                .next(dataRestructureStep(jobRepository, transactionManager))
                .next(updateDatabaseStep(jobRepository, transactionManager))
                .next(backupDatabaseStep(jobRepository, transactionManager))
                .build();
    }
}
