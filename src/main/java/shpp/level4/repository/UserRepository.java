package shpp.level4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shpp.level4.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
