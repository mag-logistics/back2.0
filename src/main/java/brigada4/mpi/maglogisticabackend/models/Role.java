package brigada4.mpi.maglogisticabackend.models;

//import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    MAGICIAN(Set.of(Permission.MAGICIAN_WRITE, Permission.STOREKEEPER_WRITE, Permission.EXTRACTOR_WRITE, Permission.HUNTER_WRITE)),
    STOREKEEPER(Set.of(Permission.STOREKEEPER_WRITE)),
    EXTRACTOR(Set.of(Permission.EXTRACTOR_WRITE)),
    HUNTER(Set.of(Permission.HUNTER_WRITE)),;

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() { return permissions; }

    //Реализация меппера для перевода пермишена в SimpleGrantedAuthority, который необходим для SpringSecurity
    //GrantedAuthority будет использовать данный SimpleGrantedAuthority для хранения коллекции прав пользователя
//    public Set<SimpleGrantedAuthority> getAuthority() {
//        return getPermissions().stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toSet());
//    }

}
