package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product,Long> {

}
