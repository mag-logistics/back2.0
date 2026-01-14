package brigada4.mpi.maglogisticabackend.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String id;
    private List<String> roles;

    private String tokenType = "Bearer";

    public AuthResponse(String token, String id, List<String> roles) {
        this.token = token;
        this.id = id;
        this.roles = roles;
    }
}
