package brigada4.mpi.maglogisticabackend.payload;

import java.util.Date;

public record CreateHunterApplicationRequest(
        String magicId,
        int volume,
        Date deadline
) {}
