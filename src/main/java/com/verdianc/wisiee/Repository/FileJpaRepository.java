package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<FileEntity,Long> {

}
