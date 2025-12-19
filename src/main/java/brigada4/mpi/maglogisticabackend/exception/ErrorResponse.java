package brigada4.mpi.maglogisticabackend.exception;

import java.time.Instant;

public record ErrorResponse(String code, String message, Instant timestamp) {
}
