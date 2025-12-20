package brigada4.mpi.maglogisticabackend.models;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    CREATED ("Создана"),
    WORKED ("В работе"),
    FINISHED ("Оформлена");

    private final String name;

    ApplicationStatus(String name) {
        this.name = name;
    }

}
