package one.bca.day2task.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BaseTasklet implements Tasklet {

    private final String message;

    public BaseTasklet(String message) {
        this.message = message;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println(message);
        return RepeatStatus.FINISHED;
    }
}
