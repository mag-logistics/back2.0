package brigada4.mpi.maglogisticabackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends GenericEntity{

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    private String email;

    private String activationCode;

    @NotEmpty(message = "Password should not be empty")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sex_id", referencedColumnName = "id")
    private Sex sex;

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Patronymic should not be empty")
    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birth_date")
    private Date birthDate;

    public User() {
    }

    public User(String email,
//                String activationCode,
                String password, Role role, Sex sex, String name, String surname, String patronymic, Date birthDate) {
        this.email = email;
//        this.activationCode = activationCode;
        this.password = password;
        this.role = role;
        this.sex = sex;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }


}
