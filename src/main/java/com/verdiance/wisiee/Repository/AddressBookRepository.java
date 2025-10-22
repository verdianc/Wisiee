package com.verdiance.wisiee.Repository;

import com.verdiance.wisiee.Entity.AddressBookEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBookEntity, Long> {
    boolean existsByUser_UserIdAndAlias(Long userId, String alias);

    List<AddressBookEntity> findByUser_UserId(Long userId);

    long countByUser_UserId(Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE AddressBookEntity a SET a.defaultAddressYn = false WHERE a.user.id = :userId AND a.id <> :addressId")
    void resetDefaultAddress(Long addressId, Long userId);

    Optional<AddressBookEntity> findByUser_UserIdAndDefaultAddressYnTrue(Long userId);

}
