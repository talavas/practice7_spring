package shpp.level4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shpp.level4.constants.TaskStatuses;
import shpp.level4.entities.TaskStatus;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    public TaskStatus findByName(TaskStatuses status);
}
