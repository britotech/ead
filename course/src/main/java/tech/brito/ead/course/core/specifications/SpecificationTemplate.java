package tech.brito.ead.course.core.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import tech.brito.ead.course.domain.models.Course;

public class SpecificationTemplate {

    @And({
            @Spec(path = "name", spec = LikeIgnoreCase.class),
            @Spec(path = "description;", spec = LikeIgnoreCase.class),
            @Spec(path = "level", spec = Equal.class),
            @Spec(path = "status", spec = Equal.class),
            @Spec(path = "userInstructor", spec = Equal.class)
        })
    public interface CourseSpec extends Specification<Course> {
    }
}
