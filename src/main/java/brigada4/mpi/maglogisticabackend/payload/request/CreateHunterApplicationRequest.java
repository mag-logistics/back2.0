package brigada4.mpi.maglogisticabackend.payload.request;

import java.util.Date;

public record CreateHunterApplicationRequest(
        String magicId,
        int volume,
        Date deadline
) {}
