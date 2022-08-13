package tech.brito.ead.course.domain.repositories;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;

@Repository
public class CourseRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public boolean existsByCourseAndUser(UUID courseId, UUID userId) {
        var sql = """
                    SELECT COUNT(*) > 0
                    FROM public.tb_course_user course_user
                    WHERE course_user.course_id = :courseId
                      AND course_user.user_id = :userId            
                """;
        var query = createNativeQuery(courseId, userId, sql);
        return (boolean) query.getSingleResult();
    }

    public void saveCourseUser(UUID courseId, UUID userId) {
        var sql = "INSERT INTO public.tb_course_user VALUES(:courseId, :userId)";
        var query = createNativeQuery(courseId, userId, sql);
        query.executeUpdate();
    }

    private Query createNativeQuery(UUID courseId, UUID userId, String sql) {
        var query = manager.createNativeQuery(sql);
        query.setParameter("courseId", courseId);
        query.setParameter("userId", userId);
        return query;
    }

    public void deleteCourseUserByCourse(UUID courseId) {
        var sql = "DELETE FROM public.tb_course_user WHERE course_id = :courseId";
        var query = manager.createNativeQuery(sql);
        query.setParameter("courseId", courseId);
        query.executeUpdate();
    }

    public void deleteCourseUserByUser(UUID userId) {
        var sql = "DELETE FROM public.tb_course_user WHERE user_id = :userId";
        var query = manager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
