package tech.brito.ead.authuser.api.models;

import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    @CPF
    private String cpf;

    @Size(max = 150)
    private String fullname;

    @Size(max = 20)
    private String phoneNumber;
}
