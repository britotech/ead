package tech.brito.ead.course.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseUserDto implements Serializable {

    private static final long serialVersionUID = 1l;

    private UUID id;

    private UUID courseId;

    private UUID userId;
}
