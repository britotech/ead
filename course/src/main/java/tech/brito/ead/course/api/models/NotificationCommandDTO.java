package tech.brito.ead.course.api.models;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationCommandDTO {

    private UUID userId;
    private String title;
    private String message;
}
