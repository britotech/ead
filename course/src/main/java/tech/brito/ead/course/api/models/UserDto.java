package tech.brito.ead.course.api.models;

import lombok.Data;
import tech.brito.ead.course.enums.UserStatus;
import tech.brito.ead.course.enums.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.UUID;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1l;

    private UUID id;

    private String username;

    private String email;

    private String cpf;

    private String fullname;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private UserType type;

    private String phoneNumber;

    private String imageUrl;
}
