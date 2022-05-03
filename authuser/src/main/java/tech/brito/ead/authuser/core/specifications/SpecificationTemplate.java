package tech.brito.ead.authuser.core.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import tech.brito.ead.authuser.domain.models.User;
import tech.brito.ead.authuser.domain.models.UserCourse;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "email", spec = LikeIgnoreCase.class),
            @Spec(path = "fullname;", spec = LikeIgnoreCase.class),
            @Spec(path = "status", spec = Equal.class),
            @Spec(path = "type", spec = Equal.class)
    })
    public interface UserSpec extends Specification<User> {
    }

    public static Specification<User> userCourseId(final UUID courseId) {
        return ((root, query, cb) -> {
            query.distinct(true);
            Join<User, UserCourse> userCourse = root.join("userCourses");
            return cb.equal(userCourse.get("courseId"), courseId);
        });
    }
}
