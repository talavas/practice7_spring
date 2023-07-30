package shpp.level4.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shpp.level4.constants.TaskStatuses;
import shpp.level4.dto.TodoTaskRequestDTO;
import shpp.level4.entities.TaskStatusEntity;
import shpp.level4.entities.TodoTask;
import shpp.level4.entities.User;
import shpp.level4.exceptions.UserNotFoundException;
import shpp.level4.repositories.TaskStatusRepository;
import shpp.level4.repositories.TodoTaskRepository;

import java.util.Optional;

@Service
@Slf4j(topic="console")
@RequiredArgsConstructor
public class TodoTaskService {
    private final TodoTaskRepository todoTaskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final MessageSource messageSource;

    public TodoTask createTodoTask(TodoTask todoTask){
        return todoTaskRepository.save(todoTask);
    }

    public TodoTask updateTodoTask(Long id, TodoTaskRequestDTO taskRequestDTO) {
        Optional<TodoTask> task = todoTaskRepository.findById(id);
        TaskStatusEntity taskStatusEntity = taskStatusRepository.findByName(TaskStatuses.valueOf(taskRequestDTO.getStatus()));
        if (task.isPresent()) {
            TodoTask updateTask = task.get();
            updateTask.setName(taskRequestDTO.getName());
            updateTask.setStatus(taskStatusEntity);
            return todoTaskRepository.save(updateTask);
        }else{
            throw new UserNotFoundException(
                    messageSource.getMessage(
                            "taskById.notfound", null, LocaleContextHolder.getLocale()
                    ) + id);
        }
    }

    public TodoTask getTodoTaskById(Long id){
         return todoTaskRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(
                            messageSource.getMessage(
                                    "taskById.notfound", null, LocaleContextHolder.getLocale()
                            ) + id)
                    );
    }

    public Page<TodoTask> getAllUserTasks(User user, Pageable pageable){
        return todoTaskRepository.findByUser(user, pageable);
    }

    public Page<TodoTask> getAllTask(Pageable pageable){
        return todoTaskRepository.findAll(pageable);
    }


}
