package shpp.level4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shpp.level4.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
