package brigada4.mpi.maglogisticabackend.payload;

import java.util.Date;

public record CreateHunterApplicationRequest(
        String hunterResponseId,
        String magicId,
        int volume,
        Date initDate,
        Date deadline
) {}
