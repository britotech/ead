package tech.brito.ead.authuser.api.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    private String cpf;

    private String fullname;

    private String phoneNumber;
}
