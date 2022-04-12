package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserRegistrationDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String cpf;

    @NotBlank
    private String fullname;

    @NotBlank
    private String password;

    private String phoneNumber;
}
