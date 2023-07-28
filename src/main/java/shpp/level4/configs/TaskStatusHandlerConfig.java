package shpp.level4.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shpp.level4.constants.TaskStatusHandler;
import shpp.level4.constants.TaskStatuses;

import java.util.HashSet;
import java.util.Set;

import static shpp.level4.constants.TaskStatuses.*;

@Configuration
public class TaskStatusHandlerConfig {
    @Bean
    public TaskStatusHandler getTaskStatusHandler(){
        Set<TaskStatuses> fixSetOfStatuses = new HashSet<>();
        fixSetOfStatuses.add(TaskStatuses.NOTIFIED);
        fixSetOfStatuses.add(TaskStatuses.SIGNED);
        fixSetOfStatuses.add(TaskStatuses.CANCELLED);

        TaskStatusHandler plannedStatus = new TaskStatusHandler(PLANNED);
        plannedStatus.addAllowedStatus(WORK_IN_PROGRESS);
        plannedStatus.addAllowedStatus(POSTPONED);
        plannedStatus.addAllowedStatus(CANCELLED);

        TaskStatusHandler workInProgressStatus = new TaskStatusHandler(WORK_IN_PROGRESS);
        workInProgressStatus.setAllowedToStatuses(fixSetOfStatuses);

        TaskStatusHandler postponedStatus = new TaskStatusHandler(POSTPONED);
        postponedStatus.setAllowedToStatuses(fixSetOfStatuses);

        TaskStatusHandler notifiedStatus = new TaskStatusHandler(NOTIFIED);
        notifiedStatus.addAllowedStatus(SIGNED);
        notifiedStatus.addAllowedStatus(CANCELLED);
        notifiedStatus.addAllowedStatus(DONE);

        TaskStatusHandler signedStatus = new TaskStatusHandler(SIGNED);
        signedStatus.addAllowedStatus(DONE);
        signedStatus.addAllowedStatus(CANCELLED);

        TaskStatusHandler doneStatus = new TaskStatusHandler(DONE);
        TaskStatusHandler cancelledStatus = new TaskStatusHandler(CANCELLED);

        plannedStatus.setNextHandler(workInProgressStatus);
        workInProgressStatus.setNextHandler(postponedStatus);
        postponedStatus.setNextHandler(notifiedStatus);
        notifiedStatus.setNextHandler(signedStatus);
        signedStatus.setNextHandler(doneStatus);
        doneStatus.setNextHandler(cancelledStatus);

        return plannedStatus;
    }
}
