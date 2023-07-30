package shpp.level4.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shpp.level4.constants.Roles;
import shpp.level4.constants.TaskStatuses;
import shpp.level4.dto.TodoTaskRequestDTO;
import shpp.level4.entities.TaskStatusEntity;
import shpp.level4.entities.TodoTask;
import shpp.level4.entities.User;
import shpp.level4.exceptions.PermissionException;
import shpp.level4.services.TaskStatusService;
import shpp.level4.services.TodoTaskService;
import shpp.level4.services.UserService;

import javax.validation.Valid;
import java.security.Principal;

import static shpp.level4.constants.TaskStatuses.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TodoTaskController {
    private final TodoTaskService todoTaskService;
    private final TaskStatusService taskStatusService;
    private final UserService userService;
    private final MessageSource messageSource;
    @PostMapping
    @PreAuthorize("hasRole(T(shpp.level4.constants.Roles).ADMIN.name()) or hasAuthority(T(shpp.level4.constants.Permissions).TASK_WRITE.name())")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TodoTask> createTodoTask(
            @RequestBody @Valid TodoTaskRequestDTO taskRequestDTO,
            Principal principal
            ){
        String username = principal.getName();
        User user = userService.findByUserName(username);

        TaskStatusEntity taskStatusEntity = taskStatusService.findByName(PLANNED);

        TodoTask todoTask= new TodoTask();
        todoTask.setName(taskRequestDTO.getName());
        todoTask.setUser(user);
        todoTask.setStatus(taskStatusEntity);

        TodoTask savedTask = todoTaskService.createTodoTask(todoTask);

        return ResponseEntity.ok(savedTask);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole(T(shpp.level4.constants.Roles).ADMIN.name()) or hasAuthority(T(shpp.level4.constants.Permissions).TASK_READ.name())")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TodoTask> getTaskById(@PathVariable Long id, Principal principal){
        String username = principal.getName();
        User user = userService.findByUserName(username);
        TodoTask task = todoTaskService.getTodoTaskById(id);

        User taskUser = task.getUser();

        if(!user.equals(taskUser) || user.getRole().getName().equals(Roles.ADMIN)){
            throw new PermissionException(
                    messageSource.getMessage("read.task.permission.deny",
                            null,
                            LocaleContextHolder.getLocale())
            );
        }
        return ResponseEntity.ok(todoTaskService.getTodoTaskById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole(T(shpp.level4.constants.Roles).ADMIN.name()) or hasAuthority(T(shpp.level4.constants.Permissions).TASK_READ.name())")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Page<TodoTask>> getAllTasks(
            @Parameter(
                    description = "Pageable object",
                    schema = @Schema(implementation = Pageable.class),
                    in = ParameterIn.QUERY,
                    example = "{\"page\": 0, \"size\": 10, \"sort\": [\"name\"]}")
            Pageable pageable,
            Principal principal
    ) {
        String username = principal.getName();
        User user = userService.findByUserName(username);
        if(user.getRole().getName().equals(Roles.ADMIN)){
            return ResponseEntity.ok(todoTaskService.getAllTask(pageable));
        }
        return ResponseEntity.ok(todoTaskService.getAllUserTasks(user, pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(shpp.level4.constants.Roles).ADMIN.name()) or hasAuthority(T(shpp.level4.constants.Permissions).TASK_WRITE.name())")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<TodoTask> updateTask(
            @PathVariable Long id,
            @RequestBody @Valid TodoTaskRequestDTO taskRequestDTO,
            Principal principal
            ){
        String username = principal.getName();
        User user = userService.findByUserName(username);

        TodoTask taskToUpdate = todoTaskService.getTodoTaskById(id);
        if(user.getRole().getName().equals(Roles.ADMIN)){
            return ResponseEntity.ok(todoTaskService.updateTodoTask(id, taskRequestDTO));
        }

       if(!user.equals(taskToUpdate.getUser())){
            throw new PermissionException(
                    messageSource.getMessage("update.task.permission.deny",
                            null,
                            LocaleContextHolder.getLocale())
                    );
        }
       if(taskStatusService.isTransitionAllowed(
                taskToUpdate.getStatus().getName(),
                TaskStatuses.valueOf(taskRequestDTO.getStatus()))
       ){
            return ResponseEntity.ok(todoTaskService.updateTodoTask(id, taskRequestDTO));
       }else{
            throw new IllegalStateException("Status change from "
                    + taskToUpdate.getStatus().getName()
                    + " to "
                    + taskRequestDTO.getStatus()
                    + " not allowed");
       }
    }
}
