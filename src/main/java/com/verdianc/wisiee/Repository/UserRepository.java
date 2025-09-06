package com.verdianc.wisiee.Repository;

import com.verdianc.wisiee.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // provider + providerId로 유저 조회
    Optional<UserEntity> findByProviderNmAndProviderId(String provider, String providerId);

    @Modifying
    @Query("UPDATE UserEntity u SET u.nickNm = :nickNm WHERE u.userId = :userId")
    void updateNickNm(@Param("userId") Long userId, @Param("nickNm") String nickNm);
}
