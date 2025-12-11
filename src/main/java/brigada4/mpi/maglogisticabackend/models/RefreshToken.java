package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Data
public class RefreshToken extends GenericEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(nullable = false, name = "token", unique = true)
    private String token;

    @Column(nullable = false, name = "expiry_date")
    private LocalDateTime expiryDate;

    @Override
    public String toString() {
        return "RefreshToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
