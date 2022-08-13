package tech.brito.ead.course.api.models;

import lombok.Data;

import java.util.UUID;
@Data
public class UserEventDTO {

    private UUID id;
    private String username;
    private String email;
    private String cpf;
    private String fullname;
    private String status;
    private String type;
    private String actionType;
}

