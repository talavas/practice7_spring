package shpp.level4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shpp.level4.constants.TaskStatuses;
import shpp.level4.entities.TaskStatusEntity;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {
    public TaskStatusEntity findByName(TaskStatuses status);
}
