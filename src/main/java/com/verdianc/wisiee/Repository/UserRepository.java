package com.verdianc.wisiee.Repository;

import com.sun.jna.platform.win32.WinDef.LONG;
import com.verdianc.wisiee.Entity.UseEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UseEntity, LONG> {
    // provider + providerId로 유저 조회
    Optional<UseEntity> findByProviderNmAndProviderId(String provider, String providerId);

}
