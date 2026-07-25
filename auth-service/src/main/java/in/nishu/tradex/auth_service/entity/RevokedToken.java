package in.nishu.tradex.auth_service.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name="revoked_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class RevokedToken {
    @Id
    @Column(length = 28)
    private String tokenHash;

    @Column(nullable = false)
    private Instant revokedAt=Instant.now();

    public RevokedToken(String tokenHash){
        this.tokenHash =tokenHash;
    }

}
