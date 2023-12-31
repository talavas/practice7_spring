package shpp.level4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shpp.level4.entities.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
