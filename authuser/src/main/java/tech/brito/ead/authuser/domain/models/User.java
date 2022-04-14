package tech.brito.ead.authuser.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;
import tech.brito.ead.authuser.domain.enums.UserStatus;
import tech.brito.ead.authuser.domain.enums.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_user")
public class User extends RepresentationModel<User> implements Serializable {

    private static final long serialVersionUID = 1l;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    @Column(name = "creation_date_time", columnDefinition = "datetime")
    private OffsetDateTime creationDateTime;

    @UpdateTimestamp
    @Column(name = "last_update_date_time", columnDefinition = "datetime")
    private OffsetDateTime lastUpdateDateTime;

    private String username;

    private String email;

    private String cpf;

    private String fullname;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "image_url")
    private String imageUrl;
}