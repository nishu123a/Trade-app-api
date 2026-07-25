package in.nishu.tradex.auth_service.entity;

import in.nishu.tradex.auth_service.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true,length = 160)
    private String email;

    @Column(nullable = false,length = 120)
    private String fullName;

    @Column(nullable = false)
    private String passwordHash;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<UserRoles> roles;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    private void setDefaultRole() {
        if(roles == null){
            roles = Set.of(UserRoles.ROLE_USER);
        }
    }
}
