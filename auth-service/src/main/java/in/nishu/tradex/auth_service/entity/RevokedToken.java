package in.nishu.tradex.auth_service.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name="revoked_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevokedToken {
    @Id
    @Column(length = 64)
    private String tokenHash;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant revokedAt;

//    public RevokedToken(String tokenHash){
//        this.tokenHash =tokenHash;
//        this.revokedAt=Instant.now();
//    }

}
