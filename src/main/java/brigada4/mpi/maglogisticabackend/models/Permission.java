package brigada4.mpi.maglogisticabackend.models;

public enum Permission {
    MAGICIAN_WRITE("magician:write"),
    STOREKEEPER_WRITE("storekeeper:write"),
    EXTRACTOR_WRITE("extractor:write"),
    HUNTER_WRITE("hunter:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() { return permission; }


}
