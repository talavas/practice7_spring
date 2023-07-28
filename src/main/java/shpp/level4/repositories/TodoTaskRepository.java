package shpp.level4.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shpp.level4.entities.TodoTask;
import shpp.level4.entities.User;

@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {

    Page<TodoTask> findByUser(User user, Pageable pageable);
}
