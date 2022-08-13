package tech.brito.ead.course.enums;

public enum ActionType {
    CREATE, UPDATE, DELETE;

    public boolean isDelete() {
        return DELETE.equals(this);
    }
}
