package tech.brito.ead.course.core.specifications;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import tech.brito.ead.course.domain.models.Course;
import tech.brito.ead.course.domain.models.CourseUser;
import tech.brito.ead.course.domain.models.Lesson;
import tech.brito.ead.course.domain.models.Module;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({@Spec(path = "name", spec = LikeIgnoreCase.class),
          @Spec(path = "description;", spec = LikeIgnoreCase.class),
          @Spec(path = "level", spec = Equal.class),
          @Spec(path = "status", spec = Equal.class),
          @Spec(path = "userInstructor", spec = Equal.class)})
    public interface CourseSpec extends Specification<Course> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpec extends Specification<Module> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpec extends Specification<Lesson> {
    }

    public static Specification<Course> courseUserId(final UUID userId){
        return ((root, query, cb) -> {
            query.distinct(true);
            Join<Course, CourseUser> userCourse = root.join("courseUsers");
            return cb.equal(userCourse.get("userId"), userId);
        });
    }
    public static Specification<Module> moduleCourseId(final UUID courseId) {
        return ((root, query, cb) -> {
            query.distinct(true);
            var module = root;
            var course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");

            return cb.and(cb.equal(course.get("id"), courseId), cb.isMember(module, courseModules));
        });
    }

    public static Specification<Lesson> lessonModuleId(final UUID moduleId) {
        return ((root, query, cb) -> {
            query.distinct(true);
            var lesson = root;
            var module = query.from(Module.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");

            return cb.and(cb.equal(module.get("id"), moduleId), cb.isMember(lesson, moduleLessons));
        });
    }
}
