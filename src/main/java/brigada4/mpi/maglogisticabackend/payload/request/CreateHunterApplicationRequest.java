package brigada4.mpi.maglogisticabackend.payload.request;

import java.util.Date;

public record CreateHunterApplicationRequest(
        String magicId,
        String animalId,
        int quantity,
        Date deadline
) {}
