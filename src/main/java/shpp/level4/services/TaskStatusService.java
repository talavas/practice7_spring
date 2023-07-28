package shpp.level4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shpp.level4.constants.TaskStatusHandler;
import shpp.level4.constants.TaskStatuses;
import shpp.level4.entities.TaskStatus;
import shpp.level4.repositories.TaskStatusRepository;

@Service
public class TaskStatusService {
    private final TaskStatusRepository taskStatusRepository;
    private final TaskStatusHandler taskStatusHandler;
    @Autowired
    public TaskStatusService(TaskStatusRepository taskStatusRepository, TaskStatusHandler taskStatusHandler) {
        this.taskStatusRepository = taskStatusRepository;
        this.taskStatusHandler = taskStatusHandler;
    }

    public TaskStatus findByName(TaskStatuses status){
        return taskStatusRepository.findByName(status);
    }

    public boolean isTransitionAllowed(TaskStatuses from, TaskStatuses to){
        return taskStatusHandler.isHandleTransition(from, to);
    }
}
