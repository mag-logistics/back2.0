package brigada4.mpi.maglogisticabackend.service;

import brigada4.mpi.maglogisticabackend.payload.request.AuthRequest;
import brigada4.mpi.maglogisticabackend.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

}
