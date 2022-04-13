package tech.brito.ead.authuser.api.models;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import tech.brito.ead.authuser.validations.Username;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserRegistrationDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @Username
    private String username;

    @NotBlank
    @Email
    @Size(max = 80)
    private String email;

    @NotBlank
    @CPF
    private String cpf;

    @NotBlank
    @Size(max = 150)
    private String fullname;

    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    @Size(max = 20)
    private String phoneNumber;
}
