package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.AdminEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

  Optional<AdminEntity> findByUsername(String username);

  boolean existsByUsername(String username);

}
