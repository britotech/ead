package tech.brito.ead.notification.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import tech.brito.ead.notification.enums.NotificationStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;
import static tech.brito.ead.notification.core.constants.DataConstants.DATE_TIME_FORMAT_UTC;

@Getter
@Setter
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_UTC)
    private OffsetDateTime creationDateTime;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

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
