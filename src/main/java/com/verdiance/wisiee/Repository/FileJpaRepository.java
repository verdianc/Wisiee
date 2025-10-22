package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileEntity,Long> {

}
