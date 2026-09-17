package in.nishusinha.tradeapp.auth_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nishusinha.tradeapp.auth_service.entity.RevokedToken;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, String> {

}
