package tech.brito.ead.course.enums;

public enum UserType {
    ADMIN, STUDENT, INSTRUCTOR;

    public boolean isStudent() {
        return STUDENT.equals(this);
    }
}
