package in.nishu.tradex.auth_service.repositories;

import in.nishu.tradex.auth_service.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken,String> {

    // boolean existsById(String tokenHash);
}
