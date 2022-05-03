package tech.brito.ead.course.enums;

public enum UserStatus {
    ACTIVE, BLOCKED;

    public boolean isBlocked() {
        return BLOCKED.equals(this);
    }
}
