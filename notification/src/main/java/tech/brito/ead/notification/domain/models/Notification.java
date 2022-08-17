package tech.brito.ead.notification.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.CreationTimestamp;
import tech.brito.ead.notification.enums.NotificationStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    private String title;

    private String message;

    @CreationTimestamp
    @Column(name = "creation_date_time", columnDefinition = "timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private OffsetDateTime creationDateTime;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(OffsetDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (isNull(o) || getClass() != o.getClass() || isNull(id)) {
            return false;
        }

        var notification = (Notification) o;
        return id.equals(notification.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Notification{id=" + id + " , userId=" + userId + ", title=" + title + ", message=" + message + ", status=" + status + "}";
    }
}
