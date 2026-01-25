package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.FileEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileJpaRepository extends JpaRepository<FileEntity,Long> {

  List<FileEntity> findByFormId(Long formId);
}
