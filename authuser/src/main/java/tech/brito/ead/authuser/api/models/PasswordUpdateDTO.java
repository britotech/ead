package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class PasswordUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    private String password;

    @NotBlank
    private String oldPassword;
}
