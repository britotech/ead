package tech.brito.ead.authuser.api.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class PasswordUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @NotBlank
    @Size(min = 3,max = 255)
    private String password;

    @NotBlank
    @Size(min = 3,max = 255)
    private String oldPassword;
}
